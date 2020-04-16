package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.Value;

import java.util.*;

public class Mem2Reg extends Optimizer.runOnFunction {

    private boolean isPromotable(IRInst.Instruction inst) {
        for (var use : inst.getUser().user) {
            if (use instanceof IRInst.LoadInst) {
                continue;
            }
            if (use instanceof IRInst.StoreInst && use.getOperand(1) == inst) {
                continue;
            }
            return false;
        }
        return true;
    }

    private Map<String, Stack<Value>> varDef;

    private void rename(DomTree.Node cur) {
        //Rename current block
        var curBlock = cur.block;
        for (var inst : curBlock.instructions) {
            if (inst instanceof IRInst.PhiInst) {
                varDef.putIfAbsent(inst.getName(), new Stack<>());
                inst.changeName(inst.getName());
            }
            if (inst instanceof IRInst.LoadInst) {
                var t = inst.getOperand(0);
                t.replaceAllUsesWith(varDef.get(t.getName()).lastElement());
            } else if (inst instanceof IRInst.StoreInst) {
                var name = inst.getOperand(1).getName();
                varDef.putIfAbsent(name, new Stack<>());
                inst.getOperand(1).changeName(name);
            }
        }

        //Rename PhiNodes in successors on the CFG graph
        var successors = cur.block.getSuccessors();
        for (var succ : successors) {
            for (var inst : succ.instructions) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                ((IRInst.PhiInst) inst).addIncoming(varDef.get(inst.getName()).lastElement());
            }
        }

        cur.children.forEach(this::rename);

        //Delete info on stack
        for (var inst : curBlock.instructions) {
            if (inst instanceof IRInst.PhiInst || inst instanceof IRInst.StoreInst) {
                varDef.get(inst.getName()).pop();
            }
        }
    }

    @Override
    void exec(IRConstant.Function func) {
        var domTree = new DomTree(func.blocks.get(0));
        domTree.calcDominanceFrontier();

        //Find Allocas whose uses are only constituted of loads and stores
        List<IRInst.Instruction> workList = new ArrayList<>();
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof IRInst.AllocaInst) {
                    if (isPromotable(inst)) workList.add(inst);
                }
            }
        }

        //Insert phi-nodes
        for (var alloca : workList) {
            var blocks = new ArrayList<BasicBlock>();
            var vis = new HashSet<BasicBlock>();
            blocks.add(alloca.belong);
            while (!blocks.isEmpty()) {
                var block = blocks.get(0);
                vis.add(block);
                blocks.remove(0);
                var curDF = domTree.getDominanceFrontier(block);
                for (var frontier : curDF) {
                    if (vis.contains(frontier)) continue;
                    //Insert in frontier block
                    new IRInst.PhiInst(alloca.getName(), alloca.getType(), frontier);
                    //Iterative Dominance Frontier
                    blocks.add(frontier);
                }
            }
        }

        //Renaming
        rename(domTree.root);
    }
}

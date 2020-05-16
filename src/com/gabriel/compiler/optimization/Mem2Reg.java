package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.*;

import java.util.*;

public class Mem2Reg extends Optimizer.runOnFunction {
    @Override
    String print() {
        return "Mem2Reg";
    }

    private List<IRInst.Instruction> getDef(IRInst.Instruction inst) {
        var ret = new ArrayList<IRInst.Instruction>();
        ret.add(inst);
        for (var use : inst.getUser().user) {
            if (use instanceof IRInst.LoadInst) {
                continue;
            } else if (use instanceof IRInst.StoreInst && use.getOperand(0) == inst) {
                ret.add((IRInst.StoreInst) use);
                continue;
            }
            return new ArrayList<>();
        }
        return ret;
    }

    private Map<String, Stack<Value>> varDef = new HashMap<>();
    private Map<String, List<IRInst.Instruction>> workList = new HashMap<>();

    private void rename(DomTree.Node cur) {
        List<IRInst.Instruction> delList = new ArrayList<>();

        //Rename current block
        var curBlock = cur.block;
        for (var inst : curBlock.instructions) {
            if (inst instanceof IRInst.LoadInst) {
                var name = inst.getOperand(0).getName();
                if (varDef.get(name) != null && !varDef.get(name).isEmpty()) {
                    inst.replaceAllUsesWith(varDef.get(name).lastElement());
                }
                if (workList.get(name) != null) delList.add(inst);
            } else if (inst instanceof IRInst.PhiInst || inst instanceof IRInst.StoreInst) {
                var name = (inst instanceof IRInst.StoreInst) ? inst.getOperand(0).getName() : inst.getOriginalName();
                if (workList.get(name) == null) continue;
                varDef.putIfAbsent(name, new Stack<>());
                varDef.get(name).add((inst instanceof IRInst.StoreInst) ? inst.getOperand(1) : inst);
                if (inst instanceof IRInst.StoreInst) delList.add(inst);
            } else if (inst instanceof IRInst.AllocaInst) {
                if (workList.get(inst.getName()) != null) delList.add(inst);
            }
        }

        //Rename PhiNodes in successors on the CFG graph
        var successors = cur.block.getSuccessors();
        for (var succ : successors) {
            for (var inst : succ.instructions) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                var tmp = varDef.get(inst.getOriginalName());
                //tmp could only be null because it could be a local variable promoted upward to func_init
                if (tmp != null && tmp.size() > 0) ((IRInst.PhiInst) inst).addIncoming(tmp.lastElement(), curBlock);
                else ((IRInst.PhiInst) inst).addIncoming(null, curBlock);
            }
        }

        cur.children.forEach(this::rename);

        //Delete info on stack
        for (var inst : curBlock.instructions) {
            if (inst instanceof IRInst.PhiInst || inst instanceof IRInst.StoreInst) {
                var name = (inst instanceof IRInst.StoreInst) ? inst.getOperand(0).getName() : inst.getOriginalName();
                if (workList.get(name) == null) continue;
                varDef.get(name).pop();
            }
        }

        //Delete unnecessary instructions
        for (var d : delList) {
//            System.out.printf("DELETE: %s\n", d.print());
            assert d.getUser().user.size() == 0;
            d.belong.delInst(d);
        }
    }

    @Override
    void exec(IRConstant.Function func) {
        var domTree = new DomTree(func.blocks.get(0), false);

        //Find Allocas whose uses are only constituted of loads and stores
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof IRInst.AllocaInst) {
                    workList.putIfAbsent(inst.getName(), new ArrayList<>());
                    workList.get(inst.getName()).addAll(getDef(inst));
                }
            }
        }

        //Insert phi-nodes
        for (var defs : workList.values()) {
            var blocks = new ArrayList<BasicBlock>();
            defs.forEach((d) -> blocks.add(d.belong));
            var vis = new HashSet<BasicBlock>();
            while (!blocks.isEmpty()) {
                var block = blocks.get(0);
                blocks.remove(0);
                var curDF = domTree.getDominanceFrontier(block);
                for (var frontier : curDF) {
                    if (vis.contains(frontier)) continue;
                    //Insert in frontier block
                    System.err.printf("Added Phi Node in %s for %s\n", frontier, defs.get(0).getName());
                    new IRInst.PhiInst(defs.get(0).getName(), IRType.dePointer(defs.get(0).getType()), frontier);
                    //Iterative Dominance Frontier
                    blocks.add(frontier);
                    vis.add(frontier);
                }
            }
        }

        //Renaming
        rename(domTree.root);
    }
}

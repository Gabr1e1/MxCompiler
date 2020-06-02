package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MSDCE extends Optimizer.runOnFunction {
    @Override
    String print() {
        return "MSDCE";
    }

    private final List<IRInst.Instruction> workList = new ArrayList<>();
    private final Set<IRInst.Instruction> marked = new HashSet<>();
    private final Set<BasicBlock> markedBlocks = new HashSet<>();
    private DomTree rdf;

    private void init() {
        workList.clear();
        marked.clear();
        markedBlocks.clear();
    }

    private void mark(IRConstant.Function func) {
        rdf = new DomTree(func.getExitBlock(), true);

        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (IRInst.isCritical(inst)) {
                    marked.add(inst);
                    workList.add(inst);
                }
            }
        }

        while (!workList.isEmpty()) {
            var cur = workList.get(0);
//            System.err.println("MSDCE WORKLIST: "+ cur.print());

            workList.remove(0);
            markedBlocks.add(cur.belong);

            //mark preds of phi
            if (cur instanceof IRInst.PhiInst) {
                for (var block : ((IRInst.PhiInst) cur).getIncomingBlocks()) {
                    var last = block.instructions.get(block.instructions.size() - 1);
                    if (!marked.contains(last)) {
                        marked.add(last);
                        workList.add(last);
                    }
                }
            }

            //cur is live -> operands of cur is also live
            cur.operands.forEach((op) -> {
                if (op instanceof IRInst.Instruction && !marked.contains(op)) {
                    marked.add((IRInst.Instruction) op);
                    workList.add((IRInst.Instruction) op);
                }
            });

            //Post dominance frontier: those that could enter current block
            for (var block : rdf.getDominanceFrontier(cur.belong)) {
                var last = block.instructions.get(block.instructions.size() - 1);
                if (!marked.contains(last)) {
                    marked.add(last);
                    workList.add(last);
                }
            }
        }
    }

    private void sweep(IRConstant.Function func) {
        var delList = new ArrayList<IRInst.Instruction>();
        for (var block : func.blocks) {
            IRInst.Instruction originalInst = null;
            BasicBlock newInstBlock = null;
            for (var inst : block.instructions) {
                if (marked.contains(inst)) continue;
                if (inst instanceof IRInst.BranchInst) {
                    if (inst.operands.size() == 1) continue;
                    //Jump to nearest marked postdominator
                    boolean found = false;
                    for (var node = rdf.getDomNode(block).father; node != null; node = node.father) {
                        if (markedBlocks.contains(node.block)) {
                            originalInst = inst;
                            newInstBlock = node.block;
                            found = true;
                            break;
                        }
                        if (node == node.father) break;
                    }
                    assert found;
                } else {
                    //Not really necessary to delete its user & usee, if deletion is correct
                    delList.add(inst);
                }
            }
            if (originalInst != null) {
                new IRInst.BranchInst(newInstBlock, block);
                originalInst.belong.delInst(originalInst);
            }
        }
        delList.forEach((inst) -> inst.belong.delInst(inst));
    }

    @Override
    void exec(IRConstant.Function func) {
        init();
        mark(func);
        sweep(func);
    }
}

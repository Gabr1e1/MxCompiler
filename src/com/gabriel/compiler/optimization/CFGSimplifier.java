package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;

public class CFGSimplifier extends Optimizer.runOnFunction {
    @Override
    String print() {
        return "CFGSimplifier";
    }

    private CFG cfg;

    private boolean foldBranch(BasicBlock block) {
        var last = block.instructions.get(block.instructions.size() - 1);
        if (last instanceof IRInst.BranchInst) {
            if (last.operands.size() > 1 && last.operands.get(1) == last.operands.get(2)) {
                last.delOperand(last.operands.get(0), last.operands.get(1));
                return true;
            }
        }
        return false;
    }

    private boolean removeEmptyBlock(IRConstant.Function func, BasicBlock block) {
        var last = block.instructions.get(block.instructions.size() - 1);
        var succ = (BasicBlock) last.operands.get(0);
        if (block == succ) return false;
        if (block.isEntry()) return false;

        if (block.instructions.size() == 1) {
            var pred = cfg.getPredecessors(block);
            //special case: entry block
            if (pred.size() == 0 && cfg.getPredecessors(succ).size() != 1) return false;
            //PhiInst contains already one of pred, merging these two blocks introduces ambiguity

            //TODO: Might be able to remove after all
            for (var inst : succ.instructions)
                if (inst instanceof IRInst.PhiInst)
                    if (inst.operands.stream().anyMatch(pred::contains)) return false;

            for (var inst : succ.instructions) {
                if (inst instanceof IRInst.PhiInst) {
//                    if (!inst.operands.contains(block)) continue;
//                    System.err.printf("Before: %s\n", inst.print());
//                    System.err.println(block.getName());

                    int i = inst.operands.indexOf(block);
                    if (i == -1) continue;

                    var v = inst.operands.get(i - 1);
                    pred.forEach((b) -> ((IRInst.PhiInst) inst).addIncoming(v, b));

                    while (inst.operands.contains(block)) {
                        inst.delOperand(inst.operands.indexOf(block) - 1);
                        inst.delOperand(block);
                    }

//                    System.err.printf("After: %s\n", inst.print());
                }
            }
            block.replaceAllUsesWith(succ);
            func.delBlock(block);
            return true;
        }
        return false;
    }

    private boolean mergeBlocks(IRConstant.Function func, BasicBlock block) {
        var last = block.instructions.get(block.instructions.size() - 1);
        var succ = (BasicBlock) last.operands.get(0);
        if (block == succ) return false;

        if (cfg.getPredecessors(succ).size() == 1) {
            for (int i = block.instructions.size() - 1; i >= 0; i--) {
                var inst = block.instructions.get(i);
                if (inst instanceof IRInst.BranchInst) continue;
                inst.belong = succ;
                succ.addInstToFront(inst);
            }
            if (block.isEntry()) succ.setAsEntry();
            block.replaceAllUsesWith(succ);
            func.delBlock(block);
            succ.hoistPhiInst();
            return true;
        }
        return false;
    }

    private boolean hoistBranch(IRConstant.Function func, BasicBlock block) {
        var last = block.instructions.get(block.instructions.size() - 1);
        var succ = (BasicBlock) last.operands.get(0);
        if (block == succ) return false;

        if (succ.instructions.size() == 1) {
            var succInst = succ.instructions.get(0);
            if (succInst instanceof IRInst.BranchInst) {
                for (var op : succInst.operands) {
                    if (op == succ) return false;
                }
                if (succInst.operands.size() == 2) {
                    block.delInst(last);
                    new IRInst.BranchInst(succInst.operands.get(0), (BasicBlock) succInst.operands.get(1),
                            (BasicBlock) succInst.operands.get(2), block);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onePass(IRConstant.Function func) {
        cfg = new CFG(func.getEntryBlock(), false);
        for (var block : cfg.getPostOrder()) {
            //Conditional Branch with same target
            if (foldBranch(block)) return true;

            //Ends in a direct jump
            var last = block.instructions.get(block.instructions.size() - 1);
            if (last instanceof IRInst.BranchInst && last.operands.size() == 1) {
                //block with only a branch
                if (removeEmptyBlock(func, block)) return true;

                //merge two blocks
                if (mergeBlocks(func, block)) return true;

                //hoist Branch
                if (hoistBranch(func, block)) return true;
            }
        }
        return false;
    }

    @Override
    void exec(IRConstant.Function func) {
        // Iterate till no change
        int cnt = 0;
        while (func.isNormal()) {
//            IRPrinter irCodeGen = new IRPrinter("./testcases/mycode_opt" + cnt + ".ll");
//            irCodeGen.visit(func.belong);
            if (!onePass(func)) break;
            cnt++;
        }
    }
}

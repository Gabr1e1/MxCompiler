package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.Value;
import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SCCP extends Optimizer.runOnFunction {

    Set<Pair<BasicBlock, BasicBlock>> visitedCFGEdges = new HashSet<>();
    List<Pair<BasicBlock, BasicBlock>> CFGWorkList = new ArrayList<>();
    Set<BasicBlock> visited = new HashSet<>();
    List<Pair<IRInst.Instruction, IRInst.Instruction>> SSAWorkList = new ArrayList<>();

    private void init() {
        visitedCFGEdges.clear();
        CFGWorkList.clear();
        visited.clear();
        SSAWorkList.clear();
    }

    private void evalPhi(IRInst.PhiInst phi) {
        var value = new Value.ConditionalLattice();

        for (int i = 0; i < phi.operands.size(); i += 2) {
            var val = phi.operands.get(i);
            if (val == null) continue;
            var block = (BasicBlock) phi.operands.get(i + 1);
            if (visitedCFGEdges.contains(new Pair<>(block, phi.belong))) {
                value = Value.ConditionalLattice.and(value, val.lattice);
            }
        }

        phi.lattice = value;
    }

    private void evalConditional(IRInst.BranchInst inst) {
        var cond = inst.operands.get(0);
        if (cond.lattice.isLower()) {
            CFGWorkList.add(new Pair<>(inst.belong, (BasicBlock) inst.operands.get(1)));
            CFGWorkList.add(new Pair<>(inst.belong, (BasicBlock) inst.operands.get(2)));
        } else if (cond.lattice.isConst()) {
            if (cond.lattice.value == 0) { //Condition is always false
                CFGWorkList.add(new Pair<>(inst.belong, (BasicBlock) inst.operands.get(2)));
            } else {
                CFGWorkList.add(new Pair<>(inst.belong, (BasicBlock) inst.operands.get(1)));
            }
        }
    }


    private void visitInst(IRInst.Instruction inst) {
        var orig = new Value.ConditionalLattice(inst.lattice);

        if (inst instanceof IRInst.PhiInst) {
            evalPhi((IRInst.PhiInst) inst);
        } else if (inst instanceof IRInst.BranchInst && ((IRInst.BranchInst) inst).isConditional()) {
            evalConditional((IRInst.BranchInst) inst);
            return;
        } else {
            inst.propagate();
        }

        if (!orig.equals(inst.lattice)) {
//            System.err.printf("%s changed from %s to %s\n", inst.getName(), orig.type, inst.lattice.type);
            for (var user : inst.getUser()) {
                assert user instanceof IRInst.Instruction;
                SSAWorkList.add(new Pair<>(inst, (IRInst.Instruction) user));
            }
        }
    }

    private void rewrite(IRConstant.Function func) {
        //rewrite inst except for inst
        var delList = new ArrayList<IRInst.Instruction>();
        for (var block : new ArrayList<>(func.blocks)) {
            for (var inst : new ArrayList<>(block.instructions)) {
                if (inst instanceof IRInst.PhiInst) continue;
                inst.rewrite();

                if (inst.lattice.isConst()) {
                    delList.add(inst);
//                    System.err.printf("%s is always %d\n", inst.print(), inst.lattice.value);
                }
            }
        }

//        rewrite phi inst
        for (var block : new ArrayList<>(func.blocks)) {
            for (var inst : new ArrayList<>(block.instructions)) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                inst.rewrite();
                if (inst.operands.size() == 2) {
                    inst.replaceAllUsesWith(inst.operands.get(0));
                    System.err.printf("Replace %s with %s\n", inst.print(), inst.operands.get(0));
                    delList.add(inst);
                }
            }
        }

        delList.forEach(inst -> inst.belong.delInst(inst));
    }

    @Override
    void exec(IRConstant.Function func) {
        init();
        var entryBlock = func.getEntryBlock();
        CFGWorkList.add(new Pair<>(null, entryBlock));

        while ((!CFGWorkList.isEmpty()) || !SSAWorkList.isEmpty()) {
            if (!CFGWorkList.isEmpty()) {
                var e = CFGWorkList.remove(0);
                if (visitedCFGEdges.contains(e)) continue;
//                System.err.printf("Visiting block %s\n", e.second.getName());

                visitedCFGEdges.add(e);

                for (var inst : e.second.instructions) {
                    if (inst instanceof IRInst.PhiInst) visitInst(inst);
                }

                if (visited.contains(e.second)) continue;
                visited.add(e.second);

                for (var inst : e.second.instructions) {
                    visitInst(inst);
                }
                for (var succ : e.second.getSuccessors())
                    CFGWorkList.add(new Pair<>(e.second, succ));
            }

            if (!SSAWorkList.isEmpty()) {
                var e = SSAWorkList.remove(0);
                var dest = e.second;
//                System.err.printf("Visiting Value %s\n", dest.getName());
                if (!visited.contains(dest.belong)) continue;
                visitInst(dest);
            }
        }

        rewrite(func);
    }

    @Override
    String print() {
        return "Sparse Conditional Constant Propagation";
    }
}

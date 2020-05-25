package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock extends Value {
    public List<IRInst.Instruction> instructions = new ArrayList<>();
    public IRConstant.Function belong;

    public BasicBlock(String name, IRConstant.Function function) {
        super(name, new IRType.LabelType());
        this.belong = function;
        function.addBlock(this);
    }

    @Override
    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    public void addInst(IRInst.Instruction inst) {
        instructions.add(inst);
        inst.belong = this;
    }

    public void addInstToFront(IRInst.Instruction inst) {
        instructions.add(0, inst);
    }

    public void delInst(IRInst.Instruction inst) {
//        System.err.println("DELETED: " + inst.print());
        inst.delete();
        instructions.remove(inst);
    }

    boolean hasTerminator() {
        for (var inst : instructions) {
            if (IRInst.isTerminator(inst)) return true;
        }
        return false;
    }

    public boolean hasPhiInst() {
        for (var inst : instructions) {
            if (inst instanceof IRInst.PhiInst) return true;
        }
        return false;
    }

    public void reorder() {
        //Put terminator at the end
        for (int i = 0; i < instructions.size(); i++) {
            var inst = instructions.get(i);
            if (IRInst.isTerminator(inst) && i != instructions.size() - 1) {
                instructions.remove(i);
                instructions.add(inst);
                break;
            }
        }
    }

    public List<BasicBlock> getSuccessors() {
        List<BasicBlock> successors = new ArrayList<>();
        for (var inst : instructions) {
            if (inst instanceof IRInst.BranchInst) {
                if (inst.operands.size() == 1) { //Direct jump
                    successors.add((BasicBlock) inst.operands.get(0));
                } else {
                    successors.add((BasicBlock) inst.operands.get(1));
                    successors.add((BasicBlock) inst.operands.get(2));
                }
            }
        }
        return successors;
    }

    public void hoistPhiInst() {
        boolean start = true;
        var hoistList = new ArrayList<IRInst.Instruction>();
        for (var inst : instructions) {
            if (!(inst instanceof IRInst.PhiInst)) start = false;
            if (inst instanceof IRInst.PhiInst && !start) {
                hoistList.add(inst);
            }
        }
        hoistList.forEach(this::delInst);
        hoistList.forEach(this::addInstToFront);
    }

    public void redirectJump(BasicBlock oldTarget, BasicBlock newTarget) {
        if (oldTarget == null) {
            new IRInst.BranchInst(newTarget, this);
            return;
        }

        for (var inst : instructions) {
            if (!IRInst.isTerminator(inst)) continue;
            assert inst instanceof IRInst.BranchInst;
            inst.replaceOperand(oldTarget, newTarget);
            break;
        }
    }

    public void delAllPhiInst() {
        for (var iter = instructions.iterator(); iter.hasNext(); ) {
            var inst = iter.next();
            if (inst instanceof IRInst.PhiInst) {
                //Stupid Java only supports this method of removal?
                inst.delete();
                iter.remove();
            }
        }
    }

    public IRInst.Instruction getParallelCopy() {
        var ret = instructions.stream().filter(inst -> inst instanceof IRInst.ParallelCopy)
                .findFirst().orElse(null);
        if (ret != null) ((IRInst.ParallelCopy) ret).organize();
        return ret;
    }
}

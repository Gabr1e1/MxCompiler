package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock extends Value {
    public List<IRInst.Instruction> instructions = new ArrayList<>();
    public IRConstant.Function belong;

    BasicBlock(String name, IRConstant.Function function) {
        super(name, new IRType.LabelType());
        this.belong = function;
        function.addBlock(this);
    }

    @Override
    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    void addInst(IRInst.Instruction inst) {
        instructions.add(inst);
    }

    void addInstToFront(IRInst.Instruction inst) {
        instructions.add(0, inst);
    }

    public void delInst(IRInst.Instruction inst) {
        instructions.remove(inst);
    }

    boolean hasTerminator() {
        for (var inst : instructions) {
            if (IRInst.isTerminator(inst)) return true;
        }
        return false;
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
}

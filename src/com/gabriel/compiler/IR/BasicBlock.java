package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock extends Value {
    List<Instruction> instructions = new ArrayList<>();

    BasicBlock(String name, IRConstant.Function function) {
        super(name, new IRType.LabelType());
        function.addBlock(this);
    }

    @Override
    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    void addInst(Instruction inst) {
        instructions.add(inst);
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

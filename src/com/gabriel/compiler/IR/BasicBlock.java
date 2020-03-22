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
}

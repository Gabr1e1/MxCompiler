package com.gabriel.compiler.IR;

import java.util.List;

public class BasicBlock extends Value {
    List<Instruction> instructions;

    BasicBlock(String name, IRConstant.Function function) {
        super(name, new IRType.LabelType());
        function.addBlock(this);
    }

    void addInst(Instruction inst) {
        instructions.add(inst);
    }
}

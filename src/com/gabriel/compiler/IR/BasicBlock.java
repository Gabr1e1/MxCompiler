package com.gabriel.compiler.IR;

import java.util.List;

public class BasicBlock extends Value {
    List<Instruction> instructions;

    BasicBlock(String name, Type type) {
        super(name, type);
    }

    void addInst(Instruction inst) {
        instructions.add(inst);
    }
}

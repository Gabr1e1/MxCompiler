package com.gabriel.compiler.IR;

abstract class Instruction extends User {
    BasicBlock parent;

    Instruction(String name, Type type) {
        super(name, type);
    }

}

public class IRInst {

}

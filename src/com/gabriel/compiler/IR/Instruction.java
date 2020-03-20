package com.gabriel.compiler.IR;

abstract class Instruction extends User {
    enum OpType {ADD, SUB, MUL, DIV}

    BasicBlock parent;

    Instruction(String name, Type type, BasicBlock basicBlock) {
        super(name, type);
        parent = basicBlock;
        basicBlock.addInst(this);
    }

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }
}

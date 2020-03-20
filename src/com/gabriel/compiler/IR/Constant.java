package com.gabriel.compiler.IR;

abstract class Constant extends User {
    Constant(String name, Type type) {
        super(name, type);
    }

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }
}

package com.gabriel.compiler.IR;

abstract class Type {
    public String baseType;
    public int bitLen;

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }
}

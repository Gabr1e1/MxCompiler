package com.gabriel.compiler.IR;

abstract class Type {
    public String baseType;
    public int bitLen;

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    public int getByteNum() {
        return bitLen / 8 + (bitLen % 8 != 0 ? 1 : 0);
    }
}

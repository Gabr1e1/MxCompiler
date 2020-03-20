package com.gabriel.compiler.IR;

import java.util.List;

public class IRConstant {
    public static class ConstInteger extends Constant {
        int num;

        ConstInteger(int num) {
            super("const", new IRType.IntegerType("int"));
            this.num = num;
        }

        ConstInteger(int num, String type) {
            super("const", new IRType.IntegerType(type));
            this.num = num;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ConstString extends Constant {
        String str;

        ConstString(String str) {
            super("const", new IRType.IntegerType("string"));
            this.str = str;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Null extends Constant {
        Null() {
            super("null", new IRType.VoidType());
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Void extends Constant {
        Void() {
            super("void", new IRType.VoidType());
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GlobalVariable extends Constant {
        Value Initialization;

        GlobalVariable(String name, Type type, Value initialization) {
            super(name, type);
            this.Initialization = initialization;
            addOperand(initialization);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Function extends Constant {
        List<BasicBlock> blocks;

        Function(String name, Type type) {
            super(name, type);
        }

        void addBlock(BasicBlock block) {
            blocks.add(block);
        }

        Value getParam(int i) {
            return ((IRType.FunctionType) type).params.get(i);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}



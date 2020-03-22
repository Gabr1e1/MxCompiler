package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

abstract class Constant extends User {
    Constant(String name, Type type) {
        super(name, type);
    }
}

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

        @Override
        public String toString() {
            IRPrinter t = new IRPrinter();
            return (String) this.accept(t);
        }

        @Override
        String getPrintName() {
            return "" + num;
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
        List<BasicBlock> blocks = new ArrayList<>();

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



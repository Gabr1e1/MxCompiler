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

        ConstInteger(int num, Type type) {
            super("const", type);
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

    //Probably don't need this
    public static class Null extends Constant {
        Type type;

        Null(Type type) {
            super("_", type);
            this.type = type;
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

        @Override
        public String toString() {
            return "void";
        }
    }

    public static class GlobalVariable extends Constant {
        Value init;

        GlobalVariable(String name, Type type, Value init) {
            super(name, new IRType.PointerType(type));
            this.init = init;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        String getPrintName() {
            return "@" + name;
        }

        @Override
        public String toString() {
            IRPrinter t = new IRPrinter();
            return type.accept(t) + " @" + name;
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



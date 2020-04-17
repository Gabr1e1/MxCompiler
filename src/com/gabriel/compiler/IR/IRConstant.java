package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

abstract class Constant extends User {
    Constant(String name, IRType.Type type) {
        super(name, type);
    }
}

public class IRConstant {
    public static class ConstInteger extends Constant {
        int num;

        public ConstInteger(int num) {
            super("const", new IRType.IntegerType("int"));
            this.num = num;
        }

        public ConstInteger(int num, String type) {
            super("const", new IRType.IntegerType(type));
            this.num = num;
        }

        public ConstInteger(int num, IRType.Type type) {
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
        public String getPrintName() {
            return "" + num;
        }
    }

    public static class ConstString extends Constant {
        IRType.Type type;
        String str;

        ConstString(String str, IRType.Type type) {
            super(".conststr", new IRType.PointerType(type));
            this.str = str;
            this.type = type;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        public String getPrintName() {
            return "@" + name;
        }

        @Override
        public String toString() {
            IRPrinter t = new IRPrinter();
            return type.accept(t) + " @" + name;
        }
    }

    //Probably don't need this
    public static class Null extends Constant {
        IRType.Type type;

        Null(IRType.Type type) {
            super("_", type);
            this.type = type;
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
        public String getPrintName() {
            return "null";
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
        GlobalVariable(String name, IRType.Type type, Value init) {
            super(name, new IRType.PointerType(type));
            addOperand(init);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        public String getPrintName() {
            return "@" + name;
        }

        @Override
        public String toString() {
            IRPrinter t = new IRPrinter();
            return type.accept(t) + " @" + name;
        }
    }

    public static class Function extends Constant {
        public List<BasicBlock> blocks = new ArrayList<>();

        Function(String name, IRType.Type type) {
            super(name, type);
        }

        public void addBlock(BasicBlock block) {
            blocks.add(block);
        }

        public void delBlock(BasicBlock block) {
            blocks.remove(block);
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



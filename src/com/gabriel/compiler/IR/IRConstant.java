package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class IRConstant {
    public abstract static class Constant extends User {
        Constant(String name, IRType.Type type) {
            super(name, type);
        }
    }

    public static class ConstInteger extends Constant {
        public int num;

        public ConstInteger(int num) {
            super("const", new IRType.IntegerType("int"));
            this.num = num;
            this.lattice.setConst(num);
        }

        public ConstInteger(int num, String type) {
            super("const", new IRType.IntegerType(type));
            this.num = num;
            this.lattice.setConst(num);
        }

        public ConstInteger(int num, IRType.Type type) {
            super("const", type);
            this.num = num;
            this.lattice.setConst(num);
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
        public String str;
        public String orig;

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

    public static class Null extends Constant {
        IRType.Type type;

        Null(IRType.Type type) {
            super("_", type);
            this.type = type;
            if (this.type == null) this.type = new IRType.IntegerType("int");
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
        public Module belong;
        public boolean builtin = false;

        public Function(String name, IRType.Type type) {
            super(name, type);
        }

        // abnormal functions could also be optimized
        public boolean isNormal() {
//            if (blocks.size() == 1) return true;
//            if (!blocks.get(0).getOriginalName().equals("func_init")) return false;
//            if (!blocks.get(blocks.size() - 1).getOriginalName().equals("retBlock")) return false;
            return true;
        }

        public void addBlock(BasicBlock block) {
            if (blocks.isEmpty()) block.setAsEntry();
            blocks.add(block);
            getEntryBlock();
        }

        public void delBlock(BasicBlock block) {
            blocks.remove(block);
            getEntryBlock();
        }

        public int getParamNum() {
            return ((IRType.FunctionType) type).params.size();
        }

        public Value getParam(int i) {
            return ((IRType.FunctionType) type).params.get(i);
        }

        public boolean ableToInline() {
            return isNormal() && !getName().equals("main") && !getName().equals("__global_init");
        }

        public int getInstCount() {
            int sum = 0;
            for (var block : blocks) sum += block.instructions.size();
            return sum;
        }

        public BasicBlock getEntryBlock() {
            if (blocks.size() == 1) blocks.get(0).setAsEntry();
            BasicBlock ret = null;
            for (var block : blocks)
                if (block.isEntry()) {
                    ret = block; break;
                }
            assert ret != null;
            blocks.remove(ret); blocks.add(0, ret);
            return ret;
        }

        public BasicBlock getExitBlock() {
            for (var block : blocks) {
                for (var inst : block.instructions) {
                    if (inst instanceof IRInst.ReturnInst) return block;
                }
            }
            assert false;
            return null;
        }

        public void setAsBuiltin() {
            this.builtin = true;
        }

        public boolean mayHaveSideEffects() {
            if (builtin) return true;
            for (var block : blocks) {
                for (var inst : block.instructions) {
                    if (IRInst.mayHaveSideEffects(inst)) return true;
                }
            }
            return false;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}



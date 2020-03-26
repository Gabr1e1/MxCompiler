package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class Instruction extends User {
    //    enum OpType {ADD, SUB, MUL, DIV}
    BasicBlock parent;

    Instruction(String name, Type type, BasicBlock basicBlock) {
        super(name, type);
        parent = basicBlock;
        basicBlock.addInst(this);
    }
}

public class IRInst {
    public static boolean isTerminator(Instruction i) {
        return i instanceof BranchInst || i instanceof ReturnInst;
    }

    public static class AllocaInst extends Instruction {
        AllocaInst(String id, Type type, BasicBlock belong) {
            super(id, new IRType.PointerType(type), belong);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class BranchInst extends Instruction {
        Value cond; //null if always taken
        BasicBlock taken, notTaken;

        BranchInst(Value cond, BasicBlock belong, BasicBlock taken, BasicBlock notTaken) {
            super("", new IRType.VoidType(), belong);
            this.cond = cond;
            this.taken = taken;
            this.notTaken = notTaken;
        }

        BranchInst(BasicBlock jump, BasicBlock belong) {
            super("", new IRType.VoidType(), belong);
            this.cond = null;
            this.taken = jump;
            this.notTaken = null;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ReturnInst extends Instruction {
        Value v;

        ReturnInst(Value v, BasicBlock belong) {
            super("", v.type, belong);
            this.v = v;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class BinaryOpInst extends Instruction {
        private static Map<String, String> OpMap = Map.ofEntries(Map.entry("+", "add"), Map.entry("-", "sub"),
                Map.entry("*", "mul"), Map.entry("/", "sdiv"), Map.entry("%", "srem"),
                Map.entry("<<", "shl"), Map.entry(">>", "ashr"),
                Map.entry("&", "and"), Map.entry("|", "or"), Map.entry("^", "xor"),
                Map.entry("&&", "and"), Map.entry("||", "or"));
        Value lhs, rhs;
        String op;

        BinaryOpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", lhs.type, belong);
            this.lhs = lhs;
            this.rhs = rhs;
            this.op = op; //OpMap.get(op);
        }

        String getCorresOp() {
            return OpMap.get(op);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    private static void constantConvert(Value a, Value b) {
        if (a instanceof IRConstant.ConstInteger) {
            if (b.type instanceof IRType.PointerType) a.type.bitLen = ((IRType.PointerType) b.type).pointer.bitLen;
            else a.type.bitLen = b.type.bitLen;
        }

        if (b instanceof IRConstant.ConstInteger) {
            if (a.type instanceof IRType.PointerType) b.type.bitLen = ((IRType.PointerType) a.type).pointer.bitLen;
            else b.type.bitLen = a.type.bitLen;
        }
    }

    public static class CmpInst extends Instruction {
        private static Map<String, String> OpMap = Map.of("<", "slt", "<=", "sle", ">", "sgt", ">=", "sge",
                "==", "eq", "!=", "neq");
        Value lhs, rhs;
        String op;

        CmpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", new IRType.IntegerType(1), belong);
            this.lhs = lhs;
            this.rhs = rhs;
            this.op = op;
//            constantConvert(lhs, rhs);
        }

        String getCorresOp() {
            return OpMap.get(op);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class StoreInst extends Instruction {
        Value dest, from;

        StoreInst(Value dest, Value from, BasicBlock belong) {
            super("", dest.type, belong);
            this.dest = dest;
            this.from = from;
//            constantConvert(dest, from);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GEPInst extends Instruction {
        List<Value> operands = new ArrayList<>();
        Value base;
        Type valueType;

        GEPInst(Type valueType, Value base, BasicBlock belong) {
            super("T", new IRType.PointerType(valueType), belong);
            this.base = base;
            this.valueType = valueType;
            operands.add(new IRConstant.ConstInteger(0));
        }

        GEPInst(Type valueType, Value base, BasicBlock belong, boolean zeroPad) {
            super("T", new IRType.PointerType(valueType), belong);
            this.base = base;
            this.valueType = valueType;
            if (zeroPad) operands.add(new IRConstant.ConstInteger(0));
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        void addOperand(Value v) {
            operands.add(v);
        }

        Value getOperand(int i) {
            return operands.get(i);
        }

        Value getBase() {
            return base;
        }
    }

    public static class CallInst extends Instruction {
        IRConstant.Function func;
        List<Value> args;

        CallInst(IRConstant.Function func, List<Value> args, BasicBlock belong) {
            super("call_" + func.name, ((IRType.FunctionType) func.type).returnType, belong);
            this.func = func;
            this.args = args;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class LoadInst extends Instruction {
        Value ptr;

        LoadInst(Value ptr, BasicBlock belong) {
            super("load_" + ptr.getOrignalName(), ((IRType.PointerType) ptr.type).pointer, belong);
            this.ptr = ptr;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class CastInst extends Instruction {
        Value from;
        Type to;

        CastInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            this.from = from;
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class SextInst extends Instruction {
        Value from;
        Type to;

        SextInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            this.from = from;
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class TruncInst extends Instruction {
        Value from;
        Type to;

        TruncInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            this.from = from;
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

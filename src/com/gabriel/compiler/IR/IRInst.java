package com.gabriel.compiler.IR;

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
        BranchInst(Value cond, BasicBlock taken, BasicBlock notTaken, BasicBlock belong) {
            super("", new IRType.VoidType(), belong);
            addOperand(cond, taken, notTaken);
        }

        BranchInst(BasicBlock jump, BasicBlock belong) {
            super("", new IRType.VoidType(), belong);
            addOperand(jump);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ReturnInst extends Instruction {
        ReturnInst(Value v, BasicBlock belong) {
            super("", v.type, belong);
            addOperand(v);
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
        String op;

        BinaryOpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", lhs.type, belong);
            addOperand(lhs, rhs);
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
        static Map<String, String> OpMap = Map.of("<", "slt", "<=", "sle", ">", "sgt", ">=", "sge",
                "==", "eq", "!=", "ne");
        String op;

        CmpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", new IRType.IntegerType(1), belong);
            addOperand(lhs, rhs);
            this.op = op;
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
        StoreInst(Value dest, Value from, BasicBlock belong) {
            super("", dest.type, belong);
            addOperand(dest, from);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GEPInst extends Instruction {
        Type valueType;

        GEPInst(Type valueType, Value base, BasicBlock belong) {
            super("T", new IRType.PointerType(valueType), belong);
            addOperand(base);
            this.valueType = valueType;
            operands.add(new IRConstant.ConstInteger(0));
        }

        GEPInst(Type valueType, Value base, BasicBlock belong, boolean zeroPad) {
            super("T", new IRType.PointerType(valueType), belong);
            addOperand(base);
            this.valueType = valueType;
            if (zeroPad) operands.add(new IRConstant.ConstInteger(0));
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        Value getBase() {
            return getOperand(0);
        }
    }

    public static class CallInst extends Instruction {

        CallInst(IRConstant.Function func, List<Value> args, BasicBlock belong) {
            super("call_" + func.name, ((IRType.FunctionType) func.type).returnType, belong);
            addOperand(func);
            addOperand(args);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class LoadInst extends Instruction {
        LoadInst(Value ptr, BasicBlock belong) {
            super("load_" + ptr.getOrignalName(), ((IRType.PointerType) ptr.type).pointer, belong);
            addOperand(ptr);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class CastInst extends Instruction {
        Type to;

        CastInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            addOperand(from);
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class SextInst extends Instruction {
        Type to;

        SextInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            addOperand(from);
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class TruncInst extends Instruction {
        Type to;

        TruncInst(Value from, Type to, BasicBlock belong) {
            super("M", to, belong);
            addOperand(from);
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

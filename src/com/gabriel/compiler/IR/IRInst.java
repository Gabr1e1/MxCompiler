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

        BranchInst(BasicBlock belong, BasicBlock jump) {
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
        private static Map<String, String> OpMap = Map.of("+", "add", "-", "sub", "*", "mul", "/", "sdiv", "%", "srem",
                "<<", "shl", ">>", "ashr", "&", "and", "|", "or", "^", "xor");
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

    public static class CmpInst extends Instruction {
        private static Map<String, String> OpMap = Map.of("<", "slt", "<=", "sle", ">", "sgt", ">=", "sge",
                "==", "eq", "!=", "neq");
        Value lhs, rhs;
        String op;

        CmpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", new IRType.IntegerType("bool"), belong);
            this.lhs = lhs;
            this.rhs = rhs;
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
        Value dest, from;

        StoreInst(Value dest, Value from, BasicBlock belong) {
            super("", dest.type, belong);
            this.dest = dest;
            this.from = from;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GEPInst extends Instruction {
        List<Value> operands;
        Value base;

        GEPInst(Type type, Value base, BasicBlock belong) {
            super("T", type, belong);
            this.base = base;
            operands.add(new IRConstant.ConstInteger(0));
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
        Type from, to;

        CastInst(Type from, Type to, BasicBlock belong) {
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

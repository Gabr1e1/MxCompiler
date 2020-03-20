package com.gabriel.compiler.IR;

import java.util.List;

public class IRInst {
    public static class AllocaInst extends Instruction {
        AllocaInst(String name, Type type, BasicBlock belong) {
            super(name, new IRType.PointerType(type), belong);
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
        //private static Map<String, OpType> OpMap = Map.of("+", OpType.ADD, "-", OpType.SUB, "*", OpType.MUL, "/", OpType.DIV, "%", Op);
        Value lhs, rhs;
        String op;

        BinaryOpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super(op, lhs.type, belong);
            this.lhs = lhs;
            this.rhs = rhs;
            this.op = op; //OpMap.get(op);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class CmpInst extends Instruction {
        Value lhs, rhs;
        String op;

        CmpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super(op, lhs.type, belong);
            this.lhs = lhs;
            this.rhs = rhs;
            this.op = op;
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

        GEPInst(Type type, Value base, BasicBlock belong) {
            super("", type, belong);
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
            return operands.get(0);
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
}

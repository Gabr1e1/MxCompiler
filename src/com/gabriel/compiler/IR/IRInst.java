package com.gabriel.compiler.IR;

import com.gabriel.compiler.optimization.CFG;
import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IRInst {
    public abstract static class Instruction extends User implements Cloneable {
        public BasicBlock belong;

        Instruction(String name, IRType.Type type, BasicBlock basicBlock) {
            super(name, type);
            belong = basicBlock;
            basicBlock.addInst(this);
            this.lattice.setLower(); //upper only for binary & cmp
        }

        Instruction(String name, IRType.Type type, BasicBlock basicBlock, boolean front) {
            super(name, type);
            belong = basicBlock;
            if (!front) basicBlock.addInst(this);
            else basicBlock.addInstToFront(this);
            this.lattice.setLower();
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public String print() {
            IRPrinter t = new IRPrinter();
            return (String) this.accept(t);
        }

        public void delete() {
            delOperand(operands.toArray(new Value[0]));
            delOperand(this);
        }

        public void propagate() {
            /* EMPTY */
        }

        public boolean isCommutative() {
            return false;
        }
    }

    public static boolean isTerminator(Instruction i) {
        return i instanceof BranchInst || i instanceof ReturnInst;
    }

    public static boolean mayHaveSideEffects(Instruction i) {
        return i instanceof CallInst || i instanceof StoreInst;
    }

    public static boolean isCritical(Instruction i) {
        return i instanceof ReturnInst || mayHaveSideEffects(i);
    }

    public static class AllocaInst extends Instruction {
        public AllocaInst(String id, IRType.Type type, BasicBlock belong) {
            super(id, new IRType.PointerType(type), belong);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class BranchInst extends Instruction {
        public BranchInst(Value cond, BasicBlock taken, BasicBlock notTaken, BasicBlock belong) {
            super("", new IRType.VoidType(), belong);
            addOperand(cond, taken, notTaken);
        }

        public BranchInst(BasicBlock jump, BasicBlock belong) {
            super("", new IRType.VoidType(), belong);
            addOperand(jump);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        public void rewrite() {
            super.rewrite();
            if (operands.size() == 1) return;
            if (!operands.get(0).lattice.isConst()) return;
            System.err.printf("Rewriting Jump %s", this.print());

            if (operands.get(0).lattice.value == 0) {
                delOperand(1);
            } else {
                delOperand(2);
            }
            delOperand(0);

            System.err.printf(" to %s\n", this.print());
        }

        public boolean isConditional() {
            return operands.size() > 1;
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
        private static final Map<String, String> OpMap = Map.ofEntries(Map.entry("+", "add"), Map.entry("-", "sub"),
                Map.entry("*", "mul"), Map.entry("/", "sdiv"), Map.entry("%", "srem"),
                Map.entry("<<", "shl"), Map.entry(">>", "ashr"),
                Map.entry("&", "and"), Map.entry("|", "or"), Map.entry("^", "xor"),
                Map.entry("&&", "and"), Map.entry("||", "or"));
        private static final Map<String, String> AsmMap = Map.ofEntries(Map.entry("+", "add"), Map.entry("-", "sub"),
                Map.entry("*", "mul"), Map.entry("/", "div"), Map.entry("%", "rem"),
                Map.entry("<<", "sll"), Map.entry(">>", "sra"),
                Map.entry("&", "and"), Map.entry("|", "or"), Map.entry("^", "xor"),
                Map.entry("&&", "and"), Map.entry("||", "or"));

        public String op;

        BinaryOpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", lhs.type, belong);
            addOperand(lhs, rhs);
            this.op = op; //OpMap.get(op);
            this.lattice.setUpper();
        }

        String getCorresOp() {
            return OpMap.get(op);
        }

        public String getCorresAsmOp() {
            return AsmMap.get(op);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        public void propagate() {
            Value lhs = operands.get(0), rhs = operands.get(1);
            if (lhs.lattice.isLower() || rhs.lattice.isLower()) this.lattice.setLower();
            else if (rhs.lattice.isUpper() || lhs.lattice.isUpper()) this.lattice.setUpper();
            else {
                // Both are constant
                int a = lhs.lattice.value, b = rhs.lattice.value;
                Integer c = null;
                switch (op) {
                    case "+":
                        c = a + b;
                        break;
                    case "-":
                        c = a - b;
                        break;
                    case "*":
                        c = a * b;
                        break;
                    case "/":
                        if (b != 0) c = a / b;
                        break;
                    case "%":
                        if (b != 0) c = a % b;
                        break;
                    case "<<":
                        c = a << b;
                        break;
                    case ">>":
                        c = a >> b;
                        break;
                    case "&":
                        c = a & b;
                        break;
                    case "|":
                        c = a | b;
                        break;
                    case "^":
                        c = a ^ b;
                        break;
                    case "&&":
                        c = ((a > 0) && (b > 0)) ? 1 : 0;
                        break;
                    case "||":
                        c = ((a > 0) || (b > 0)) ? 1 : 0;
                        break;
                    default:
                        assert false;
                }
                if (c != null) this.lattice.setConst(c);
                else this.lattice.setLower();
//                System.err.printf("%s is constant %d\n", this.print(), c);
            }
        }

        private static final Set<String> commutative = Set.of("+", "-", "*", "&", "|", "^", "&&", "||");

        @Override
        public boolean isCommutative() {
            return commutative.contains(op);
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
        static Map<String, String> AsmMap = Map.of("<", "blt", "<=", "ble", ">", "bgt", ">=", "bge",
                "==", "beq", "!=", "bne");
        public String op;

        CmpInst(Value lhs, Value rhs, String op, BasicBlock belong) {
            super("T", new IRType.IntegerType(1), belong);
            addOperand(lhs, rhs);
            this.op = op;
            this.lattice.setUpper();
        }

        String getCorresOp() {
            return OpMap.get(op);
        }

        public String getCorresAsmOp() {
            return AsmMap.get(op);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        @Override
        public void propagate() {
            Value lhs = operands.get(0), rhs = operands.get(1);
            if (lhs.lattice.isLower() || rhs.lattice.isLower()) this.lattice.setLower();
            else if (rhs.lattice.isUpper() || lhs.lattice.isUpper()) this.lattice.setUpper();
            else {
                // Both are constant
                int a = lhs.lattice.value, b = rhs.lattice.value;
                boolean c;
                switch (op) {
                    case "<":
                        c = (a < b);
                        break;
                    case "<=":
                        c = (a <= b);
                        break;
                    case ">":
                        c = (a > b);
                        break;
                    case ">=":
                        c = (a >= b);
                        break;
                    case "==":
                        c = (a == b);
                        break;
                    case "!=":
                        c = (a != b);
                        break;
                    default:
                        c = false;
                        assert false;
                }
                this.lattice.setConst(c ? 1 : 0);
//                System.err.printf("%s is constant %d\n", this.print(), c ? 1 : 0);
            }
        }

        private static final Set<String> commutative = Set.of("==", "!=");

        @Override
        public boolean isCommutative() {
            return commutative.contains(op);
        }
    }

    public static class StoreInst extends Instruction {
        public StoreInst(Value dest, Value from, BasicBlock belong) {
            super("", dest.type, belong);
            addOperand(dest, from);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GEPInst extends Instruction {
        public IRType.Type valueType;
        public boolean zeroPad;

        GEPInst(IRType.Type valueType, Value base, BasicBlock belong) {
            super("T", new IRType.PointerType(valueType), belong);
            addOperand(base);
            this.valueType = valueType;
            operands.add(new IRConstant.ConstInteger(0));
            this.zeroPad = true;
        }

        GEPInst(IRType.Type valueType, Value base, BasicBlock belong, boolean zeroPad) {
            super("T", new IRType.PointerType(valueType), belong);
            addOperand(base);
            this.valueType = valueType;
            if (zeroPad) operands.add(new IRConstant.ConstInteger(0));
            this.zeroPad = zeroPad;
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

        public boolean tail = false;

        CallInst(IRConstant.Function func, List<Value> args, BasicBlock belong) {
            super("call_" + func.name, ((IRType.FunctionType) func.type).returnType, belong);
            addOperand(func);
            addOperand(args);
        }

        public void checkTailCall() {
            if (belong.instructions.indexOf(this) + 1 == belong.instructions.size() - 1) {
                var last = belong.instructions.get(belong.instructions.size() - 1);
                if (last instanceof IRInst.ReturnInst) {
                    if (last.operands.get(0) == this) {
                        System.err.println("Tail Call Optimized");
                        tail = true;
//                        assert false;
                    }
                }
            }
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class LoadInst extends Instruction {
        public LoadInst(Value ptr, BasicBlock belong) {
            super("load_" + ptr.getOriginalName(), ((IRType.PointerType) ptr.type).pointer, belong);
            addOperand(ptr);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class CastInst extends Instruction {
        IRType.Type to;

        CastInst(Value from, IRType.Type to, BasicBlock belong) {
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
        IRType.Type to;

        SextInst(Value from, IRType.Type to, BasicBlock belong) {
            super("M", to, belong);
            addOperand(from);
            this.to = to;
            this.lattice.setUpper();
        }

        @Override
        public void propagate() {
            var lattice = operands.get(0).lattice;
            this.lattice = new Value.ConditionalLattice(lattice);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class TruncInst extends Instruction {
        IRType.Type to;

        TruncInst(Value from, IRType.Type to, BasicBlock belong) {
            super("M", to, belong);
            addOperand(from);
            this.to = to;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class PhiInst extends Instruction {

        public PhiInst(String name, IRType.Type t, BasicBlock belong) {
            super(name, t, belong, true);
        }

        public void addIncoming(Value v, BasicBlock b) {
            addOperand(v);
            addOperand(b);
        }

        public List<BasicBlock> getIncomingBlocks() {
            var ret = new ArrayList<BasicBlock>();
            for (int i = 1; i < operands.size(); i += 2) ret.add((BasicBlock) operands.get(i));
            return ret;
        }

        @Override
        public void rewrite() {
            super.rewrite();
            var cfg = new CFG(belong.belong.getEntryBlock(), false);
            var pred = cfg.getPredecessors(this.belong);

            boolean flg = true;
            while (flg) {
                flg = false;
                for (int i = 0; i < operands.size(); i += 2) {
                    var v = operands.get(i);
                    var b = (BasicBlock) operands.get(i + 1);
                    if (!pred.contains(b)) {
                        System.err.printf("Rewriting Phi Inst %s", this.print());

                        delOperand(i);
                        delOperand(b);
                        flg = true;

                        System.err.printf(" to %s\n", this.print());
                        break;
                    }
                }
            }
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ParallelCopy extends Instruction {
        public List<Pair<Value, Value>> pcopy;

        public ParallelCopy(BasicBlock belong) {
            super("_Parallel_Copy", null, belong);
        }

        public void addCopy(Value src, Value dest) {
            addOperand(src);
            addOperand(dest);
        }

        public void organize() {
            if (pcopy != null) return;
            pcopy = new ArrayList<>();
            for (int i = 0; i < operands.size(); i += 2) {
                var src = operands.get(i);
                var dest = operands.get(i + 1);
                pcopy.add(new Pair<>(src, dest));
            }
        }
    }

    public static class CopyInst extends Instruction {

        public CopyInst(Value template, BasicBlock belong) {
            super(template.getOriginalName(), template.type, belong);
        }

        public void add(Value src, Value dest) {
            addOperand(dest);
            addOperand(src);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

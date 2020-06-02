package com.gabriel.compiler.backend;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.min;

public class AsmInst {

    public abstract static class Instruction {
        Register.base rs1, rs2, rd;
        Object imm;    //0 stands for both unneeded and numeric zero
        String opcode;
        AsmStruct.Block belong;
        Set<Register.base> use = new HashSet<>(), def = new HashSet<>();

        protected void addUse(Register.base r) {
            if (r == null) return;
            r.addUse(this);
            use.add(r);
        }

        protected void delUse(Register.base r) {
            r.delUse(this);
            use.remove(r);
        }

        protected void addDef(Register.base r) {
            if (r == null) return;
            r.addDef(this);
            def.add(r);
        }

        protected void delDef(Register.base r) {
            r.delDef(this);
            def.remove(r);
        }

        public Instruction(Register.base rs1, Register.base rs2, Register.base rd,
                           Object imm, String opcode, AsmStruct.Block belong) {
            this.rs1 = rs1;
            this.rs2 = rs2;
            this.rd = rd;
            this.imm = imm;
            this.opcode = opcode;
            this.belong = belong;
            belong.addInst(this);

            addUse(this.rs1);
            addUse(this.rs2);
            addDef(this.rd);
        }

        public Instruction(AsmStruct.Block belong) {
            this.belong = belong;
            belong.addInst(this);
        }

        public String print() {
            return (String) (new AsmPrinter()).visit(this);
        }

        public Set<Register.base> getDef() {
            return def;
        }

        public Set<Register.base> getUse() {
            return use;
        }

        public void replaceDefWith(Register.base orig, Register.base now) {
            if (rd == orig) {
                delDef(orig);
                addDef(now);
                rd = now;
            }
        }

        public void replaceUseWith(Register.base orig, Register.base now) {
            if (rs1 == orig || rs2 == orig) {
                delUse(orig); addUse(now);
                if (rs1 == orig) rs1 = now;
                if (rs2 == orig) rs2 = now;
            }
        }

        public void replaceWith(Register.base orig, Register.base now) {
            replaceDefWith(orig, now);
            replaceUseWith(orig, now);
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class mv extends Instruction {
        public mv(Register.base rd, Register.base rs, AsmStruct.Block belong) {
            super(rs, null, rd, 0, "mv", belong);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ComputeRegImm extends Instruction {
        public ComputeRegImm(String op, Register.base rs, int imm, Register.base rd, AsmStruct.Block belong) {
            super(rs, null, rd, imm, op + "i", belong);
            assert !(op.equals("mul") || op.equals("div"));
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ComputeRegReg extends Instruction {
        public ComputeRegReg(String op, Register.base rs1, Register.base rs2, Register.base rd, AsmStruct.Block belong) {
            super(rs1, rs2, rd, 0, op, belong);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ComputeReg extends Instruction {
        public ComputeReg(String op, Register.base rs1, Register.base rd, AsmStruct.Block belong) {
            super(rs1, null, rd, 0, op, belong);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class li extends Instruction {
        public li(Register.base rd, int imm, AsmStruct.Block belong) {
            super(null, null, rd, imm, "li", belong);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class load extends Instruction {
        private static final Map<Integer, String> width = Map.of(1, "lb", 2, "lh", 4, "lw");
        public int byteNum;

        public load(Register.base baseAddr, int offset, int byteNum, Register.base rd, AsmStruct.Block belong) {
            super(baseAddr, null, rd, offset, width.get(byteNum), belong);
            assert width.containsKey(byteNum);
            this.byteNum = byteNum;
        }

        public load(Register.base baseAddr, String offset, int byteNum, Register.base rd, AsmStruct.Block belong) {
            super(baseAddr, null, rd, offset, width.get(byteNum), belong);
            assert width.containsKey(byteNum);
            this.byteNum = byteNum;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class store extends Instruction {
        private static final Map<Integer, String> width = Map.of(1, "sb", 2, "sh", 4, "sw");
        public int byteNum;

        public store(Register.base src, Register.base baseAddr, int offset, int byteNum, AsmStruct.Block belong) {
            super(baseAddr, src, null, offset, width.get(byteNum), belong);
            assert width.containsKey(byteNum);
            this.byteNum = byteNum;

        }

        public store(Register.base src, Register.base baseAddr, String offset, int byteNum, AsmStruct.Block belong) {
            super(baseAddr, src, null, offset, width.get(byteNum), belong);
            assert width.containsKey(byteNum);
            this.byteNum = byteNum;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class call extends Instruction {
        AsmStruct.Function target;
        boolean tail;

        public call(AsmStruct.Function func, AsmStruct.Block belong, boolean tail) {
            super(belong);
            this.target = func;
            this.tail = tail;
            //Implicit use for arg registers and def for all caller-save registers
//            System.err.printf("%s's arg num: %d\n", func.label, func.getArgCount());
            for (int i = 0; i < min(8, func.getArgCount()); i++) {
                addUse(Register.Machine.get("a" + i));
            }
            for (var arg : Register.Machine.callerSave.keySet()) {
                addDef(Register.Machine.get(arg));
            }
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ret extends Instruction {
        public ret(AsmStruct.Block belong) {
            super(belong);
            //Implicit use for a0 and ra
            addUse(Register.Machine.get("a0"));
            addUse(Register.Machine.get("ra"));
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class jump extends Instruction {
        AsmStruct.Block target;

        public jump(AsmStruct.Block target, AsmStruct.Block belong) {
            super(belong);
            this.target = target;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class branch extends Instruction {
        AsmStruct.Block target;

        public branch(String op, Register.base lhs, Register.base rhs, AsmStruct.Block target, AsmStruct.Block belong) {
            super(lhs, rhs, null, 0, op, belong);
            this.target = target;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class la extends Instruction {
        String name;

        public la(Register.base rd, String name, AsmStruct.Block belong) {
            super(null, null, rd, 0, "la", belong);
            this.name = name;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class lui extends Instruction {
        public lui(Register.base rd, int imm, AsmStruct.Block belong) {
            super(null, null, rd, imm, "lui", belong);
        }

        public lui(Register.base rd, String imm, AsmStruct.Block belong) {
            super(null, null, rd, imm, "lui", belong);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

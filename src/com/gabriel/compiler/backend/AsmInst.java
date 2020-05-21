package com.gabriel.compiler.backend;

import java.util.Map;

public class AsmInst {

    public abstract static class Instruction {
        Register.base rs1, rs2, rd;
        Object imm;    //0 stands for both unneeded and numeric zero
        String opcode;
        AsmStruct.Block belong;

        public Instruction(Register.base rs1, Register.base rs2, Register.base rd,
                           Object imm, String opcode, AsmStruct.Block belong) {
            //TODO: Maintain a def-use chain
            this.rs1 = rs1;
            this.rs2 = rs2;
            this.rd = rd;
            this.imm = imm;
            this.opcode = opcode;
            this.belong = belong;
            belong.addInst(this);
        }

        public Instruction(AsmStruct.Block belong) {
            this.belong = belong;
            belong.addInst(this);
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

        public load(Register.base baseAddr, int offset, int bitLen, Register.base rd, AsmStruct.Block belong) {
            super(baseAddr, null, rd, offset, width.get(bitLen / 8), belong);
            assert width.containsKey(bitLen / 8);
        }

        public load(Register.base baseAddr, String offset, int bitLen, Register.base rd, AsmStruct.Block belong) {
            super(baseAddr, null, rd, offset, width.get(bitLen / 8), belong);
            assert width.containsKey(bitLen / 8);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

//    public static class load_global extends Instruction {
//        private static final Map<Integer, String> width = Map.of(1, "lb", 2, "lh", 4, "lw");
//        String symbol;
//
//        public load_global(Register.base rd, int bitLen, String symbol, AsmStruct.Block belong) {
//            super(null, null, rd, 0, width.get(bitLen / 8), belong);
//            assert width.containsKey(bitLen / 8);
//            this.symbol = symbol;
//        }
//
//        @Override
//        public Object accept(AsmVisitor visitor) {
//            return visitor.visit(this);
//        }
//    }

    public static class store extends Instruction {
        private static final Map<Integer, String> width = Map.of(1, "sb", 2, "sh", 4, "sw");

        public store(Register.base src, Register.base baseAddr, int offset, int bitLen, AsmStruct.Block belong) {
            super(baseAddr, src, null, offset, width.get(bitLen / 8), belong);
            assert width.containsKey(bitLen / 8);
        }

        public store(Register.base src, Register.base baseAddr, String offset, int bitLen, AsmStruct.Block belong) {
            super(baseAddr, src, null, offset, width.get(bitLen / 8), belong);
            assert width.containsKey(bitLen / 8);
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }


//    public static class store_global extends Instruction {
//        private static final Map<Integer, String> width = Map.of(1, "sb", 2, "sh", 4, "sw");
//        String symbol;
//
//        public store_global(Register.base src, int bitLen, String symbol, AsmStruct.Block belong) {
//            super(src, null, null, 0, width.get(bitLen / 8), belong);
//            assert width.containsKey(bitLen / 8);
//            this.symbol = symbol;
//        }
//
//        @Override
//        public Object accept(AsmVisitor visitor) {
//            return visitor.visit(this);
//        }
//    }

    public static class call extends Instruction {
        AsmStruct.Function target;

        public call(AsmStruct.Function func, AsmStruct.Block belong) {
            super(belong);
            this.target = func;
        }

        @Override
        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ret extends Instruction {
        public ret(AsmStruct.Block belong) {
            super(belong);
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

    static class stackPush extends Instruction {
        int alloc;

        public stackPush(int b, AsmStruct.Block belong) {
            super(belong);
            this.alloc = b;
        }
    }
}

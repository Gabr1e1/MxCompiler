package com.gabriel.compiler.backend;

import java.util.Map;

public class AsmInst {

    public abstract static class Instruction {
        Register.base rs1, rs2, rd;
        int imm;    //0 stands for both unneeded and numeric zero
        String opcode;
        AsmStruct.Block belong;

        public Instruction(Register.base rs1, Register.base rs2, Register.base rd,
                           int imm, String opcode, AsmStruct.Block belong) {
            //TODO: Maintain a def-use chain
            this.rs1 = rs1;
            this.rs2 = rs2;
            this.rd = rd;
            this.imm = imm;
            this.opcode = opcode;
            this.belong = belong;
            belong.addInst(this);
        }
    }

    public static class mv extends Instruction {
        public mv(Register.base rd, Register.base rs, AsmStruct.Block belong) {
            super(rs, null, rd, 0, "mv", belong);
        }
    }

    public static class ComputeRegImm extends Instruction {
        public ComputeRegImm(String op, Register.base rs, int imm, Register.base rd, AsmStruct.Block belong) {
            super(rs, null, rd, imm, op + "i", belong);
        }
    }

    public static class ComputeRegReg extends Instruction {
        public ComputeRegReg(String op, Register.base rs1, Register.base rs2, Register.base rd, AsmStruct.Block belong) {
            super(rs1, rs2, rd, 0, op, belong);
        }
    }

    public static class li extends Instruction {
        public li(Register.base rd, int imm, AsmStruct.Block belong) {
            super(null, null, rd, imm, "li", belong);
        }
    }

    public static class load extends Instruction {
        private static final Map<Integer, String> width = Map.of(1, "lb", 2, "lh", 4, "lw");

        public load(Register.base baseAddr, int offset, int bitLen, Register.base rd, AsmStruct.Block belong) {
            super(baseAddr, null, rd, offset, width.get(bitLen / 8), belong);
            assert bitLen % 8 == 0;
        }
    }

    public static class store extends Instruction {
        private static final Map<Integer, String> width = Map.of(1, "sb", 2, "sh", 4, "sw");

        public store(Register.base src, Register.base baseAddr, int offset, int bitLen, AsmStruct.Block belong) {
            super(baseAddr, src, null, offset, width.get(bitLen / 8), belong);
        }
    }

    public static class
}

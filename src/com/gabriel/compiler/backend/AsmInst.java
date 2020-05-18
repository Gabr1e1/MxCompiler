package com.gabriel.compiler.backend;

public class AsmInst {

    public abstract static class Instruction {
        Register.base rs1, rs2, rd;
        int imm;
        String opcode;
        ASMStruct.Block belong;

        public Instruction(Register.base rs1, Register.base rs2, Register.base rd,
                           int imm, String opcode, ASMStruct.Block belong) {
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
        public mv(Register.base rd, Register.base rs, ASMStruct.Block belong) {
            super(rs, null, rd, 0, "mv", belong);
        }
    }
}

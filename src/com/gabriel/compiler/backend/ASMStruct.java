package com.gabriel.compiler.backend;

import java.util.ArrayList;
import java.util.List;

public class ASMStruct {
    public static class Block {
        List<AsmInst.Instruction> instructions = new ArrayList<>();

        String label;

        public Block(String label) {
            this.label = label;
        }

        public void addInst(AsmInst.Instruction instruction) {
            instructions.add(instruction);
        }
    }
}

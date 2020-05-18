package com.gabriel.compiler.backend;

import java.util.ArrayList;
import java.util.List;

public class AsmStruct {

    public static class Function {
        List<AsmStruct.Block> blocks = new ArrayList<>();
        String label;

        public Function(String label) {
            this.label = label;
        }

        public void addBlock(Block block) {
            blocks.add(block);
        }
    }

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

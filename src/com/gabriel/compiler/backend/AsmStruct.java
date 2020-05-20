package com.gabriel.compiler.backend;

import java.util.ArrayList;
import java.util.List;

public class AsmStruct {

    public static class Stack {
        int size;
    }

    public static class Program {
        List<Function> functions = new ArrayList<>();
        List<GlobalVariable> globalVariables = new ArrayList<>();

        public void addFunc(Function func) {
            functions.add(func);
        }

        public void addGlobalVariable(GlobalVariable gv) {
            globalVariables.add(gv);
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class GlobalVariable {
        int bitLen;
        String name;
        String init = null;

        public GlobalVariable(String name, int bitLen) {
            this.bitLen = bitLen;
            this.name = name;
        }

        public GlobalVariable(String name, int bitLen, String init) {
            this.bitLen = bitLen;
            this.name = name;
            this.init = init;
        }

        public int getAlign() {
            return (int) Math.log(bitLen / 8);
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Function {
        List<AsmStruct.Block> blocks = new ArrayList<>();
        String label;
        Stack stack = new Stack();

        public Function(String label) {
            this.label = label;
        }

        public void addBlock(Block block) {
            blocks.add(block);
        }

        public void pushStack(int alloc) {
            stack.size += alloc;
        }

        @Override
        public String toString() {
            return label;
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Block {
        List<AsmInst.Instruction> instructions = new ArrayList<>();
        boolean isFirst = false;

        String label;

        public Block(String label) {
            this.label = label;
        }

        public void addInst(AsmInst.Instruction instruction) {
            instructions.add(instruction);
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst() {
            isFirst = true;
        }

        @Override
        public String toString() {
            return label;
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

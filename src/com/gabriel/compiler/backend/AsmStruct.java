package com.gabriel.compiler.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        int initNum = 0;

        public GlobalVariable(String name, int bitLen, int initNum) {
            this.bitLen = bitLen;
            this.name = name;
            this.initNum = initNum;
        }

        public GlobalVariable(String name, int bitLen, String init) {
            this.bitLen = bitLen;
            this.name = name;
            this.init = init;
        }

        public int getAlign() {
            return (int) (Math.log(bitLen / 8) / Math.log(2));
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
            this.stack.size += alloc;
        }

        public int newVariable() {
            var ret = stack.size;
            pushStack(4);
            return ret;
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
        List<AsmStruct.Block> successors = new ArrayList<>();

        public Block(String label) {
            this.label = label;
        }

        public void addInst(AsmInst.Instruction instruction) {
            instructions.add(instruction);
        }

        public void moveInst(AsmInst.Instruction inst, int targetIndex) {
            instructions.remove(inst);
            instructions.add(targetIndex, inst);
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst() {
            isFirst = true;
        }

        public void addSuccessor(AsmStruct.Block succ) {
            successors.add(succ);
        }

        public List<AsmStruct.Block> getSuccessor() {
            return this.successors;
        }

        public List<Register.base> getDef() {
            Set<Register.base> ret = new HashSet<>();
            for (var inst : instructions) ret.addAll(inst.getDef());
            return new ArrayList<>(ret);
        }

        public List<Register.base> getUse() {
            Set<Register.base> ret = new HashSet<>();
            for (var inst : instructions) ret.addAll(inst.getUse());
            return new ArrayList<>(ret);
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

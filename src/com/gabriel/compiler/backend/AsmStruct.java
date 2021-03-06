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

        public void cleanUp() {
            functions.forEach(Function::cleanUp);
        }
    }

    public static class GlobalVariable {
        int byteNum;
        String name;
        String init = null;
        int initNum = 0;

        public GlobalVariable(String name, int byteNum, int initNum) {
            this.byteNum = byteNum;
            this.name = name;
            this.initNum = initNum;
        }

        public GlobalVariable(String name, int byteNum, String init) {
            this.byteNum = byteNum;
            this.name = name;
            this.init = init;
        }

        public int getAlign() {
            return (int) (Math.log(byteNum) / Math.log(2));
        }

        public Object accept(AsmVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class Function {
        List<AsmStruct.Block> blocks = new ArrayList<>();
        String label;
        Stack stack = new Stack();
        int argCount = 0;
        AsmInst.Instruction[] placeHolderForSp = new AsmInst.Instruction[2];

        public Function(String label, int argCount) {
            this.label = label;
            this.argCount = argCount;
        }

        public int getArgCount() {
            return argCount;
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

        public void setPlaceHolderForSp(AsmInst.Instruction inst, int i) {
            this.placeHolderForSp[i] = inst;
        }

        public Set<Register.base> getAllVregs() {
            var ret = new HashSet<Register.base>();
            blocks.forEach(b -> ret.addAll(b.getAllVregs()));
            return ret;
        }

        public void cleanUp() {
            blocks.forEach(Block::cleanUp);
            for (var inst : placeHolderForSp) {
                assert inst instanceof AsmInst.ComputeRegImm;
                inst.imm = stack.size;
            }

            placeHolderForSp[1].belong.moveInst(placeHolderForSp[1],
                    placeHolderForSp[1].belong.instructions.size() - 2);
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
            if ((!instructions.contains(inst)) || targetIndex < 0 || targetIndex >= instructions.size())
                return;
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

        public Set<Register.base> getDef() {
            Set<Register.base> ret = new HashSet<>();
            for (var inst : instructions) ret.addAll(inst.getDef());
            return ret;
        }

        public Set<Register.base> getUse() {
            Set<Register.base> ret = new HashSet<>();
            for (var inst : instructions) ret.addAll(inst.getUse());
            return ret;
        }


        public Set<Register.base> getAllVregs() {
            var ret = new HashSet<>(getDef());
            ret.addAll(getUse());
            ret.removeAll(Register.Machine.regs.values());
            return ret;
        }

        public void cleanUp() {
            var delList = new ArrayList<AsmInst.Instruction>();
            for (var inst : instructions) {
                if (inst instanceof AsmInst.mv) {
                    if (inst.rd == inst.rs1) delList.add(inst);
                }
            }
            instructions.removeAll(delList);
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

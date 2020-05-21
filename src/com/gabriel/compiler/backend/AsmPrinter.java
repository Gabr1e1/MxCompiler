package com.gabriel.compiler.backend;

import java.io.FileWriter;
import java.io.IOException;

public class AsmPrinter implements AsmVisitor {
    String filename;
    FileWriter codeWriter;

    public AsmPrinter(String filename) {
        try {
            this.filename = filename;
            this.codeWriter = new FileWriter(filename);
//            System.out.println("Starting Asm Code Gen");
        } catch (IOException e) {
            System.out.println("Can't even create a fucking file");
        }
    }

    public void finish() {
        try {
            codeWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void writeCode(Object obj, boolean indent) {
        try {
            var str = (String) obj;
            codeWriter.write((indent ? "\t" : "") + str + "\n");
//            System.out.print(str + "\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    @Override
    public Object visit(AsmStruct.Program program) {
        writeCode(".text", true);
        for (var func : program.functions) {
            func.accept(this);
        }
        writeCode(".section .sdata,\"aw\",@progbits\n", true);
        for (var gv : program.globalVariables) {
            gv.accept(this);
        }
        finish();
        return null;
    }

    @Override
    public Object visit(AsmStruct.GlobalVariable globalVariable) {
        writeCode(String.format(".globl %s", globalVariable.name), true);
        if (globalVariable.init == null) writeCode(".p2align " + globalVariable.getAlign(), true);
        writeCode(globalVariable.name + ":", false);
        if (globalVariable.init == null) { //Init to 0
            writeCode(".word " + globalVariable.initNum, true);
        } else {
            writeCode(String.format(".asciz \"%s\"", globalVariable.init), true);
        }
        writeCode("", false);
        return null;
    }

    @Override
    public Object visit(AsmStruct.Function func) {
        writeCode(".globl " + func.label, true);
        writeCode(".p2align	2", true);
        writeCode(".type " + func.label + ", @function", true);
        writeCode(func.label + ":", false);
        for (var block : func.blocks) {
            block.accept(this);
            writeCode("", false);
        }
        return null;
    }

    @Override
    public Object visit(AsmStruct.Block block) {
        if (!block.isFirst()) writeCode(block.label + ":", false);
        for (var inst : block.instructions) {
            writeCode(inst.accept(this), true);
        }
        return null;
    }

    @Override
    public Object visit(AsmInst.Instruction inst) {
        System.err.println("Missing overrode method for " + inst);
        assert false;
        return null;
    }

    @Override
    public Object visit(AsmInst.mv inst) {
        return String.format("mv %s, %s", inst.rd, inst.rs1);
    }

    @Override
    public Object visit(AsmInst.ComputeRegImm inst) {
        if (inst.opcode.equals("subi")) {
            inst.opcode = "addi";
            inst.imm = -(Integer) inst.imm;
        }
        return String.format("%s %s, %s, %s", inst.opcode, inst.rd, inst.rs1, inst.imm);
    }

    @Override
    public Object visit(AsmInst.ComputeRegReg inst) {
        return String.format("%s %s, %s, %s", inst.opcode, inst.rd, inst.rs1, inst.rs2);
    }

    @Override
    public Object visit(AsmInst.li inst) {
        return String.format("li %s, %s", inst.rd, inst.imm);
    }

    @Override
    public Object visit(AsmInst.load inst) {
        return String.format("%s %s, %s(%s)", inst.opcode, inst.rd, inst.imm, inst.rs1);
    }

//    @Override
//    public Object visit(AsmInst.load_global inst) {
//        return String.format("%s %s, %s", inst.opcode, inst.rd, inst.symbol);
//    }

    @Override
    public Object visit(AsmInst.store inst) {
        return String.format("%s %s, %s(%s)", inst.opcode, inst.rs2, inst.imm, inst.rs1);
    }

//    @Override
//    public Object visit(AsmInst.store_global inst) {
//        return String.format("%s %s, %s", inst.opcode, inst.rd, inst.symbol);
//    }

    @Override
    public Object visit(AsmInst.call inst) {
        return String.format("call %s", inst.target);
    }

    @Override
    public Object visit(AsmInst.ret inst) {
        return "ret";
    }

    @Override
    public Object visit(AsmInst.jump inst) {
        return String.format("j %s", inst.target);
    }

    @Override
    public Object visit(AsmInst.branch inst) {
        return String.format("%s %s, %s, %s", inst.opcode, inst.rs1, inst.rs2, inst.target);
    }

    @Override
    public Object visit(AsmInst.lui inst) {
        return String.format("lui %s %s", inst.rd, inst.imm);
    }

    @Override
    public Object visit(AsmInst.la inst) {
        return String.format("la %s, %s", inst.rd, inst.name);
    }
}

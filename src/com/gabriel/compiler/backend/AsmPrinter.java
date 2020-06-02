package com.gabriel.compiler.backend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

    public AsmPrinter() {
        /* EMPTY */
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
        visited = new HashSet<>();

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

    Set<AsmStruct.Block> visited;

    private void dfs(AsmStruct.Block block) {
        if (visited.contains(block)) return;
        visited.add(block);
        AsmStruct.Block next = null;
        if (block.instructions.get(block.instructions.size() - 1) instanceof AsmInst.jump) {
            next = ((AsmInst.jump) block.instructions.get(block.instructions.size() - 1)).target;
            if (!visited.contains(next))
                block.instructions.remove(block.instructions.size() - 1);
        }
        block.accept(this);
        writeCode("", false);

        if (next != null) dfs(next);

        for (var inst : block.instructions) {
            if (inst instanceof AsmInst.branch) {
                dfs(((AsmInst.branch) inst).target);
            }
        }

        for (int i = 0; i < block.instructions.size() - 2; i++) {
            assert !(block.instructions.get(i) instanceof AsmInst.jump);
        }
    }

    @Override
    public Object visit(AsmStruct.Function func) {
        writeCode(".globl " + func.label, true);
        writeCode(".p2align	2", true);
        writeCode(".type " + func.label + ", @function", true);
        writeCode(func.label + ":", false);
        dfs(func.blocks.get(0));

//        for (var block : func.blocks) {
//            block.accept(this);
//            writeCode("", false);
//        }
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
        return inst.accept(this);
    }

    @Override
    public Object visit(AsmInst.mv inst) {
        return String.format("mv %s, %s", inst.rd, inst.rs1);
    }

    @Override
    public Object visit(AsmInst.ComputeRegImm inst) {
        var opcode = inst.opcode;
        var imm = inst.imm;
        if (inst.opcode.equals("subi")) {
            opcode = "addi";
            imm = -(Integer) inst.imm;
        }
        return String.format("%s %s, %s, %s", opcode, inst.rd, inst.rs1, imm);
    }

    @Override
    public Object visit(AsmInst.ComputeRegReg inst) {
        return String.format("%s %s, %s, %s", inst.opcode, inst.rd, inst.rs1, inst.rs2);
    }

    @Override
    public Object visit(AsmInst.ComputeReg inst) {
        return String.format("%s %s, %s", inst.opcode, inst.rd, inst.rs1);
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
        return inst.tail ? String.format("tail call %s", inst.target) : String.format("call %s", inst.target);
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
        return String.format("lui %s, %s", inst.rd, inst.imm);
    }

    @Override
    public Object visit(AsmInst.la inst) {
        return String.format("la %s, %s", inst.rd, inst.name);
    }
}

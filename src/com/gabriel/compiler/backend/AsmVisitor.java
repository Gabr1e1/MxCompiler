package com.gabriel.compiler.backend;

public interface AsmVisitor {
    //Structure
    Object visit(AsmStruct.Program program);
    Object visit(AsmStruct.GlobalVariable globalVariable);
    Object visit(AsmStruct.Function func);
    Object visit(AsmStruct.Block block);

    //Instructions
    Object visit(AsmInst.Instruction inst);
    Object visit(AsmInst.mv inst);
    Object visit(AsmInst.ComputeRegImm inst);
    Object visit(AsmInst.ComputeRegReg inst);
    Object visit(AsmInst.ComputeReg inst);
    Object visit(AsmInst.li inst);
    Object visit(AsmInst.load inst);
//    Object visit(AsmInst.load_global inst);
    Object visit(AsmInst.store inst);
//    Object visit(AsmInst.store_global inst);
    Object visit(AsmInst.call inst);
    Object visit(AsmInst.ret inst);
    Object visit(AsmInst.jump inst);
    Object visit(AsmInst.branch inst);
    Object visit(AsmInst.lui inst);
    Object visit(AsmInst.la inst);
}

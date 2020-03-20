package com.gabriel.compiler.IR;

public interface IRVisitor {
    //Entry
    Object visit(Module module);

    //Instruction
    Object visit(Instruction inst);
    Object visit(IRInst.AllocaInst inst);
    Object visit(IRInst.BranchInst inst);
    Object visit(IRInst.ReturnInst inst);
    Object visit(IRInst.BinaryOpInst inst);
    Object visit(IRInst.CmpInst inst);
    Object visit(IRInst.StoreInst inst);
    Object visit(IRInst.GEPInst inst);
    Object visit(IRInst.CallInst inst);

    //Constant
    Object visit(Constant constant);
    Object visit(IRConstant.ConstInteger constant);
    Object visit(IRConstant.ConstString constant);
    Object visit(IRConstant.Null constant);
    Object visit(IRConstant.Void constant);
    Object visit(IRConstant.GlobalVariable constant);
    Object visit(IRConstant.Function constant);

    //Type
    Object visit(Type type);
    Object visit(IRType.VoidType type);
    Object visit(IRType.IntegerType type);
    Object visit(IRType.ClassType type);
    Object visit(IRType.PointerType type);
    Object visit(IRType.ArrayType type);
    Object visit(IRType.FunctionType type);
    Object visit(IRType.LabelType type);
}

package com.gabriel.compiler.IR;

public interface IRVisitor {
    //Top level components
    Object visit(Module module);
    Object visit(IRConstant.GlobalVariable globalVariable);
    Object visit(IRConstant.Function function);
    Object visit(BasicBlock block);

    //Helper
    Object visit(Value value);

    //Instruction
    Object visit(IRInst.AllocaInst inst);
    Object visit(IRInst.BranchInst inst);
    Object visit(IRInst.ReturnInst inst);
    Object visit(IRInst.BinaryOpInst inst);
    Object visit(IRInst.CmpInst inst);
    Object visit(IRInst.StoreInst inst);
    Object visit(IRInst.GEPInst inst);
    Object visit(IRInst.CallInst inst);
    Object visit(IRInst.LoadInst inst);
    Object visit(IRInst.CastInst inst);
    Object visit(IRInst.SextInst inst);
    Object visit(IRInst.TruncInst inst);
    Object visit(IRInst.PhiInst inst);
    Object visit(IRInst.CopyInst inst);

    //Constant
    Object visit(IRConstant.ConstInteger constant);
    Object visit(IRConstant.ConstString constant);
    Object visit(IRConstant.Null constant);
    Object visit(IRConstant.Void constant);

    //Type
    Object visit(IRType.VoidType type);
    Object visit(IRType.IntegerType type);
    Object visit(IRType.ClassType type);
    Object visit(IRType.PointerType type);
    Object visit(IRType.ArrayType type);
    Object visit(IRType.FunctionType type);
    Object visit(IRType.LabelType type);
}

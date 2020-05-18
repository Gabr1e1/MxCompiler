package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;

import java.util.HashMap;
import java.util.Map;

public class InstSelection implements IRVisitor {

    private Map<Value, Register.base> regMap = new HashMap<>();

    private Register.base getRegister(Value v) {
        regMap.putIfAbsent(v, new Register.Virtual());
        return regMap.get(v);
    }

    private void putRegister(Value v, Register.base reg) {
        regMap.put(v, reg);
    }

    @Override
    public Object visit(Module module) {
        for (var func : module.functions) {
            func.accept(this);
        }
    }

    @Override
    public Object visit(IRConstant.GlobalVariable globalVariable) {
        return null;
    }

    @Override
    public Object visit(IRConstant.Function function) {
        return null;
    }

    @Override
    public Object visit(BasicBlock block) {
        return null;
    }

    @Override
    public Object visit(Value value) {
        return null;
    }

    @Override
    public Object visit(IRInst.AllocaInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.BranchInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.ReturnInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.BinaryOpInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.CmpInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.StoreInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.GEPInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.CallInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.LoadInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.CastInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.SextInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.TruncInst inst) {
        return null;
    }

    @Override
    public Object visit(IRInst.PhiInst inst) {
        System.err.println("Phi Inst shouldn't exist by now");
        assert false;
        return null;
    }

    @Override
    public Object visit(IRInst.CopyInst inst) {
        return new AsmInst.mv(getRegister(inst.operands.get(0)),
                getRegister(inst.operands.get(1)));
    }

    @Override
    public Object visit(IRConstant.ConstInteger constant) {
        return null;
    }

    @Override
    public Object visit(IRConstant.ConstString constant) {
        return null;
    }

    @Override
    public Object visit(IRConstant.Null constant) {
        return null;
    }

    @Override
    public Object visit(IRConstant.Void constant) {
        return null;
    }

    @Override
    public Object visit(IRType.VoidType type) {
        return null;
    }

    @Override
    public Object visit(IRType.IntegerType type) {
        return null;
    }

    @Override
    public Object visit(IRType.ClassType type) {
        return null;
    }

    @Override
    public Object visit(IRType.PointerType type) {
        return null;
    }

    @Override
    public Object visit(IRType.ArrayType type) {
        return null;
    }

    @Override
    public Object visit(IRType.FunctionType type) {
        return null;
    }

    @Override
    public Object visit(IRType.LabelType type) {
        return null;
    }
}

package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;

import java.util.HashMap;
import java.util.Map;

public class InstSelection implements IRVisitor {

    private final Map<IRConstant.Function, AsmStruct.Function> funcMap = new HashMap<>();
    private final Map<BasicBlock, AsmStruct.Block> blockMap = new HashMap<>();
    private final Map<Value, Register.base> regMap = new HashMap<>();
    private AsmStruct.Function curFunc = null;
    private AsmStruct.Block curBlock = null;


    private AsmStruct.Function getFunction(IRConstant.Function func) {
        funcMap.putIfAbsent(func, new AsmStruct.Function(func.getName()));
        return funcMap.get(func);
    }

    private AsmStruct.Block getBlock(BasicBlock block) {
        blockMap.putIfAbsent(block, new AsmStruct.Block(block.getName()));
        return blockMap.get(block);
    }

    private Register.base getRegister(Value v) {
        regMap.putIfAbsent(v, new Register.Virtual());
        var ret = regMap.get(v);
        if (v instanceof IRConstant.ConstInteger) loadImm(ret, ((IRConstant.ConstInteger) v).num);
        return ret;
    }

    private void loadImm(Register.base v, int num) {
        new AsmInst.li(v, num, curBlock);
    }

    private void init() {
        Register.Machine.init();
    }

    @Override
    public Object visit(Module module) {
        init();
        for (var gv : module.globalVariables) {
            gv.accept(this);
        }
        for (var func : module.functions) {

            func.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(IRConstant.GlobalVariable globalVariable) {
        return null;
    }

    @Override
    public Object visit(IRConstant.Function function) {
        //TODO: Save all callee-save registers

        curFunc = getFunction(function);
        for (var block : function.blocks) {
            curFunc.addBlock((AsmStruct.Block) block.accept(this));
        }
        return curFunc;
    }

    @Override
    public Object visit(BasicBlock block) {
        curBlock = getBlock(block);
        block.instructions.forEach((inst) -> inst.accept(this));
        return curBlock;
    }

    @Override
    public Object visit(Value value) {
        /* Empty */
        return null;
    }

    @Override
    public Object visit(IRInst.AllocaInst inst) {
        System.err.println("Alloca Inst shouldn't exist by now");
        assert false;
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

    //TODO: PEEPHOLE to eliminate unnecessary register holding a constant value

    @Override
    public Object visit(IRInst.BinaryOpInst inst) {
        var lhs = getRegister(inst.operands.get(0));
        var rhs = getRegister(inst.operands.get(1));
        var op = inst.getCorresAsmOp();
        return new AsmInst.ComputeRegReg(op, lhs, rhs, getRegister(inst), curBlock);
    }

    @Override
    public Object visit(IRInst.CmpInst inst) {
        var lhs = getRegister(inst.operands.get(0));
        var rhs = getRegister(inst.operands.get(1));
        var op = inst.op;

        if (op.equals(">") || op.equals("<=")) {
            //Swap lhs and rhs
            lhs = getRegister(inst.operands.get(1));
            rhs = getRegister(inst.operands.get(0));
            op = op.equals(">") ? "<" : ">=";
        }

        AsmInst.Instruction ret;
        if (op.equals("<") || op.equals("!=")) {
            var op2 = op.equals("<") ? "slt" : "xor";
            var t = new AsmInst.ComputeRegReg(op2, lhs, rhs, new Register.Virtual(), curBlock);
            ret = new AsmInst.ComputeRegReg("snez", t.rd, null, getRegister(inst), curBlock);
        } else {
            var op2 = op.equals(">=") ? "slt" : "xor";
            var t = new AsmInst.ComputeRegReg(op2, lhs, rhs, new Register.Virtual(), curBlock);
            ret = new AsmInst.ComputeRegReg("seqz", t.rd, null, getRegister(inst), curBlock);
        }
        return ret;
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
        var ptr = inst.operands.get(0);
        var base = getRegister(ptr);
        return new AsmInst.load(base, 0, ptr.getType().bitLen, getRegister(inst), curBlock);
    }

    @Override
    public Object visit(IRInst.StoreInst inst) {
        var dest = inst.operands.get(0);
        var src = inst.operands.get(1);
        return new AsmInst.store(getRegister(src), getRegister(dest),
                0, dest.getType().bitLen, curBlock);
    }

    //Cast, Sext, Trunc is just copy...

    @Override
    public Object visit(IRInst.CastInst inst) {
        return new AsmInst.mv(getRegister(inst),
                getRegister(inst.operands.get(0)), curBlock);
    }

    @Override
    public Object visit(IRInst.SextInst inst) {
        return new AsmInst.mv(getRegister(inst),
                getRegister(inst.operands.get(0)), curBlock);
    }

    @Override
    public Object visit(IRInst.TruncInst inst) {
        return new AsmInst.mv(getRegister(inst),
                getRegister(inst.operands.get(0)), curBlock);
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
                getRegister(inst.operands.get(1)), curBlock);
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

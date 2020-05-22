package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;

import java.util.HashMap;
import java.util.Map;

public class InstSelection implements IRVisitor {

    private final Map<IRConstant.Function, AsmStruct.Function> funcMap = new HashMap<>();
    private final Map<BasicBlock, AsmStruct.Block> blockMap = new HashMap<>();
    private final Map<Value, Register.base> regMap = new HashMap<>();
    private final Map<Value, Register.base> globalMap = new HashMap<>();
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
        if (v instanceof IRConstant.GlobalVariable) new AsmInst.la(ret, v.getName(), curBlock);
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
        var program = new AsmStruct.Program();
        init();
        for (var gv : module.globalVariables) {
            program.addGlobalVariable((AsmStruct.GlobalVariable) gv.accept(this));
        }
        for (var func : module.functions) {
            program.addFunc((AsmStruct.Function) func.accept(this));
        }
        return program;
    }

    //TODO: GET RID OF GLOBAL_INIT

    @Override
    public Object visit(IRConstant.GlobalVariable globalVariable) {
        var init = globalVariable.getOperand(0);
        var num = 0;
        if (init instanceof IRConstant.ConstInteger) num = ((IRConstant.ConstInteger) init).num;
        return new AsmStruct.GlobalVariable(globalVariable.getName(),
                globalVariable.getType().bitLen, num);
    }


    @Override
    public Object visit(IRConstant.ConstString constant) {
        return new AsmStruct.GlobalVariable(constant.getName(), constant.getType().bitLen, constant.str);
    }

    @Override
    public Object visit(IRConstant.Function function) {
        curFunc = getFunction(function);
        curBlock = getBlock(function.blocks.get(0));

        //Placeholder for sp
        var sp = new AsmInst.ComputeRegImm("sub", Register.Machine.get("sp"), curFunc.stack.size, Register.Machine.get("sp"), curBlock);

        //Save callee-save registers
        for (var reg : Register.Machine.calleeSave.keySet()) {
            Register.save(reg, curBlock);
        }

        //Read args
        for (int i = 0; i < function.getParamNum(); i++) {
            var cur = function.getParam(i);
            if (i < 8) {
                new AsmInst.mv(getRegister(cur), Register.Machine.get("a" + i), curBlock);
            } else {
                new AsmInst.load(Register.Machine.get("sp"), (i - 8) * 4, 32, getRegister(cur), curBlock);
            }
        }
        for (int i = 0; i < function.blocks.size(); i++) {
            var block = function.blocks.get(i);
            var cur = (AsmStruct.Block) block.accept(this);
            if (i == 0) cur.setFirst();
            curFunc.addBlock(cur);
        }

        //Restore callee-save registers
        for (var reg : Register.Machine.calleeSave.keySet()) {
            new AsmInst.mv(Register.Machine.get(reg), Register.calleeTmpSave.get(reg), curBlock);
        }
        new AsmInst.ret(curBlock);
        sp.imm = curFunc.stack.size;

        return curFunc;
    }

    @Override
    public Object visit(BasicBlock block) {
        curBlock = getBlock(block);
        block.instructions.forEach((inst) -> inst.accept(this));
        for (var succ : block.getSuccessors()) {
            curBlock.addSuccessor(getBlock(succ));
        }
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
        if (!inst.isConditional()) {
            new AsmInst.jump(getBlock((BasicBlock) inst.getOperand(0)), curBlock);
        } else {
            new AsmInst.branch("blt", Register.Machine.get("zero"),
                    getRegister(inst.getOperand(0)), getBlock((BasicBlock) inst.getOperand(1)), curBlock);
            new AsmInst.jump(getBlock((BasicBlock) inst.getOperand(2)), curBlock);
        }
        return null;
    }

    @Override
    public Object visit(IRInst.ReturnInst inst) {
        for (var reg : Register.Machine.calleeSave.keySet()) {
            new AsmInst.mv(Register.Machine.get(reg), Register.calleeTmpSave.get(reg), curBlock);
        }
        new AsmInst.ComputeRegImm("add", Register.Machine.get("sp"),
                curFunc.stack.size, Register.Machine.get("sp"), curBlock);
        if (!(inst.getType() instanceof IRType.VoidType))
            new AsmInst.mv(Register.Machine.get("a0"), getRegister(inst.getOperand(0)), curBlock);
        return null;
    }

    //TODO: Eliminate unnecessary register holding a constant value

    @Override
    public Object visit(IRInst.BinaryOpInst inst) {
        var lhs = getRegister(inst.operands.get(0));
        var rhs = getRegister(inst.operands.get(1));
        var op = inst.getCorresAsmOp();
        return new AsmInst.ComputeRegReg(op, lhs, rhs, getRegister(inst), curBlock);
    }

    //TODO: Eliminate cmp inst whose result determines a branch

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
        var base = inst.getOperand(0);
        if (base instanceof IRConstant.ConstString || base instanceof IRConstant.GlobalVariable) {
            return new AsmInst.la(getRegister(inst), base.getName(), curBlock);
        }

        var offset = new Register.Virtual();
        if (inst.getType() instanceof IRType.ClassType) {
            assert inst.zeroPad;
            loadImm(offset, ((IRType.ClassType) inst.valueType).getOffset(((IRConstant.ConstInteger) inst.getOperand(2)).num));
        } else {
            assert inst.getType() instanceof IRType.PointerType;
            loadImm(offset, ((IRConstant.ConstInteger) inst.getOperand(inst.zeroPad ? 2 : 1)).num);
            new AsmInst.ComputeRegImm("mul", offset, base.getType().bitLen / 8, offset, curBlock);
        }
        return new AsmInst.ComputeRegReg("add", getRegister(base), offset, getRegister(inst), curBlock);
    }

    @Override
    public Object visit(IRInst.CallInst inst) {
        var func = getFunction((IRConstant.Function) inst.operands.get(0));
        for (int i = inst.operands.size() - 1; i > 0; i--) {
            if (i <= 8) {
                new AsmInst.mv(Register.Machine.get("a" + (i - 1)), getRegister(inst.operands.get(i)), curBlock);
            } else {
//                new AsmInst.stackPush(4, curBlock);
                curFunc.pushStack(4);
                new AsmInst.store(getRegister(inst.operands.get(i)), Register.Machine.get("sp"), (i - 9) * 4,
                        inst.operands.get(i).getType().bitLen, curBlock);
            }
        }
        new AsmInst.call(func, curBlock);

        //Get Return Value
        var ret = getRegister(inst);
        if (inst.getType() instanceof IRType.VoidType) return ret;
        new AsmInst.mv(ret, Register.Machine.get("a0"), curBlock);
        return ret;
    }

    private AsmInst.Instruction loadGlobal(Register.base rd, int bitLen, IRConstant.GlobalVariable variable) {
        new AsmInst.lui(rd, "%hi(" + variable.getName() + ")", curBlock);
        return new AsmInst.load(rd, "%lo(" + variable.getName() + ")", bitLen, rd, curBlock);
    }

    private AsmInst.Instruction storeGlobal(Register.base src, int bitLen, IRConstant.GlobalVariable variable) {
        var rt = new Register.Virtual();
        new AsmInst.lui(rt, "%hi(" + variable.getName() + ")", curBlock);
        return new AsmInst.store(src, rt, "%lo(" + variable.getName() + ")", bitLen, curBlock);
    }

    @Override
    public Object visit(IRInst.LoadInst inst) {
        var ptr = inst.operands.get(0);
        if (ptr instanceof IRConstant.GlobalVariable) {
            return loadGlobal(getRegister(inst), inst.getType().bitLen, (IRConstant.GlobalVariable) ptr);
        } else {
            var base = getRegister(ptr);
            return new AsmInst.load(base, 0, inst.getType().bitLen, getRegister(inst), curBlock);
        }
    }

    @Override
    public Object visit(IRInst.StoreInst inst) {
        var dest = inst.operands.get(0);
        var src = inst.operands.get(1);
        if (dest instanceof IRConstant.GlobalVariable) {
            return storeGlobal(getRegister(src), dest.getType().bitLen, (IRConstant.GlobalVariable) dest);
        } else {
            return new AsmInst.store(getRegister(src), getRegister(dest),
                    0, dest.getType().bitLen, curBlock);
        }
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

    /* !!! The following are meaningless... !!! */
    @Override
    public Object visit(IRConstant.ConstInteger constant) {
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

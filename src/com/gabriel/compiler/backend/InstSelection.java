package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InstSelection implements IRVisitor {

    private final Map<IRConstant.Function, AsmStruct.Function> funcMap = new HashMap<>();
    private final Map<BasicBlock, AsmStruct.Block> blockMap = new HashMap<>();
    private final Map<Value, Register.base> regMap = new HashMap<>();
    private final Map<Value, Register.base> globalMap = new HashMap<>();
    private AsmStruct.Function curFunc = null;
    private AsmStruct.Block curBlock = null;


    private AsmStruct.Function getFunction(IRConstant.Function func) {
        funcMap.putIfAbsent(func, new AsmStruct.Function(func.getName(), func.getParamNum()));
        return funcMap.get(func);
    }

    private AsmStruct.Block getBlock(BasicBlock block) {
        blockMap.putIfAbsent(block, new AsmStruct.Block(block.getName()));
        return blockMap.get(block);
    }

    private Register.base getRegister(Value v) {
        if (v == null || v instanceof IRConstant.Null) return Register.Machine.get("zero");
        regMap.putIfAbsent(v, new Register.Virtual());
        var ret = regMap.get(v);
        if (v instanceof IRConstant.ConstInteger) {
            if (((IRConstant.ConstInteger) v).num == 0) return Register.Machine.get("zero");
            loadImm(ret, ((IRConstant.ConstInteger) v).num);
        }
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

    @Override
    public Object visit(IRConstant.GlobalVariable globalVariable) {
        var init = globalVariable.getOperand(0);
        var num = 0;
        if (init instanceof IRConstant.ConstInteger) num = ((IRConstant.ConstInteger) init).num;
        return new AsmStruct.GlobalVariable(globalVariable.getName(),
                globalVariable.getType().getByteNum(), num);
    }


    @Override
    public Object visit(IRConstant.ConstString constant) {
        return new AsmStruct.GlobalVariable(constant.getName(), constant.getType().getByteNum(), constant.orig);
    }

    @Override
    public Object visit(IRConstant.Function function) {
        curFunc = getFunction(function);
        curBlock = getBlock(function.blocks.get(0));

        //Placeholder for sp
        var sp = new AsmInst.ComputeRegImm("sub", Register.Machine.get("sp"), curFunc.stack.size, Register.Machine.get("sp"), curBlock);
        curFunc.setPlaceHolderForSp(sp, 0);

        //Save callee-save registers
        for (var reg : Register.Machine.calleeSaveExt.keySet()) {
            Register.save(reg, curBlock);
        }

        //Read args
        for (int i = 0; i < function.getParamNum(); i++) {
            var cur = function.getParam(i);
            if (i < 8) {
                new AsmInst.mv(getRegister(cur), Register.Machine.get("a" + i), curBlock);
            } else {
                new AsmInst.load(Register.Machine.get("sp"), (i - 8) * 4, 4, getRegister(cur), curBlock);
            }
        }
        for (int i = 0; i < function.blocks.size(); i++) {
            var block = function.blocks.get(i);
            var cur = (AsmStruct.Block) block.accept(this);
            if (i == 0) cur.setFirst();
            curFunc.addBlock(cur);
        }
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

    private boolean isOnlyForBranch(Value inst) {
        if (inst instanceof IRInst.CmpInst) {
            for (var user : inst.getUser())
                if (!(user instanceof IRInst.BranchInst)) return false;
            return true;
        }
        return false;
    }

    @Override
    public Object visit(IRInst.BranchInst inst) {
        if (!inst.isConditional()) {
            new AsmInst.jump(getBlock((BasicBlock) inst.getOperand(0)), curBlock);
        } else {
            var cond = inst.getOperand(0);
            if (isOnlyForBranch(cond)) {
                var cast = (IRInst.CmpInst) cond;
                new AsmInst.branch(cast.getCorresAsmOp(), getRegister(cast.getOperand(0)), getRegister(cast.getOperand(1)),
                        getBlock((BasicBlock) inst.getOperand(1)), curBlock);
            } else {
                new AsmInst.branch("blt", Register.Machine.get("zero"),
                        getRegister(inst.getOperand(0)), getBlock((BasicBlock) inst.getOperand(1)), curBlock);
            }
            new AsmInst.jump(getBlock((BasicBlock) inst.getOperand(2)), curBlock);
        }
        return null;
    }

    @Override
    public Object visit(IRInst.ReturnInst inst) {
        for (var reg : Register.Machine.calleeSaveExt.keySet()) {
            assert Register.calleeTmpSave.get(reg) != null;
            new AsmInst.mv(Register.Machine.get(reg), Register.calleeTmpSave.get(reg), curBlock);
        }
        if (!(inst.getType() instanceof IRType.VoidType))
            new AsmInst.mv(Register.Machine.get("a0"), getRegister(inst.getOperand(0)), curBlock);
        var sp = new AsmInst.ComputeRegImm("add", Register.Machine.get("sp"),
                curFunc.stack.size, Register.Machine.get("sp"), curBlock);
        curFunc.setPlaceHolderForSp(sp, 1);
        return new AsmInst.ret(curBlock);
    }

    private boolean isFit(int num) {
        return num >= -2048 && num < 2048;
    }

    private boolean isPowerOfTwo(int num) {
        if (num == 0) return false;
        double v = Math.log(num) / Math.log(2);
        return (int) (Math.ceil(v)) == (int) (Math.floor(v));
    }

    static Set<String> abel = Set.of("add", "and", "or", "xor");
    static Set<String> mustReg = Set.of("sll", "sra", "rem", "mul", "div");

    private boolean abel(String op) {
        for (var a : abel) {
            if (op.equals(a)) return true;
        }
        return false;
    }

    private boolean mustReg(String op) {
        for (var a : mustReg) {
            if (op.equals(a)) return true;
        }
        return false;
    }

    private AsmInst.Instruction compute(String op, Value lhs, Value rhs, Register.base dest) {
        if (op.equals("mul")) {
            if (lhs instanceof IRConstant.ConstInteger && isPowerOfTwo(((IRConstant.ConstInteger) lhs).num)) {
                var p = (int) (Math.log(((IRConstant.ConstInteger) lhs).num) / Math.log(2));
                return new AsmInst.ComputeRegImm("sll", getRegister(rhs), p, dest, curBlock);
            }
            if (rhs instanceof IRConstant.ConstInteger && isPowerOfTwo(((IRConstant.ConstInteger) rhs).num)) {
                var p = (int) (Math.log(((IRConstant.ConstInteger) rhs).num) / Math.log(2));
                return new AsmInst.ComputeRegImm("sll", getRegister(lhs), p, dest, curBlock);
            }
            return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);
        }
        if (op.equals("div")) {
            if (rhs instanceof IRConstant.ConstInteger && isPowerOfTwo(((IRConstant.ConstInteger) rhs).num)) {
                var p = (int) (Math.log(((IRConstant.ConstInteger) rhs).num) / Math.log(2));
                return new AsmInst.ComputeRegImm("sra", getRegister(lhs), p, dest, curBlock);
            }
            return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);
        }

        if (op.equals("rem")) {
            if (rhs instanceof IRConstant.ConstInteger && isPowerOfTwo(((IRConstant.ConstInteger) rhs).num)) {
                var p = ((IRConstant.ConstInteger) rhs).num - 1;
                return new AsmInst.ComputeRegImm("and", getRegister(lhs), p, dest, curBlock);
            }
            return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);
        }

        if (mustReg(op))
            return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);

        if (rhs instanceof IRConstant.ConstInteger && isFit(((IRConstant.ConstInteger) rhs).num))
            return new AsmInst.ComputeRegImm(op, getRegister(lhs), ((IRConstant.ConstInteger) rhs).num, dest, curBlock);
        if (!abel(op))
            return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);
        if (lhs instanceof IRConstant.ConstInteger && isFit(((IRConstant.ConstInteger) lhs).num))
            return new AsmInst.ComputeRegImm(op, getRegister(rhs), ((IRConstant.ConstInteger) lhs).num, dest, curBlock);
        return new AsmInst.ComputeRegReg(op, getRegister(lhs), getRegister(rhs), dest, curBlock);
    }

    @Override
    public Object visit(IRInst.BinaryOpInst inst) {
        var lhs = inst.operands.get(0);
        var rhs = inst.operands.get(1);
        var op = inst.getCorresAsmOp();
        return compute(op, lhs, rhs, getRegister(inst));
    }

    @Override
    public Object visit(IRInst.CmpInst inst) {
        if (isOnlyForBranch(inst)) return null;

        var lhs = inst.operands.get(0);
        var rhs = inst.operands.get(1);
        var op = inst.op;

        if (op.equals(">") || op.equals("<=")) {
            //Swap lhs and rhs
            lhs = inst.operands.get(1);
            rhs = inst.operands.get(0);
            op = op.equals(">") ? "<" : ">=";
        }

        AsmInst.Instruction ret;
        if (op.equals("<") || op.equals("!=")) {
            var op2 = op.equals("<") ? "slt" : "xor";
            var t = compute(op2, lhs, rhs, new Register.Virtual()); // new AsmInst.ComputeRegReg(op2, lhs, rhs, new Register.Virtual(), curBlock);
            ret = new AsmInst.ComputeReg("snez", t.rd, getRegister(inst), curBlock);
        } else {
            var op2 = op.equals(">=") ? "slt" : "xor";
            var t = compute(op2, lhs, rhs, new Register.Virtual()); //new AsmInst.ComputeRegReg(op2, lhs, rhs, new Register.Virtual(), curBlock);
            ret = new AsmInst.ComputeReg("seqz", t.rd, getRegister(inst), curBlock);
        }
        return ret;
    }

    @Override
    public Object visit(IRInst.GEPInst inst) {
        var base = inst.getOperand(0);
        if (base instanceof IRConstant.ConstString || base instanceof IRConstant.GlobalVariable) {
            return new AsmInst.la(getRegister(inst), base.getName(), curBlock);
        }

        assert base.getType() instanceof IRType.PointerType;
        if (((IRType.PointerType) base.getType()).getPointer() instanceof IRType.ClassType) {
            assert inst.zeroPad;
            int offset = ((IRType.ClassType) ((IRType.PointerType) base.getType()).getPointer()).getOffset(((IRConstant.ConstInteger)
                    inst.getOperand(2)).num);
            return new AsmInst.ComputeRegImm("add", getRegister(base), offset, getRegister(inst), curBlock);
        } else {
            assert inst.getType() instanceof IRType.PointerType;
            var ptr = ((IRType.PointerType) inst.getType()).getPointer();
            if (inst.getOperand(inst.zeroPad ? 2 : 1) instanceof IRConstant.ConstInteger) {
                int offset = ((IRConstant.ConstInteger) inst.getOperand(inst.zeroPad ? 2 : 1)).num;
                offset *= ptr.getByteNum();
                return new AsmInst.ComputeRegImm("add", getRegister(base), offset, getRegister(inst), curBlock);
            } else {
                var tmp = new Register.Virtual();
                compute("mul", inst.getOperand(inst.zeroPad ? 2 : 1), new IRConstant.ConstInteger(ptr.getByteNum()), tmp);
                return new AsmInst.ComputeRegReg("add", getRegister(base), tmp, getRegister(inst), curBlock);
            }
        }
    }

    @Override
    public Object visit(IRInst.CallInst inst) {
        var func = getFunction((IRConstant.Function) inst.operands.get(0));
        for (int i = inst.operands.size() - 1; i > 0; i--) {
            if (i <= 8) {
                new AsmInst.mv(Register.Machine.get("a" + (i - 1)), getRegister(inst.operands.get(i)), curBlock);
            } else {
                curFunc.pushStack(4);
                new AsmInst.store(getRegister(inst.operands.get(i)), Register.Machine.get("sp"), (i - 9) * 4,
                        inst.operands.get(i).getType().getByteNum(), curBlock);
            }
        }
//        inst.checkTailCall();
        new AsmInst.call(func, curBlock, inst.tail);

        //Get Return Value
        var ret = getRegister(inst);
        if (inst.getType() instanceof IRType.VoidType) return ret;
        new AsmInst.mv(ret, Register.Machine.get("a0"), curBlock);
        return ret;
    }

    private AsmInst.Instruction loadGlobal(Register.base rd, int byteNum, IRConstant.GlobalVariable variable) {
        new AsmInst.lui(rd, "%hi(" + variable.getName() + ")", curBlock);
        return new AsmInst.load(rd, "%lo(" + variable.getName() + ")", byteNum, rd, curBlock);
    }

    private AsmInst.Instruction storeGlobal(Register.base src, int byteNum, IRConstant.GlobalVariable variable) {
        var rt = new Register.Virtual();
        new AsmInst.lui(rt, "%hi(" + variable.getName() + ")", curBlock);
        return new AsmInst.store(src, rt, "%lo(" + variable.getName() + ")", byteNum, curBlock);
    }

    @Override
    public Object visit(IRInst.LoadInst inst) {
        var ptr = inst.operands.get(0);
        if (ptr instanceof IRConstant.GlobalVariable) {
            return loadGlobal(getRegister(inst), inst.getType().getByteNum(), (IRConstant.GlobalVariable) ptr);
        } else {
            var base = getRegister(ptr);
            return new AsmInst.load(base, 0, inst.getType().getByteNum(), getRegister(inst), curBlock);
        }
    }

    @Override
    public Object visit(IRInst.StoreInst inst) {
        var dest = inst.operands.get(0);
        var src = inst.operands.get(1);
        if (dest instanceof IRConstant.GlobalVariable) {
            return storeGlobal(getRegister(src), src.getType().getByteNum(), (IRConstant.GlobalVariable) dest);
        } else {
            return new AsmInst.store(getRegister(src), getRegister(dest),
                    0, src.getType().getByteNum(), curBlock);
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

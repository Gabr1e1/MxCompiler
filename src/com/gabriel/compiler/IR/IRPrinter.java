package com.gabriel.compiler.IR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IRPrinter implements IRVisitor {
    String filename;
    FileWriter codeWriter;

    public IRPrinter(String filename) {
        try {
            this.filename = filename;
            this.codeWriter = new FileWriter(filename);
            System.out.println("Starting IR Code Gen");
        } catch (IOException e) {
            System.out.println("Can't even create a fucking file");
        }
    }

    public IRPrinter() {
    }

    public void finish() {
        try {
            codeWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void writeCode(Object obj) {
        try {
            var str = (String) obj;
            codeWriter.write(str + "\n");
            System.out.print(str + "\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String stringify(List<Value> list) {
        List<String> ret = new ArrayList<>();
        list.forEach((x) -> ret.add(x.toString()));
        return String.join(", ", ret);
    }

    void addBuiltinFunctions(Module module) {
        module.builtin.forEach((x) -> writeCode(x.second));
    }

    @Override
    public Object visit(Module module) {
        addBuiltinFunctions(module);
        writeCode("");
        module.classes.forEach((className, type) -> {
            if (!(className.equals("string"))) {
                List<String> members = new ArrayList<>();
                type.members.forEach((m) -> members.add((String) m.accept(this)));
                writeCode(String.format("%%%s = type { %s }\n", type.className, String.join(", ", members)));
            }
        });
        module.globalVariables.forEach((var) -> writeCode(var.accept(this)));
        module.functions.forEach((function) -> function.accept(this));
        finish();
        return null;
    }

    @Override
    public Object visit(IRType.ClassType type) {
//        List<String> members = new ArrayList<>();
//        type.members.forEach((m) -> members.add((String) m.accept(this)));
//        return String.format("%%%s = type { %s }\n", type.className, String.join(", ", members));
        return String.format("%%%s", type.className);
    }

    @Override
    public Object visit(IRConstant.Function function) {
        writeCode(function.type.accept(this) + "{");
        function.blocks.forEach((block) -> block.accept(this));
        writeCode("}\n");
        return null;
    }

    @Override
    public Object visit(BasicBlock block) {
        if (block.instructions.size() == 0) {
            return null;
        }
        writeCode(block.getName() + ":");
        for (int i = 0; i < block.instructions.size(); i++) {
            var inst = block.instructions.get(i);
            writeCode(inst.accept(this));
            if (IRInst.isTerminator(inst)) {
                block.instructions = block.instructions.subList(0, i + 1);
                break;
            }
        }
        writeCode("");
        return null;
    }

    @Override
    public Object visit(Value value) {
        return null;
    }

    @Override
    public Object visit(IRInst.AllocaInst inst) {
        var t = ((IRType.PointerType) inst.type).getBase();
        return String.format("%s = alloca %s, align %d", inst.getPrintName(),
                t.accept(this), t.getByteNum());
    }

    @Override
    public Object visit(IRConstant.GlobalVariable globalVariable) {
        return String.format("%s = global %s, align %d", globalVariable.getPrintName(),
                globalVariable.init.accept(this), globalVariable.type.getByteNum());
    }

    @Override
    public Object visit(IRInst.BranchInst inst) {
        if (inst.cond != null)
            return String.format("br %s, %s, %s", inst.cond, inst.taken, inst.notTaken);
        else
            return String.format("br %s", inst.taken);
    }

    @Override
    public Object visit(IRInst.ReturnInst inst) {
        return "ret " + inst.v;
    }

    @Override
    public Object visit(IRInst.BinaryOpInst inst) {
        return String.format("%s = %s %s, %s", inst.getPrintName(), inst.getCorresOp(),
                inst.lhs, inst.rhs.getPrintName());
    }

    @Override
    public Object visit(IRInst.CmpInst inst) {
        return String.format("%s = icmp %s %s, %s", inst.getPrintName(), inst.getCorresOp(),
                inst.lhs, inst.rhs.getPrintName());
    }

    @Override
    public Object visit(IRInst.StoreInst inst) {
        return String.format("store %s, %s", inst.from, inst.dest);
    }

    @Override
    public Object visit(IRInst.GEPInst inst) {
        return String.format("%s = getelementptr %s, %s %s, %s", inst.getPrintName(), ((IRType.PointerType) inst.base.type).pointer.accept(this),
                inst.base.type.accept(this), inst.base.getPrintName(), stringify(inst.operands));
    }

    @Override
    public Object visit(IRInst.CallInst inst) {
        if (inst.type instanceof IRType.VoidType)
            return String.format("call %s @%s(%s)", ((IRType.FunctionType) inst.func.type).returnType.accept(this),
                    inst.func.name, stringify(inst.args));
        else return String.format("%s = call %s @%s(%s)", inst.getPrintName(),
                ((IRType.FunctionType) inst.func.type).returnType.accept(this), inst.func.name, stringify(inst.args));
    }

    @Override
    public Object visit(IRInst.LoadInst inst) {
        return String.format("%s = load %s, %s", inst.getPrintName(), ((IRType.PointerType) inst.ptr.type).pointer, inst.ptr);
    }

    @Override
    public Object visit(IRInst.CastInst inst) {
        return String.format("%s = bitcast %s to %s", inst.getPrintName(), inst.from, inst.to.accept(this));
    }

    @Override
    public Object visit(IRInst.SextInst inst) {
        return String.format("%s = sext %s to %s", inst.getPrintName(), inst.from, inst.to.accept(this));
    }

    @Override
    public Object visit(IRInst.TruncInst inst) {
        return String.format("%s = trunc %s to %s", inst.getPrintName(), inst.from, inst.to.accept(this));
    }

    @Override
    public Object visit(IRConstant.ConstInteger constant) {
        return String.format("%s %d", constant.type.accept(this), constant.num);
    }

    @Override
    public Object visit(IRConstant.ConstString constant) {
        return String.format("%s = constant %s c\"%s\\00\", align 1", constant.getPrintName(), constant.type.accept(this), constant.str);
    }

    @Override
    public Object visit(IRConstant.Null constant) {
        return constant.type.accept(this) + " null";
    }

    @Override
    public Object visit(IRConstant.Void constant) {
        return "void";
    }

    @Override
    public Object visit(IRType.VoidType type) {
        return "void";
    }

    @Override
    public Object visit(IRType.IntegerType type) {
        return String.format("i%d", type.bitLen);
    }

    @Override
    public Object visit(IRType.PointerType type) {
        return type.pointer.accept(this) + "*";
    }

    @Override
    public Object visit(IRType.ArrayType type) {
        return String.format("[%d x %s]", type.dimension, type.baseType.accept(this));
    }

    @Override
    public Object visit(IRType.FunctionType type) {
        return String.format("define %s @%s(%s)", type.returnType.accept(this), type.funcName, stringify(type.params));
    }

    @Override
    public Object visit(IRType.LabelType type) {
        return "label";
    }
}

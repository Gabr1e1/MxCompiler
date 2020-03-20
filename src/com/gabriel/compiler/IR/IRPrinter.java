package com.gabriel.compiler.IR;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IRPrinter implements IRVisitor {
    String filename;
    FileWriter codeWriter;

    IRPrinter(String filename) {
        try {
            this.filename = filename;
            this.codeWriter = new FileWriter(filename);
            System.out.println("Starting IR Code Gen");
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

    private void genCode(Object obj) {
        try {
            var str = (String) obj;
            codeWriter.write(str + "\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public Object visit(Module module) {
        module.classes.forEach((className, type) -> {
            genCode(type.accept(this));
        });
        module.globalVariables.forEach((var) -> var.accept(this));
        module.functions.forEach((function) -> function.accept(this));
        return null;
    }

    @Override
    public Object visit(Instruction inst) {
        inst.accept(this);
        return null;
    }

    @Override
    public Object visit(IRInst.AllocaInst inst) {
        var str = String.format("%%%s = alloca %s, align %d", inst.name, inst.type.accept(this), inst.type.getByteNum());
        genCode(str);
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
    public Object visit(Constant constant) {
        return null;
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
    public Object visit(IRConstant.GlobalVariable constant) {
        return null;
    }

    @Override
    public Object visit(IRConstant.Function constant) {
        return null;
    }

    @Override
    public Object visit(Type type) {
        return type.visit(this);
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
        List<String> members = new ArrayList<>();
        type.members.forEach((m) -> members.add((String) m.accept(this)));
        return String.format("%% %s = type { %s }", type.className, String.join(",", members);
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

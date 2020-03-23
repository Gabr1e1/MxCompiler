package com.gabriel.compiler.IR;

import java.util.List;
import java.util.Map;

abstract class Type {
    public String baseType;
    public int bitLen;

    public int getByteNum() {
        return bitLen / 8 + (bitLen % 8 != 0 ? 1 : 0);
    }

    public abstract Object accept(IRVisitor visitor);

    public String toString() {
        IRPrinter t = new IRPrinter();
        return (String) this.accept(t);
    }
}

public class IRType {
    public static Type convert(String type) {
        if (type.equals("void")) return new VoidType();
        else if (type.equals("int") || type.equals("char") || type.equals("bool"))
            return new IntegerType(type);
        else if (type.charAt(type.length() - 1) == '*')
            return new PointerType(convert(type.substring(0, type.length() - 1)));
        return null;
    }

    public static Type convert(com.gabriel.compiler.frontend.Type type) {
        if (type.isArray()) {
            var pointer = new com.gabriel.compiler.frontend.Type(type.baseType, type.getDimension() - 1);
            return new PointerType(convert(pointer));
        } else if (type.isString()) {
            return new PointerType(new IntegerType("char"));
        } else if (type.isVoid()) {
            return new VoidType();
        } else if (type.isPrimitiveNonArrayType()) {
            return new IntegerType(type.toString());
        } else {
            //TODO
//            return new ClassType(type.toString());
        }
        return null;
    }

    public static class VoidType extends Type {
        VoidType() {
            this.bitLen = 0;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class IntegerType extends Type {
        public static final Map<String, Integer> BitLen = Map.of("char", 8,
                "bool", 1, "int", 32);

        IntegerType(String baseType) {
            this.baseType = baseType;
            this.bitLen = BitLen.get(baseType);
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ClassType extends Type {
        List<Type> members;
        List<String> member_name;
        String className;

        ClassType(String className, List<Type> members, List<String> member_name) {
            this.className = className;
            this.members = members;
            this.member_name = member_name;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        String getName() {
            return className;
        }
    }

    public static class PointerType extends Type {
        Type pointer;

        PointerType(Type pointer) {
            this.pointer = pointer;
            this.bitLen = 32; //32-bit address
        }

        Type getBase() {
            return pointer;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    //NOT NEEDED ACTUALLY
//    public static class ArrayType extends Type {
//        Type baseType;
//        int dimension;
//
//        ArrayType(Type baseType, int dimension) {
//            this.baseType = baseType;
//            this.dimension = dimension;
//        }
//
//        void setDimension(int x) {
//            dimension = x;
//        }
//        @Override
//        public Object accept(IRVisitor visitor) {
//            return visitor.visit(this);
//        }
//    }

    public static class FunctionType extends Type {
        List<Value> params;
        Type returnType;
        String funcName;

        FunctionType(List<Value> params, Type returnType, String funcName) {
            this.params = params;
            this.returnType = returnType;
            this.funcName = funcName;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class LabelType extends Type {
        String label;

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

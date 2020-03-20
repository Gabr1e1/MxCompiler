package com.gabriel.compiler.IR;

import java.util.List;
import java.util.Map;

public class IRType {
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
            return new ClassType(type.toString());
        }
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
        public static final Map<String, Integer> BitLen = Map.of("char", 1,
                "bool", 1, "int", 4, "long", 8);

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
            this.bitLen = 4; //32-bit address
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class ArrayType extends Type {
        Type baseType;
        int dimension;

        ArrayType(Type baseType, int dimension) {
            this.baseType = baseType;
            this.dimension = dimension;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class FunctionType extends Type {
        List<Value> params;
        Type returnType;

        FunctionType(List<Value> params, Type returnType) {
            this.params = params;
            this.returnType = returnType;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }

    public static class LabelType extends Type {
        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }
    }
}

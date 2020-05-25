package com.gabriel.compiler.IR;

import com.gabriel.compiler.util.Pair;

import java.util.List;
import java.util.Map;

public class IRType {
    public abstract static class Type {
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

    public static Value getDefaultValue(Type type) {
        if (type instanceof IntegerType)
            return new IRConstant.ConstInteger(0, type);
        else if (type instanceof PointerType)
            return new IRConstant.Null(type);
        return null;
    }

    public static boolean isClassPointer(Type f) {
        while (f instanceof IRType.PointerType) {
            f = ((IRType.PointerType) f).pointer;
        }
        return f instanceof IRType.ClassType;
    }

    public static boolean isClassPointer(Value v) {
        var f = ((IRInst.Instruction) v).type;
        return isClassPointer(f);
    }

    public static Type convert(String type) {
        if (type.equals("void")) return new VoidType();
        else if (type.equals("int") || type.equals("char") || type.equals("bool") || type.equals("long"))
            return new IntegerType(type);
        else if (type.charAt(type.length() - 1) == '*')
            return new PointerType(convert(type.substring(0, type.length() - 1)));
        return null;
    }

    public static Type convert(com.gabriel.compiler.frontend.Type type, Module module) {
        if (type.isArray()) {
            var pointer = new com.gabriel.compiler.frontend.Type(type.baseType, type.getDimension() - 1);
            return new PointerType(convert(pointer, module));
        } else if (type.isString()) {
            return new PointerType(new IntegerType("char"));
        } else if (type.isVoid()) {
            return new VoidType();
        } else if (type.isPrimitiveNonArrayType()) {
            return new IntegerType(type.toString());
        } else {
            return new PointerType(module.getClass("struct." + type.baseType));
        }
    }

    public static Type dePointer(Type type) {
        if (type instanceof PointerType) return ((PointerType) type).pointer;
        else return type;
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
                "bool", 1, "int", 32, "long", 64);

        IntegerType(String baseType) {
            this.baseType = baseType;
            this.bitLen = BitLen.get(baseType);
        }

        IntegerType(int bitLen) {
            this.bitLen = bitLen;
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
        IRConstant.Function constructor;

        ClassType(String className, List<Type> members, List<String> member_name, int size) {
            this.className = className;
            this.members = members;
            this.member_name = member_name;
            this.bitLen = size;
        }

        @Override
        public Object accept(IRVisitor visitor) {
            return visitor.visit(this);
        }

        String getName() {
            return className;
        }

        Pair<Type, Integer> getMember(String name) {
            for (int i = 0; i < members.size(); i++) {
                if (name.equals(member_name.get(i))) {
                    return new Pair<>(members.get(i), i);
                }
            }
            return null;
        }

        void addConstructor(IRConstant.Function f) {
            constructor = f;
        }

        public int getOffset(int kth) {
            int ret = 0;
            for (int i = 0; i < kth; i++) {
                var t = members.get(i);
                ret += (t.getByteNum() - (ret % t.getByteNum())) % t.getByteNum();
                ret += t.getByteNum();
            }
            return ret;
        }
    }

    public static class PointerType extends Type {
        Type pointer;

        PointerType(Type pointer) {
            this.pointer = pointer;
            this.bitLen = 32;
        }

        public Type getPointer() {
            return pointer;
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
        public List<Value> params;
        public Type returnType;
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

        void addFront(Value v) {
            params.add(0, v);
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

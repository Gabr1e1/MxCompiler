package com.gabriel.compiler.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum TypeKind {CLASS, FUNCTION, VARIABLE}

public class Type {
    private static final String[] PRIMITIVE = {"bool", "int", "void", "string"};

    public String baseType = "";
    public List<Integer> array = new ArrayList<>();
    public TypeKind typeKind;

    //for class & function
    public Node node;

    public Type(String baseType) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
    }

    public Type(String baseType, int dimension) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
        for (int i = 0; i < dimension; i++) array.add(-1);
    }

    public Type(TypeKind kind, Node node) {
        this.typeKind = kind;
        this.node = node;
    }

    public void appendDimension(int x) {
        array = (array == null) ? (new ArrayList<>()) : array;
        array.add(x);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(baseType);
        for (int i : array) ret.append("[").append(i != -1 ? i : "").append("]");
        return ret.toString();
    }

    public boolean isPrimitiveType() {
        return Arrays.asList(PRIMITIVE).contains(baseType);
    }

    public boolean isArray() {
        return array != null && array.size() > 0;
    }

    public boolean isBool() {
        return baseType.equals("bool");
    }

    public boolean isInt() {
        return baseType.equals("int");
    }

    public boolean isString() {
        return baseType.equals("string");
    }

    public boolean isVoid() {
        return baseType.equals("void");
    }

    public static boolean isSameType(Type type1, Type type2) {
        if (!type1.baseType.equals(type2.baseType)) return false;
        if (type1.typeKind != type2.typeKind) return false;
        if (type1.array.size() != type2.array.size()) return false;
        if (type1.array.size() != 0) {
            for (int i = 0; i < type1.array.size(); i++) {
                if (type1.array.get(i) == -1 || type2.array.get(i) == -1) continue;
                if (type1.array.get(i) != type2.array.get(i)) return false;
            }
        }
        return true;
    }
}

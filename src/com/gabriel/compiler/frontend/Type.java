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

    public Type(String baseType) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
    }

    public Type(String baseType, int dimension) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
        for (int i = 0; i < dimension; i++) array.add(-1);
    }

    public Type(TypeKind kind) {
        this.typeKind = kind;
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
        return array != null;
    }
}

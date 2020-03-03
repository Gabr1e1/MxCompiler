package com.gabriel.compiler.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Type {
    private static final String[] PRIMITIVE = {"bool", "int", "void", "string"};
//    private enum TypeKind { CLASS, FUNCTION, VARIABLE };

    public String baseType;
    public List<Integer> array = new ArrayList<>();

    public Type(String baseType) {
        this.baseType = baseType;
    }

    public Type(String baseType, int dimension) {
        this.baseType = baseType;
        for (int i = 0; i < dimension; i++) array.add(-1);
    }

    public void appendDimension(int x) {
        array = (array == null) ? (new ArrayList<>()) : array;
        array.add(x);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(baseType);
        for (int i : array) ret.append("[").append(i).append("]");
        return ret.toString();
    }

    public boolean isPrimitiveType() {
        return Arrays.asList(PRIMITIVE).contains(baseType);
    }

    public boolean isArray() {
        return array != null;
    }
}

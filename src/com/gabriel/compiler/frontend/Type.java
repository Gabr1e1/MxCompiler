package com.gabriel.compiler.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum TypeKind {CLASS, FUNCTION, VARIABLE, LOOP}

public class Type implements Cloneable {
    private static final String[] PRIMITIVE = {"bool", "int", "void", "string"};

    public String baseType = "";
    public List<Integer> array = new ArrayList<>();
    public TypeKind typeKind;
    public boolean lvalue;

    //for class & function
    public Node node;

    public Type(Type other) {
        this.baseType = other.baseType;
        this.array = new ArrayList<>(other.array);
        this.typeKind = other.typeKind;
        this.lvalue = other.lvalue;
        this.node = other.node;
    }

    public Type(String baseType) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
        this.lvalue = false;
    }

    public Type(String baseType, boolean lvalue) {
        this.baseType = baseType;
        if (lvalue) {
            this.typeKind = TypeKind.VARIABLE;
        }
        this.lvalue = lvalue;

    }

    public Type(String baseType, int dimension) {
        this.baseType = baseType;
        this.typeKind = TypeKind.VARIABLE;
        for (int i = 0; i < dimension; i++) array.add(-1);
    }

    public Type(TypeKind kind, Node node) {
        if (node instanceof ASTNode.Function)
            this.baseType = ((ASTNode.Function) node).funcName;
        else if (node instanceof ASTNode.Class)
            this.baseType = ((ASTNode.Class) node).className;
        this.typeKind = kind;
        this.node = node;
    }

    public void setDimension(int x) {
        array = new ArrayList<>();
        for (int i = 0; i < x; i++) array.add(-1);
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(baseType);
        for (int i : array) ret.append("[").append(i != -1 ? i : "").append("]");
        return ret.toString();
    }

    public boolean isPrimitiveType() {
        return Arrays.asList(PRIMITIVE).contains(baseType);
    }

    public static boolean isPrimitiveType(String id) {
        return Arrays.asList(PRIMITIVE).contains(id);
    }

    public boolean isPrimitiveNonArrayType() {
        return Arrays.asList(PRIMITIVE).contains(baseType) && !isArray();
    }

    public boolean isArray() {
        return array.size() > 0;
    }

    public int getDimension() {
        return array.size();
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

    public boolean isNull() {
        return baseType.equals("null");
    }

    public boolean isVariable() {
        return typeKind == TypeKind.VARIABLE;
    }

    public boolean isFunction() {
        return typeKind == TypeKind.FUNCTION;
    }

    public boolean isClass() {
        return typeKind == TypeKind.CLASS;
    }

    public boolean isLeftValue() {
        return this.lvalue;
    }

    public void setRightValue() {
        this.lvalue = false;
    }

    public void setLeftValue() {
        this.lvalue = true;
    }

    public static boolean isSameType(Type type1, Type type2) {
        if (type1 == null || type2 == null) return false;
        if ((type1.isNull() && !type2.isPrimitiveNonArrayType())
                || (type2.isNull() && !type1.isPrimitiveNonArrayType())) return true;
        if (!type1.baseType.equals(type2.baseType)) return false;
//        if (type1.typeKind != type2.typeKind) return false;
        if (type1.array.size() != type2.array.size()) return false;
        if (type1.array.size() != 0) {
            for (int i = 0; i < type1.array.size(); i++) {
                if (type1.array.get(i) == -1 || type2.array.get(i) == -1) continue;
                if (!type1.array.get(i).equals(type2.array.get(i))) return false;
            }
        }
        return true;
    }
}

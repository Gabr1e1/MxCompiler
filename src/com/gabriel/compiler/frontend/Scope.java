package com.gabriel.compiler.frontend;

import com.gabriel.compiler.error.SemanticError;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    public Scope father;
    public String name;
    public Map<String, Type> symbolTable;

    public Scope(String name, Scope father) {
        this.name = name;
        this.father = father;
        symbolTable = new HashMap<>();
    }

    public void addSymbol(String id, Type type) {
        if (!symbolTable.containsKey(id)) {
            symbolTable.put(id, type);
        } else {
            throw new SemanticError.Redeclare(id, name);
        }
    }

    public Type find(String id) {
        if (symbolTable.containsKey(id)) {
            return symbolTable.get(id);
        } else {
            if (father != null) return father.find(id);
            else return null;
        }
    }

    public Type findClass(String id) {
        if (father != null) return father.findClass(id);
        else //in global scope
            return find(id);
    }


    public Scope findScope(String id) {
        if (symbolTable.containsKey(id)) {
            return this;
        } else {
            if (father != null) return father.findScope(id);
            else return null;
        }
    }

    public void modify(String id, Type type) {
        symbolTable.put(id, type);
    }

    public boolean isValidType(Type type) {
        //Ignore check if type represents a class / function
        if (type.typeKind == TypeKind.CLASS || type.typeKind == TypeKind.FUNCTION) return true;
        if (type.isPrimitiveType()) return true;
        Type t = this.find(type.baseType);
        return t != null && t.typeKind == TypeKind.CLASS;
    }

    public boolean containsVar(String id) {
        Type t = this.find(id);
        return t != null && t.typeKind == TypeKind.VARIABLE;
    }

    public boolean containsFunc(String id) {
        Type t = this.find(id);
        return t != null && t.typeKind == TypeKind.FUNCTION;
    }

    public ASTNode.Function belongFunction(String name) {
        Type t = find(name.equals("") ? this.name : name);
        if (t != null && t.typeKind == TypeKind.FUNCTION)
            return (ASTNode.Function) t.node;
        if (father != null) return father.belongFunction(this.name);
        else return null;
    }

    public ASTNode.Class belongClass() {
        Type t = findClass(this.name);
        if (t != null && t.typeKind == TypeKind.CLASS)
            return (ASTNode.Class) t.node;
        if (father != null) return father.belongClass();
        else return null;
    }

    public boolean withinLoop() {
        if (name.equals("for") || name.equals("while"))
            return true;
        else if (father != null) return father.withinLoop();
        else return false;
    }
}

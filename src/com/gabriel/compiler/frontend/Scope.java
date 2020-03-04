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
        symbolTable = new HashMap<String, Type>();
    }

    //TODO: check if not equal to any function or class name
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
}

package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.Scope;
import com.gabriel.compiler.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    Map<String, Value> symbolTable = new HashMap<>();
    Map<Pair<Scope, String>, Value> corresTable = new HashMap<>();

    void put(Value value, Scope scope) {
        symbolTable.put(value.name, value);
        corresTable.put(new Pair<>(scope, value.originalName), value);
    }

    Value get(String name) {
        return symbolTable.get(name);
    }

    Value getFromOriginal(String name, Scope scope) {
        return corresTable.get(new Pair<>(scope, name));
    }
}

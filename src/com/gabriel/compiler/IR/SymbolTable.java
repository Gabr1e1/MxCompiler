package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.Scope;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    Map<String, Value> symbolTable = new HashMap<>();
    Map<String, Map<Scope, Value>> corresTable = new HashMap<>();

    void put(Value value, Scope scope) {
        symbolTable.put(value.name, value);
        if (corresTable.containsKey(value.originalName)) {
            var map = corresTable.get(value.originalName);
            map.put(scope, value);
        } else {
            var map = new HashMap<Scope, Value>();
            map.put(scope, value);
            corresTable.put(value.originalName, map);
        }
    }

    Value get(String name) {
        return symbolTable.get(name);
    }

    Value getFromOriginal(String name, Scope scope) {
        var t = corresTable.get(name);
        if (t != null)
            return t.get(scope);
        return null;
    }
}

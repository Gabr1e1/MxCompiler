package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.Scope;
import com.gabriel.compiler.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    Map<String, Value> symbolTable = new HashMap<>();
    Map<String, Integer> counter = new HashMap<>();
    Map<Pair<Scope, String>, Value> corresTable = new HashMap<>();

    private String gen(String name, Value value) {
        int cnt = 0;
        if (symbolTable.get(name) != null) {
            cnt = counter.get(name) + 1;
            counter.put(name, cnt);
        }
        return name + ((cnt == 0) ? "" : cnt);
    }

    void put(String name, Value value, Scope scope) {
        var newName = gen(name, value);
        value.setName(newName);
        symbolTable.put(newName, value);
        corresTable.put(new Pair<>(scope, name), value);
    }

    Value get(String name) {
        return symbolTable.get(name);
    }

    Value getFromOriginal(String name, Scope scope) {
        return corresTable.get(new Pair<>(scope, name));
    }
}

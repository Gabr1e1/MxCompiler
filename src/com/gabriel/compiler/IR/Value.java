package com.gabriel.compiler.IR;

import java.util.HashMap;
import java.util.Map;

public class Value {
    static Map<String, Integer> counter = new HashMap<>();

    String originalName;
    String name;
    Type type;

    private String gen(String name) {
        int cnt = 0;
        if (counter.get(name) != null) {
            cnt = counter.get(name) + 1;
            counter.put(name, cnt);
        }
        return name + ((cnt == 0) ? "" : cnt);
    }

    public Value(String originalName, Type type) {
        this.originalName = originalName;
        this.type = type;
        this.name = gen(originalName);
    }

    String getName() {
        return name;
    }
}

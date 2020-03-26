package com.gabriel.compiler.IR;

import java.util.HashMap;
import java.util.Map;

public class Value {
    static Map<String, Integer> counter = new HashMap<>();
    static int label = 0;

    String originalName;
    String name;
    Type type;

    static String labelAllocator() {
        return "label_" + (++label);
    }

    private String gen(String name) {
        int cnt = 0;
        if (counter.get(name) != null) {
            cnt = counter.get(name) + 1;
            counter.put(name, cnt);
        } else {
            counter.put(name, 0);
        }
        return name + (cnt == 0 ? "" : cnt);
    }

    private boolean noName() {
        return (this instanceof IRConstant.ConstInteger);
    }

    public Value(String originalName, Type type) {
        this.originalName = originalName;
        this.type = type;
        this.name = noName() ? "" : gen(originalName);
    }

    String getName() {
        return name;
    }

    String getPrintName() {
        return "%" + name;
    }

    String getOrignalName() {
        return originalName;
    }

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        IRPrinter t = new IRPrinter();
        if (type instanceof IRType.VoidType)
            return (String) type.accept(t);
        else return type.accept(t) + " %" + name;
    }

    void replaceAllUsesWith(Value other) {
        //TODO!!!
    }
}

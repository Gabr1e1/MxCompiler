package com.gabriel.compiler.IR;

public class Value {
    String name;
    Type type;

    public Value(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return name;
    }

    void setName(String newName) {
        this.name = newName;
    }
}

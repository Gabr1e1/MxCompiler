package com.gabriel.compiler.IR;

import java.util.List;

public class User extends Value {
    List<Value> operands;

    User(String name, Type type) {
        super(name, type);
    }

    void addOperand(Value v) {
        operands.add(v);
    }

    Value getOperand(int i) {
        return operands.get(i);
    }
}

package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User extends Value {
    List<Value> operands = new ArrayList<>();

    User(String name, Type type) {
        super(name, type);
    }

    void addOperand(Value... v) {
        operands.addAll(Arrays.asList(v));
        (Arrays.asList(v)).forEach((i) -> i.user.addUser(this));
    }

    void addOperand(List<Value> v) {
        operands.addAll(v);
        v.forEach((i) -> i.user.addUser(this));
    }

    Value getOperand(int i) {
        return operands.get(i);
    }

    int findOperand(Value v) {
        return operands.indexOf(v);
    }
}

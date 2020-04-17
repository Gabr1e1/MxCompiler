package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User extends Value {
    List<Value> operands = new ArrayList<>();

    User(String name, IRType.Type type) {
        super(name, type);
    }

    public void addOperand(Value... v) {
        operands.addAll(Arrays.asList(v));
        (Arrays.asList(v)).forEach((i) -> {
            if (i != null) i.user.addUser(this);
        });
    }

    public void addOperand(List<Value> v) {
        operands.addAll(v);
        v.forEach((i) -> i.user.addUser(this));
    }

    public Value getOperand(int i) {
        return operands.get(i);
    }

    public int findOperand(Value v) {
        return operands.indexOf(v);
    }
}

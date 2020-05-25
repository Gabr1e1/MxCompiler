package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User extends Value {
    public List<Value> operands = new ArrayList<>();

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

    public void delOperand(Value... v) {
        (Arrays.asList(v)).forEach((i) -> {
            if (i != null) i.user.delUser(this);
            operands.remove(i);
        });
    }

    public void delOperand(int kth) {
        if (operands.get(kth) != null) operands.get(kth).user.delUser(this);
        operands.remove(kth);
    }

    public void replaceOperand(Value oldValue, Value newValue) {
        if (oldValue != null) oldValue.user.delUser(this);
        if (newValue != null) newValue.user.addUser(this);
        operands.set(findOperand(oldValue), newValue);
    }

    public void replaceAllOperand(Value oldValue, Value newValue) {
        while (findOperand(oldValue) != -1)
            replaceOperand(oldValue, newValue);
    }

    public Value getOperand(int i) {
        return operands.get(i);
    }

    public int findOperand(Value v) {
        return operands.indexOf(v);
    }
}

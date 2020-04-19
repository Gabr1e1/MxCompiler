package com.gabriel.compiler.IR;

import java.util.HashMap;
import java.util.Map;

public class Value {
    static Map<String, Integer> counter = new HashMap<>();
    static int label = 0;

    String originalName;
    String name;
    IRType.Type type;
    Use user;

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
        return name + (cnt == 0 ? "" : "." + cnt);
    }

    private boolean noName() {
        return (this instanceof IRConstant.ConstInteger);
    }

    public Value(String originalName, IRType.Type type) {
        this.originalName = originalName;
        this.type = type;
        this.name = noName() ? "" : gen(originalName);
        this.user = new Use();
    }

    public void changeName(String newName) {
        this.originalName = newName;
        this.name = noName() ? "" : gen(newName);
    }

    public String getName() {
        return name;
    }

    public String getPrintName() {
        return "%" + name;
    }

    public String getOriginalName() {
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

    public void replaceAllUsesWith(Value other) {
        for (var u : user.user) {
            var index = u.findOperand(this);
            u.operands.set(index, other);
            other.user.addUser(u);
        }
        user = new Use();
    }

    public Use getUser() {
        return user;
    }

    public IRType.Type getType() {
        return type;
    }
}

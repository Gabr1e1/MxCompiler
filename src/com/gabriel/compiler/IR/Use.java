package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class Use {
    List<Value> user = new ArrayList<>();

    void addUser(Value v) {
        user.add(v);
    }

    void deleteUser(Value v) {
        user.remove(v);
    }
}

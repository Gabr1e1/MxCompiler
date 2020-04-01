package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class Use {
    List<User> user = new ArrayList<>();

    void addUser(User u) {
        user.add(u);
    }

    void deleteUser(Value u) {
        user.remove(u);
    }
}

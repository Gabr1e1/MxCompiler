package com.gabriel.compiler.IR;

import java.util.ArrayList;
import java.util.List;

public class Use {
    public List<User> user = new ArrayList<>();

    void addUser(User u) {
        user.add(u);
    }

    void delUser(User u) {
        user.remove(u);
    }
}

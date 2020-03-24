package com.gabriel.compiler.IR;

import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module {
    List<IRConstant.Function> functions = new ArrayList<>();
    List<IRConstant.GlobalVariable> globalVariables = new ArrayList<>();
    Map<String, IRType.ClassType> classes = new HashMap<>();
    List<Pair<IRConstant.Function, String>> builtin = new ArrayList<>();

    String name;

    Module(String name) {
        this.name = name;
    }

    void addFunction(IRConstant.Function function) {
        functions.add(function);
    }

    void addGlobalVariable(IRConstant.GlobalVariable globalVariable) {
        globalVariables.add(globalVariable);
    }

    void addClass(String className, IRType.ClassType type) {
        classes.put(className, type);
    }

    IRType.ClassType getClass(String className) {
        if (classes.get(className) == null) {
            return classes.get("struct." + className);
        } else return classes.get(className);
    }
}

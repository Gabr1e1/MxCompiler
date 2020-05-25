package com.gabriel.compiler.IR;

import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module {
    public List<IRConstant.Function> functions = new ArrayList<>();
    public List<Value> globalVariables = new ArrayList<>();
    public Map<String, IRType.ClassType> classes = new HashMap<>();
    public List<Pair<IRConstant.Function, String>> builtin = new ArrayList<>();

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

    public IRConstant.Function getFunction(String funcName) {
        for (var func : functions) {
            if (func.getName().equals(funcName)) return func;
        }
        return null;
    }

    public List<IRConstant.Function> getAllBuiltinFunctions() {
        var ret = new ArrayList<IRConstant.Function>();
        builtin.forEach(function -> ret.add(function.first));
        return ret;
    }
}



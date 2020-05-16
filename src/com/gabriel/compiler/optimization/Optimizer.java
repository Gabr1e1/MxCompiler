package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Optimizer {
    Module module;
    private List<Object> optims = new ArrayList<>();

    public Optimizer(Module module) {
        this.module = module;
    }

    public abstract static class runOnFunction {
        abstract void exec(IRConstant.Function func);

        abstract String print();
    }

    public void addPass(runOnFunction... pass) {
        optims.addAll(Arrays.asList(pass));
    }

    public void useDefaultPass() {
        addPass(new Mem2Reg());
        addPass(new CFGSimplifier(), new MSDCE());
        addPass(new CFGSimplifier());
    }

    public void optimize() {
        for (var optim : optims) {
            if (optim instanceof runOnFunction) {
                module.functions.forEach((func) -> {
                    if (func.isNormal()) {
                        System.err.printf("Optimize Pass %s for %s\n", ((runOnFunction) optim).print(), func.getName());
                        ((runOnFunction) optim).exec(func);
                    }
                });
            }
        }
    }
}

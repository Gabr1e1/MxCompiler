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
    }

    public void addPass(runOnFunction... pass) {
        optims.addAll(Arrays.asList(pass));
    }

    public void optimize() {
        for (var optim : optims) {
            if (optim instanceof runOnFunction) {
                module.functions.forEach(((runOnFunction) optim)::exec);
            }
        }
    }
}
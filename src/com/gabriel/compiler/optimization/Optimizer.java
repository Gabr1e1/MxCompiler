package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.Module;

import java.util.ArrayList;

public class Optimizer {
    Module module;

    public Optimizer(Module module) {
        this.module = module;
    }

    abstract static class runOnFunction {
        abstract void exec(IRConstant.Function func);
    }

    public void optimize() {
        var optims = new ArrayList<>();

        optims.add(new Mem2Reg());
        optims.add(new MSDCE());

        for (var optim : optims) {
            if (optim instanceof runOnFunction) {
                module.functions.forEach(((runOnFunction) optim)::exec);
            }
        }
    }
}

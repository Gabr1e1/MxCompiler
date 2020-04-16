package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.Module;

public class Optimizer {
    Module module;

    public Optimizer(Module module) {
        this.module = module;
    }

    private abstract class runOnFunction {
        abstract void exec(IRConstant.Function func);

        void run() {
            for (var func : module.functions) {
                exec(func);
            }
        }
    }

    public class Mem2Reg extends runOnFunction {

        @Override
        void exec(IRConstant.Function func) {
            var domTree = new DomTree(func.blocks.get(0));
            domTree.calcDominanceFrontier();
        }
    }

    public void optimize() {
        (new Mem2Reg()).run();
    }
}

package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;

public class Global2Local extends Optimizer.runOnModule {

    Module module;

    private boolean isDowngradable(IRConstant.Function func, Value variable) {
        boolean flg = false;
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof IRInst.CallInst) {
                    if (module.getAllBuiltinFunctions().contains((IRConstant.Function) inst.operands.get(0))) continue;
                    if (((IRConstant.Function) inst.operands.get(0)).getName().equals("__global_init")) continue;
                    return false;
                }
                if (inst.operands.contains(variable)) flg = true;
            }
        }
        return flg;
    }

    @Override
    void exec(Module module) {
        this.module = module;
        for (var variable : module.globalVariables) {
            if (!(variable instanceof IRConstant.GlobalVariable)) continue;

            for (var func : module.functions) {
                if (!isDowngradable(func, variable)) continue;
                System.err.printf("Downgrading %s in %s\n", variable.getName(), func.getName());
                var load = new IRInst.LoadInst(variable, func.getEntryBlock());
                var proxy = new IRInst.AllocaInst(variable.getName() + "_proxy", IRType.dePointer(variable.getType()), func.getEntryBlock());
                var store = new IRInst.StoreInst(proxy, load, func.getEntryBlock());
                func.getEntryBlock().moveToFront(load, proxy, store);

                for (var block : func.blocks) {
                    for (var inst : block.instructions) {
                        if (inst != load && inst != proxy && inst != store)
                            inst.replaceAllOperand(variable, proxy);
                    }
                }

                var lastBlock = func.getExitBlock();
                new IRInst.StoreInst(variable, new IRInst.LoadInst(proxy, lastBlock), lastBlock);
                lastBlock.reorder();
                func.getEntryBlock().reorder();
            }
        }
    }

    @Override
    String print() {
        return "Global2Local";
    }
}

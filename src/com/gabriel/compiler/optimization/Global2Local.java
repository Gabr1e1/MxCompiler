package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.IRType;
import com.gabriel.compiler.IR.Module;

public class Global2Local extends Optimizer.runOnModule {

    Module module;

    private boolean isDowngradable(IRConstant.Function func, IRConstant.GlobalVariable variable) {
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
                if (!isDowngradable(func, (IRConstant.GlobalVariable) variable)) continue;
                System.err.printf("Downgrading %s in %s\n", variable.getName(), func.getName());
                var load = new IRInst.LoadInst(variable, func.blocks.get(0));
                var proxy = new IRInst.AllocaInst(variable.getName() + ".proxy", IRType.dePointer(variable.getType()), func.blocks.get(0));
                var store = new IRInst.StoreInst(proxy, load, func.blocks.get(0));
                func.blocks.get(0).moveToFront(load, proxy, store);

                for (var block : func.blocks) {
                    for (var inst : block.instructions) {
                        if (inst != load && inst != proxy && inst != store)
                            inst.replaceAllOperand(variable, proxy);
                    }
                }

                var lastBlock = func.blocks.get(func.blocks.size() - 1);
                new IRInst.StoreInst(variable, new IRInst.LoadInst(proxy, lastBlock), lastBlock);
                lastBlock.reorder();
                func.blocks.get(0).reorder();
            }
        }
    }

    @Override
    String print() {
        return "Global2Local";
    }
}

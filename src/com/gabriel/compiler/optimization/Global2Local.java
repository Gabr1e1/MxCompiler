package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.IRType;
import com.gabriel.compiler.IR.Module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Global2Local extends Optimizer.runOnModule {

    Module module;
    Map<IRConstant.Function, Set<IRConstant.GlobalVariable>> mark;
    Map<IRConstant.Function, Set<IRConstant.GlobalVariable>> loadOnly;
    Set<IRConstant.GlobalVariable> gv;

    private void checkSafety(IRConstant.Function func) {
        mark.putIfAbsent(func, new HashSet<>());
        loadOnly.putIfAbsent(func, new HashSet<>(gv));

        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof IRInst.StoreInst) {
                    if (inst.operands.get(0) instanceof IRConstant.GlobalVariable) {
                        mark.get(func).add((IRConstant.GlobalVariable) inst.operands.get(0));
                        loadOnly.get(func).remove((IRConstant.GlobalVariable) inst.operands.get(0));
                    }
                }
                if (inst instanceof IRInst.LoadInst) {
                    if (inst.operands.get(0) instanceof IRConstant.GlobalVariable) {
                        mark.get(func).add((IRConstant.GlobalVariable) inst.operands.get(0));
                    }
                }
            }
        }
    }

    private void solve(Module module) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (var func : module.functions) {
                mark.putIfAbsent(func, new HashSet<>());

                for (var block : func.blocks) {
                    for (var inst : block.instructions) {
                        if (inst instanceof IRInst.CallInst) {
                            var callee = (IRConstant.Function) inst.operands.get(0);
                            if (callee.getName().equals("__global_init")) continue;
                            mark.putIfAbsent(callee, new HashSet<>());
                            loadOnly.putIfAbsent(callee, new HashSet<>(gv));

                            int orig = mark.get(func).size();
                            mark.get(func).addAll(mark.get(callee));
                            changed |= orig != mark.get(func).size();

                            orig = loadOnly.get(func).size();
                            var tmp = new HashSet<>(gv);
                            tmp.removeAll(loadOnly.get(callee));
                            loadOnly.get(func).removeAll(tmp);
                            changed |= orig != loadOnly.get(func).size();
                        }
                    }
                }
            }
        }
        for (var func : module.functions) {
            System.err.printf("Function %s's usage: ", func.getName());
            for (var ban : mark.get(func)) {
                System.err.printf("%s, ", ban.getName());
            }
            System.err.print("\n");
        }
    }

    private boolean isDowngradable(IRConstant.Function func, IRConstant.GlobalVariable variable) {
        if (loadOnly.get(func).contains(variable)) return true;
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof IRInst.CallInst) {
                    if (mark.get((IRConstant.Function) inst.operands.get(0)).contains(variable)) return false;
                }
            }
        }
        return true;
    }

    @Override
    void exec(Module module) {
        this.module = module;
        gv = new HashSet<IRConstant.GlobalVariable>();
        module.globalVariables.forEach(var -> {
            if (var instanceof IRConstant.GlobalVariable)
                gv.add((IRConstant.GlobalVariable) var);
        });
        mark = new HashMap<>();
        loadOnly = new HashMap<>();
        for (var func : module.functions) checkSafety(func);
        solve(module);

        for (var variable : module.globalVariables) {
            if (!(variable instanceof IRConstant.GlobalVariable)) continue;

            for (var func : module.functions) {
                if (!isDowngradable(func, (IRConstant.GlobalVariable) variable)) continue;

                boolean needToStore = false;
                for (var block : func.blocks) {
                    for (var inst : block.instructions) {
                        if (inst instanceof IRInst.StoreInst && inst.operands.get(0) == variable) {
                            needToStore = true;
                            break;
                        }
                    }
                }

                System.err.printf("Localizing %s in %s\n", variable.getName(), func.getName());
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
                if (needToStore)
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

package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InlineFunction extends Optimizer.runOnModule {
    final int RECURSIVE = 2;
    final int THRESHOLD = 100;

    private Set<IRConstant.Function> find(Module module) {
        var ret = new HashSet<IRConstant.Function>();
        for (var func : module.functions) {
            if (func.getInstCount() < THRESHOLD && func.ableToInline()) {
                ret.add(func);
            }
        }
        return ret;
    }

    private Pair<BasicBlock, BasicBlock> splitBlock(IRInst.Instruction inst) {
        var newBlock = new BasicBlock(inst.belong.getName() + ".split", inst.belong.belong);
        var successors = new ArrayList<>(inst.belong.getSuccessors());
        var index = inst.belong.instructions.indexOf(inst);
        for (int i = index + 1; i < inst.belong.instructions.size(); i++) {
            var cur = inst.belong.instructions.get(i);
            inst.belong.instructions.remove(cur);
            newBlock.addInst(cur);
        }
        // Replace block reference in successor blocks
        for (var succ : successors) {
            for (var i : succ.instructions) {
                if (i instanceof IRInst.PhiInst)
                    i.replaceAllOperand(inst.belong, newBlock);
            }
        }
        return new Pair<>(inst.belong, newBlock);
    }

    private void rewrite(IRInst.CallInst callSite, IRConstant.Function inlineTarget) {
        var split = splitBlock(callSite);
        BasicBlock first = split.first, second = split.second;

        //Start copying and rewriting
        var curFunc = callSite.belong.belong;
        for (var block : inlineTarget.blocks) {
            new BasicBlock(block.getName(), curFunc);
            
        }
    }

    private void inline(IRConstant.Function inlineTarget) {
        System.err.printf("Inlining Function %s\n", inlineTarget.getName());
        var callSites = inlineTarget.getUser();
        for (var callSite : new HashSet<>(callSites)) {
            assert callSite instanceof IRInst.CallInst;
            rewrite((IRInst.CallInst) callSite, inlineTarget);
        }
    }

    @Override
    void exec(Module module) {
        for (int i = 0; i < RECURSIVE; i++) {
            var funcToInline = find(module);
            funcToInline.forEach(this::inline);
        }
    }

    @Override
    String print() {
        return "Inline Function";
    }
}


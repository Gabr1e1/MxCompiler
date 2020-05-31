package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.Module;
import com.gabriel.compiler.IR.*;
import com.gabriel.compiler.util.Pair;

import java.util.*;

public class InlineFunction extends Optimizer.runOnModule {
    final int DEPTH = 3;
    final int THRESHOLD = 100;
    final int LIMIT = 40;

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
        var newBlock = new BasicBlock(inst.belong.getName() + "_split", inst.belong.belong);
        var successors = new ArrayList<>(inst.belong.getSuccessors());
        var index = inst.belong.instructions.indexOf(inst);
        var delList = new ArrayList<IRInst.Instruction>();
        for (int i = index + 1; i < inst.belong.instructions.size(); i++) {
            var cur = inst.belong.instructions.get(i);
            delList.add(cur);
            newBlock.addInst(cur);
        }
        for (var del : delList) inst.belong.instructions.remove(del);

        // Replace block reference in successor blocks
        for (var succ : successors) {
            for (var i : succ.instructions) {
                if (i instanceof IRInst.PhiInst)
                    i.replaceAllOperand(inst.belong, newBlock);
            }
        }
        return new Pair<>(inst.belong, newBlock);
    }

    Map<Value, Value> corres = new HashMap<>();

    private void copy(IRInst.Instruction origInst, BasicBlock newBlock) {
        try {
            var newInst = (IRInst.Instruction) origInst.clone();
            newInst.name = newInst.gen(newInst.originalName);
            newInst.operands = new ArrayList<>();
            newInst.user = new Use();
            newInst.lattice = new Value.ConditionalLattice(origInst.lattice);

            newInst.addOperand(origInst.operands);
            newBlock.addInst(newInst);
            corres.put(origInst, newInst);
        } catch (CloneNotSupportedException ignored) {
        }
    }

    private void replace(IRInst.Instruction origInst, BasicBlock newBlock) {
        var newInst = (IRInst.Instruction) corres.get(origInst);
        for (var op : origInst.operands) {
            if (op == null || op instanceof IRConstant.Constant || op.getType() instanceof IRType.VoidType) continue;
            assert corres.containsKey(op);
            newInst.replaceAllOperand(op, corres.get(op));
        }
    }

    private void rewrite(IRInst.CallInst callSite, IRConstant.Function inlineTarget) {
        var split = splitBlock(callSite);
        BasicBlock first = split.first, second = split.second;

        //Start copying and rewriting
        var curFunc = callSite.belong.belong;

        int add = 0;
        if (callSite.operands.size() - 1 != ((IRType.FunctionType) inlineTarget.getType()).params.size()) {
            // difference in arg count due to "this"
            add = 1;
            var funcThis = ((IRType.FunctionType) inlineTarget.getType()).params.get(0);
            var siteThis = ((IRType.FunctionType) callSite.belong.belong.getType()).params.get(1);
            corres.put(funcThis, siteThis);
        }
        for (int i = add; i < ((IRType.FunctionType) inlineTarget.getType()).params.size(); i++) {
            var funcArg = ((IRType.FunctionType) inlineTarget.getType()).params.get(i);
            corres.put(funcArg, callSite.getOperand(1 + i));
        }
        //Step 0: put all blocks in
        var blocks = new ArrayList<>(inlineTarget.blocks);
        var entry = inlineTarget.getEntryBlock();
        var exit = inlineTarget.getExitBlock();

        for (var origBlock : blocks) {
            var newBlock = new BasicBlock(origBlock.getName(), curFunc);
            corres.put(origBlock, newBlock);
        }
        new IRInst.BranchInst((BasicBlock) corres.get(entry), first);
        //Step 1: gather information
        for (var origBlock : blocks) {
            var curBlock = (BasicBlock) corres.get(origBlock);
            for (var origInst : origBlock.instructions) {
                copy(origInst, curBlock);
            }
        }
        //Step 2: replace operands
        for (var origBlock : blocks) {
            var curBlock = (BasicBlock) corres.get(origBlock);
            for (var origInst : origBlock.instructions) {
                replace(origInst, curBlock);
            }
        }

        var exitBlock = ((BasicBlock) corres.get(exit));
        for (var inst : exitBlock.instructions) {
            if (inst instanceof IRInst.ReturnInst) {
                callSite.replaceAllUsesWith(inst.getOperand(0));
                exitBlock.delInst(inst);
                break;
            }
        }
        new IRInst.BranchInst(second, exitBlock);
    }

    private void inline(IRConstant.Function inlineTarget) {
        System.err.printf("Inlining Function %s\n", inlineTarget.getName());
        var callSites = inlineTarget.getUser();
        int cnt = 0;
        for (var callSite : new HashSet<>(callSites)) {
            corres.clear();
            assert callSite instanceof IRInst.CallInst;
            //TODO: RECURSION INLINE
            if (((IRInst.CallInst) callSite).belong.belong == inlineTarget) continue;
            rewrite((IRInst.CallInst) callSite, inlineTarget);
            ((IRInst.CallInst) callSite).belong.delInst((IRInst.CallInst) callSite);
            if (++cnt > LIMIT) break;
        }
    }

    @Override
    void exec(Module module) {
        for (int i = 0; i < DEPTH; i++) {
            var funcToInline = find(module);
            funcToInline.forEach(this::inline);
        }
    }

    @Override
    String print() {
        return "Inline Function";
    }
}


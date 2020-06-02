package com.gabriel.compiler.backend;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.Value;
import com.gabriel.compiler.optimization.CFG;
import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SSADestructor {

    private void splitCriticalEdge(IRConstant.Function func) {
        var cfg = new CFG(func.getEntryBlock(), false);
        var blocks_original = new ArrayList<>(func.blocks);
        for (var block : blocks_original) {
            if (!block.hasPhiInst()) continue;
            Map<BasicBlock, IRInst.ParallelCopy> pc = new HashMap<>();

            for (var pred : cfg.getPredecessors(block)) {
                if (cfg.getSuccessors(pred).size() > 1) {
                    //split edge
                    var splitBlock = new BasicBlock("splitBlock", func);
                    pred.redirectJump(block, splitBlock);
                    splitBlock.redirectJump(null, block);
                    pc.put(pred, new IRInst.ParallelCopy(splitBlock));
                } else {
                    pc.put(pred, new IRInst.ParallelCopy(pred));
                }
            }

            var inst_original = new ArrayList<>(block.instructions);
            for (var inst : inst_original) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                for (int i = 0; i < inst.operands.size(); i += 2) {
                    var v = inst.operands.get(i);
                    if (v == null) continue;
                    var b = (BasicBlock) inst.operands.get(i + 1);

                    //WARNING: might be wrong
                    if (pc.get(b) == null) continue;

                    var newV = new Value(v);
                    pc.get(b).addCopy(v, newV);
                    inst.replaceOperand(v, newV);
                }
            }
        }
    }

    private int[] father;

    private int findParent(int u) {
        if (father[u] == u) return u;
        else {
            father[u] = findParent(father[u]);
            return father[u];
        }
    }

    private void union(int x, int y) {
        int fx = findParent(x), fy = findParent(y);
        if (fx != fy) {
            father[fx] = fy;
        }
    }

    private void knitPhiWeb(IRConstant.Function func) {
        int cnt = 0;
        Map<Value, Integer> value2index = new HashMap<>();
        Map<Integer, Value> index2value = new HashMap<>();

        //get all variable
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                for (var op : inst.operands) {
                    if (op == null) continue;
                    if (!value2index.containsKey(op)) {
                        value2index.put(op, cnt);
                        index2value.put(cnt++, op);
                    }
                }
                if (!value2index.containsKey(inst)) {
                    value2index.put(inst, cnt);
                    index2value.put(cnt++, inst);
                }
            }
        }

        father = new int[cnt + 1];
        for (int i = 0; i < father.length; i++) father[i] = i;

        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                for (int i = 0; i < inst.operands.size(); i += 2) {
//                    System.err.printf("Union %s with %s\n", inst, inst.operands.get(i));
                    if (inst.operands.get(i) == null) continue;
                    union(value2index.get(inst), value2index.get(inst.operands.get(i)));
                }
            }
        }

        //Coalesce all variables in the same phi web, using the root as the representative
        for (int i = 0; i < father.length; i++) {
            if (father[i] == i) continue;
//            System.err.printf("Replace %s with %s\n", index2value.get(i), index2value.get(findParent(i)));
            if (index2value.get(i) == null) continue;
            index2value.get(i).replaceAllUsesWith(index2value.get(findParent(i)));
        }

        //Delete all PhiInst
        func.blocks.forEach(BasicBlock::delAllPhiInst);
    }


    private void parallel2seq(BasicBlock block) {
        if (block.getParallelCopy() == null) return;

        var pcopy = ((IRInst.ParallelCopy) block.getParallelCopy()).pcopy;

        Map<Value, Integer> outDeg;
        while (pcopy.size() != 0) {
            outDeg = new HashMap<>();
            for (var copy : pcopy) {
                outDeg.putIfAbsent(copy.first, 0);
                outDeg.put(copy.first, outDeg.get(copy.first) + 1);
            }

            boolean flg = false;
            for (var copy : pcopy) {
                if (!outDeg.containsKey(copy.second)) {
                    var c = new IRInst.CopyInst(copy.second, block);
                    c.add(copy.first, copy.second);
                    pcopy.remove(copy);
                    flg = true;
                    break;
                }
            }
            if (flg) continue;

            //Only constituted of cycles
            var copy = pcopy.get(0);
            var c = new IRInst.CopyInst(copy.first, block);
            c.add(copy.first, copy.second);
            pcopy.set(0, new Pair<>(c, copy.second));
        }

        block.delInst(block.getParallelCopy());
    }

    public void exec(IRConstant.Function func) {
        splitCriticalEdge(func);
        knitPhiWeb(func);
        func.blocks.forEach(this::parallel2seq);
        func.blocks.forEach(BasicBlock::reorder);
    }
}

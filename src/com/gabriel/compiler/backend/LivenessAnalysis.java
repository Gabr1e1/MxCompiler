package com.gabriel.compiler.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LivenessAnalysis {

    Map<AsmStruct.Block, Set<Register.base>> liveIn = new HashMap<>();
    Map<AsmStruct.Block, Set<Register.base>> liveOut = new HashMap<>();

    private void init() {

    }

    public LivenessAnalysis(AsmStruct.Function func) {
        for (var block : func.blocks) {
            liveIn.put(block, new HashSet<>());
            liveOut.put(block, new HashSet<>());
        }

        boolean flg = true;
        while (flg) {
            flg = false;
            for (var block : func.blocks) {
                var newIn = new HashSet<>(liveOut.get(block));
                newIn.removeAll(block.getDef());
                newIn.addAll(block.getUse());
                flg |= newIn.equals(liveIn.get(block));
                liveIn.put(block, newIn);

                var newOut = new HashSet<Register.base>();
                for (var succ : block.getSuccessor()) {
                    newOut.addAll(liveIn.get(succ));
                }
                flg |= newOut.equals(liveOut.get(block));
                liveOut.put(block, newOut);
            }
        }
    }

    public Set<Register.base> getLiveOut(AsmStruct.Block block) {
        return liveOut.get(block);
    }

    public Set<Register.base> getLiveIn(AsmStruct.Block block) {
        return liveIn.get(block);
    }
}

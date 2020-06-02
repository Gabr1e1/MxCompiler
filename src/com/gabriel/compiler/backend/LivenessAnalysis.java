package com.gabriel.compiler.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LivenessAnalysis {

    Map<AsmStruct.Block, Set<Register.base>> liveIn = new HashMap<>();
    Map<AsmStruct.Block, Set<Register.base>> liveOut = new HashMap<>();
    Map<AsmStruct.Block, Set<Register.base>> gen = new HashMap<>();
    Map<AsmStruct.Block, Set<Register.base>> kill = new HashMap<>();

    public LivenessAnalysis(AsmStruct.Function func) {
        for (var block : func.blocks) {
            liveIn.put(block, new HashSet<>());
            liveOut.put(block, new HashSet<>());
            gen.put(block, new HashSet<>());
            kill.put(block, new HashSet<>());

            for (var inst : block.instructions) {
                var t = new HashSet<>(inst.getUse());
                t.removeAll(kill.get(block));
                gen.get(block).addAll(t);
                kill.get(block).addAll(inst.getDef());
            }
        }

        boolean flg = true;
        while (flg) {
            flg = false;
            for (var block : func.blocks) {
                var newIn = new HashSet<>(liveOut.get(block));
                newIn.removeAll(kill.get(block));
                newIn.addAll(gen.get(block));
                flg |= !newIn.equals(liveIn.get(block));
                liveIn.put(block, newIn);

                var newOut = new HashSet<Register.base>();
                for (var succ : block.getSuccessor()) {
                    newOut.addAll(liveIn.getOrDefault(succ, new HashSet<>()));
                }
                flg |= !newOut.equals(liveOut.get(block));
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

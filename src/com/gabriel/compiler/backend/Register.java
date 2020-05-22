package com.gabriel.compiler.backend;

import java.util.*;

public class Register {
    public abstract static class base {
        int index;
        List<AsmInst.Instruction> def = new ArrayList<>();
        List<AsmInst.Instruction> use = new ArrayList<>();

        public void addDef(AsmInst.Instruction inst) {
            def.add(inst);
        }

        public void addUse(AsmInst.Instruction inst) {
            use.add(inst);
        }
    }

    static List<Virtual> vregs = new ArrayList<>();

    static class Virtual extends base {
        static int virtualCounter = 0;

        public Virtual() {
            this.index = virtualCounter++;
            vregs.add(this);
        }

        @Override
        public String toString() {
            return "t" + this.index;
        }
    }

    static class Machine extends base {
        static int K;

        static Map<String, Integer> specialReg = new HashMap<>(Map.of("zero", 0, "sp", 2, "gp", 3, "tp", 4));
        static Map<String, Integer> callerSave = new HashMap<>(Map.of("ra", 1));
        static Map<String, Integer> calleeSave = new HashMap<>(Map.of("s0", 8, "s1", 9));

        static Map<String, Machine> regs = new HashMap<>();

        static void init() {
            /* MUST CALL BEFORE INSTANTIATION */
            for (int i = 0; i < 3; i++) callerSave.put("t" + i, 5 + i);
            for (int i = 0; i < 8; i++) calleeSave.put("a" + i, 10 + i);
            for (int i = 2; i <= 11; i++) calleeSave.put("s" + i, 16 + i);
            for (int i = 3; i <= 6; i++) callerSave.put("t" + i, 25 + i);

            Arrays.asList(specialReg, calleeSave, callerSave).forEach(
                    l -> l.forEach(
                            (s, i) -> regs.put(s, new Machine(s))));
            K = calleeSave.size() + callerSave.size();
        }

        static Machine get(String name) {
            return regs.get(name);
        }

        String name;

        public int find(String name) {
            if (specialReg.containsKey(name)) return specialReg.get(name);
            if (callerSave.containsKey(name)) return callerSave.get(name);
            if (calleeSave.containsKey(name)) return calleeSave.get(name);
            return -1;
        }

        public Machine(String name) {
            this.name = name;
            this.index = find(name);
            assert this.index != -1;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static Map<String, Virtual> calleeTmpSave = new HashMap<>();

    static void save(String reg, AsmStruct.Block curBlock) {
        var t = new Virtual();
        new AsmInst.mv(t, Machine.get(reg), curBlock);
        calleeTmpSave.put(reg, t);
    }
}

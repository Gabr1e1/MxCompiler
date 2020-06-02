package com.gabriel.compiler.backend;

import java.util.ArrayList;

public class Peephole {
    int cnt = 0;

    public Peephole(AsmStruct.Program prog, boolean last) {
        prog.cleanUp();
        cnt = 1;
        while (cnt > 0) {
            cnt = 0;
            for (var func : prog.functions) {
                algebraicSimplify(func, last);
                removeRedundantMemOp(func);
                removeBlocksWithOneJump(func);
            }
            System.err.printf("Peephole optimized %d instructions out\n", cnt);
        }
    }

    private void algebraicSimplify(AsmStruct.Function func, boolean last) {
        for (var block : func.blocks) {
            var delList = new ArrayList<AsmInst.Instruction>();
            for (var inst : block.instructions) {
                if (inst instanceof AsmInst.ComputeRegImm) {
                    var cur = (AsmInst.ComputeRegImm) inst;
                    if (cur.rd != cur.rs1 || (cur.rd == Register.Machine.get("sp") && !last)) continue;

                    boolean flg = false;
                    if (cur.opcode.equals("addi") && (int) cur.imm == 0) flg = true;
                    if (cur.opcode.equals("subi") && (int) cur.imm == 0) flg = true;
                    if (cur.opcode.equals("slli") && (int) cur.imm == 0) flg = true;
                    if (cur.opcode.equals("srai") && (int) cur.imm == 0) flg = true;
                    if (cur.opcode.equals("xori") && (int) cur.imm == 0) flg = true;
                    if (cur.opcode.equals("ori") && (int) cur.imm == 0) flg = true;
                    if (flg) {
                        cnt++;
//                        System.err.printf("Peephole deleted %s\n", cur.print());
                        delList.add(cur);
                    }
                }
            }
            block.instructions.removeAll(delList);
        }
    }

    private void removeRedundantMemOp(AsmStruct.Function func) {
        for (var block : func.blocks) {
            var delList = new ArrayList<AsmInst.Instruction>();
            for (var inst : block.instructions) {
                if (inst instanceof AsmInst.mv) {
//                    System.err.printf("Debug %s\n", inst.print());
                    if (inst.rs1 == inst.rd) {
                        delList.add(inst);
//                        System.err.printf("Deleted %s\n", inst.print());
                    }
                }
            }
            cnt += delList.size();
            block.instructions.removeAll(delList);
        }

        int W = 5;
        for (var block : func.blocks) {
            for (int i = 0; i < block.instructions.size(); i++) {

                for (int j = 1; j <= W; j++) {
                    if (i >= j && block.instructions.get(i) instanceof AsmInst.load &&
                            block.instructions.get(i - j) instanceof AsmInst.store) {
                        var cur = (AsmInst.load) block.instructions.get(i);
                        var prev = (AsmInst.store) block.instructions.get(i - j);
                        if (cur.rs1 == prev.rs1 && cur.imm.equals(prev.imm) && cur.byteNum == prev.byteNum) {

                            //Must guarantee does not interfere
                            boolean flg = true;
                            for (int k = 1; k < j; k++) {
                                if (block.instructions.get(i - k).rd == prev.rs2
                                        || block.instructions.get(i - k).rd == prev.rs1
                                        || block.instructions.get(i - k) instanceof AsmInst.store
                                        || block.instructions.get(i - k) instanceof AsmInst.call) {
                                    flg = false;
                                    break;
                                }
                            }
                            if (!flg) continue;
                            cnt++;

//                            System.err.printf("Peephole optimized %s %s\n", cur.print(), prev.print());
                            var t = new AsmInst.mv(cur.rd, prev.rs2, block);
                            block.instructions.remove(i);
                            block.moveInst(t, i);
                            break;
                        }
                    }
                }
            }
        }

        for (var block : func.blocks) {
            for (int i = 0; i < block.instructions.size(); i++) {

                for (int j = 1; j <= W; j++) {
                    if (i >= j && block.instructions.get(i) instanceof AsmInst.load &&
                            block.instructions.get(i - j) instanceof AsmInst.load) {
                        var cur = (AsmInst.load) block.instructions.get(i);
                        var prev = (AsmInst.load) block.instructions.get(i - j);
                        if (cur.rs1 == prev.rs1 && cur.imm.equals(prev.imm) && cur.byteNum == prev.byteNum) {
                            //Must guarantee does not interfere
                            boolean flg = true;
                            if (prev.rs1 == prev.rd) flg = false;

                            for (int k = 1; k < j; k++) {
                                if (block.instructions.get(i - k).rd == prev.rs1
                                        || block.instructions.get(i - k).rd == prev.rd
                                        || block.instructions.get(i - k) instanceof AsmInst.store
                                        || block.instructions.get(i - k) instanceof AsmInst.call) {
                                    flg = false;
                                    break;
                                }
                            }
                            if (!flg) continue;
                            cnt++;

//                            System.err.printf("Peephole optimized %s %s\n", cur.print(), prev.print());
                            var t = new AsmInst.mv(cur.rd, prev.rd, block);
                            block.instructions.remove(i);
                            block.moveInst(t, i);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void replaceJump(AsmStruct.Function func, AsmStruct.Block oldDest, AsmStruct.Block newDest) {
        for (var block : func.blocks) {
            for (var inst : block.instructions) {
                if (inst instanceof AsmInst.jump) {
                    var cur = (AsmInst.jump) inst;
                    if (cur.target == oldDest) cur.target = newDest;
                }

                if (inst instanceof AsmInst.branch) {
                    var cur = (AsmInst.branch) inst;
                    if (cur.target == oldDest) cur.target = newDest;
                }
            }
        }
    }

    private void removeBlocksWithOneJump(AsmStruct.Function func) {
        var delList = new ArrayList<AsmStruct.Block>();
        for (var block : func.blocks) {
            if (block.instructions.size() == 1 && block.instructions.get(0) instanceof AsmInst.jump) {
                var cur = (AsmInst.jump) block.instructions.get(0);
                replaceJump(func, block, cur.target);
                delList.add(block);
            }
        }
        cnt += delList.size();
        func.blocks.removeAll(delList);
    }
}

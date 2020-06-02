package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;
import com.gabriel.compiler.IR.IRInst;
import com.gabriel.compiler.IR.Value;

import java.util.*;

public class DVNT extends Optimizer.runOnFunction {
    DomTree dom;

    private static class InstWrap {
        IRInst.Instruction inst;

        public InstWrap(IRInst.Instruction inst) {
            this.inst = inst;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            IRInst.Instruction other = ((InstWrap) obj).inst;
            if (inst.getClass() != other.getClass()) return false;

            if (inst.operands.size() != other.operands.size()) return false;
            for (int i = 0; i < inst.operands.size(); i++) {
                if (!inst.operands.get(i).getPrintName().equals(other.operands.get(i).getPrintName()))
                    return false;
            }
            if (inst instanceof IRInst.BinaryOpInst)
                return ((IRInst.BinaryOpInst) inst).op.equals(((IRInst.BinaryOpInst) other).op);
            if (inst instanceof IRInst.CmpInst)
                return ((IRInst.CmpInst) inst).op.equals(((IRInst.CmpInst) other).op);
//            if (inst instanceof IRInst.CallInst)
//                return !((IRConstant.Function) inst.operands.get(0)).mayHaveSideEffects();
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            for (var op : inst.operands) if (op != null) hash ^= op.hashCode();
            return hash;
        }
    }

    private static class Scope {
        Map<Value, Value> valueNumber = new HashMap<>();
        Map<InstWrap, Value> hashTable = new HashMap<>(); //Inst -> VN
        Scope father;

        public Scope(Scope father) {
            this.father = father;
        }

        public Value find(Value v) {
            if (v instanceof IRConstant.Constant) return v;
            if (valueNumber.containsKey(v)) return valueNumber.get(v);
            if (father != null) return father.find(v);
            else return v; //Could be global variable or func arguments
        }

        public void put(Value v, Value vn) {
            valueNumber.put(v, vn);
        }

        public Value getValueNumber(IRInst.Instruction inst) {
            if (hashTable.containsKey(new InstWrap(inst))) return hashTable.get(new InstWrap(inst));
            if (father != null) return father.getValueNumber(inst);
            else return null;
        }

        public void addToHashTable(IRInst.Instruction inst, Value v) {
            hashTable.put(new InstWrap(inst), v);
        }
    }

    Map<BasicBlock, Scope> scopeTable = new HashMap<>();

    private Scope getScope(BasicBlock block, BasicBlock father) {
        scopeTable.putIfAbsent(block, new Scope(scopeTable.getOrDefault(father, null)));
        return scopeTable.get(block);
    }

    List<IRInst.Instruction> delList = new ArrayList<>();

    private void init(IRConstant.Function func, Scope scope) {
        dom = new DomTree(func.getEntryBlock(), false);
        delList.clear();
    }

    private void visit(BasicBlock block, BasicBlock father) {
        var curScope = getScope(block, father);
        if (father == null) init(block.belong, curScope);

        for (var inst : block.instructions) {
            if (!(inst instanceof IRInst.PhiInst)) continue;

            var opVn = new ArrayList<Value>();
            for (int i = 0; i < inst.operands.size(); i += 2) {
                var v = inst.operands.get(i);
                if (v == null) continue;
                opVn.add(curScope.find(v));
            }

            //Meaningless or Redundant
            if (opVn.stream().distinct().count() <= 1) {
                curScope.put(inst, opVn.get(0));
                inst.replaceAllUsesWith(opVn.get(0));
                delList.add(inst);
            } else numberInst(curScope, inst, inst);
        }

        for (var inst : block.instructions) {
            if (inst instanceof IRInst.PhiInst) continue;

            var opVn = new ArrayList<Value>();
            for (int i = 0; i < inst.operands.size(); i++) {
                var v = inst.operands.get(i);
                if (v == null) continue;
                opVn.add(curScope.find(v));
            }

            IRInst.Instruction instClone = null;
            try {
                instClone = (IRInst.Instruction) inst.clone();
            } catch (CloneNotSupportedException ignored) {
            }
            assert instClone != null;
            instClone.operands = opVn;
            //TODO: Less strict criteria
            boolean fixedValue = (inst instanceof IRInst.BinaryOpInst) || (inst instanceof IRInst.CmpInst)
                    || (inst instanceof IRInst.GEPInst)
                    || (inst instanceof IRInst.SextInst) || (inst instanceof IRInst.TruncInst) || (inst instanceof IRInst.CastInst);

            if (!fixedValue) {
                curScope.put(inst, inst);
                curScope.addToHashTable(inst, inst);
                continue;
            }

            //Try interchange operands
            if (curScope.getValueNumber(instClone) == null && inst.isCommutative()) {
                Collections.reverse(instClone.operands);
            }

            numberInst(curScope, inst, instClone);
        }

        for (var succ : block.getSuccessors()) {
            for (var inst : succ.instructions) {
                if (!(inst instanceof IRInst.PhiInst)) continue;
                for (var op : new ArrayList<>(inst.operands)) {
                    //it doesn't matter including the blocks in phi
                    if (curScope.find(op) != null)
                        inst.replaceOperand(op, curScope.find(op));
                }
            }
        }

        for (var child : dom.getChildrenBlocks(block)) {
            visit(child, block);
        }
    }

    private void numberInst(Scope curScope, IRInst.Instruction inst, IRInst.Instruction instClone) {
        if (curScope.getValueNumber(instClone) != null) {
            curScope.put(inst, curScope.getValueNumber(instClone));
            inst.replaceAllUsesWith(curScope.getValueNumber(instClone));
            delList.add(inst);
        } else {
            curScope.put(inst, inst);
            curScope.addToHashTable(inst, inst);
        }
    }

    @Override
    void exec(IRConstant.Function func) {
        visit(func.getEntryBlock(), null);
        if (!delList.isEmpty()) System.err.printf("Deleted %s instructions\n", delList.size());
        delList.forEach(inst -> {
            System.err.println(inst.print());
            inst.belong.delInst(inst);
        });
        func.blocks.forEach(BasicBlock::hoistPhiInst);
    }

    @Override
    String print() {
        return "Dominator-based Value Numbering Technique";
    }
}

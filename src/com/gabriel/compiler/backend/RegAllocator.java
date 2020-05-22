package com.gabriel.compiler.backend;

import com.gabriel.compiler.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class RegAllocator {

    public RegAllocator(AsmStruct.Program prog) {
        init();
        for (var func : prog.functions) {
            exec(func);
        }
    }

    int K = Register.Machine.K;
    LivenessAnalysis livenessAnalysis;
    Set<AsmInst.Instruction> coalescedMoves, constrainedMoves, frozenMoves, worklistMoves, activeMoves;
    Set<Pair<Register.base, Register.base>> adjSet;
    Set<Register.base> precolored, initial;
    Set<Register.base> coalescedNodes;
    Map<Register.base, Register.base> alias;
    Map<Register.base, Set<AsmInst.Instruction>> moveList;
    Map<Register.base, Integer> degree;
    Map<Register.base, Set<Register.base>> adjList;
    List<Register.base> spillWorklist, freezeWorklist, simplifyWorklist;
    Stack<Register.base> selectStack;
    Map<Register.base, Integer> color;
    Set<Register.base> coloredNodes, spilledNodes;

    private void exec(AsmStruct.Function func) {
        rebuild();
        livenessAnalysis = new LivenessAnalysis(func);
        build(func);
        makeWorklist();
        do {
            if (!simplifyWorklist.isEmpty()) simplify();
            else if (!worklistMoves.isEmpty()) coalesce();
            else if (!freezeWorklist.isEmpty()) freeze();
            else if (!spillWorklist.isEmpty()) selectSpill();
        }
        while (!(simplifyWorklist.isEmpty() && worklistMoves.isEmpty() && freezeWorklist.isEmpty() && spillWorklist.isEmpty()));
        assignColors();
        if (!spilledNodes.isEmpty()) {
            rewriteProgram(func);
            exec(func);
        }
    }

    private void init() {
        coalescedMoves = new HashSet<>();
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        worklistMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        adjSet = new HashSet<>();
        precolored = new HashSet<>(Register.Machine.regs.values());
        initial = new HashSet<>(Register.vregs);
        coalescedNodes = new HashSet<>();
        alias = new HashMap<>();
        moveList = new HashMap<>();
        degree = new HashMap<>();
        adjList = new HashMap<>();
        spillWorklist = new ArrayList<>();
        freezeWorklist = new ArrayList<>();
        simplifyWorklist = new ArrayList<>();
        selectStack = new Stack<>();
        color = new HashMap<>();
        coloredNodes = new HashSet<>();
        spilledNodes = new HashSet<>();
    }

    private void rebuild() {
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        adjSet = new HashSet<>();
        coalescedNodes = new HashSet<>();
        alias = new HashMap<>();
        moveList = new HashMap<>();
        degree = new HashMap<>();
        adjList = new HashMap<>();
        selectStack = new Stack<>();
        color = new HashMap<>();
    }

    private void build(AsmStruct.Function func) {
        for (var block : func.blocks) {
            var live = livenessAnalysis.getLiveOut(block);
            for (int i = block.instructions.size() - 1; i >= 0; i--) {
                var inst = block.instructions.get(i);
                if (inst instanceof AsmInst.mv) {
                    live.removeAll(inst.getUse());
                    for (var n : setUnion(inst.getUse(), inst.getDef())) {
                        moveList.putIfAbsent(n, new HashSet<>());
                        moveList.get(n).add(inst);
                    }
                    worklistMoves.add(inst);
                }
                live.addAll(inst.getDef());
                for (var d : inst.getDef()) {
                    for (var l : live) {
                        addEdge(l, d);
                    }
                }
                live.removeAll(inst.getDef());
                live.addAll(inst.getUse());
            }
        }
    }

    private void addEdge(Register.base u, Register.base v) {
        if (u != v && !adjSet.contains(new Pair<>(u, v))) {
            adjSet.add(new Pair<>(u, v));
            adjSet.add(new Pair<>(v, u));
            if (!precolored.contains(u)) {
                adjList.putIfAbsent(u, new HashSet<>());
                adjList.get(u).add(v);
                degree.putIfAbsent(u, 0);
                degree.put(u, degree.get(u) + 1);
            }
            if (!precolored.contains(v)) {
                adjList.putIfAbsent(v, new HashSet<>());
                adjList.get(v).add(u);
                degree.putIfAbsent(v, 0);
                degree.put(v, degree.get(v) + 1);
            }
        }
    }

    private Set<AsmInst.Instruction> getNodeMoves(Register.base n) {
        var ret = new HashSet<>(activeMoves);
        ret.addAll(worklistMoves);
        ret.retainAll(moveList.get(n));
        return ret;
    }

    private boolean isMoveRelated(Register.base n) {
        return getNodeMoves(n).size() > 0;
    }

    private void makeWorklist() {
        for (var n : initial) {
            initial.remove(n);
            if (degree.get(n) >= K) {
                spillWorklist.add(n);
            } else if (isMoveRelated(n)) {
                freezeWorklist.add(n);
            } else {
                simplifyWorklist.add(n);
            }
        }
    }

    private Set<Register.base> getAdjacent(Register.base n) {
        var ret = new HashSet<>(adjList.get(n));
        ret.removeAll(selectStack);
        ret.removeAll(coalescedNodes);
        return ret;
    }

    private void simplify() {
        var n = simplifyWorklist.get(0);
        simplifyWorklist.remove(n);
        selectStack.push(n);
        for (var m : getAdjacent(n)) {
            decrementDegree(m);
        }
    }

    private void enableMoves(Set<Register.base> nodes) {
        for (var n : nodes) {
            for (var m : getNodeMoves(n)) {
                if (activeMoves.contains(m)) {
                    activeMoves.remove(m);
                    worklistMoves.add(m);
                }
            }
        }
    }

    private void decrementDegree(Register.base m) {
        int d = degree.get(m);
        degree.put(m, d - 1);
        if (d == K) {
            var t = getAdjacent(m);
            t.add(m);
            enableMoves(t);
            spillWorklist.remove(m);
            if (isMoveRelated(m)) {
                freezeWorklist.add(m);
            } else {
                simplifyWorklist.add(m);
            }
        }
    }

    private void addWorkList(Register.base u) {
        if ((!precolored.contains(u)) && (!isMoveRelated(u)) && degree.get(u) < K) {
            freezeWorklist.remove(u);
            simplifyWorklist.add(u);
        }
    }

    private boolean Ok(Register.base t, Register.base r) {
        return degree.get(t) < K || precolored.contains(t) || adjSet.contains(new Pair<>(t, r));
    }

    private boolean isAllOk(Set<Register.base> set, Register.base u) {
        for (var s : set)
            if (!Ok(s, u)) return false;
        return true;
    }

    private boolean isConservative(Set<Register.base> nodes) {
        int k = 0;
        for (var n : nodes)
            if (degree.get(n) > K) k = k + 1;
        return k < K;
    }

    private Register.base getAlias(Register.base n) {
        if (coalescedNodes.contains(n)) return getAlias(alias.get(n));
        else return n;
    }

    private <T> Set<T> setUnion(Set<T> a, Set<T> b) {
        var ret = new HashSet<>(a);
        a.addAll(b);
        return ret;
    }

    private void coalesce() {
        var m = new ArrayList<>(worklistMoves).get(0);
        var x = getAlias(m.rd);
        var y = getAlias(m.rs1);
        Register.base u, v;
        if (precolored.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }
        worklistMoves.remove(m);
        if (u == v) {
            coalescedMoves.add(m);
            addWorkList(u);
        } else if (precolored.contains(v) || adjSet.contains(new Pair<>(u, v))) {
            constrainedMoves.add(m);
            addWorkList(u);
            addWorkList(v);
        } else if ((precolored.contains(u) && isAllOk(getAdjacent(v), u)) ||
                ((!precolored.contains(u)) && isConservative(setUnion(getAdjacent(u), getAdjacent(v))))) {
            coalescedMoves.add(m);
            combine(u, v);
            addWorkList(u);
        } else {
            activeMoves.add(m);
        }
    }

    private void combine(Register.base u, Register.base v) {
        if (freezeWorklist.contains(v)) {
            freezeWorklist.remove(v);
        } else {
            spillWorklist.remove(v);
        }
        coalescedNodes.add(v);
        alias.put(v, u);
        moveList.get(u).addAll(moveList.get(v));
        enableMoves(Collections.singleton(v));
        for (var t : getAdjacent(v)) {
            addEdge(t, u);
            decrementDegree(t);
        }
        if (degree.get(u) >= K && freezeWorklist.contains(u)) {
            freezeWorklist.remove(u);
            spillWorklist.remove(u);
        }
    }

    private void freezeMoves(Register.base u) {
        for (var m : getNodeMoves(u)) {
            var x = m.rd;
            var y = m.rs1;
            Register.base v;
            if (getAlias(y) == getAlias(u)) {
                v = getAlias(x);
            } else {
                v = getAlias(y);
            }
            activeMoves.remove(m);
            frozenMoves.add(m);
            if (freezeWorklist.contains(v) && getNodeMoves(v).size() == 0) {
                freezeWorklist.remove(v);
                simplifyWorklist.add(v);
            }
        }
    }

    private void freeze() {
        var u = freezeWorklist.get(0);
        freezeWorklist.remove(u);
        simplifyWorklist.add(u);
        freezeMoves(u);
    }

    private void selectSpill() {
        //TODO: using better heuristic
        var m = spillWorklist.get(0);
        spillWorklist.remove(m);
        simplifyWorklist.add(m);
        freezeMoves(m);
    }

    private void assignColors() {
        while (!selectStack.isEmpty()) {
            var n = selectStack.pop();
            var okColors = new HashSet<>(Arrays.asList(IntStream.of(IntStream.range(0, K).toArray()).boxed().toArray(Integer[]::new)));
            for (var w : adjList.get(n)) {
                if (setUnion(coloredNodes, precolored).contains(getAlias(w))) {
                    okColors.remove(color.get(getAlias(w)));
                }
            }
            if (okColors.isEmpty()) {
                spilledNodes.add(n);
            } else {
                coloredNodes.add(n);
                int c = new ArrayList<>(okColors).get(0);
                color.put(n, c);
            }
        }
        for (var n : coalescedNodes) {
            color.put(n, color.get(getAlias(n)));
        }
    }

    private void rewriteProgram(AsmStruct.Function func) {
        Set<Register.base> newTemps = new HashSet<>();
        for (var v : spilledNodes) {
            var p = func.newVariable();
            for (var inst : v.def) {
                var reg = new Register.Virtual();
                newTemps.add(reg);
                var newInst = new AsmInst.store(reg, Register.Machine.get("sp"), p, 32, inst.belong);
                inst.belong.moveInst(newInst, inst.belong.instructions.indexOf(inst) + 1);
            }

            for (var inst : v.use) {
                var reg = new Register.Virtual();
                newTemps.add(reg);
                var newInst = new AsmInst.load(Register.Machine.get("sp"), p, 32, reg, inst.belong);
                inst.belong.moveInst(newInst, inst.belong.instructions.indexOf(inst));
            }
        }
        spilledNodes = new HashSet<>();
        initial = setUnion(setUnion(coloredNodes, coalescedNodes), newTemps);
        coloredNodes = new HashSet<>();
        coalescedNodes = new HashSet<>();
    }
}

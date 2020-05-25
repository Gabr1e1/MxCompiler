package com.gabriel.compiler.backend;

import com.gabriel.compiler.util.Pair;

import java.util.*;

public class RegAllocator {

    final int MAX_DEGREE = 1000000;

    public RegAllocator(AsmStruct.Program prog) {
        for (var func : prog.functions) {
            init(func);
            exec(func);
            func.getAllVregs().forEach((reg) -> ((Register.Virtual) reg).tryReplace());
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
    Set<Register.base> spillWorklist, freezeWorklist, simplifyWorklist;
    Stack<Register.base> selectStack;
    Set<Register.base> coloredNodes, spilledNodes;
    Set<Register.base> newTemps;
    Random rand = new Random();

    private void exec(AsmStruct.Function func) {
        rebuild(func);
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
        assignColors(func);
        if (!spilledNodes.isEmpty()) {
            rewriteProgram(func);
            exec(func);
        }
    }

    private void init(AsmStruct.Function func) {
        coalescedMoves = new HashSet<>();
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        worklistMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        adjSet = new HashSet<>();
        precolored = new HashSet<>(Register.Machine.regs.values());
        initial = func.getAllVregs();
        coalescedNodes = new HashSet<>();
        spillWorklist = new HashSet<>();
        freezeWorklist = new HashSet<>();
        simplifyWorklist = new HashSet<>();
        selectStack = new Stack<>();
        coloredNodes = new HashSet<>();
        spilledNodes = new HashSet<>();
        alias = new HashMap<>();
        moveList = new HashMap<>();
        adjList = new HashMap<>();
        degree = new HashMap<>();
        newTemps = new HashSet<>();
    }

    private void rebuild(AsmStruct.Function func) {
        coalescedMoves = new HashSet<>();
        constrainedMoves = new HashSet<>();
        frozenMoves = new HashSet<>();
        activeMoves = new HashSet<>();
        alias = new HashMap<>();
        adjSet = new HashSet<>();

        initial.forEach((reg) -> ((Register.Virtual) reg).color = null);
        moveList.forEach((k, v) -> moveList.put(k, new HashSet<>()));
        degree.forEach((k, v) -> degree.put(k, 0));
        adjList.forEach((k, v) -> adjList.put(k, new HashSet<>()));

        setUnion(precolored, initial).forEach((reg) -> moveList.putIfAbsent(reg, new HashSet<>()));
        initial.forEach((reg) -> degree.putIfAbsent(reg, 0));
        precolored.forEach((reg) -> degree.put(reg, MAX_DEGREE));
        setUnion(precolored, initial).forEach((reg) -> adjList.putIfAbsent(reg, new HashSet<>()));
    }

    private void build(AsmStruct.Function func) {
        for (var block : func.blocks) {
            var live = livenessAnalysis.getLiveOut(block);
            for (int i = block.instructions.size() - 1; i >= 0; i--) {
                var inst = block.instructions.get(i);
                if (inst instanceof AsmInst.mv) {
                    live.removeAll(inst.getUse());
                    for (var n : setUnion(inst.getUse(), inst.getDef())) {
                        moveList.get(n).add(inst);
                    }
                    worklistMoves.add(inst);
                }
                live.addAll(inst.getDef());
                // Prevent allocation to zero!!
                live.add(Register.Machine.get("zero"));
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
//        assert adjList.get(u).contains(v) == adjSet.contains(new Pair<>(u, v));
        if (u != v && !adjSet.contains(new Pair<>(u, v))) {
            adjSet.add(new Pair<>(u, v));
            adjSet.add(new Pair<>(v, u));
            if (!precolored.contains(u)) {
                adjList.get(u).add(v);
                degree.put(u, degree.getOrDefault(u, 0) + 1);
            }
            if (!precolored.contains(v)) {
                adjList.get(v).add(u);
                degree.put(v, degree.getOrDefault(v, 0) + 1);
            }
        }
    }

    private Set<AsmInst.Instruction> getNodeMoves(Register.base n) {
        var ret = new HashSet<>(activeMoves);
        ret.addAll(worklistMoves);
        ret.retainAll(moveList.getOrDefault(n, new HashSet<>()));
        return ret;
    }

    private boolean isMoveRelated(Register.base n) {
        return getNodeMoves(n).size() > 0;
    }

    private void makeWorklist() {
        for (var n : initial) {
            if (degree.getOrDefault(n, 0) >= K) {
                spillWorklist.add(n);
            } else if (isMoveRelated(n)) {
                freezeWorklist.add(n);
            } else {
                simplifyWorklist.add(n);
            }
        }
        initial.clear();
    }

    private Set<Register.base> getAdjacent(Register.base n) {
        var ret = new HashSet<>(adjList.get(n));
        ret.removeAll(selectStack);
        ret.removeAll(coalescedNodes);
        return ret;
    }

    private void simplify() {
        var n = new ArrayList<>(simplifyWorklist).get(0);
        simplifyWorklist.remove(n);
//        System.err.printf("Push to stack %s\n", n);
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
            if (degree.get(n) >= K) k = k + 1;
        return k < K;
    }

    private Register.base getAlias(Register.base n) {
        if (coalescedNodes.contains(n)) return getAlias(alias.get(n));
        else return n;
    }

    private <T> Set<T> setUnion(Set<T> a, Set<T> b) {
        var ret = new HashSet<>(a);
        ret.addAll(b);
        return ret;
    }

    private void coalesce() {
        var m = new ArrayList<>(worklistMoves).get(0);
        assert m instanceof AsmInst.mv;
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
        assert u != Register.Machine.get("zero");
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
            spillWorklist.add(u);
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
        var u = new ArrayList<>(freezeWorklist).get(0);
        freezeWorklist.remove(u);
        simplifyWorklist.add(u);
        freezeMoves(u);
    }

    class degreeComparator implements Comparator<Register.base> {
        @Override
        public int compare(Register.base o1, Register.base o2) {
            return -degree.get(o1).compareTo(degree.get(o2));
        }
    }

    private void selectSpill() {
        //TODO: using better heuristic
        var t = new ArrayList<>(spillWorklist);
        t.sort(new degreeComparator());

        Register.base m = t.get(0);
        while (t.size() > 0 && newTemps.contains(t.get(0))) t.remove(0);
        if (spillWorklist.size() > 0) m = t.get(0);

        spillWorklist.remove(m);
        simplifyWorklist.add(m);
        freezeMoves(m);
    }

    private void assignColors(AsmStruct.Function func) {
        while (!selectStack.isEmpty()) {
            var n = selectStack.pop();
            var okColors = new HashSet<>(Register.Machine.available);
            assert okColors.size() == K;
            for (var w : adjList.get(n)) {
                if (setUnion(coloredNodes, precolored).contains(getAlias(w))) {
                    okColors.remove(getAlias(w).getColor());
                }
            }
            if (okColors.isEmpty()) {
                spilledNodes.add(n);
            } else {
                coloredNodes.add(n);
                var c = new ArrayList<>(okColors).get(0);
                n.setColor(c);
            }
        }
        for (var n : coalescedNodes) {
            n.setColor(getAlias(n).getColor());
        }
    }

    private void rewriteProgram(AsmStruct.Function func) {
        Set<Register.base> newTemps = new HashSet<>();
//        System.err.printf("Spilled %d nodes\n", spilledNodes.size());

        for (var v : spilledNodes) {
            var p = func.newVariable();
            for (var inst : new HashSet<>(v.def)) {
                var reg = new Register.Virtual();
                newTemps.add(reg);
                var newInst = new AsmInst.store(reg, Register.Machine.get("sp"), p, 4, inst.belong);
                inst.belong.moveInst(newInst, inst.belong.instructions.indexOf(inst) + 1);
                inst.replaceDefWith(v, reg);
            }

            for (var inst : new HashSet<>(v.use)) {
                var reg = new Register.Virtual();
                newTemps.add(reg);
                var newInst = new AsmInst.load(Register.Machine.get("sp"), p, 4, reg, inst.belong);
                inst.belong.moveInst(newInst, inst.belong.instructions.indexOf(inst));
                inst.replaceUseWith(v, reg);
            }
        }
        this.newTemps.addAll(newTemps);

        spilledNodes = new HashSet<>();
        initial = setUnion(setUnion(coloredNodes, coalescedNodes), newTemps);
        coloredNodes = new HashSet<>();
        coalescedNodes = new HashSet<>();
    }
}

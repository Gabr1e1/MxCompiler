package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;
import com.gabriel.compiler.IR.IRConstant;

import java.util.*;

public class CFG {
    public static class Node {
        BasicBlock block;
        List<Node> in = new ArrayList<>(), out = new ArrayList<>();

        Node(BasicBlock block) {
            this.block = block;
        }
    }

    Node entry;
    private Map<BasicBlock, Node> corres = new HashMap<>();

    private void addEdge(Node a, Node b) { //a -> b
//        System.out.printf("EDGE: %s --> %s\n", a.block.getName(), b.block.getName());
        a.out.add(b);
        b.in.add(a);
    }

    private Node build(BasicBlock block) {
        Node ret = new Node(block);
        corres.put(block, ret);
//        System.out.printf("BUILD: %s\n", block);
        var successors = block.getSuccessors();
        for (var succ : successors) {
            if (corres.get(succ) == null) corres.put(succ, build(succ));
            addEdge(ret, corres.get(succ));
        }
        return ret;
    }

    public void cleanUnreachableBlocks(IRConstant.Function func) {
        var delList = new ArrayList<BasicBlock>();
        func.blocks.forEach((block) -> {
            if (corres.get(block) == null) delList.add(block);
        });
        delList.forEach(func::delBlock);
    }

    public CFG(BasicBlock entryBlock, boolean reverse) {
        build(entryBlock.belong.blocks.get(0));
        entry = corres.get(entryBlock);
        cleanUnreachableBlocks(entryBlock.belong);
        if (reverse) {
            for (var node : corres.values()) {
                var tmp = node.out;
                node.out = node.in;
                node.in = tmp;
            }
        }
    }

    private Set<BasicBlock> visited;

    private void visit(Node cur, List<BasicBlock> rpo) {
        if (cur == null) return;
        visited.add(cur.block);
        for (var succ : cur.out) {
            if (!visited.contains(succ.block)) visit(succ, rpo);
        }
        rpo.add(cur.block);
    }

    List<BasicBlock> getPostOrder() {
        var ret = new ArrayList<BasicBlock>();
        visited = new HashSet<>();
        visit(entry, ret);
        return ret;
    }

    List<BasicBlock> getRPO() {
        var ret = getPostOrder();
        Collections.reverse(ret);
        return ret;
    }

    List<BasicBlock> getPredecessors(BasicBlock block) {
        var ret = new ArrayList<BasicBlock>();
        if (corres.get(block) != null) {
            corres.get(block).in.forEach((n) -> ret.add(n.block));
        }
        return ret;
    }

    List<BasicBlock> getSuccessors(BasicBlock block) {
        var ret = new ArrayList<BasicBlock>();
        if (corres.get(block) != null) {
            corres.get(block).out.forEach((n) -> ret.add(n.block));
        }
        return ret;
    }
}

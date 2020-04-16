package com.gabriel.compiler.optimization;

import com.gabriel.compiler.IR.BasicBlock;

import java.util.*;

public class DomTree {
    public static class Node {
        BasicBlock block;

        Node father; //Immediate Dominator
        List<Node> children = new ArrayList<>();

        Node(BasicBlock cur) {
            this.block = cur;
        }
    }

    Node root;
    private CFG cfg;
    private final Map<BasicBlock, Node> corres = new HashMap<>();
    private final List<BasicBlock> rpo;
    private final Map<BasicBlock, Integer> rpoNum = new HashMap<>();

    public DomTree(BasicBlock entryBlock) {
        cfg = new CFG(entryBlock);
        rpo = cfg.getRPO();
        int cnt = 0;
        for (var b : rpo) {
            corres.put(b, new Node(b));
            rpoNum.put(b, cnt++);
        }

        //Initialize
        corres.get(entryBlock).father = corres.get(entryBlock);

        //Iterative Algorithm
        boolean changed = true;
        while (changed) {
            changed = false;
            for (var block : rpo) {
                var preds = cfg.getPredecessors(block);
                boolean first = true;
                Node newIDom = null;
                for (var pred : preds) {
                    if (corres.get(pred).father == null) continue;
                    if (first) {
                        first = false;
                        newIDom = corres.get(pred);
                    } else {
                        newIDom = getLCA(newIDom, corres.get(pred));
                    }
                }
                if (newIDom != null && newIDom != corres.get(block).father) {
                    changed = true;
                    corres.get(block).father = newIDom;
                }
            }
        }
        root = corres.get(entryBlock);
    }

    private Node getLCA(Node u, Node v) {
        int dep1 = rpoNum.get(u.block), dep2 = rpoNum.get(v.block);
        while (dep1 != dep2) {
            while (dep1 > dep2) {
                u = u.father;
                dep1 = rpoNum.get(u.block);
            }
            while (dep2 > dep1) {
                v = v.father;
                dep2 = rpoNum.get(v.block);
            }
        }
        return u;
    }

    public void print() {
        for (var block : rpo) {
            System.out.printf("IDom of %s is %s\n",
                    block.getName(), corres.get(block).father.block.getName());
            System.out.printf("Dominance Frontier of %s: ", block.getName());
            if (DF.get(block) != null)
                DF.get(block).forEach((b) -> System.out.printf("%s ", b.getName()));
            System.out.print("\n\n");
        }
    }

    private Map<BasicBlock, Set<BasicBlock>> DF = new HashMap<>();

    public void calcDominanceFrontier() {
        for (var block : rpo) {
            var preds = cfg.getPredecessors(block);
            if (preds.size() <= 1) continue;
            for (var pred : preds) {
                var curPred = pred;
                while (curPred != corres.get(block).father.block) {
                    DF.computeIfAbsent(curPred, k -> new HashSet<>());
                    DF.get(curPred).add(block);
                    curPred = corres.get(curPred).father.block;
                }
            }
        }
//        print();
    }

    public Set<BasicBlock> getDominanceFrontier(BasicBlock block) {
        return DF.get(block);
    }
}

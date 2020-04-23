package com.gabriel.compiler.util;

public class Pair<U, V> {
    public U first;
    public V second;

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?,?> p = (Pair<?,?>) obj;
        return this.first.equals(p.first) && this.second.equals(second);
    }
}

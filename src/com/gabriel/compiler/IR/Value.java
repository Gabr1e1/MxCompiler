package com.gabriel.compiler.IR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Value {
    public enum Lattice {UPPER, LOWER, CONST}

    public static class ConditionalLattice {
        public Lattice type;
        public int value;

        public ConditionalLattice() {
            type = Lattice.UPPER;
        }

        public ConditionalLattice(Lattice type) {
            this.type = type;
        }

        public ConditionalLattice(ConditionalLattice other) {
            this.type = other.type;
            this.value = other.value;
        }

        public static ConditionalLattice and(ConditionalLattice left, ConditionalLattice right) {
            if (left.type == Lattice.LOWER) return new ConditionalLattice(Lattice.LOWER);
            if (right.type == Lattice.LOWER) return new ConditionalLattice(Lattice.LOWER);

            if (left.type == Lattice.UPPER) return new ConditionalLattice(right);
            if (right.type == Lattice.UPPER) return new ConditionalLattice(left);

            assert left.type == Lattice.CONST && right.type == Lattice.CONST;
            if (left.value != right.value) return new ConditionalLattice(Lattice.LOWER);
            else return new ConditionalLattice(right);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            return type == ((ConditionalLattice) obj).type && value == ((ConditionalLattice) obj).value;
        }

        public boolean isUpper() {
            return type == Lattice.UPPER;
        }

        public boolean isLower() {
            return type == Lattice.LOWER;
        }

        public boolean isConst() {
            return type == Lattice.CONST;
        }

        public void setUpper() {
            type = Lattice.UPPER;
        }

        public void setLower() {
            type = Lattice.LOWER;
        }

        public void setConst(int num) {
            this.type = Lattice.CONST;
            this.value = num;
        }
    }

    static Map<String, Integer> counter = new HashMap<>();
    static int label = 0;

    public String originalName;
    public String name;
    public IRType.Type type;
    public Use user;
    public ConditionalLattice lattice = new ConditionalLattice();

    public String gen(String name) {
        int cnt = 0;
        if (name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
            name = name.substring(0, name.lastIndexOf("."));
        }

        if (counter.get(name) != null) {
            cnt = counter.get(name) + 1;
            counter.put(name, cnt);
        } else {
            counter.put(name, 0);
        }
        return name + (cnt == 0 ? "" : "." + cnt);
    }

    private boolean noName() {
        return this instanceof IRConstant.ConstInteger;
    }

    public Value(String originalName, IRType.Type type) {
        this.originalName = originalName;
        this.type = type;
        this.name = noName() ? "" : gen(originalName);
        this.user = new Use();
    }

    public Value(Value other) {
        this.originalName = other.originalName;
        this.type = other.type;
        this.name = noName() ? "" : gen(this.originalName);
        this.user = new Use();
        this.lattice = other.lattice;
    }

    public void duplicate(Value other) {
        this.originalName = other.originalName;
        this.type = other.type;
        this.name = other.name;
        this.user = other.user;
    }

    public void changeName(String newName) {
        this.originalName = newName;
        this.name = noName() ? "" : gen(newName);
    }

    public String getName() {
        return name;
    }

    public String getPrintName() {
        return "%" + name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Object accept(IRVisitor visitor) {
        return visitor.visit(this);
    }

    public String toString() {
        IRPrinter t = new IRPrinter();
        if (type instanceof IRType.VoidType)
            return (String) type.accept(t);
        else return type.accept(t) + " %" + name;
    }

    public void replaceAllUsesWith(Value other) {
        if (this == other) return;
        for (var u : user.user) {
            var index = u.findOperand(this);
            u.operands.set(index, other);
            if (other != null) other.user.addUser(u);
        }
        user = new Use();
    }

    public List<User> getUser() {
        return user.user;
    }

    public IRType.Type getType() {
        return type;
    }
}

package com.gabriel.compiler.IR;

import java.util.List;

abstract class Constant extends User {
    Constant(String name, Type type) {
        super(name, type);
    }
}

public class IRConstant {
    public static class ConstInteger extends Constant {
        ConstInteger(String name, Type type) {
            super(name, type);
        }
    }

    public static class GlobalVariable extends Constant {
        Value Initialization;

        GlobalVariable(String name, Type type, Value initialization) {
            super(name, type);
            this.Initialization = initialization;
            addOperand(initialization);
        }
    }

    public static class Function extends Constant {
        List<BasicBlock> blocks;

        Function(String name, Type type) {
            super(name, type);
        }

        void addBlock(BasicBlock block) {
            blocks.add(block);
        }
    }
}



package com.gabriel.compiler.frontend;

import java.util.ArrayList;
import java.util.List;

abstract class Node {
    public Scope scope;
    public Node father;
    public List<Node> children;

    Node() {
        this.scope = null;
        this.father = null;
        this.children = null;
    }

    Node(Scope scope) {
        this.scope = scope;
        this.father = null;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        if (child == null) return;
        child.father = this;
        this.children.add(child);
    }
}

public class ASTNode {
    public static class Program extends Node {
        //TODO: delete redundant lists, info already in List<Node> children
        List<Node> classes, functions, variables;

        Program(Scope globalScope, List<Node> classes, List<Node> functions, List<Node> variables) {
            super(globalScope);
            this.classes = classes;
            this.functions = functions;
            this.variables = variables;
        }
    }

    public static class Class extends Node {
        List<Variable> variables;
        List<Function> functions;
        String className;

        Class(String className, Scope scope, List<Variable> variables, List<Function> functions) {
            super(scope);
            this.className = className;
            this.variables = variables;
            this.functions = functions;
        }
    }

    public static class Param extends Node {
        Type type;
        String id;

        Param(String id, Type type) {
            this.id = id;
            this.type = type;
        }
    }

    public static class ParamList extends Node {
        List<Param> paramList;

        ParamList(List<Param> paramList) {
            this.paramList = paramList;
        }
    }

    public static class Function extends Node {
        Type returnType;
        String funcName;
        ParamList paramList;
        Block block;

        Function(Scope scope, Type returnType, String funcName, ParamList paramList, Block block) {
            super(scope);
            this.returnType = returnType;
            this.funcName = funcName;
            this.paramList = paramList;
            this.block = block;
        }
    }

    public static class Variable extends Node {
        Type type;
        String id;
        Expression Initilization;

        Variable(Type type, String id, Expression Initilization) {
            this.type = type;
            this.id = id;
            this.Initilization = Initilization;
        }
    }

    public static class VariableList extends Node {
        public List<Variable> variables;

        VariableList(List<Variable> variables) {
            this.variables = variables;
        }
    }

    public static class TypeNode extends Node {
        public Type type;

        TypeNode(Type type) {
            super();
            this.type = type;
        }
    }

    public static class Expression extends Node {

    }

    public static class Block extends Node {
        List<Statement> statements = new ArrayList<Statement>();

        Block(Scope scope, List<Statement> statements) {
            super(scope);
            this.statements = statements;
        }

        void addStatement(Statement statement) {
            this.statements.add(statement);
        }
    }

    public static class Statement extends Node {
    }

    public static class ForStatement extends Statement {
        Expression init, cond, incr;

        public ForStatement(Expression init, Expression cond, Expression incr) {
            this.init = init;
            this.cond = cond;
            this.incr = incr;
        }
    }

    public static class WhileStatement extends Statement {
        Expression cond;

        public WhileStatement(Expression cond) {
            this.cond = cond;
        }
    }
}


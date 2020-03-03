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

        Class(Scope scope, List<Variable> variables, List<Function> functions) {
            super(scope);
            this.variables = variables;
            this.functions = functions;
        }
    }

    public static class Function extends Node {

    }

    public static class Variable extends Node {
        Type type;
        String id;
        Expression Initilization;

        Variable(Scope scope, Type type, String id, Expression Initilization) {
            super(scope);
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
}


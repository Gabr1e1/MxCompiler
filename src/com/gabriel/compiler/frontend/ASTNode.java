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

    //TODO: this method doesn't seem necessary...
    public void addChild(Node child) {
        if (child == null) return;
        child.father = this;
        this.children.add(child);
    }
}

public class ASTNode {
    public static class Program extends Node {
        //TODO: delete redundant lists, info already in List<Node> children
        List<Class> classes;
        List<Function> functions;
        List<Variable> variables;

        Program(Scope globalScope, List<Class> classes, List<Function> functions, List<Variable> variables) {
            super(globalScope);
            this.classes = classes;
            this.functions = functions;
            this.variables = variables;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
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

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Param extends Node {
        Type type;
        String id;

        Param(String id, Type type) {
            this.id = id;
            this.type = type;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ParamList extends Node {
        List<Param> paramList;

        ParamList(List<Param> paramList) {
            this.paramList = paramList;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
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

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Variable extends Node {
        Type type;
        String id;
        Expression Initialization;

        Variable(Type type, String id, Expression Initialization) {
            this.type = type;
            this.id = id;
            this.Initialization = Initialization;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class VariableList extends Statement {
        List<Variable> variables;

        VariableList(List<Variable> variables) {
            this.variables = variables;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class TypeNode extends Node {
        public Type type;

        TypeNode(Type type) {
            super();
            this.type = type;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Statement extends Node {
        Statement() {
        }

        Statement(Scope scope) {
            super(scope);
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Block extends Statement {
        List<Statement> statements;

        Block(Scope scope, List<Statement> statements) {
            super(scope);
            this.statements = statements;
        }

        void addStatement(Statement statement) {
            this.statements.add(statement);
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ExprStatement extends Statement {
        Expression expr;

        ExprStatement(Expression expr) {
            this.expr = expr;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ForStatement extends Statement {
        Expression init, cond, incr;
        Statement statement;

        ForStatement(Expression init, Expression cond, Expression incr, Statement statement) {
            this.init = init;
            this.cond = cond;
            this.incr = incr;
            this.statement = statement;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class WhileStatement extends Statement {
        Expression cond;
        Statement statement;

        WhileStatement(Expression cond, Statement statement) {
            this.cond = cond;
            this.statement = statement;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ConditionalStatement extends Statement {
        Expression cond;
        Statement if_statement, else_statement;

        ConditionalStatement(Expression cond, Statement if_statement, Statement else_statement) {
            this.cond = cond;
            this.if_statement = if_statement;
            this.else_statement = else_statement;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ReturnStatement extends Statement {
        Expression expr;

        ReturnStatement(Expression expr) {
            this.expr = expr;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class BreakStatement extends Statement {
        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ContinueStatement extends Statement {
        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Expression extends Node {
        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ExpressionList extends Node {
        List<Expression> exprList;

        ExpressionList(List<Expression> exprList) {
            this.exprList = exprList;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ConstantExpression extends Expression {
        int numConstant;
        String strConstant = "";
        boolean isBool = false, boolConstant;
        boolean isNull = false, isThis = false;
        String id = "";

        ConstantExpression(int a) {
            this.numConstant = a;
        }

        ConstantExpression(String a, boolean isId) {
            switch (a) {
                case "true":
                case "false":
                    this.isBool = true;
                    this.boolConstant = a.equals("true");
                    break;
                case "null":
                    this.isNull = true;
                    break;
                case "this":
                    this.isThis = true;
                    break;
                default:
                    if (isId) this.id = a;
                    else this.strConstant = a;
                    break;
            }
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
        //TODO: methods that return to which constant type the expression belongs
    }

    public static class SuffixExpression extends Expression {
        Expression expr;
        String op;

        SuffixExpression(Expression expr, String op) {
            this.expr = expr;
            this.op = op;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class UnaryExpression extends Expression {
        Expression expr;
        String op;

        UnaryExpression(Expression expr, String op) {
            this.expr = expr;
            this.op = op;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class BinaryExpression extends Expression {
        Expression expr1, expr2;
        String op;

        BinaryExpression(Expression expr1, Expression expr2, String op) {
            this.expr1 = expr1;
            this.expr2 = expr2;
            this.op = op;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class CmpExpression extends Expression {
        Expression expr1, expr2;
        String op;

        CmpExpression(Expression expr1, Expression expr2, String op) {
            this.expr1 = expr1;
            this.expr2 = expr2;
            this.op = op;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class LogicExpression extends Expression {
        Expression expr1, expr2;
        String op;

        LogicExpression(Expression expr1, Expression expr2, String op) {
            this.expr1 = expr1;
            this.expr2 = expr2;
            this.op = op;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class AssignExpression extends Expression {
        Expression expr1, expr2;

        AssignExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class MemberExpression extends Expression {
        Expression expr;
        String id;

        MemberExpression(Expression expr, String id) {
            this.expr = expr;
            this.id = id;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class FuncExpression extends Expression {
        String funcName;
        ExpressionList exprList;

        FuncExpression(String funcName, ExpressionList exprList) {
            this.funcName = funcName;
            this.exprList = exprList;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ArrayExpression extends Expression {
        Expression expr1, expr2;

        ArrayExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class NewExpression extends Expression {
        Type type;
        List<Expression> expressions;
        int dimension_left;

        NewExpression(Type type, List<Expression> expressions, int dimension_left) {
            this.type = type;
            this.expressions = expressions;
            this.dimension_left = dimension_left;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }
}


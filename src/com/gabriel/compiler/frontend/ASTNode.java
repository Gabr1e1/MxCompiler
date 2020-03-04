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

    //TODO: Doesn't seem necessary...
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

    public static class Block extends Node {
        List<Statement> statements;

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
        Statement statement;

        ForStatement(Expression init, Expression cond, Expression incr, Statement statement) {
            this.init = init;
            this.cond = cond;
            this.incr = incr;
            this.statement = statement;
        }
    }

    public static class WhileStatement extends Statement {
        Expression cond;
        Statement statement;

        WhileStatement(Expression cond, Statement statement) {
            this.cond = cond;
            this.statement = statement;
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
    }

    public static class ReturnStatement extends Statement {
        Expression expr;

        ReturnStatement(Expression expr) {
            this.expr = expr;
        }
    }

    public static class BreakStatement extends Statement {
    }

    public static class ContinueStatement extends Statement {
    }

    public static class Expression extends Node {
    }

    public static class ExpressionList extends Node {
        List<Expression> exprList;

        ExpressionList(List<Expression> exprList) {
            this.exprList = exprList;
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
        //TODO: methods that return to which constant type the expression belongs
    }

    public static class SuffixExpression extends Expression {
        Expression expr;
        String op;

        SuffixExpression(Expression expr, String op) {
            this.expr = expr;
            this.op = op;
        }
    }

    public static class UnaryExpression extends Expression {
        Expression expr;
        String op;

        UnaryExpression(Expression expr, String op) {
            this.expr = expr;
            this.op = op;
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
    }

    public static class CmpExpression extends Expression {
        Expression expr1, expr2;
        String op;

        CmpExpression(Expression expr1, Expression expr2, String op) {
            this.expr1 = expr1;
            this.expr2 = expr2;
            this.op = op;
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
    }

    public static class AssignExpression extends Expression {
        Expression expr1, expr2;

        AssignExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }
    }

    public static class MemberExpression extends Node {
        Expression expr;
        String id;

        MemberExpression(Expression expr, String id) {
            this.expr = expr;
            this.id = id;
        }
    }

    public static class FuncExpression extends Node {
        Expression expr;
        ExpressionList exprList;

        FuncExpression(Expression expr, ExpressionList exprList) {
            this.expr = expr;
            this.exprList = exprList;
        }
    }

    public static class ArrayExpression extends Node {
        Expression expr1, expr2;

        ArrayExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
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
    }

}


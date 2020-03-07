package com.gabriel.compiler.frontend;

import java.util.ArrayList;
import java.util.List;

abstract class Node {
    public Scope scope;  //the scope node ** belongs **
    public Node father;

    Node() {
        this.scope = null;
        this.father = null;
    }

    Node(Scope scope) {
        this.scope = scope;
        this.father = null;
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
        List<Function> constructors;
        String className;

        Class(Scope scope, String className, List<Variable> variables, List<Function> functions, List<Function> constructors) {
            super(scope);
            this.className = className;
            this.variables = variables;
            this.functions = functions;
            this.constructors = constructors;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Param extends Node {
        Type type;
        String id;

        Param(Scope scope, String id, Type type) {
            super(scope);
            this.id = id;
            this.type = type;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ParamList extends Node {
        List<Param> paramList;

        ParamList(Scope scope) {
            super(scope);
            this.paramList = new ArrayList<>();
        }

        ParamList(Scope scope, List<Param> paramList) {
            super(scope);
            this.paramList = paramList;
        }

        int size() {
            return paramList.size();
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

        boolean isConstructor() {
            return returnType == null;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Variable extends Node {
        Type type;
        String id;
        Expression Initialization;

        Variable(Scope scope, Type type, String id, Expression Initialization) {
            super(scope);
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

        VariableList(Scope scope, List<Variable> variables) {
            super(scope);
            this.variables = variables;
        }

        int size() {
            return variables.size();
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class TypeNode extends Node {
        public Type type;

        TypeNode(Scope scope, Type type) {
            super(scope);
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

        ExprStatement(Scope scope, Expression expr) {
            super(scope);
            this.expr = expr;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ForStatement extends Statement {
        Expression init, cond, incr;
        Statement statement;

        ForStatement(Scope scope, Expression init, Expression cond, Expression incr, Statement statement) {
            super(scope);
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

        WhileStatement(Scope scope, Expression cond, Statement statement) {
            super(scope);
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

        ConditionalStatement(Scope scope, Expression cond, Statement if_statement, Statement else_statement) {
            super(scope);
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

        ReturnStatement(Scope scope, Expression expr) {
            super(scope);
            this.expr = expr;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class BreakStatement extends Statement {
        public BreakStatement(Scope scope) {
            super(scope);
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ContinueStatement extends Statement {
        public ContinueStatement(Scope scope) {
            super(scope);
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Expression extends Node {
        Type type;

        Expression(Scope scope) {
            super(scope);
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ExpressionList extends Node {
        List<Expression> exprList;

        ExpressionList(Scope scope) {
            super(scope);
            exprList = new ArrayList<>();
        }

        ExpressionList(Scope scope, List<Expression> exprList) {
            super(scope);
            this.exprList = exprList;
        }

        int size() {
            return exprList.size();
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class LiteralExpression extends Expression {
        int numConstant;
        String strConstant = null;
        boolean isBool = false, boolConstant;
        boolean isNull = false, isThis = false;
        String id = null;
        boolean isFunc = false;

        LiteralExpression(Scope scope, int a) {
            super(scope);
            this.numConstant = a;
        }

        LiteralExpression(Scope scope, String a, boolean isId) {
            super(scope);
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
                    if (isId)
                        this.id = a;
                    else
                        this.strConstant = a.substring(1, a.length() - 1);
                    break;
            }
        }

        LiteralExpression(Scope scope, String a, boolean isId, boolean isFunc) {
            this(scope, a, isId);
            this.isFunc = isFunc;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
        //TODO: methods that return to which constant type the expression belongs
    }

    public static class SuffixExpression extends Expression {
        Expression expr;
        String op;

        SuffixExpression(Scope scope, Expression expr, String op) {
            super(scope);
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

        UnaryExpression(Scope scope, Expression expr, String op) {
            super(scope);
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

        BinaryExpression(Scope scope, Expression expr1, Expression expr2, String op) {
            super(scope);
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

        CmpExpression(Scope scope, Expression expr1, Expression expr2, String op) {
            super(scope);
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

        LogicExpression(Scope scope, Expression expr1, Expression expr2, String op) {
            super(scope);
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

        AssignExpression(Scope scope, Expression expr1, Expression expr2) {
            super(scope);
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

        MemberExpression(Scope scope, Expression expr, String id) {
            super(scope);
            this.expr = expr;
            this.id = id;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class FuncExpression extends Expression {
        Expression expr;
        ExpressionList exprList;

        //b.fun(123) / fun(123)
        FuncExpression(Scope scope, Expression expr, ExpressionList exprList) {
            super(scope);
            this.expr = expr;
            this.exprList = exprList;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class ArrayExpression extends Expression {
        Expression expr;
        List<Expression> index;

        ArrayExpression(Scope scope, Expression expr, List<Expression> index) {
            super(scope);
            this.expr = expr;
            this.index = index;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class NewExpression extends Expression {
        String id;
        List<Expression> expressions;
        int dimension_left;

        NewExpression(Scope scope, String id, List<Expression> expressions, int dimension_left) {
            super(scope);
            this.id = id;
            this.expressions = expressions;
            this.dimension_left = dimension_left;
        }

        void accept(ASTVisitor visitor) {
            visitor.visit(this);
        }
    }
}


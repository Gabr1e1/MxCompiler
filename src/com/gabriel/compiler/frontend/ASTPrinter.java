package com.gabriel.compiler.frontend;

public class ASTPrinter implements ASTVisitor {
    //TODO: Print scope

    @Override
    public void visit(ASTNode.Program node) {
        System.out.println("Visiting Program");
        node.classes.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
    }

    @Override
    public void visit(ASTNode.Class node) {
        System.out.println("Visiting class: " + node.className);
        node.variables.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
    }

    @Override
    public void visit(ASTNode.Function node) {
        System.out.printf("Visiting function %s with return type %s", node.funcName, node.returnType);
        if (node.paramList != null) {
            System.out.print("and parameters: ");
            node.paramList.accept(this);
        }
        System.out.println("");
        node.block.accept(this);
    }

    @Override
    public void visit(ASTNode.Variable node) {
        System.out.printf("Visiting variable %s: %s", node.id, node.type);
        if (node.Initialization != null) {
            System.out.print(" with init ");
            node.Initialization.accept(this);
        }
        System.out.println("");
    }

    @Override
    public void visit(ASTNode.VariableList node) {
        node.variables.forEach((var) -> var.accept(this));
    }

    @Override
    public void visit(ASTNode.TypeNode node) {
        System.out.println(node.type);
    }

    @Override
    public void visit(ASTNode.Param node) {
        System.out.print(node.id + ": " + node.type + ", ");
    }

    @Override
    public void visit(ASTNode.ParamList node) {
        System.out.print("[");
        node.paramList.forEach((param) -> param.accept(this));
        System.out.print("]");
    }

    @Override
    public void visit(ASTNode.Statement node) {
        System.out.println("123");
        node.accept(this);
    }

    @Override
    public void visit(ASTNode.Block node) {
        node.statements.forEach((stmt) -> stmt.accept(this));
    }

    @Override
    public void visit(ASTNode.ForStatement node) {

    }

    @Override
    public void visit(ASTNode.WhileStatement node) {

    }

    @Override
    public void visit(ASTNode.ConditionalStatement node) {

    }

    @Override
    public void visit(ASTNode.ReturnStatement node) {

    }

    @Override
    public void visit(ASTNode.BreakStatement node) {

    }

    @Override
    public void visit(ASTNode.ContinueStatement node) {

    }

    @Override
    public void visit(ASTNode.ExprStatement node) {
        node.expr.accept(this);
        System.out.println("");
    }

    @Override
    public void visit(ASTNode.Expression node) {

    }

    @Override
    public void visit(ASTNode.ExpressionList node) {

    }

    @Override
    public void visit(ASTNode.ConstantExpression node) {

    }

    @Override
    public void visit(ASTNode.SuffixExpression node) {

    }

    @Override
    public void visit(ASTNode.UnaryExpression node) {

    }

    @Override
    public void visit(ASTNode.BinaryExpression node) {

    }

    @Override
    public void visit(ASTNode.CmpExpression node) {

    }

    @Override
    public void visit(ASTNode.LogicExpression node) {

    }

    @Override
    public void visit(ASTNode.AssignExpression node) {

    }

    @Override
    public void visit(ASTNode.MemberExpression node) {

    }

    @Override
    public void visit(ASTNode.FuncExpression node) {
        System.out.printf("Calling %s", node.funcName);
        if (node.exprList != null) {
            System.out.print("(");
            node.exprList.accept(this);
            System.out.print(")");
        }
    }

    @Override
    public void visit(ASTNode.ArrayExpression node) {

    }

    @Override
    public void visit(ASTNode.NewExpression node) {

    }
}

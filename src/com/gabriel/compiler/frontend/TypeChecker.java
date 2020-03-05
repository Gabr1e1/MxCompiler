package com.gabriel.compiler.frontend;

public class TypeChecker implements ASTVisitor {
    @Override
    public void visit(ASTNode.Program node) {
        node.classes.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
    }

    @Override
    public void visit(ASTNode.Class node) {
        node.variables.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
    }

    @Override
    public void visit(ASTNode.Function node) {
        if (node.paramList != null) {
            node.paramList.accept(this);
        }
        node.block.accept(this);
    }

    @Override
    public void visit(ASTNode.Variable node) {
        //TODO: not void

    }

    @Override
    public void visit(ASTNode.VariableList node) {

    }

    @Override
    public void visit(ASTNode.TypeNode node) {

    }

    @Override
    public void visit(ASTNode.Param node) {

    }

    @Override
    public void visit(ASTNode.ParamList node) {

    }

    @Override
    public void visit(ASTNode.Statement node) {

    }

    @Override
    public void visit(ASTNode.Block node) {

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

    }

    @Override
    public void visit(ASTNode.Expression node) {

    }

    @Override
    public void visit(ASTNode.ExpressionList node) {

    }

    @Override
    public void visit(ASTNode.LiteralExpression node) {

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

    }

    @Override
    public void visit(ASTNode.ArrayExpression node) {

    }

    @Override
    public void visit(ASTNode.NewExpression node) {

    }
}

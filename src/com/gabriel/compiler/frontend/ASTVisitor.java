package com.gabriel.compiler.frontend;

public interface ASTVisitor {
    void visit(ASTNode.Program node);
    void visit(ASTNode.Class node);
    void visit(ASTNode.Function node);
    void visit(ASTNode.Variable node);
    void visit(ASTNode.VariableList node);

    void visit(ASTNode.TypeNode node);
    void visit(ASTNode.Param node);
    void visit(ASTNode.ParamList node);

    void visit(ASTNode.Statement node);
    void visit(ASTNode.Block node);
    void visit(ASTNode.ForStatement node);
    void visit(ASTNode.WhileStatement node);
    void visit(ASTNode.ConditionalStatement node);
    void visit(ASTNode.ReturnStatement node);
    void visit(ASTNode.BreakStatement node);
    void visit(ASTNode.ContinueStatement node);
    void visit(ASTNode.ExprStatement node);

    void visit(ASTNode.Expression node);
    void visit(ASTNode.ExpressionList node);
    void visit(ASTNode.LiteralExpression node);
    void visit(ASTNode.SuffixExpression node);
    void visit(ASTNode.UnaryExpression node);
    void visit(ASTNode.BinaryExpression node);
    void visit(ASTNode.CmpExpression node);
    void visit(ASTNode.LogicExpression node);
    void visit(ASTNode.AssignExpression node);
    void visit(ASTNode.MemberExpression node);
    void visit(ASTNode.FuncExpression node);
    void visit(ASTNode.ArrayExpression node);
    void visit(ASTNode.NewExpression node);
}


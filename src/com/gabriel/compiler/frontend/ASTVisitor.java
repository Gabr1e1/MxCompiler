package com.gabriel.compiler.frontend;

public interface ASTVisitor {
    Object visit(ASTNode.Program node);
    Object visit(ASTNode.Class node);
    Object visit(ASTNode.Function node);
    Object visit(ASTNode.Variable node);
    Object visit(ASTNode.VariableList node);

    Object visit(ASTNode.TypeNode node);
    Object visit(ASTNode.Param node);
    Object visit(ASTNode.ParamList node);

    Object visit(ASTNode.Statement node);
    Object visit(ASTNode.Block node);
    Object visit(ASTNode.ForStatement node);
    Object visit(ASTNode.WhileStatement node);
    Object visit(ASTNode.ConditionalStatement node);
    Object visit(ASTNode.ReturnStatement node);
    Object visit(ASTNode.BreakStatement node);
    Object visit(ASTNode.ContinueStatement node);
    Object visit(ASTNode.ExprStatement node);

    Object visit(ASTNode.Expression node);
    Object visit(ASTNode.ExpressionList node);
    Object visit(ASTNode.LiteralExpression node);
    Object visit(ASTNode.SuffixExpression node);
    Object visit(ASTNode.UnaryExpression node);
    Object visit(ASTNode.BinaryExpression node);
    Object visit(ASTNode.CmpExpression node);
    Object visit(ASTNode.LogicExpression node);
    Object visit(ASTNode.AssignExpression node);
    Object visit(ASTNode.MemberExpression node);
    Object visit(ASTNode.FuncExpression node);
    Object visit(ASTNode.ArrayExpression node);
    Object visit(ASTNode.NewExpression node);
}


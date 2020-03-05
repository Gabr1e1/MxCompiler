package com.gabriel.compiler.frontend;

import com.gabriel.compiler.error.SemanticError;

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
        if (node.type.isVoid())
            throw new SemanticError.VoidType(node.id, node.scope.name);
    }

    @Override
    public void visit(ASTNode.VariableList node) {
        node.variables.forEach((var) -> var.accept(this));
    }

    @Override
    public void visit(ASTNode.TypeNode node) {
    }

    @Override
    public void visit(ASTNode.Param node) {
        if (node.type.isVoid())
            throw new SemanticError.VoidType("", node.scope.name);
    }

    @Override
    public void visit(ASTNode.ParamList node) {
        node.paramList.forEach((param) -> param.accept(this));
    }

    @Override
    public void visit(ASTNode.Statement node) {
        node.accept(this);
    }

    @Override
    public void visit(ASTNode.Block node) {
        node.statements.forEach((stmt) -> stmt.accept(this));
    }

    @Override
    public void visit(ASTNode.ForStatement node) {
        node.init.accept(this);
        node.cond.accept(this);
        if (!node.cond.type.isBool())
            throw new SemanticError.GeneralError("Condition in for loop must be boolean type", node.scope.name);
        node.incr.accept(this);
        node.statement.accept(this);
    }

    @Override
    public void visit(ASTNode.WhileStatement node) {
        node.cond.accept(this);
        if (!node.cond.type.isBool())
            throw new SemanticError.GeneralError("Condition in for loop must be boolean type", node.scope.name);
        node.statement.accept(this);
    }

    @Override
    public void visit(ASTNode.ConditionalStatement node) {
        node.cond.accept(this);
        if (!node.cond.type.isBool())
            throw new SemanticError.InvalidType("Condition in for loop must be boolean type", node.scope.name);
        node.if_statement.accept(this);
        if (node.else_statement != null)
            node.else_statement.accept(this);
    }

    @Override
    public void visit(ASTNode.ReturnStatement node) {
        node.expr.accept(this);
        if (!Type.isSameType(node.scope.belongFunction().returnType, node.expr.type)) {
            throw new SemanticError.InvalidType("Wrong return type", node.scope.name);
        }
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
    }

    @Override
    public void visit(ASTNode.Expression node) {
        node.accept(this);
    }

    @Override
    public void visit(ASTNode.ExpressionList node) {
        node.exprList.forEach((expr) -> expr.accept(this));
    }

    @Override
    public void visit(ASTNode.LiteralExpression node) {
        if (node.strConstant != null) node.type = new Type("string");
        else if (node.isBool) node.type = new Type("bool");
        else if (node.isNull) node.type = new Type("null");
        else if (node.isThis) node.type = new Type(TypeKind.CLASS, node.scope.belongClass());
        else if (node.id != null) node.type = node.scope.find(node.id);
        else node.type = new Type("int");
    }

    @Override
    public void visit(ASTNode.SuffixExpression node) {
        node.expr.accept(this);
        if (node.expr.type.toString().equals("int"))
            throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
        node.type = node.expr.type;
    }

    @Override
    public void visit(ASTNode.UnaryExpression node) {
        node.expr.accept(this);
        if (node.expr.type.toString().equals("int"))
            throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
        node.type = node.expr.type;
    }

    @Override
    public void visit(ASTNode.BinaryExpression node) {

    }

    @Override
    public void visit(ASTNode.CmpExpression node) {

    }

    @Override
    public void visit(ASTNode.LogicExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if ((!node.expr1.type.toString().equals("bool")) || (!node.expr1.type.toString().equals("bool")))
            throw new SemanticError.InvalidType("Bool is needed", node.scope.name);
        node.type = new Type("bool");
    }

    @Override
    public void visit(ASTNode.AssignExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if (!Type.isSameType(node.expr1.type, node.expr2.type))
            throw new SemanticError.TypeMismatch("Cannot assign");
        node.type = node.expr2.type;
    }

    @Override
    public void visit(ASTNode.MemberExpression node) {
        node.expr.accept(this);
        Type t = node.scope.find(node.id);
        if (!Type.isSameType(node.expr.type, t))
            throw new SemanticError.TypeMismatch("Invalid member type");
        node.type = t;
    }

    @Override
    public void visit(ASTNode.FuncExpression node) {

    }

    @Override
    public void visit(ASTNode.ArrayExpression node) {

    }

    @Override
    public void visit(ASTNode.NewExpression node) {
        node.expressions.forEach((expr) -> {
            expr.accept(this)
        });
        node.type = 
    }
}

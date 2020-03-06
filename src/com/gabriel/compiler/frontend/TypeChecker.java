package com.gabriel.compiler.frontend;

import com.gabriel.compiler.error.SemanticError;

public class TypeChecker implements ASTVisitor {
    @Override
    public void visit(ASTNode.Program node) {
        node.classes.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
        Type t = node.scope.find("main");
        if (t == null || t.typeKind != TypeKind.FUNCTION)
            throw new SemanticError.NoMainFunction();
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
        Type t;
        if (node.expr != null) {
            node.expr.accept(this);
            t = node.expr.type;
        } else
            t = new Type("void");
        if (!Type.isSameType(node.scope.belongFunction().returnType, t)) {
            throw new SemanticError.InvalidType("Wrong return type", node.scope.name);
        }
    }

    @Override
    public void visit(ASTNode.BreakStatement node) {
        /* Empty */
    }

    @Override
    public void visit(ASTNode.ContinueStatement node) {
        /* Empty */
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
        if (!node.expr.type.toString().equals("int"))
            throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
        node.type = node.expr.type;
    }

    @Override
    public void visit(ASTNode.UnaryExpression node) {
        node.expr.accept(this);
        if (!node.expr.type.toString().equals("int"))
            throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
        node.type = node.expr.type;
    }

    @Override
    public void visit(ASTNode.BinaryExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if (!Type.isSameType(node.expr1.type, node.expr2.type))
            throw new SemanticError.InvalidOperation("different type");
        if (node.op.equals("+")) {
            if (!node.expr1.type.isString() && !node.expr1.type.isInt())
                throw new SemanticError.InvalidOperation("operator " + node.op + " invalid");
        } else {
            if (!node.expr1.type.isInt())
                throw new SemanticError.InvalidOperation("operator " + node.op + " invalid");
        }
        node.type = node.expr1.type;
    }

    @Override
    public void visit(ASTNode.CmpExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if (!Type.isSameType(node.expr1.type, node.expr2.type))
            throw new SemanticError.InvalidOperation("different type");
        if (!node.expr1.type.isString() && !node.expr1.type.isBool() && !node.expr1.type.isInt())
            throw new SemanticError.InvalidOperation("operator " + node.op + " invalid");
        node.type = new Type("bool");
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
        Type t = node.scope.find(node.expr.type.toString());
        boolean flg = false;
        if (t.typeKind == TypeKind.CLASS) {
            ASTNode.Class c = (ASTNode.Class) t.node;
            //member variable
            for (ASTNode.Variable var : c.variables) {
                if (node.id.equals(var.id)) {
                    flg = true;
                    node.type = var.type;
                }
            }
            //member method
            for (ASTNode.Function func : c.functions) {
                if (node.id.equals(func.funcName)) {
                    flg = true;
                    node.type = c.scope.find(func.funcName);
                }
            }
        }
        if (!flg) throw new SemanticError.TypeMismatch("Invalid member type");
    }

    boolean compatibleCheck(ASTNode.ExpressionList exprList, ASTNode.ParamList paramList) {
        if (exprList.size() != paramList.size()) return false;
        for (int i = 0; i < exprList.size(); i++) {
            if (!Type.isSameType(exprList.exprList.get(i).type, paramList.paramList.get(i).type))
                return false;
        }
        return true;
    }

    @Override
    public void visit(ASTNode.FuncExpression node) {
        node.expr.accept(this);
        node.exprList.accept(this);
        if (!node.expr.type.isFunction())
            throw new SemanticError.TypeMismatch("Not even a function");

        ASTNode.Function func = (ASTNode.Function) node.expr.type.node;
        if (!compatibleCheck(node.exprList, func.paramList)) {
            throw new SemanticError.TypeMismatch("Not a valid function call " + node.expr.type.baseType);
        }
        node.type = func.returnType;
    }

    @Override
    public void visit(ASTNode.ArrayExpression node) {
        node.expr.accept(this);
        node.index.forEach((index) -> {
            index.accept(this);
            if (!index.type.isInt()) {
                throw new SemanticError.InvalidType("Invalid index type", node.scope.name);
            }
        });
        if (!node.expr.type.isArray()) {
            throw new SemanticError.InvalidType("Not an array", node.scope.name);
        }
        if (node.expr.type.getDimension() > node.index.size()) {
            throw new SemanticError.TypeMismatch("Too many indices");
        }
        node.type = new Type(node.expr.type.baseType, node.expr.type.getDimension() - node.index.size());
    }

    @Override
    public void visit(ASTNode.NewExpression node) {
        node.expressions.forEach((expr) -> {
            expr.accept(this);
            if (!expr.type.isInt())
                throw new SemanticError.InvalidType("Invalid index type", node.scope.name);
        });
    }
}

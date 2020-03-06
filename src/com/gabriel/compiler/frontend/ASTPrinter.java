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
            System.out.print(" and parameters: ");
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
        node.accept(this);
    }

    @Override
    public void visit(ASTNode.Block node) {
        node.statements.forEach((stmt) -> stmt.accept(this));
    }

    @Override
    public void visit(ASTNode.ForStatement node) {
        System.out.println("Visiting For loop with init: ");
        node.init.accept(this);
        System.out.println("\ncond: ");
        node.cond.accept(this);
        System.out.println("\nincr: ");
        node.incr.accept(this);
        System.out.println("");
        node.statement.accept(this);
    }

    @Override
    public void visit(ASTNode.WhileStatement node) {
        System.out.println("Visiting While loop with ");
        System.out.println("cond: ");
        node.cond.accept(this);
        node.statement.accept(this);
    }

    @Override
    public void visit(ASTNode.ConditionalStatement node) {
        System.out.print("Visiting If: ");
        node.cond.accept(this);
        System.out.println("\nIF true:");
        node.if_statement.accept(this);
        if (node.else_statement != null) {
            System.out.println("IF false:");
            node.else_statement.accept(this);
        }
    }

    @Override
    public void visit(ASTNode.ReturnStatement node) {
        System.out.print("Visiting Return ");
        if (node.expr != null)
            node.expr.accept(this);
        System.out.println("");
    }

    @Override
    public void visit(ASTNode.BreakStatement node) {
        System.out.println("Visiting Break");
    }

    @Override
    public void visit(ASTNode.ContinueStatement node) {
        System.out.println("Visiting Continue");
    }

    @Override
    public void visit(ASTNode.ExprStatement node) {
        node.expr.accept(this);
        System.out.println("");
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
        if (node.strConstant != null) System.out.print("constant \"" + node.strConstant + "\"");
        else if (node.isBool) System.out.print("constant " + (node.boolConstant ? "true" : "false"));
        else if (node.isNull) System.out.print("constant null");
        else if (node.isThis) System.out.print("constant this");
        else if (node.id != null) System.out.print("variable " + node.id);
        else System.out.printf("constant number %d", node.numConstant);
    }

    @Override
    public void visit(ASTNode.SuffixExpression node) {
        node.expr.accept(this);
        System.out.print(" " + node.op + " ");
    }

    @Override
    public void visit(ASTNode.UnaryExpression node) {
        node.expr.accept(this);
        System.out.print(" " + node.op + " ");
    }

    @Override
    public void visit(ASTNode.BinaryExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
    }

    @Override
    public void visit(ASTNode.CmpExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
    }

    @Override
    public void visit(ASTNode.LogicExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
    }

    @Override
    public void visit(ASTNode.AssignExpression node) {
        node.expr1.accept(this);
        System.out.print(" = ");
        node.expr2.accept(this);
    }

    @Override
    public void visit(ASTNode.MemberExpression node) {
        node.expr.accept(this);
        System.out.printf(".%s", node.id);
    }

    @Override
    public void visit(ASTNode.FuncExpression node) {
        System.out.print("Calling ");
        node.expr.accept(this);
        if (node.exprList != null) {
            System.out.print("(");
            node.exprList.accept(this);
            System.out.print(")");
        }
    }

    @Override
    public void visit(ASTNode.ArrayExpression node) {
        node.expr.accept(this);
        node.index.forEach((index) -> {
            System.out.print(" [ ");
            index.accept(this);
            System.out.print(" ] ");
        });
    }

    @Override
    public void visit(ASTNode.NewExpression node) {
        System.out.print("new ");
        if (node.expressions.size() != 0) {
            System.out.print("[ ");
            node.expressions.forEach((expr) -> expr.accept(this));
            System.out.print(" ]");
        }
    }
}

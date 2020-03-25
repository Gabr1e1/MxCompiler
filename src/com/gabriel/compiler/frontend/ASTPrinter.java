package com.gabriel.compiler.frontend;

public class ASTPrinter implements ASTVisitor {
    @Override
    public Object visit(ASTNode.Program node) {
        System.out.println("Visiting Program");
        node.classes.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.Class node) {
        System.out.println("Visiting class: " + node.className);
        System.out.println("Constructors:");
        node.constructors.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.Function node) {
        System.out.printf("Visiting function %s with return type %s", node.funcName, node.returnType);
        if (node.paramList != null) {
            System.out.print(" and parameters: ");
            node.paramList.accept(this);
        }
        System.out.println("");
        if (node.block != null) node.block.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        System.out.printf("Visiting variable %s: %s", node.id, node.type);
        if (node.Initialization != null) {
            System.out.print(" with init ");
            node.Initialization.accept(this);
        }
        System.out.println("");
        return null;
    }

    @Override
    public Object visit(ASTNode.VariableList node) {
        node.variables.forEach((var) -> var.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.TypeNode node) {
        System.out.println(node.type);
        return null;
    }

    @Override
    public Object visit(ASTNode.Param node) {
        System.out.print(node.id + ": " + node.type + ", ");
        return null;
    }

    @Override
    public Object visit(ASTNode.ParamList node) {
        System.out.print("[");
        node.paramList.forEach((param) -> param.accept(this));
        System.out.print("]");
        return null;
    }

    @Override
    public Object visit(ASTNode.Statement node) {
        node.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.Block node) {
        if (node.statements != null)
            node.statements.forEach((stmt) -> {
                if (stmt != null) stmt.accept(this);
            });
        return null;
    }

    @Override
    public Object visit(ASTNode.ForStatement node) {
        System.out.println("Visiting For loop with init: ");
        if (node.init != null) node.init.accept(this);
        System.out.println("\ncond: ");
        if (node.cond != null) node.cond.accept(this);
        System.out.println("\nincr: ");
        if (node.incr != null) node.incr.accept(this);
        System.out.println("");
        if (node.statement != null)
            node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        System.out.println("Visiting While loop with ");
        System.out.println("cond: ");
        node.cond.accept(this);
        if (node.statement != null)
            node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        System.out.print("Visiting If: ");
        node.cond.accept(this);
        if (node.if_statement != null) {
            System.out.println("\nIF true:");
            node.if_statement.accept(this);
        }
        if (node.else_statement != null) {
            System.out.println("IF false:");
            node.else_statement.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        System.out.print("Visiting Return ");
        if (node.expr != null)
            node.expr.accept(this);
        System.out.println("");
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        System.out.println("Visiting Break");
        return null;
    }

    @Override
    public Object visit(ASTNode.ContinueStatement node) {
        System.out.println("Visiting Continue");
        return null;
    }

    @Override
    public Object visit(ASTNode.ExprStatement node) {
        node.expr.accept(this);
        System.out.println("");
        return null;
    }

    @Override
    public Object visit(ASTNode.Expression node) {
        node.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.ExpressionList node) {
        node.exprList.forEach((expr) -> expr.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.LiteralExpression node) {
        if (node.strConstant != null) System.out.print("constant \"" + node.strConstant + "\"");
        else if (node.isBool) System.out.print("constant " + (node.boolConstant ? "true" : "false"));
        else if (node.isNull) System.out.print("constant null");
        else if (node.isThis) System.out.print("constant this");
        else if (node.id != null) System.out.print("var/func " + node.id);
        else System.out.printf("constant number %d", node.numConstant);
        return null;
    }

    @Override
    public Object visit(ASTNode.SuffixExpression node) {
        node.expr.accept(this);
        System.out.print(" " + node.op + " ");
        return null;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        node.expr.accept(this);
        System.out.print(" " + node.op + " ");
        return null;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.LogicExpression node) {
        node.expr1.accept(this);
        System.out.print(" " + node.op + " ");
        node.expr2.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        node.expr1.accept(this);
        System.out.print(" = ");
        node.expr2.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.MemberExpression node) {
        node.expr.accept(this);
        System.out.printf(".(member)%s", node.id);
        return null;
    }

    @Override
    public Object visit(ASTNode.FuncExpression node) {
        System.out.print("Calling ");
        node.expr.accept(this);
        if (node.exprList != null) {
            System.out.print("(");
            node.exprList.accept(this);
            System.out.print(")");
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.ArrayExpression node) {
        node.expr.accept(this);
        node.index.forEach((index) -> {
            System.out.print(" [ ");
            index.accept(this);
            System.out.print(" ] ");
        });
        return null;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        System.out.print("new " + node.id);
        if (node.expressions.size() != 0) {
            System.out.print("[ ");
            node.expressions.forEach((expr) -> expr.accept(this));
            System.out.print(" ]");
        }
        return null;
    }
}

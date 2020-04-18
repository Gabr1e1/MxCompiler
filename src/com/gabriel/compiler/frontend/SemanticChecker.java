package com.gabriel.compiler.frontend;

import com.gabriel.compiler.error.SemanticError;

public class SemanticChecker implements ASTVisitor {
    @Override
    public Object visit(ASTNode.Program node) {
        Type t = node.scope.find("main");
        if (t == null || t.typeKind != TypeKind.FUNCTION
                || !((ASTNode.Function) t.node).returnType.toString().equals("int")
                || ((ASTNode.Function) t.node).paramList.size() != 0)
            throw new SemanticError.NoMainFunction();
        node.classes.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.variables.forEach((n) -> n.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.Class node) {
        node.variables.forEach((n) -> n.accept(this));
        node.functions.forEach((n) -> n.accept(this));
        node.constructors.forEach((n) -> n.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.Function node) {
        if (node.paramList != null) {
            node.paramList.accept(this);
        }
        if (node.block != null) node.block.accept(this);
        if (!node.hasReturn && !node.returnType.toString().equals("void") && !node.funcName.equals("main"))
            throw new SemanticError.NoReturnStatement(node.funcName);
        return null;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        if (node.Initialization != null) {
            node.Initialization.accept(this);
        }
        if (node.type.isVoid())
            throw new SemanticError.VoidType(node.id, node.scope.name);
        if (!node.type.isPrimitiveType()) {
            Type t = node.scope.findClass(node.type.baseType);
            if (t == null || t.typeKind != TypeKind.CLASS)
                throw new SemanticError.NotDeclared(node.type.baseType, node.scope.name);
        }
        if (node.Initialization != null && !Type.isSameType(node.type, node.Initialization.type))
            throw new SemanticError.TypeMismatch(node.id);
        return null;
    }

    @Override
    public Object visit(ASTNode.VariableList node) {
        node.variables.forEach((var) -> var.accept(this));
        return null;
    }

    @Override
    public Object visit(ASTNode.TypeNode node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.Param node) {
        node.type.setLeftValue();
        if (node.type.isVoid())
            throw new SemanticError.VoidType("", node.scope.name);
        return null;
    }

    @Override
    public Object visit(ASTNode.ParamList node) {
        node.paramList.forEach((param) -> param.accept(this));
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
        if (node.init != null)
            node.init.accept(this);
        if (node.cond != null)
            node.cond.accept(this);
        if (node.cond != null && !node.cond.type.isBool())
            throw new SemanticError.GeneralError("Condition in for loop must be boolean type", node.scope.name);
        if (node.incr != null)
            node.incr.accept(this);
        if (node.statement != null)
            node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        node.cond.accept(this);
        if (!node.cond.type.isBool())
            throw new SemanticError.GeneralError("Condition in for loop must be boolean type", node.scope.name);
        if (node.statement != null)
            node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        node.cond.accept(this);
        if (!node.cond.type.isBool())
            throw new SemanticError.InvalidType("Condition in for loop must be boolean type", node.scope.name);
        if (node.if_statement != null)
            node.if_statement.accept(this);
        if (node.else_statement != null)
            node.else_statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        Type t;
        if (node.expr != null) {
            node.expr.accept(this);
            t = node.expr.type;
        } else
            t = new Type("void");
        ASTNode.Function func = node.scope.belongFunction("");
        if (func == null)
            throw new SemanticError.InvalidStatement("Cannot return here");
        if (!Type.isSameType(func.returnType, t)) {
            throw new SemanticError.InvalidType("Wrong return type", node.scope.name);
        }
        func.hasReturn = true;
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        if (!node.scope.withinLoop())
            throw new SemanticError.InvalidJump("break");
        return null;
    }

    @Override
    public Object visit(ASTNode.ContinueStatement node) {
        if (!node.scope.withinLoop())
            throw new SemanticError.InvalidJump("continue");
        return null;
    }

    @Override
    public Object visit(ASTNode.ExprStatement node) {
        node.expr.accept(this);
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
        if (node.strConstant != null) node.type = new Type("string", false);
        else if (node.isBool) node.type = new Type("bool", false);
        else if (node.isNull) node.type = new Type("null", false);
        else if (node.isThis) {
            node.type = new Type(node.scope.belongClass().className);
        } else if (node.id != null) {
            node.type = node.scope.find(node.id);
            if (node.type == null)
                throw new SemanticError.NotDeclared(node.id, node.scope.name);
            if (node.type.typeKind == TypeKind.VARIABLE) node.type.setLeftValue();
            if (node.type.typeKind == TypeKind.VARIABLE && node.isFunc)
                throw new SemanticError.NotDeclared(node.id, node.scope.name);
            if (node.type.typeKind != TypeKind.VARIABLE && !node.isFunc)
                throw new SemanticError.NotDeclared(node.id, node.scope.name);
        } else node.type = new Type("int", false);
        return null;
    }

    @Override
    public Object visit(ASTNode.SuffixExpression node) {
        node.expr.accept(this);
        if (!node.expr.type.toString().equals("int"))
            throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
        if (!node.expr.type.isLeftValue())
            throw new SemanticError.LvalueRequired();
        node.type = new Type(node.expr.type);
        node.type.setRightValue();
        return null;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        node.expr.accept(this);
        if (node.op.equals("~") || node.op.equals("!")) {
            if (!node.expr.type.toString().equals("bool") && !node.expr.type.toString().equals("int"))
                throw new SemanticError.InvalidType("Boolean is needed", node.scope.name);
            node.type = new Type(node.expr.type);
            node.type.setRightValue();
        } else {
            if (!node.expr.type.toString().equals("int"))
                throw new SemanticError.InvalidType("Integer is needed", node.scope.name);
            if (node.op.equals("++") || node.op.equals("--")) {
                if (!node.expr.type.isLeftValue())
                    throw new SemanticError.LvalueRequired();
            }
            node.type = new Type(node.expr.type);
            node.type.setLeftValue();
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
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
        node.type = new Type(node.expr1.type);
        node.type.setRightValue();
        return null;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if (!Type.isSameType(node.expr1.type, node.expr2.type))
            throw new SemanticError.InvalidOperation("different type");
        if (!node.op.equals("==") && !node.op.equals("!=")) {
            if (!node.expr1.type.isString() && !node.expr1.type.isBool() && !node.expr1.type.isInt())
                throw new SemanticError.InvalidOperation("operator " + node.op + " invalid");
        }
        node.type = new Type("bool");
        node.type.setRightValue();
        return null;
    }

    @Override
    public Object visit(ASTNode.LogicExpression node) {
        node.expr1.accept(this);
        node.expr2.accept(this);
        if ((!node.expr1.type.toString().equals("bool")) || (!node.expr1.type.toString().equals("bool")))
            throw new SemanticError.InvalidType("Bool is needed", node.scope.name);
        node.type = new Type("bool");
        node.type.setRightValue();
        return null;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        node.expr1.accept(this);
        if (!node.expr1.type.isLeftValue()) {
            throw new SemanticError.TypeMismatch("Cannot assign to right value");
        }
        node.expr2.accept(this);
        if (!Type.isSameType(node.expr1.type, node.expr2.type))
            throw new SemanticError.TypeMismatch("Cannot assign");
        node.type = new Type(node.expr1.type);
        return null;
    }

    @Override
    public Object visit(ASTNode.MemberExpression node) {
        node.expr.accept(this);
        Type t = node.scope.findClass(node.expr.type.toString());
        boolean flg = false;
        if (t != null && t.typeKind == TypeKind.CLASS) {
            ASTNode.Class c = (ASTNode.Class) t.node;
            //member variable
            for (ASTNode.Variable var : c.variables) {
                if (node.id.equals(var.id)) {
                    flg = true;
                    node.type = new Type(var.type);
                    node.type.setLeftValue();
                }
            }
            //member method
            for (ASTNode.Function func : c.functions) {
                if (node.id.equals(func.funcName)) {
                    flg = true;
                    node.type = new Type(func.scope.find(func.funcName));
                    node.type.setRightValue();
                }
            }
        } else if (node.id.equals("size")) {
            if (node.expr.type.isArray()) {
                flg = true;
                node.type = new Type(TypeKind.FUNCTION,
                        new ASTNode.Function(node.scope,
                                new Type("int"), "size",
                                new ASTNode.ParamList(node.scope),
                                null, false));
            }
        }

        if (!flg) throw new SemanticError.TypeMismatch("Invalid member type");
        return null;
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
    public Object visit(ASTNode.FuncExpression node) {
        node.expr.accept(this);
        node.exprList.accept(this);
        if (!node.expr.type.isFunction() && !node.expr.type.isClass())
            throw new SemanticError.TypeMismatch("Not a valid func expression");

        if (node.expr.type.isClass()) { //class constructor
            ASTNode.Class c = (ASTNode.Class) node.expr.type.node;
            for (ASTNode.Function func : c.constructors) {
                if (compatibleCheck(node.exprList, func.paramList)) {
                    node.type = new Type(c.className);
                    node.type.setRightValue();
                    return null;
                }
            }
            throw new SemanticError.NotDeclared("Such a constructor is not found!", node.scope.name);
        } else {
            ASTNode.Function func = (ASTNode.Function) node.expr.type.node;
            if (!compatibleCheck(node.exprList, func.paramList)) {
                throw new SemanticError.TypeMismatch("Not a valid function call " + node.expr.type.baseType);
            }
            node.type = new Type(func.returnType);
            node.type.setRightValue();
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.ArrayExpression node) {
        node.expr.accept(this);
        node.index.forEach((index) -> {
            index.accept(this);
            if (!index.type.isInt() || index.type.isArray()) {
                throw new SemanticError.InvalidType("Invalid index type", node.scope.name);
            }
        });
        if (!node.expr.type.isArray()) {
            throw new SemanticError.InvalidType("Not an array", node.scope.name);
        }
        if (node.expr.type.getDimension() < node.index.size()) {
            throw new SemanticError.TypeMismatch("Too many indices");
        }
        node.type = new Type(node.expr.type.baseType, node.expr.type.getDimension() - node.index.size());
        node.type.setLeftValue();
        return null;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        node.expressions.forEach((expr) -> {
            expr.accept(this);
            if (!expr.type.isInt())
                throw new SemanticError.InvalidType("Invalid index type", node.scope.name);
        });
        Type t = node.scope.find(node.id);
        if (t == null && !Type.isPrimitiveType(node.id))
            throw new SemanticError.InvalidType("Invalid IRType in new expression", node.scope.name);

        if (Type.isPrimitiveType(node.id)) {
            node.type = new Type(node.id, node.dimension_left + node.expressions.size());
        } else {
            node.type = new Type(t);
            node.type.setDimension(node.dimension_left + node.expressions.size());
        }
        node.type.setLeftValue();
        if (node.type.baseType.equals("void"))
            throw new SemanticError.InvalidType("Invalid IRType in new expression", node.scope.name);
        return null;
    }
}

package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.ASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class IRBuilder implements ASTVisitor {

    private Module module;
    private SymbolTable symbolTable;

    private ASTNode.Class curClass;
    private ASTNode.Function curFunc;

    IRBuilder() {
        module = new Module("__program");
        symbolTable = new SymbolTable();
    }

    @Override
    public Object visit(ASTNode.Program node) {
        node.variables.forEach((var) -> {
            if (var.Initialization != null) {
                Value v = (Value) var.Initialization.accept(this);
                IRConstant.GlobalVariable g = new IRConstant.GlobalVariable(var.id,
                        IRType.convert(var.type), v);
                symbolTable.put(g.getName(), g, node.scope);
                module.addGlobalVariable(g);
            }
        });

        node.functions.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            symbolTable.put(f.getName(), f, node.scope);
            module.addFunction(f);
        });

        node.classes.forEach((c) -> {
            IRType.ClassType classType = (IRType.ClassType) c.accept(this);
            module.addClass(classType.getName(), classType);
        });
        return module;
    }

    @Override
    public Object visit(ASTNode.Class node) {
        curClass = node;

        List<Type> members = new ArrayList<>();
        List<String> member_name = new ArrayList<>();
        node.variables.forEach((var) -> {
            Value v = (Value) var.accept(this);
            members.add(v.type);
            member_name.add(var.id);
        });
        node.functions.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            symbolTable.put(f.getName(), f, node.scope);
            module.addFunction(f);
        });

        curClass = null;
        return new IRType.ClassType(node.className, members, member_name);
    }

    @Override
    public Object visit(ASTNode.Function node) {
        curFunc = node;

        Type returnType = IRType.convert(node.returnType);
        List<Value> params = (List<Value>) (node.paramList.accept(this));
        node.block.accept(this);
        var type = new IRType.FunctionType(params, returnType);

        curFunc = null;
        var funcName = (curClass != null ? curClass.className + "_" : "") + node.funcName;
        return new IRConstant.Function(funcName, type);
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.VariableList node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.TypeNode node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.Param node) {
        Value v = new Value(node.id, IRType.convert(node.type));
        symbolTable.put(node.id, v, node.scope);
        return v;
    }

    @Override
    public Object visit(ASTNode.ParamList node) {
        List<Value> params = new ArrayList<>();
        node.paramList.forEach((param) -> params.add((Value) param.accept(this)));
        return params;
    }

    @Override
    public Object visit(ASTNode.Statement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.Block node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ForStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ContinueStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ExprStatement node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.Expression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ExpressionList node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.LiteralExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.SuffixExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.LogicExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.MemberExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.FuncExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.ArrayExpression node) {
        return null;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        return null;
    }
}

package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.ASTVisitor;
import com.gabriel.compiler.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class IRBuilder implements ASTVisitor {

    private Module module;
    private SymbolTable symbolTable;

    private ASTNode.Class curClass;
    private IRConstant.Function curFunc;
    private BasicBlock curBlock;

    private List<Pair<BasicBlock, BasicBlock>> controlBlock = new ArrayList<>();

    IRBuilder() {
        module = new Module("__program");
        symbolTable = new SymbolTable();
    }

    void init() {
        //TODO: Builtin Functions
        //TODO: String member functions
        //TODO: Main Block
    }
    //TODO: ADD USER?
    //TODO: FIX FUNCTION PARAM 0, SHOULD BE THIS

    @Override
    public Object visit(ASTNode.Program node) {
        init();
        node.variables.forEach((var) -> {
            if (var.Initialization != null) {
                Value v = (Value) var.Initialization.accept(this);
                IRConstant.GlobalVariable g = new IRConstant.GlobalVariable(var.id,
                        IRType.convert(var.type), v);
                symbolTable.put(g, node.scope);
                module.addGlobalVariable(g);
            }
        });

        node.functions.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            symbolTable.put(f, node.scope);
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
            symbolTable.put(f, node.scope);
            module.addFunction(f);
        });

        curClass = null;
        return new IRType.ClassType(node.className, members, member_name);
    }

    @Override
    public Object visit(ASTNode.Function node) {
        var funcName = (curClass != null ? curClass.className + "_" : "") + node.funcName;
        Type returnType = IRType.convert(node.returnType);
        List<Value> params = (List<Value>) (node.paramList.accept(this));
        var type = new IRType.FunctionType(params, returnType);
        curFunc = new IRConstant.Function(funcName, type);
        node.block.accept(this);
        return curFunc;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        var variable = new IRInst.AllocaInst(node.id, IRType.convert(node.type), curBlock);
        if (node.Initialization != null) {
            Value v = (Value) node.Initialization.accept(this);
            new IRInst.StoreInst(variable, v, curBlock);
        }
        return variable;
    }

    @Override
    public Object visit(ASTNode.VariableList node) {
        List<Value> vars = new ArrayList<>();
        node.variables.forEach((var) -> vars.add((Value) var.accept(this)));
        return vars;
    }

    @Override
    public Object visit(ASTNode.TypeNode node) {
        return IRType.convert(node.type);
    }

    @Override
    public Object visit(ASTNode.Param node) {
        Value v = new Value(node.id, IRType.convert(node.type));
        symbolTable.put(v, node.scope);
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
        return node.accept(this);
    }

    @Override
    public Object visit(ASTNode.Block node) {
        node.statements.forEach((stmt) -> stmt.accept(this));
        return null;
    }

    //TODO: MERGE FOR AND WHILE

    @Override
    public Object visit(ASTNode.ForStatement node) {
        Value init = (Value) node.init.accept(this);
        BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
        BasicBlock body = new BasicBlock("for_body", curFunc);
        BasicBlock after = new BasicBlock("for_after", curFunc);

        new IRInst.BranchInst(curBlock, checkCond);

        curBlock = checkCond;
        Value cond = (Value) node.cond.accept(this);
        new IRInst.BranchInst(cond, curBlock, body, after);

        curBlock = body;
        node.statement.accept(this);
        Value incr = (Value) node.incr.accept(this);
        new IRInst.BranchInst(curBlock, checkCond);

        curBlock = after;
        controlBlock.add(new Pair<>(checkCond, after));
        node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
        BasicBlock body = new BasicBlock("for_body", curFunc);
        BasicBlock after = new BasicBlock("for_after", curFunc);
        new IRInst.BranchInst(curBlock, checkCond);

        curBlock = checkCond;
        Value cond = (Value) node.cond.accept(this);
        new IRInst.BranchInst(cond, curBlock, body, after);

        curBlock = body;
        node.statement.accept(this);
        new IRInst.BranchInst(curBlock, checkCond);

        curBlock = after;
        controlBlock.add(new Pair<>(checkCond, after));
        node.statement.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        //TODO: SHOULD I USE PHI NODE?
        Value cond = (Value) (node.cond.accept(this));
        BasicBlock taken = new BasicBlock("if_taken", curFunc);
        BasicBlock notTaken = new BasicBlock("if_notTaken", curFunc);
        BasicBlock after = new BasicBlock("if_after", curFunc);
        new IRInst.BranchInst(cond, curBlock, taken, notTaken);

        curBlock = taken;
        node.if_statement.accept(this);
        new IRInst.BranchInst(curBlock, after);

        curBlock = notTaken;
        node.else_statement.accept(this);
        new IRInst.BranchInst(curBlock, after);

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        Value v = (Value) node.expr.accept(this);
        new IRInst.ReturnInst(v, curBlock);
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        new IRInst.BranchInst(curBlock, controlBlock.get(controlBlock.size() - 1).second);
        return null;
    }

    @Override
    public Object visit(ASTNode.ContinueStatement node) {
        new IRInst.BranchInst(curBlock, controlBlock.get(controlBlock.size() - 1).first);
        return null;
    }

    @Override
    public Object visit(ASTNode.ExprStatement node) {
        node.expr.accept(this);
        return null;
    }

    @Override
    public Object visit(ASTNode.Expression node) {
        return node.accept(this);
    }

    @Override
    public Object visit(ASTNode.ExpressionList node) {
        List<Value> ret = new ArrayList<>();
        node.exprList.forEach((expr) -> ret.add((Value) expr.accept(this)));
        return ret;
    }

    @Override
    public Object visit(ASTNode.LiteralExpression node) {
        if (node.strConstant != null) node.val = new IRConstant.ConstString(node.strConstant);
        else if (node.isBool) node.val = new IRConstant.ConstInteger(node.boolConstant ? 1 : 0, "bool");
        else if (node.isNull) node.val = new IRConstant.Null();
        else if (node.isThis) {
            node.val = curFunc.getParam(0);
        } else if (node.id != null) {
            if (node.type.isVariable()) {
                node.val = symbolTable.getFromOriginal(node.id, node.scope);
            } else if (node.type.isFunction()) {
                node.val = symbolTable.getFromOriginal(node.id, node.scope);
            }
        } else {
            node.val = new IRConstant.ConstInteger(node.numConstant);
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.SuffixExpression node) {
        node.val = (Value) node.expr.accept(this);
        Value v = new IRInst.BinaryOpInst(node.val, new IRConstant.ConstInteger(1),
                node.op.equals("++") ? "+" : "-", curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        Value t = (Value) node.expr.accept(this);
        switch (node.op) {
            case "++":
            case "--":
                node.val = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(1),
                        node.op.equals("++") ? "+" : "-", curBlock);
                break;
            case "+":
            case "-":
                node.val = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(0),
                        node.op, curBlock);
                break;
            case "!":
                node.val = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(1),
                        "xor", curBlock);
                break;
            case "~":
                node.val = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(-1),
                        "and", curBlock);
                break;
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        //TODO: STRING & INT, SCREW THE BUILTIN FUNCTIONS!!!
        Value lhs = (Value) node.expr1.accept(this);
        Value rhs = (Value) node.expr2.accept(this);
        node.val = new IRInst.BinaryOpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        Value lhs = (Value) node.expr1.accept(this);
        Value rhs = (Value) node.expr2.accept(this);
        node.val = new IRInst.CmpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.LogicExpression node) {
        Value lhs = (Value) node.expr1.accept(this);
        Value rhs = (Value) node.expr2.accept(this);
        node.val = new IRInst.BinaryOpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        Value dest = (Value) node.expr1.accept(this);
        Value from = (Value) node.expr2.accept(this);
        node.val = new IRInst.StoreInst(dest, from, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.MemberExpression node) {
        var t = node.scope.findClass(node.expr.type.toString());
        if (t != null && t.isClass()) {
            if (node.type.isLeftValue()) { //Member Variable
                
            } else { //Member Function
            }
        } else if (node.id.equals("size")) {
            //TODO: WTF IS THIS?
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.FuncExpression node) {
        var func = (IRConstant.Function) node.expr.accept(this);
        var args = (List<Value>) node.exprList.accept(this);
        if (node.expr.type.isClass()) { //class constructor
            //TODO: MALLOC
        } else {
            node.val = new IRInst.CallInst(func, args, curBlock);
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.ArrayExpression node) {
        Value base = (Value) node.expr.accept(this);
        node.val = new IRInst.GEPInst(IRType.convert(node.type), base, curBlock);
        node.index.forEach((index) -> {
            Value i = (Value) index.accept(this);
            ((IRInst.GEPInst) node.val).addOperand(i);
        });
        return node.val;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        return null;
    }
}

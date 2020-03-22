package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.ASTVisitor;
import com.gabriel.compiler.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class IRBuilder implements ASTVisitor {

    private Module module;
    private SymbolTable symbolTable;

    private ASTNode.Class curClass;
    private IRConstant.Function curFunc;
    private BasicBlock curBlock;

    private List<Pair<BasicBlock, BasicBlock>> controlBlock = new ArrayList<>();
    private Map<String, IRConstant.Function> builtin = new HashMap<>();

    public IRBuilder() {
        module = new Module("__program");
        symbolTable = new SymbolTable();
    }

    void init() {
        //TODO: String member functions
        //TODO: Main Block
        addBuiltinFunctions();
    }
    //TODO: ADD USER?
    //TODO: FIX FUNCTION PARAM 0, SHOULD BE THIS

    private void addBuiltinFunctions() {
        try {
            FileReader fr = new FileReader("./src/com/gabriel/compiler/IR/builtin");
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                var t = line.split(" ");
                var returnType = IRType.convert(t[0]);
                String funcName = t[1];
                var func = new IRConstant.Function(funcName, new IRType.FunctionType(new ArrayList<Value>(), returnType));
                line = reader.readLine();
                module.builtin.add(new Pair<>(func, line));
                builtin.put(funcName, func);
            }
        } catch (Exception e) {
            System.out.println("error reading builtin functions " + e.toString());
        }
    }

    private Value load(Value v) {
        //Could use isLeftValue to load automatically?
        if (v.type instanceof IRType.PointerType)
            return new IRInst.LoadInst(v, curBlock);
        else return v;
    }

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

        node.classes.forEach((c) -> {
            IRType.ClassType classType = (IRType.ClassType) c.accept(this);
            module.addClass(classType.getName(), classType);
        });

        node.functions.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            symbolTable.put(f, node.scope);
            module.addFunction(f);
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
        curBlock = new BasicBlock("func", curFunc);
        if (node.block != null) node.block.accept(this);
        return curFunc;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        var variable = new IRInst.AllocaInst(node.id, IRType.convert(node.type), curBlock);
        if (node.Initialization != null) {
            Value v = (Value) node.Initialization.accept(this);
            new IRInst.StoreInst(variable, v, curBlock);
        } else {
            //TODO: Default
        }
        symbolTable.put(variable, node.scope);
        return variable;
    }

    @Override
    public Object visit(ASTNode.VariableList node) {
        node.variables.forEach((var) -> var.accept(this));
        return null;
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

        controlBlock.add(new Pair<>(checkCond, after));
        curBlock = body;
        node.statement.accept(this);
        node.incr.accept(this);
        new IRInst.BranchInst(curBlock, checkCond);
        controlBlock.remove(controlBlock.size() - 1);

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        BasicBlock checkCond = new BasicBlock("while_cond", curFunc);
        BasicBlock body = new BasicBlock("while_body", curFunc);
        BasicBlock after = new BasicBlock("while_after", curFunc);
        new IRInst.BranchInst(curBlock, checkCond);

        curBlock = checkCond;
        Value cond = (Value) node.cond.accept(this);
        new IRInst.BranchInst(cond, curBlock, body, after);

        controlBlock.add(new Pair<>(checkCond, after));
        curBlock = body;
        node.statement.accept(this);
        new IRInst.BranchInst(curBlock, checkCond);
        controlBlock.remove(controlBlock.size() - 1);

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        Value cond = (Value) (node.cond.accept(this));
        BasicBlock taken = new BasicBlock("if_taken", curFunc);
        BasicBlock after = new BasicBlock("if_after", curFunc);
        BasicBlock notTaken = (node.else_statement == null) ? after : new BasicBlock("if_notTaken", curFunc);
        new IRInst.BranchInst(cond, curBlock, taken, notTaken);

        curBlock = taken;
        node.if_statement.accept(this);
        new IRInst.BranchInst(curBlock, after);

        if (notTaken != after) {
            curBlock = notTaken;
            node.else_statement.accept(this);
            new IRInst.BranchInst(curBlock, after);
        }

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        Value v = load((Value) node.expr.accept(this));
        new IRInst.ReturnInst(v, curBlock);
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        //TODO: POSSIBLE UNREACHABLE BRANCH
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
                //Load only when need to read, doesn't need to load on store
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
        Value lvalue = load(node.val);
        Value ret = new IRInst.AllocaInst("T", lvalue.type, curBlock);
        Value v = new IRInst.BinaryOpInst(lvalue, new IRConstant.ConstInteger(1),
                node.op.equals("++") ? "+" : "-", curBlock);
        new IRInst.StoreInst(node.val, v, curBlock);
        return ret;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        Value t = load((Value) node.expr.accept(this));
        Value v = null;
        switch (node.op) {
            case "++":
            case "--":
                v = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(1),
                        node.op.equals("++") ? "+" : "-", curBlock);
                break;
            case "+":
            case "-":
                v = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(0),
                        node.op, curBlock);
                break;
            case "!":
                v = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(1),
                        "xor", curBlock);
                break;
            case "~":
                v = new IRInst.BinaryOpInst(t, new IRConstant.ConstInteger(-1),
                        "and", curBlock);
                break;
        }
        new IRInst.StoreInst(node.val, v, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        //TODO: STRING & INT, SCREW THE BUILTIN FUNCTIONS!!!
        Value lhs = load((Value) node.expr1.accept(this));
        Value rhs = load((Value) node.expr2.accept(this));
        node.val = new IRInst.BinaryOpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        Value lhs = load((Value) node.expr1.accept(this));
        Value rhs = load((Value) node.expr2.accept(this));
        node.val = new IRInst.CmpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.LogicExpression node) {
        Value lhs = load((Value) node.expr1.accept(this));
        Value rhs = load((Value) node.expr2.accept(this));
        node.val = new IRInst.BinaryOpInst(lhs, rhs, node.op, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        Value dest = (Value) node.expr1.accept(this);
        Value from = load((Value) node.expr2.accept(this));
        node.val = new IRInst.StoreInst(dest, from, curBlock);
        return node.val;
    }

    @Override
    public Object visit(ASTNode.MemberExpression node) {
        Value base = (Value) node.expr.accept(this);
        var t = node.scope.findClass(node.expr.type.toString());
        if (t != null && t.isClass()) {
            var c = module.getClass(((ASTNode.Class) t.node).className);

            if (node.type.isLeftValue()) { //Member Variable
                for (int i = 0; i < c.members.size(); i++) {
                    if (c.member_name.get(i).equals(node.id)) {
                        node.val = new IRInst.GEPInst(c.members.get(i), base, curBlock);
                        ((IRInst.GEPInst) node.val).addOperand(new IRConstant.ConstInteger(i));
                        return node.val;
                    }
                }
            } else { //Member Function
                var func = (IRConstant.Function) symbolTable.getFromOriginal(node.id, ((ASTNode.Class) t.node).scope);
                node.val = func;
                return node.val;
            }
        } else if (node.id.equals("size")) {
            //TODO: WTF IS THIS?
        }
        return null;
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
        Type t = IRType.convert(node.id);
        for (int i = 0; i < node.dimension_left; i++) {
            t = new IRType.PointerType(t);
        }
        Value size = null;
        for (ASTNode.Expression expr : node.expressions) {
            Value cur = (Value) expr.accept(this);
            if (size != null) {
                size = new IRInst.BinaryOpInst(size, cur, "*", curBlock);
            } else {
                size = cur;
            }
        }
        Value malloc = new IRInst.CallInst(builtin.get("malloc"), Collections.singletonList(size), curBlock);
        node.val = new IRInst.CastInst(malloc.type, t, curBlock);
        return node.val;
    }
}

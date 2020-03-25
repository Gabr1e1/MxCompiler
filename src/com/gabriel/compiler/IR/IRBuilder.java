package com.gabriel.compiler.IR;

import com.gabriel.compiler.frontend.ASTNode;
import com.gabriel.compiler.frontend.ASTVisitor;
import com.gabriel.compiler.frontend.Scope;
import com.gabriel.compiler.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class IRBuilder implements ASTVisitor {

    private Module module;
    private SymbolTable symbolTable;

    private IRType.ClassType curClass;
    private IRConstant.Function curFunc;
    private BasicBlock curBlock, retBlock;
    private Value curBlockRet;

    private List<Pair<BasicBlock, BasicBlock>> controlBlock = new ArrayList<>();
    private Map<String, IRConstant.Function> builtin = new HashMap<>();
    private Scope globalScope;

    public IRBuilder() {
        module = new Module("__program");
        symbolTable = new SymbolTable();
    }

    void init() {
        //TODO: String member functions
        addBuiltinFunctions();
        addStringMethods();
    }
    //TODO: ADD USER?

    private void addBuiltinFunctions() {
        try {
            FileReader fr = new FileReader("./src/com/gabriel/compiler/builtin/builtin.info");
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                var t = line.split(" ");
                var returnType = IRType.convert(t[0]);
                String funcName = t[1];
                var args = new ArrayList<Value>();
                for (int i = 3; i < t.length - 1; i++) {
                    args.add(new Value("_", IRType.convert(t[i])));
                }
                var func = new IRConstant.Function(funcName, new IRType.FunctionType(args, returnType, funcName));
                line = reader.readLine();
                module.builtin.add(new Pair<>(func, line));
                builtin.put(funcName, func);
            }
        } catch (Exception e) {
            System.out.println("error reading builtin functions " + e.toString());
        }
    }

    private void addStringMethods() {
        module.classes.put("struct.string", new IRType.ClassType("string", null, null,
                (new IRType.PointerType(new IRType.IntegerType("char"))).bitLen));
        try {
            FileReader fr = new FileReader("./src/com/gabriel/compiler/builtin/string_methods.info");
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                var t = line.split(" ");
                var returnType = IRType.convert(t[0]);
                String funcName = t[1];
                var args = new ArrayList<Value>();
                args.add(new Value("_", new IRType.PointerType(new IRType.IntegerType("char"))));
                for (int i = 3; i < t.length - 1; i++) {
                    args.add(new Value("_", IRType.convert(t[i])));
                }
                var func = new IRConstant.Function("string_" + funcName, new IRType.FunctionType(args, returnType, funcName));
                line = reader.readLine();
                module.builtin.add(new Pair<>(func, line));
                symbolTable.put(func, globalScope);
            }
        } catch (Exception e) {
            System.out.println("error reading string methods " + e.toString());
        }
    }

    private Value load(Value v) {
        while (v.type instanceof IRType.PointerType) {
            v = new IRInst.LoadInst(v, curBlock);
        }
        return v;
    }

    private int getPtrNum(Type t) {
        if (t instanceof IRType.PointerType)
            return getPtrNum(((IRType.PointerType) t).pointer) + 1;
        else return 0;
    }

    private Value loadUntilType(Value v, Type t) {
        int n = getPtrNum(v.type), m = getPtrNum(t);
        while (n > m) {
            v = new IRInst.LoadInst(v, curBlock);
            n -= 1;
        }
        return v;
    }

    private Value loadUntilType(Value v, int m) {
        int n = getPtrNum(v.type);
        while (n > m) {
            v = new IRInst.LoadInst(v, curBlock);
            n -= 1;
        }
        return v;
    }

    private List<Value> convertToCorres(List<Value> from, List<Value> to) {
        List<Value> ret = new ArrayList<>();
        for (int i = 0; i < from.size(); i++) {
            if (from.get(i).type instanceof IRType.IntegerType && to.get(i).type instanceof IRType.IntegerType) {
                var l = (IRType.IntegerType) from.get(i).type;
                var r = (IRType.IntegerType) to.get(i).type;
                if (l.bitLen < r.bitLen) {
                    ret.add(new IRInst.SextInst(from.get(i), to.get(i).type, curBlock));
                    continue;
                } else if (l.bitLen > r.bitLen) {
                    ret.add(new IRInst.TruncInst(from.get(i), to.get(i).type, curBlock));
                    continue;
                }
            }
            ret.add(from.get(i));
        }
        return ret;
    }

    private Value malloc(Value size, Type baseType) {
        var args = convertToCorres(Collections.singletonList(size), ((IRType.FunctionType) builtin.get("malloc").type).params);
        Value malloc = new IRInst.CallInst(builtin.get("malloc"), args, curBlock);
        return new IRInst.CastInst(malloc, baseType, curBlock);
    }

    @Override
    public Object visit(ASTNode.Program node) {
        globalScope = node.scope;
        init();
        node.variables.forEach((var) -> {
            if (var.Initialization != null) {
                Value v = (Value) var.Initialization.accept(this);
                IRConstant.GlobalVariable g = new IRConstant.GlobalVariable(var.id,
                        IRType.convert(var.type, module), v);
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
            module.addFunction(f);
        });
        return module;
    }

    @Override
    public Object visit(ASTNode.Class node) {
        List<Type> members = new ArrayList<>();
        List<String> member_name = new ArrayList<>();
        int size = 0;
        for (ASTNode.Variable var : node.variables) {
            var type = IRType.convert(var.type, module);
            members.add(type);
            member_name.add(var.id);
            size += (type.bitLen - (size % type.bitLen)) % type.bitLen;
            size += type.bitLen;
        }
        var ret = new IRType.ClassType("struct." + node.className, members, member_name, size);
        curClass = ret;
        node.functions.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            module.addFunction(f);
        });
        node.constructors.forEach((func) -> {
            IRConstant.Function f = (IRConstant.Function) func.accept(this);
            module.addFunction(f);
            ret.addConstructor(f);
        });

        curClass = null;
        return ret;
    }

    private Value This;

    @Override
    public Object visit(ASTNode.Function node) {
        var funcName = (curClass != null ? curClass.className + "_" : "") + node.funcName;
        Type returnType = IRType.convert(node.returnType, module);
        List<Value> params = (List<Value>) (node.paramList.accept(this));
        if (curClass != null) {
            This = new Value("this", new IRType.PointerType(curClass));
            params.add(0, This);
        }
        var type = new IRType.FunctionType(params, returnType, funcName);

        curFunc = new IRConstant.Function(funcName, type);
        symbolTable.put(curFunc, globalScope);

        curBlock = new BasicBlock("func", curFunc);

        //Create Return Block
        if (!(returnType instanceof IRType.VoidType)) {
            curBlockRet = new IRInst.AllocaInst("ret", returnType, curBlock);
            retBlock = new BasicBlock("retBlock", curFunc);
            var tmp = curBlock;
            curBlock = retBlock;
            new IRInst.ReturnInst(loadUntilType(curBlockRet, returnType), retBlock);
            curBlock = tmp;
        }
        if (node.block != null) node.block.accept(this);
        curFunc.blocks.forEach((block) -> {
            if (block.instructions.size() == 0) block.replaceAllUsesWith(retBlock);
        });

        if (!(returnType instanceof IRType.VoidType)) {
            for (int i = 0; i < curFunc.blocks.size(); i++) {
                if (curFunc.blocks.get(i) == retBlock) {
                    curFunc.blocks.remove(i);
                    curFunc.blocks.add(retBlock);
                    break;
                }
            }
        } else {
            new IRInst.ReturnInst(new IRConstant.Void(), curBlock);
        }
        return curFunc;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        var variable = new IRInst.AllocaInst(node.id, IRType.convert(node.type, module), curBlock);
        if (node.Initialization != null) {
            Value v = loadUntilType((Value) node.Initialization.accept(this), IRType.convert(node.type, module));
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
        return IRType.convert(node.type, module);
    }

    @Override
    public Object visit(ASTNode.Param node) {
        Value v = new Value(node.id, IRType.convert(node.type, module));
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

    @Override
    public Object visit(ASTNode.ForStatement node) {
        Value init = (Value) node.init.accept(this);
        BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
        BasicBlock body = new BasicBlock("for_body", curFunc);
        BasicBlock after = new BasicBlock("for_after", curFunc);

        new IRInst.BranchInst(checkCond, curBlock);

        curBlock = checkCond;
        Value cond = load((Value) node.cond.accept(this));
        cond = convertToCorres(Collections.singletonList(cond),
                Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
        new IRInst.BranchInst(cond, curBlock, body, after);

        controlBlock.add(new Pair<>(checkCond, after));
        curBlock = body;
        node.statement.accept(this);
        node.incr.accept(this);
        new IRInst.BranchInst(checkCond, curBlock);
        controlBlock.remove(controlBlock.size() - 1);

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.WhileStatement node) {
        BasicBlock checkCond = new BasicBlock("while_cond", curFunc);
        BasicBlock body = new BasicBlock("while_body", curFunc);
        BasicBlock after = new BasicBlock("while_after", curFunc);
        new IRInst.BranchInst(checkCond, curBlock);

        curBlock = checkCond;
        Value cond = load((Value) node.cond.accept(this));
        cond = convertToCorres(Collections.singletonList(cond),
                Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
        new IRInst.BranchInst(cond, curBlock, body, after);

        controlBlock.add(new Pair<>(checkCond, after));
        curBlock = body;
        node.statement.accept(this);
        new IRInst.BranchInst(checkCond, curBlock);
        controlBlock.remove(controlBlock.size() - 1);

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.ConditionalStatement node) {
        Value cond = load((Value) node.cond.accept(this));
        cond = convertToCorres(Collections.singletonList(cond),
                Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);

        BasicBlock taken = new BasicBlock("if_taken", curFunc);
        BasicBlock notTaken = (node.else_statement == null) ? new BasicBlock("if_after", curFunc) : new BasicBlock("if_notTaken", curFunc);
        BasicBlock after = (node.else_statement == null) ? notTaken : new BasicBlock("if_after", curFunc);
        new IRInst.BranchInst(cond, curBlock, taken, notTaken);

        curBlock = taken;
        node.if_statement.accept(this);
        new IRInst.BranchInst(after, curBlock);

        if (notTaken != after) {
            curBlock = notTaken;
            node.else_statement.accept(this);
            new IRInst.BranchInst(after, curBlock);
        }

        curBlock = after;
        return null;
    }

    @Override
    public Object visit(ASTNode.ReturnStatement node) {
        Value v = loadUntilType((Value) node.expr.accept(this),
                ((IRType.FunctionType) curFunc.type).returnType);
        new IRInst.StoreInst(curBlockRet, v, curBlock);
        new IRInst.BranchInst(retBlock, curBlock);
        return null;
    }

    @Override
    public Object visit(ASTNode.BreakStatement node) {
        new IRInst.BranchInst(controlBlock.get(controlBlock.size() - 1).second, curBlock);
        return null;
    }

    @Override
    public Object visit(ASTNode.ContinueStatement node) {
        new IRInst.BranchInst(controlBlock.get(controlBlock.size() - 1).first, curBlock);
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

    private Value newString(String str) {
        Value ret = new IRInst.AllocaInst("str", new IRType.PointerType(new IRType.IntegerType("char")), curBlock);
        Value m = malloc(new IRConstant.ConstInteger(1 + str.length() * IRType.IntegerType.BitLen.get("char"), "long"),
                new IRType.PointerType(new IRType.IntegerType("char")));
        for (int i = 0; i <= str.length(); i++) {
            var t = new IRInst.GEPInst(new IRType.IntegerType("char"), m, curBlock, false);
            t.addOperand(new IRConstant.ConstInteger(i));
            new IRInst.StoreInst(t, new IRConstant.ConstInteger(i < str.length() ? str.charAt(i) : 0, "char"), curBlock);
        }
        new IRInst.StoreInst(ret, m, curBlock);
        return ret;
    }

    @Override
    public Object visit(ASTNode.LiteralExpression node) {
        if (node.strConstant != null) {
            node.val = newString(node.strConstant);
        } else if (node.isBool) node.val = new IRConstant.ConstInteger(node.boolConstant ? 1 : 0, "bool");
        else if (node.isNull) node.val = new IRConstant.Null();
        else if (node.isThis) {
            node.val = curFunc.getParam(0);
        } else if (node.id != null) {
            if (node.type.isVariable()) {
                node.val = symbolTable.getFromOriginal(node.id, node.scope);
                if (node.val == null) { //class member
                    node.val = new IRInst.GEPInst(curClass.getType(node.id).first, This, curBlock, true);
                    ((IRInst.GEPInst) node.val).addOperand(new IRConstant.ConstInteger(curClass.getType(node.id).second));
                }
            } else if (node.type.isFunction()) {
                node.val = symbolTable.getFromOriginal(curClass != null ? curClass.className + "_" + node.id : node.id, globalScope);
                if (node.val == null) node.val = builtin.get(node.id);
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
        new IRInst.StoreInst(ret, lvalue, curBlock);
        Value v = new IRInst.BinaryOpInst(lvalue, new IRConstant.ConstInteger(1),
                node.op.equals("++") ? "+" : "-", curBlock);
        new IRInst.StoreInst(node.val, v, curBlock);
        return ret;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        Value t = (Value) node.expr.accept(this);
        Value v = null;
        switch (node.op) {
            case "++":
            case "--":
                v = new IRInst.BinaryOpInst(load(t), new IRConstant.ConstInteger(1),
                        node.op.equals("++") ? "+" : "-", curBlock);
                break;
            case "+":
            case "-":
                v = new IRInst.BinaryOpInst(load(t), new IRConstant.ConstInteger(0),
                        node.op, curBlock);
                break;
            case "!":
                v = new IRInst.BinaryOpInst(load(t), new IRConstant.ConstInteger(1),
                        "^", curBlock);
                break;
            case "~":
                v = new IRInst.BinaryOpInst(load(t), new IRConstant.ConstInteger(-1),
                        "&", curBlock);
                break;
        }
        new IRInst.StoreInst(t, v, curBlock);
        return t;
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        //TODO: STRING CONCATENATE
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
        Value from = loadUntilType((Value) node.expr2.accept(this), ((IRType.PointerType) dest.type).pointer);
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
                base = loadUntilType(base, 1);
                node.val = new IRInst.GEPInst(c.getType(node.id).first, base, curBlock, true);
                ((IRInst.GEPInst) node.val).addOperand(new IRConstant.ConstInteger(c.getType(node.id).second));
                return node.val;
            } else { //Member Function
                var func = (IRConstant.Function) symbolTable.getFromOriginal(c.className + "_" + node.id, globalScope);
                node.val = func;
                This = base;
                return node.val;
            }
        } else if (node.id.equals("size")) {
            //TODO: WTF IS THIS?
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.FuncExpression node) {
        var f = node.expr.accept(this);
        var args = new ArrayList<Value>();

        if (f instanceof IRConstant.Function) {
            var func = (IRConstant.Function) f;
            var tmp = (List<Value>) node.exprList.accept(this);
            var argsNeed = ((IRType.FunctionType) func.type).params;
            boolean flg = false;
            if (tmp.size() == argsNeed.size() - 1) { //lacking "this"
                args.add(loadUntilType(This, 1));
                flg = true;
            }
            for (int i = 0; i < tmp.size(); i++) {
                args.add(loadUntilType(tmp.get(i), argsNeed.get(flg ? (i + 1) : i).type));
            }
            node.val = new IRInst.CallInst(func, args, curBlock);
        } else if (IRType.isClassPointer((Value) f)) { //class constructor
            var c = (IRType.ClassType) ((IRType.PointerType) (((Value) f).type)).pointer;
            args.add(0, (Value) f);
            new IRInst.CallInst(c.constructor, args, curBlock);
            node.val = (Value) f;
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.ArrayExpression node) {
        Value base = loadUntilType((Value) node.expr.accept(this), getPtrNum(IRType.convert(node.type, module)) + node.index.size());
        node.val = base;

        for (int i = 0; i < node.index.size(); i++) {
            var index = node.index.get(i);
            Value v = load((Value) index.accept(this));
            var t = ((IRType.PointerType) node.val.type).pointer;
            node.val = new IRInst.GEPInst(t, node.val, curBlock, false);
            ((IRInst.GEPInst) node.val).addOperand(v);
            if (i != node.index.size() - 1) node.val = loadUntilType(node.val, t);
        }
        return node.val;
    }

    private Value allocateArray(Type baseType, Value dimension) {
        var size = new IRInst.BinaryOpInst(new IRConstant.ConstInteger(((IRType.PointerType) baseType).pointer.getByteNum()), dimension, "*", curBlock);
        return malloc(size, baseType);
    }

    private Value allocate(Type t, List<Value> d) {
        Value dimension = d.get(0);
        Value ret = allocateArray(t, dimension);
        ((IRType.PointerType) ret.type).setDimension(dimension);
        if (d.size() > 1) {
            t = ((IRType.PointerType) t).pointer;
            d = d.subList(1, d.size());

            var i = new IRInst.AllocaInst("i", new IRType.IntegerType("int"), curBlock);
            new IRInst.StoreInst(i, new IRConstant.ConstInteger(0), curBlock);

            BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
            BasicBlock body = new BasicBlock("for_body", curFunc);
            BasicBlock after = new BasicBlock("for_after", curFunc);
            new IRInst.BranchInst(checkCond, curBlock);

            curBlock = checkCond;
            var tmp = load(i);
            Value cond = new IRInst.CmpInst(tmp, dimension, "<", curBlock);
            cond = convertToCorres(Collections.singletonList(cond),
                    Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
            new IRInst.BranchInst(cond, curBlock, body, after);

            curBlock = body;
            var curStore = new IRInst.GEPInst(t, ret, curBlock, false);
            curStore.addOperand(tmp);

            new IRInst.StoreInst(curStore, allocate(t, d), curBlock);
            var newi = new IRInst.BinaryOpInst(tmp, new IRConstant.ConstInteger(1), "+", curBlock);
            new IRInst.StoreInst(i, newi, curBlock);
            new IRInst.BranchInst(checkCond, curBlock);
            curBlock = after;
        }
        return ret;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        Type t = IRType.convert(node.type, module);
        List<Value> d = new ArrayList<>();
        for (ASTNode.Expression expr : node.expressions) {
            d.add((Value) expr.accept(this));
        }
        if (d.size() != 0) node.val = allocate(t, d);
        else {
            var size = new IRConstant.ConstInteger(((IRType.PointerType) t).pointer.getByteNum(), "long");
            node.val = malloc(size, t);
        }
        return node.val;
    }
}
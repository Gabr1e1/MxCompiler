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
    private Map<String, IRConstant.ConstString> constStrings = new HashMap<>();

    public IRBuilder() {
        module = new Module("__program");
        symbolTable = new SymbolTable();
    }

    void init() {
        addBuiltinFunctions();
        addStringMethods();
    }

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

    private void _addStringMethods(String filename, boolean member) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                var t = line.split(" ");
                var returnType = t[0].equals("char") ? IRType.convert("bool") : IRType.convert(t[0]);
                String funcName = t[1];
                var args = new ArrayList<Value>();
                if (member) args.add(new Value("_", new IRType.PointerType(new IRType.IntegerType("char"))));
                for (int i = 3; i < t.length - 1; i++) {
                    args.add(new Value("_", IRType.convert(t[i])));
                }
                var func = new IRConstant.Function("_string_" + funcName, new IRType.FunctionType(args, returnType, funcName));
                line = reader.readLine();
                module.builtin.add(new Pair<>(func, line));
                symbolTable.put(func, globalScope);
            }
        } catch (Exception e) {
            System.out.println("error reading string methods " + e.toString());
        }
    }

    private void addStringMethods() {
        module.classes.put("string", new IRType.ClassType("_string", null, null,
                (new IRType.PointerType(new IRType.IntegerType("char"))).bitLen));
        _addStringMethods("./src/com/gabriel/compiler/builtin/string_methods.info", true);
        _addStringMethods("./src/com/gabriel/compiler/builtin/string_utility.info", false);
    }

    private int getPtrNum(IRType.Type t) {
        if (t instanceof IRType.PointerType)
            return getPtrNum(((IRType.PointerType) t).pointer) + 1;
        else return 0;
    }

    private Value loadUntilType(Value v, IRType.Type t) {
        if (v instanceof IRConstant.Null) {
            v = new IRConstant.Null(t);
            return v;
        }
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

    private Value loadTimes(Value v, int m) {
        for (int i = 0; i < m; i++) {
            if (v.type instanceof IRType.PointerType)
                v = new IRInst.LoadInst(v, curBlock);
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

    private Value store(Value dest, Value to, BasicBlock curBlock) {
        var tmp = loadTimes(dest, 1);
        var newTo = convertToCorres(Collections.singletonList(to), Collections.singletonList(tmp));
        return new IRInst.StoreInst(dest, newTo.get(0), curBlock);
    }

    private Value malloc(Value size, IRType.Type baseType) {
        var args = convertToCorres(Collections.singletonList(size), ((IRType.FunctionType) builtin.get("malloc").type).params);
        Value malloc = new IRInst.CallInst(builtin.get("malloc"), args, curBlock);
        return new IRInst.CastInst(malloc, baseType, curBlock);
    }

    private int classCollecting, funcCollecting;

    private IRConstant.Function work(List<ASTNode.Function> functions, boolean collecting) {
        IRConstant.Function ret = null;
        if (collecting) {
            for (ASTNode.Function func : functions) {
                funcCollecting = 1;
                IRConstant.Function f = (IRConstant.Function) func.accept(this);
                module.addFunction(f);
                ret = f;
            }
        } else {
            for (ASTNode.Function func : functions) {
                funcCollecting = 2;
                var funcName = (curClass != null ? curClass.className + "_" : "") + func.funcName;
                curBlock = ((IRConstant.Function) symbolTable.getFromOriginal(funcName, globalScope)).blocks.get(0);
                func.accept(this);
            }
        }
        return ret;
    }

    @Override
    public Object visit(ASTNode.Program node) {
        globalScope = node.scope;
        init();
        //Collect class info: className
        classCollecting = 1;
        node.classes.forEach((c) -> c.accept(this));

        //Collect function info: name, args
        work(node.functions, true);

        //Collect class info: member, method def
        classCollecting = 2;
        node.classes.forEach((c) -> c.accept(this));

        //Global Variable
        var type = new IRType.FunctionType(new ArrayList<>(), IRType.convert("void"), "__global_init");
        curFunc = new IRConstant.Function("__global_init", type);
        curBlock = new BasicBlock("init", curFunc);
        symbolTable.put(curFunc, globalScope);
        module.addFunction(curFunc);
        node.variables.forEach((var) -> {
            IRConstant.GlobalVariable g = new IRConstant.GlobalVariable(var.id,
                    IRType.convert(var.type, module), IRType.getDefaultValue(IRType.convert(var.type, module)));

            if (var.Initialization != null) {
                Value v = loadUntilType((Value) var.Initialization.accept(this), IRType.convert(var.type, module));
                store(g, v, curBlock);
            }
            symbolTable.put(g, globalScope);
            module.addGlobalVariable(g);
        });
        new IRInst.ReturnInst(new Value("_", new IRType.VoidType()), curBlock);

        //finalize class collecting: methods impl
        classCollecting = 3;
        node.classes.forEach((c) -> c.accept(this));

        //finalize function collecting: function impl
        work(node.functions, false);

        //put alloca forward
        for (var func : module.functions) {
            var first = func.blocks.get(0);
            for (int i = 1; i < func.blocks.size(); i++) {
                for (var inst : func.blocks.get(i).instructions) {
                    if (inst instanceof IRInst.AllocaInst) {
                        first.instructions.add(0, inst);
                        inst.belong = first;
                    }
                }
                func.blocks.get(i).instructions.removeIf(inst -> inst instanceof IRInst.AllocaInst);
            }
        }

        return module;
    }

    @Override
    public Object visit(ASTNode.Class node) {
        List<IRType.Type> members;
        List<String> member_name;

        if (classCollecting == 1) {
            members = new ArrayList<>();
            member_name = new ArrayList<>();
            var ret = new IRType.ClassType("struct." + node.className, members, member_name, 0);
            module.addClass(ret.getName(), ret);
        } else if (classCollecting == 2) {
            var c = module.getClass("struct." + node.className);
            members = c.members;
            member_name = c.member_name;
            int size = 0;
            for (ASTNode.Variable var : node.variables) {
                var type = IRType.convert(var.type, module);
                members.add(type);
                member_name.add(var.id);
                size += (type.bitLen - (size % type.bitLen)) % type.bitLen;
                size += type.bitLen;
            }
            c.bitLen = size;
            curClass = c;
            work(node.functions, true);
            c.addConstructor(work(node.constructors, true));
            curClass = null;
        } else if (classCollecting == 3) {
            var c = module.getClass("struct." + node.className);
            curClass = c;
            work(node.functions, false);
            work(node.constructors, false);
            curClass = null;
            return c;
        }
        return null;
    }

    private Value This;
    private boolean currentThis;

    @Override
    public Object visit(ASTNode.Function node) {
        var funcName = (curClass != null ? curClass.className + "_" : "") + node.funcName;
        if (funcCollecting == 1) {
            IRType.Type returnType = IRType.convert(node.returnType, module);
            curFunc = new IRConstant.Function(funcName, null);
            funcName = curFunc.name;
            curBlock = new BasicBlock("func_init", curFunc);
            symbolTable.put(curFunc, globalScope);

            @SuppressWarnings("unchecked") List<Value> params = (List<Value>) (node.paramList.accept(this));
            if (curClass != null) {
                This = new Value("this", new IRType.PointerType(curClass));
                params.add(0, This);
            }
            curFunc.type = new IRType.FunctionType(params, returnType, funcName);
            return curFunc;
        } else {
            //Create Return Block
            curFunc = (IRConstant.Function) symbolTable.getFromOriginal(funcName, globalScope);
            var returnType = curFunc == null ? new IRType.VoidType() : ((IRType.FunctionType) curFunc.type).returnType;
            retBlock = new BasicBlock("retBlock", curFunc);
            if (!(returnType instanceof IRType.VoidType)) {
                curBlockRet = new IRInst.AllocaInst("ret", returnType, curBlock);
                if (node.funcName.equals("main"))
                    new IRInst.StoreInst(curBlockRet, new IRConstant.ConstInteger(0), curBlock);
                var tmp = curBlock;
                curBlock = retBlock;
                new IRInst.ReturnInst(loadUntilType(curBlockRet, returnType), retBlock);
                curBlock = tmp;
            } else {
                new IRInst.ReturnInst(new IRConstant.Void(), retBlock);
            }

            //initialize global variable
            if (node.funcName.equals("main")) {
                new IRInst.CallInst((IRConstant.Function) symbolTable.get("__global_init"), new ArrayList<>(), curBlock);
            }

            if (node.block != null) node.block.accept(this);
            curFunc.blocks.forEach((block) -> {
                if (block.instructions.size() == 0) block.replaceAllUsesWith(retBlock);
                else if (!block.hasTerminator()) {
                    new IRInst.BranchInst(retBlock, block);
                }
            });


            //Put return block to the end
            for (var block : curFunc.blocks) {
                if (block.getOriginalName().equals("retBlock")) {
                    curFunc.blocks.remove(block);
                    curFunc.blocks.add(block);
                    break;
                }
            }

            //Clean up
            curFunc.blocks.removeIf((b) -> b.instructions.size() == 0);
            for (var block : curFunc.blocks) {
                for (int i = 0; i < block.instructions.size(); i++) {
                    var inst = block.instructions.get(i);
                    if (IRInst.isTerminator(inst)) {
                        block.instructions = block.instructions.subList(0, i + 1);
                        break;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.Variable node) {
        var variable = new IRInst.AllocaInst(node.id, IRType.convert(node.type, module), curBlock);
        Value init;
        if (node.Initialization != null) {
            init = loadUntilType((Value) node.Initialization.accept(this), IRType.convert(node.type, module));
        } else {
            init = IRType.getDefaultValue(IRType.convert(node.type, module));
        }
        store(variable, init, curBlock);
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
        Value p = new Value(node.id, IRType.convert(node.type, module));
        Value np = new IRInst.AllocaInst(node.id, p.type, curBlock);
        new IRInst.StoreInst(np, p, curBlock);
        symbolTable.put(np, node.scope);
        return p;
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
        for (var stmt : node.statements) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(ASTNode.ForStatement node) {
        if (node.init != null) node.init.accept(this);
        BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
        BasicBlock body = new BasicBlock("for_body", curFunc);
        BasicBlock incr = new BasicBlock("for_incr", curFunc);
        BasicBlock after = new BasicBlock("for_after", curFunc);

        new IRInst.BranchInst(checkCond, curBlock);

        curBlock = checkCond;
        if (node.cond != null) {
            Value cond = loadTimes((Value) node.cond.accept(this), 1);
            cond = convertToCorres(Collections.singletonList(cond),
                    Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
            new IRInst.BranchInst(cond, body, after, curBlock);
        } else {
            new IRInst.BranchInst(body, curBlock);
        }

        controlBlock.add(new Pair<>(incr, after));
        curBlock = body;
        if (node.statement != null) node.statement.accept(this);
        new IRInst.BranchInst(incr, curBlock);

        curBlock = incr;
        if (node.incr != null) node.incr.accept(this);
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
        Value cond = loadTimes((Value) node.cond.accept(this), 1);
        cond = convertToCorres(Collections.singletonList(cond),
                Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
        new IRInst.BranchInst(cond, body, after, curBlock);

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
        Value cond = loadTimes((Value) node.cond.accept(this), 1);
        cond = convertToCorres(Collections.singletonList(cond),
                Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);

        BasicBlock taken = new BasicBlock("if_taken", curFunc);
        BasicBlock notTaken = (node.else_statement == null) ? new BasicBlock("if_after", curFunc) : new BasicBlock("if_notTaken", curFunc);
        BasicBlock after = (node.else_statement == null) ? notTaken : new BasicBlock("if_after", curFunc);
        new IRInst.BranchInst(cond, taken, notTaken, curBlock);

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
        if (node.expr != null) {
            Value v = loadUntilType((Value) node.expr.accept(this),
                    ((IRType.FunctionType) curFunc.type).returnType);
            store(curBlockRet, v, curBlock);
        }
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
        IRConstant.ConstString s = constStrings.get(str);
        if (s == null) {
            s = new IRConstant.ConstString(replace2(str), new IRType.ArrayType(IRType.convert("char"), str.length() + 1));
            module.globalVariables.add(s);
        }
        var ret = new IRInst.GEPInst(s.type, s, curBlock, true);
        ret.type = IRType.convert("char*");
        ret.addOperand(new IRConstant.ConstInteger(0));
        return ret;
    }

    private String replace1(String str) {
        var ret = str.replace("\\\\", "\\");
        ret = ret.replace("\\n", "\n");
        ret = ret.replace("\\\"", "\"");
        return ret;
    }

    private String replace2(String str) {
        var ret = str.replace("\\", "\\5C");
        ret = ret.replace("\n", "\\0A");
        ret = ret.replace("\"", "\\22");
        return ret;
    }

    @Override
    public Object visit(ASTNode.LiteralExpression node) {
        if (node.strConstant != null) {
            node.strConstant = replace1(node.strConstant);
            node.val = newString(node.strConstant);
        } else if (node.isBool) node.val = new IRConstant.ConstInteger(node.boolConstant ? 1 : 0, "bool");
        else if (node.isNull)
            node.val = new IRConstant.Null(null);
        else if (node.isThis) {
            node.val = curFunc.getParam(0);
        } else if (node.id != null) {
            if (node.type.isVariable()) {
                node.val = symbolTable.getFromOriginal(node.id, node.scope);
                if (node.val == null) { //class member
                    node.val = new IRInst.GEPInst(curClass.getMember(node.id).first, ((IRType.FunctionType) curFunc.type).params.get(0), curBlock, true);
                    ((IRInst.GEPInst) node.val).addOperand(new IRConstant.ConstInteger(curClass.getMember(node.id).second));
                }
            } else if (node.type.isFunction()) {
                node.val = symbolTable.getFromOriginal(curClass != null ? curClass.className + "_" + node.id : node.id, globalScope);
                if (node.val == null) node.val = symbolTable.getFromOriginal(node.id, globalScope);
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
        Value lvalue = loadTimes(node.val, 1);
        Value v = new IRInst.BinaryOpInst(lvalue, new IRConstant.ConstInteger(1),
                node.op.equals("++") ? "+" : "-", curBlock);
        store(node.val, v, curBlock);
        return lvalue;
    }

    @Override
    public Object visit(ASTNode.UnaryExpression node) {
        Value t = (Value) node.expr.accept(this);
        Value v = null;
        switch (node.op) {
            case "++":
            case "--":
                v = new IRInst.BinaryOpInst(loadTimes(t, 1), new IRConstant.ConstInteger(1),
                        node.op.equals("++") ? "+" : "-", curBlock);
                break;
            case "+":
            case "-":
                v = new IRInst.BinaryOpInst(new IRConstant.ConstInteger(0), loadTimes(t, 1),
                        node.op, curBlock);
                break;
            case "!":
                v = new IRInst.BinaryOpInst(loadTimes(t, 1), new IRConstant.ConstInteger(1),
                        "^", curBlock);
                break;
            case "~":
                v = new IRInst.BinaryOpInst(loadTimes(t, 1), new IRConstant.ConstInteger(-1),
                        "^", curBlock);
                break;
        }
        if (node.op.equals("++") || node.op.equals("--")) {
            store(t, v, curBlock);
            return t;
        } else {
            return v;
        }
    }

    @Override
    public Object visit(ASTNode.BinaryExpression node) {
        if (node.type.baseType.equals("string")) {
            Value lhs = loadUntilType((Value) node.expr1.accept(this), 1);
            Value rhs = loadUntilType((Value) node.expr2.accept(this), 1);
            node.val = new IRInst.CallInst((IRConstant.Function) symbolTable.get("_string_concatenate"),
                    Arrays.asList(lhs, rhs), curBlock);
        } else {
            Value lhs = loadTimes((Value) node.expr1.accept(this), 1);
            Value rhs = loadTimes((Value) node.expr2.accept(this), 1);
            node.val = new IRInst.BinaryOpInst(lhs, rhs, node.op, curBlock);
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.CmpExpression node) {
        Value lhs = (Value) node.expr1.accept(this);
        Value rhs = (Value) node.expr2.accept(this);
        if (node.expr1.type.toString().equals("string")) {
            var func = (IRConstant.Function) symbolTable.getFromOriginal("_string_" + IRInst.CmpInst.OpMap.get(node.op), globalScope);
            lhs = loadUntilType(lhs, 1);
            rhs = loadUntilType(rhs, 1);
            node.val = new IRInst.CallInst(func, Arrays.asList(lhs, rhs), curBlock);
        } else {
            //TODO: SHITTY CODE
            if (rhs instanceof IRConstant.Null) {
                lhs = loadTimes(lhs, 1);
                rhs = new IRConstant.Null(lhs.type);
            } else if (lhs instanceof IRConstant.Null) {
                rhs = loadTimes(rhs, 1);
                lhs = new IRConstant.Null(rhs.type);
            } else {
                lhs = loadTimes(lhs, 1);
                rhs = loadTimes(rhs, 1);
            }
            node.val = new IRInst.CmpInst(lhs, rhs, node.op, curBlock);
        }
        return node.val;
    }


    @Override
    public Object visit(ASTNode.LogicExpression node) {
        //TODO: RHS Could jump directly to the global short_circuit block, instead of jumping several times between different sc blocks
        Value lhs = loadTimes((Value) node.expr1.accept(this), 1);
        //Short circuit eval
        BasicBlock cont = new BasicBlock("continue", curFunc);
        BasicBlock short_circuit = new BasicBlock("short_circuit", curFunc);
        var logic = new IRInst.AllocaInst("", new IRType.IntegerType("bool"), curBlock);
        new IRInst.StoreInst(logic, lhs, curBlock);

        if (node.op.equals("&&"))
            new IRInst.BranchInst(lhs, cont, short_circuit, curBlock);
        else
            new IRInst.BranchInst(lhs, short_circuit, cont, curBlock);

        curBlock = cont;
        Value rhs = loadTimes((Value) node.expr2.accept(this), 1);
        new IRInst.StoreInst(logic, rhs, curBlock);
        new IRInst.BranchInst(short_circuit, curBlock);

        curBlock = short_circuit;
        return logic;
    }

    @Override
    public Object visit(ASTNode.AssignExpression node) {
        Value dest = (Value) node.expr1.accept(this);
        Value from = loadUntilType((Value) node.expr2.accept(this), ((IRType.PointerType) dest.type).pointer);
        node.val = store(dest, from, curBlock);
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
                node.val = new IRInst.GEPInst(c.getMember(node.id).first, base, curBlock, true);
                ((IRInst.GEPInst) node.val).addOperand(new IRConstant.ConstInteger(c.getMember(node.id).second));
            } else { //Member Function
                node.val = (IRConstant.Function) symbolTable.getFromOriginal(c.className + "_" + node.id, globalScope);
                This = base;
                currentThis = true;
            }
        } else if (node.id.equals("size")) {
            node.val = base;
        }
        return node.val;
    }

    @Override
    public Object visit(ASTNode.FuncExpression node) {
        var f = node.expr.accept(this);
        var args = new ArrayList<Value>();

        if (node.expr instanceof ASTNode.MemberExpression && ((ASTNode.MemberExpression) node.expr).expr.type.isArray()
                && ((ASTNode.MemberExpression) node.expr).id.equals("size")) {
            var start = new IRInst.LoadInst((Value) f, curBlock);
            var t = new IRInst.CastInst(start, IRType.convert("int*"), curBlock);
            var sizePtr = new IRInst.GEPInst(IRType.convert("int"), t, curBlock, false);
            sizePtr.addOperand(new IRConstant.ConstInteger(-1));
            node.val = loadTimes(sizePtr, 1);
        } else if (f instanceof IRConstant.Function) {
            var func = (IRConstant.Function) f;
            var curThis = currentThis ? This : (curClass == null ? null : ((IRType.FunctionType) curFunc.type).params.get(0));
            currentThis = false;

            @SuppressWarnings("unchecked") var tmp = (List<Value>) node.exprList.accept(this);
            var argsNeed = ((IRType.FunctionType) func.type).params;
            boolean flg = false;
            if (tmp.size() == argsNeed.size() - 1) { //lacking "this"
                assert curThis != null;
                args.add(loadUntilType(curThis, 1));
                flg = true;
            }
            for (int i = 0; i < tmp.size(); i++) {
                args.add(loadUntilType(tmp.get(i), argsNeed.get(flg ? (i + 1) : i).type));
            }
            node.val = new IRInst.CallInst(func, args, curBlock);
        } else {
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
            Value v = loadTimes((Value) index.accept(this), 1);
            var t = ((IRType.PointerType) node.val.type).pointer;
            node.val = new IRInst.GEPInst(t, node.val, curBlock, false);
            ((IRInst.GEPInst) node.val).addOperand(v);
            if (i != node.index.size() - 1) node.val = loadUntilType(node.val, t);
        }
        return node.val;
    }

    private Value allocateArray(IRType.Type baseType, Value dimension) {
        var size = new IRInst.BinaryOpInst(new IRConstant.ConstInteger(((IRType.PointerType) baseType).pointer.getByteNum()), dimension, "*", curBlock);
        size = new IRInst.BinaryOpInst(size, new IRConstant.ConstInteger(IRType.convert("int").getByteNum()), "+", curBlock);
        var ret = malloc(size, IRType.convert("int*"));
        //Store size
        store(ret, dimension, curBlock);
        var locAfterSize = new IRInst.GEPInst(IRType.convert("int"), ret, curBlock, false);
        locAfterSize.addOperand(new IRConstant.ConstInteger(1));
        ret = new IRInst.CastInst(locAfterSize, baseType, curBlock);
        return ret;
    }

    private Value allocate(IRType.Type t, List<Value> d) {
        Value dimension = loadTimes(d.get(0), 1);
        Value ret = allocateArray(t, dimension);
        if (d.size() > 1) {
            t = ((IRType.PointerType) t).pointer;
            d = d.subList(1, d.size());

            var i = new IRInst.AllocaInst("i", new IRType.IntegerType("int"), curBlock);
            store(i, new IRConstant.ConstInteger(0), curBlock);

            BasicBlock checkCond = new BasicBlock("for_cond", curFunc);
            BasicBlock body = new BasicBlock("for_body", curFunc);
            BasicBlock after = new BasicBlock("for_after", curFunc);
            new IRInst.BranchInst(checkCond, curBlock);

            curBlock = checkCond;
            var tmp = loadTimes(i, 1);
            Value cond = new IRInst.CmpInst(tmp, dimension, "<", curBlock);
            cond = convertToCorres(Collections.singletonList(cond),
                    Collections.singletonList(new Value("", new IRType.IntegerType(1)))).get(0);
            new IRInst.BranchInst(cond, body, after, curBlock);

            curBlock = body;
            var curStore = new IRInst.GEPInst(t, ret, curBlock, false);
            curStore.addOperand(tmp);

            store(curStore, allocate(t, d), curBlock);
            var newi = new IRInst.BinaryOpInst(tmp, new IRConstant.ConstInteger(1), "+", curBlock);
            store(i, newi, curBlock);
            new IRInst.BranchInst(checkCond, curBlock);
            curBlock = after;
        }
        return ret;
    }

    @Override
    public Object visit(ASTNode.NewExpression node) {
        IRType.Type t = IRType.convert(node.type, module);
        List<Value> d = new ArrayList<>();
        for (ASTNode.Expression expr : node.expressions) {
            d.add((Value) expr.accept(this));
        }
        if (d.size() != 0) node.val = allocate(t, d);
        else {
            var size = new IRConstant.ConstInteger(((IRType.PointerType) t).pointer.getByteNum(), "long");
            node.val = malloc(size, t);
            if (IRType.isClassPointer(t)) { //class constructor
                var c = (IRType.ClassType) ((IRType.PointerType) t).pointer;
                var args = new ArrayList<Value>();
                args.add(0, node.val);
                new IRInst.CallInst(c.constructor, args, curBlock);
            }
        }
        return node.val;
    }
}
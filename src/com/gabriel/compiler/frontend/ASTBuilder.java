package com.gabriel.compiler.frontend;

import com.gabriel.compiler.error.SemanticError;
import com.gabriel.compiler.parser.MxGrammarBaseVisitor;
import com.gabriel.compiler.parser.MxGrammarParser;

import java.util.*;

public class ASTBuilder extends MxGrammarBaseVisitor<Node> {

    Stack<Scope> scopes = new Stack<>();
    Scope GlobalScope;

    public ASTBuilder() {
        super();
        GlobalScope = new Scope("Global", null);
        scopes.push(GlobalScope);
    }

    private ASTNode.Function gen(Scope scope, String str1, String str2, String str3, String str4, String str5, String str6) {
        ASTNode.ParamList param;
        if (!str3.equals("")) {
            if (str5.equals(""))
                param = new ASTNode.ParamList(scope, Collections.singletonList(new ASTNode.Param(scope, str4, new Type(str3))));
            else
                param = new ASTNode.ParamList(scope, Arrays.asList(new ASTNode.Param(scope, str4, new Type(str3)), new ASTNode.Param(scope, str6, new Type(str5))));
        } else {
            param = new ASTNode.ParamList(scope);
        }
        ASTNode.Function ret = new ASTNode.Function(scope, new Type(str1), str2, param, null);
        scope.addSymbol(str2, new Type(TypeKind.FUNCTION, ret));
        return ret;
    }

    private void addBuiltin(Scope scope, List<ASTNode.Function> builtins) {
        List<ASTNode.Function> strFunc = new ArrayList<>();
        Scope newScope = new Scope("string", scope);
        strFunc.add(gen(newScope, "int", "length", "", "", "", ""));
        strFunc.add(gen(newScope, "string", "substring", "int", "left", "int", "right"));
        strFunc.add(gen(newScope, "int", "parseInt", "", "", "", ""));
        strFunc.add(gen(newScope, "int", "ord", "int", "", "", ""));
        ASTNode.Class strClass = new ASTNode.Class(scope, "string", new ArrayList<>(), strFunc, null);
        scope.addSymbol("string", new Type(TypeKind.CLASS, strClass));

        builtins.add(gen(scope, "void", "print", "string", "str", "", ""));
        builtins.add(gen(scope, "void", "println", "string", "str", "", ""));
        builtins.add(gen(scope, "void", "printInt", "int", "n", "", ""));
        builtins.add(gen(scope, "string", "getString", "", "", "", ""));
        builtins.add(gen(scope, "int", "getInt", "", "", "", ""));
        builtins.add(gen(scope, "string", "toString", "int", "i", "", ""));
    }

    @Override
    public Node visitProgram(MxGrammarParser.ProgramContext ctx) {
        Scope curScope = scopes.peek();
        List<ASTNode.Class> classes = new ArrayList<>();
        List<ASTNode.Function> functions = new ArrayList<>(), builtins = new ArrayList<>();
        List<ASTNode.Variable> variables = new ArrayList<>();
        addBuiltin(curScope, builtins);

        ASTNode.Program cur = new ASTNode.Program(curScope, classes, functions, variables);
        for (MxGrammarParser.DeclarationContext decl : ctx.declaration()) {
            Node n = visit(decl);
            if (n instanceof ASTNode.Class) {
                classes.add((ASTNode.Class) n);
            } else if (n instanceof ASTNode.Function) {
                functions.add((ASTNode.Function) n);
            } else if (n instanceof ASTNode.VariableList) {
                variables.addAll(((ASTNode.VariableList) n).variables);
            }
        }
        return cur;
    }

    @Override
    public Node visitDeclaration(MxGrammarParser.DeclarationContext ctx) {
        if (ctx.classDeclaration() != null)
            return visit(ctx.classDeclaration());
        else if (ctx.functionDeclaration() != null)
            return visit(ctx.functionDeclaration());
        else
            return visit(ctx.variableDeclaration());
    }


    @Override
    public Node visitClassDeclaration(MxGrammarParser.ClassDeclarationContext ctx) {
        Scope curScope = scopes.peek();
        String className = ctx.name.getText();

        Scope newScope = new Scope(className, scopes.peek());
        scopes.push(newScope);
        curScope.addSymbol(className, new Type(TypeKind.CLASS, null));

        List<ASTNode.Variable> variables = new ArrayList<>();
        List<ASTNode.Function> functions = new ArrayList<>(), constructors = new ArrayList<>();

        ASTNode.Class ret = new ASTNode.Class(curScope, className, variables, functions, constructors);

        ctx.variableDeclaration().forEach((var) -> {
            ASTNode.VariableList vars = (ASTNode.VariableList) visit(var);
            variables.addAll(vars.variables);
        });
        boolean hasExplicitConstructor = false;
        for (MxGrammarParser.FunctionDeclarationContext func : ctx.functionDeclaration()) {
            ASTNode.Function n = (ASTNode.Function) visit(func);
            if (n.isConstructor()) {
                constructors.add(n);
                hasExplicitConstructor = true;
            } else functions.add(n);
        }
        if (!hasExplicitConstructor)
            constructors.add(new ASTNode.Function(newScope, null, className, new ASTNode.ParamList(newScope), null));
        scopes.pop();
        curScope.modify(className, new Type(TypeKind.CLASS, ret));
        return ret;
    }

    @Override
    public Node visitFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx) {
        Scope curScope = scopes.peek();
        String funcName = ctx.functionIdentifier.getText();

        Scope newScope = new Scope(ctx.functionIdentifier.getText(), scopes.peek());
        scopes.push(newScope);

        curScope.addSymbol(funcName, new Type(TypeKind.FUNCTION, null));

        ASTNode.TypeNode type = (ctx.returnType != null) ? (ASTNode.TypeNode) visit(ctx.returnType) : null;
        ASTNode.ParamList paramList = new ASTNode.ParamList(newScope);
        if (ctx.parameterList() != null)
            paramList = (ASTNode.ParamList) visit(ctx.parameterList());
        ASTNode.Block block = (ASTNode.Block) visit(ctx.block());
        scopes.pop();
        ASTNode.Function ret = new ASTNode.Function(curScope, type != null ? type.type : null, funcName, paramList, block);
        curScope.modify(funcName, new Type(TypeKind.FUNCTION, ret));
        return ret;
    }

    @Override
    public Node visitVariableDeclaration(MxGrammarParser.VariableDeclarationContext ctx) {
        Scope curScope = scopes.peek();
        ASTNode.TypeNode type = (ASTNode.TypeNode) visit(ctx.typename());
        if (ctx.expression() != null) {
            ASTNode.Expression expr = (ASTNode.Expression) visit(ctx.expression());
            ASTNode.Variable ret = new ASTNode.Variable(curScope, type.type, ctx.Identifier(0).getText(), expr);
            curScope.addSymbol(ctx.Identifier(0).getText(), type.type);
            return new ASTNode.VariableList(curScope, Collections.singletonList(ret));
        } else { //multiple declarations
            List<ASTNode.Variable> variables = new ArrayList<>();
            ctx.Identifier().forEach((id) -> {
                variables.add(new ASTNode.Variable(curScope, type.type, id.getText(), null));
                curScope.addSymbol(id.getText(), type.type);
            });
            return new ASTNode.VariableList(curScope, variables);
        }
    }

    @Override
    public Node visitTypename(MxGrammarParser.TypenameContext ctx) {
        Type type;
        if (ctx.Identifier() != null) {
            type = new Type(ctx.Identifier().getText());
            return new ASTNode.TypeNode(scopes.peek(), type);
        } else {
            return visit(ctx.arrayType());
        }
    }

    @Override
    public Node visitArrayType(MxGrammarParser.ArrayTypeContext ctx) {
        int dimension = (ctx.getChildCount() - 1) / 2;
        Type type = new Type(ctx.Identifier().getText(), dimension);
        return new ASTNode.TypeNode(scopes.peek(), type);
    }

    @Override
    public Node visitParameter(MxGrammarParser.ParameterContext ctx) {
        Scope curScope = scopes.peek();
        String id = ctx.Identifier().getText();
        Type type = ((ASTNode.TypeNode) visit(ctx.typename())).type;
        curScope.addSymbol(id, type);
        return new ASTNode.Param(curScope, id, type);
    }

    @Override
    public Node visitParameterList(MxGrammarParser.ParameterListContext ctx) {
        List<ASTNode.Param> list = new ArrayList<>();
        ctx.parameter().forEach((param) -> {
            list.add((ASTNode.Param) visit(param));
        });
        return new ASTNode.ParamList(scopes.peek(), list);
    }

    @Override
    public Node visitStatement(MxGrammarParser.StatementContext ctx) {
        if (ctx.expression() == null) return super.visitStatement(ctx);
        else {
            ASTNode.Expression expr = (ASTNode.Expression) visit(ctx.expression());
            return new ASTNode.ExprStatement(scopes.peek(), expr);
        }
    }

    @Override
    public Node visitBlock(MxGrammarParser.BlockContext ctx) {
        Scope newScope = new Scope("", scopes.peek());
        scopes.push(newScope);
        List<ASTNode.Statement> statements = new ArrayList<>();
        ASTNode.Block ret = new ASTNode.Block(scopes.peek(), statements);

        ctx.statement().forEach((statement) -> {
            Node n = visit(statement);
            statements.add((ASTNode.Statement) n);
        });
        scopes.pop();
        return ret;
    }

    @Override
    public Node visitForStatement(MxGrammarParser.ForStatementContext ctx) {
        Scope newScope = new Scope("for", scopes.peek());
        scopes.push(newScope);

        ASTNode.Expression init = ctx.init == null ? null : (ASTNode.Expression) visit(ctx.init);
        ASTNode.Expression cond = ctx.condition == null ? null : (ASTNode.Expression) visit(ctx.condition);
        ASTNode.Expression incr = ctx.increment == null ? null : (ASTNode.Expression) visit(ctx.increment);
        ASTNode.Statement statement = (ASTNode.Statement) visit(ctx.statement());

        scopes.pop();
        ASTNode.ForStatement ret = new ASTNode.ForStatement(scopes.peek(), init, cond, incr, statement);
        return ret;
    }

    @Override
    public Node visitWhileStatement(MxGrammarParser.WhileStatementContext ctx) {
        Scope newScope = new Scope("while", scopes.peek());
        scopes.push(newScope);

        ASTNode.Expression cond = (ASTNode.Expression) visit(ctx.condition);
        ASTNode.Statement statement = (ASTNode.Statement) visit(ctx.statement());

        scopes.pop();
        ASTNode.WhileStatement ret = new ASTNode.WhileStatement(scopes.peek(), cond, statement);
        return ret;
    }

    @Override
    public Node visitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx) {
        return new ASTNode.ConditionalStatement(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()),
                (ASTNode.Statement) visit(ctx.statement(0)),
                ctx.statement().size() == 1 ? null : (ASTNode.Statement) visit(ctx.statement(1)));
    }

    @Override
    public Node visitReturnStmt(MxGrammarParser.ReturnStmtContext ctx) {
        if (ctx.expression() != null)
            return new ASTNode.ReturnStatement(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()));
        else
            return new ASTNode.ReturnStatement(scopes.peek(), null);
    }

    @Override
    public Node visitBreakStmt(MxGrammarParser.BreakStmtContext ctx) {
        return new ASTNode.BreakStatement(scopes.peek());
    }

    @Override
    public Node visitContinueStmt(MxGrammarParser.ContinueStmtContext ctx) {
        return new ASTNode.ContinueStatement(scopes.peek());
    }

    @Override
    public Node visitParenthesesExpr(MxGrammarParser.ParenthesesExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Node visitAssignmentExpr(MxGrammarParser.AssignmentExprContext ctx) {
        return new ASTNode.AssignExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)));
    }

    @Override
    public Node visitUnaryExpr(MxGrammarParser.UnaryExprContext ctx) {
        return new ASTNode.UnaryExpression(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()), ctx.op.getText());
    }

    @Override
    public Node visitFuncExpr(MxGrammarParser.FuncExprContext ctx) {
        ASTNode.ExpressionList expressionList = new ASTNode.ExpressionList(scopes.peek());
        if (ctx.expressionList() != null)
            expressionList = (ASTNode.ExpressionList) visit(ctx.expressionList());
        return new ASTNode.FuncExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression()), expressionList);
    }

    @Override
    public Node visitArrayExpr(MxGrammarParser.ArrayExprContext ctx) {
        ASTNode.Expression expr1 = (ASTNode.Expression) visit(ctx.expression(0));
        ASTNode.Expression expr2 = (ASTNode.Expression) visit(ctx.expression(1));

        if (expr1 instanceof ASTNode.ArrayExpression) {
            ASTNode.ArrayExpression arrayExpr = (ASTNode.ArrayExpression) expr1;
            List<ASTNode.Expression> newIndex = new ArrayList<>(arrayExpr.index);
            newIndex.add(expr2);
            return new ASTNode.ArrayExpression(scopes.peek(), arrayExpr.expr, newIndex);
        } else
            return new ASTNode.ArrayExpression(scopes.peek(), expr1, Collections.singletonList(expr2));
    }

    @Override
    public Node visitMemberExpr(MxGrammarParser.MemberExprContext ctx) {
        return new ASTNode.MemberExpression(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()), ctx.Identifier().getText());
    }

    @Override
    public Node visitSuffixExpr(MxGrammarParser.SuffixExprContext ctx) {
        return new ASTNode.SuffixExpression(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()), ctx.op.getText());
    }

    @Override
    public Node visitBinaryExpr(MxGrammarParser.BinaryExprContext ctx) {
        return new ASTNode.BinaryExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)),
                ctx.op.getText());
    }

    @Override
    public Node visitCmpExpr(MxGrammarParser.CmpExprContext ctx) {
        return new ASTNode.CmpExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)),
                ctx.op.getText());
    }

    @Override
    public Node visitLogicExpr(MxGrammarParser.LogicExprContext ctx) {
        return new ASTNode.LogicExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)),
                ctx.op.getText());
    }

    @Override
    public Node visitExpressionList(MxGrammarParser.ExpressionListContext ctx) {
        List<ASTNode.Expression> list = new ArrayList<>();
        ctx.expression().forEach((expr) -> {
            list.add((ASTNode.Expression) visit(expr));
        });
        return new ASTNode.ExpressionList(scopes.peek(), list);
    }

    @Override
    public Node visitLiteral(MxGrammarParser.LiteralContext ctx) {
        if (ctx.NumLiteral() != null)
            return new ASTNode.LiteralExpression(scopes.peek(), Integer.parseInt(ctx.NumLiteral().getText()));
        else if (ctx.StringLiteral() != null)
            return new ASTNode.LiteralExpression(scopes.peek(), ctx.StringLiteral().getText(), false);
        else if (ctx.BoolLiteral() != null)
            return new ASTNode.LiteralExpression(scopes.peek(), ctx.BoolLiteral().getText(), false);
        else return new ASTNode.LiteralExpression(scopes.peek(), ctx.NullLiteral().getText(), false);
    }

    @Override
    public Node visitBasicExpression(MxGrammarParser.BasicExpressionContext ctx) {
        if (ctx.This() != null) return new ASTNode.LiteralExpression(scopes.peek(), "this", false);
        else if (ctx.Identifier() != null) {
            Scope belongScope = scopes.peek().findScope(ctx.Identifier().getText());
            boolean isFunc = false;
            if (belongScope == null) {
                belongScope = scopes.peek();
                isFunc = true;
            }
//            if (belongScope == null)
//                throw new SemanticError.NotDeclared(ctx.Identifier().getText(), "");
            return new ASTNode.LiteralExpression(belongScope, ctx.Identifier().getText(), true, isFunc);
        } else return visit(ctx.literal());
    }

    @Override
    public Node visitNewExpression(MxGrammarParser.NewExpressionContext ctx) {
        if (ctx.error != null)
            throw new SemanticError.InvalidOperation("new");

        List<ASTNode.Expression> expressions = new ArrayList<>();
        int dimension_total = (ctx.getChildCount() - 2 - ctx.expression().size() * 3) / 2 + ctx.expression().size();
        ctx.expression().forEach((expr) -> {
            expressions.add((ASTNode.Expression) visit(expr));
        });
        return new ASTNode.NewExpression(scopes.peek(), ctx.type.getText(),
                expressions, dimension_total - expressions.size());
    }
}

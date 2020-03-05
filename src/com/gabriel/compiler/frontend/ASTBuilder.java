package com.gabriel.compiler.frontend;

import com.gabriel.compiler.parser.MxGrammarBaseVisitor;
import com.gabriel.compiler.parser.MxGrammarParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ASTBuilder extends MxGrammarBaseVisitor<Node> {

    Stack<Scope> scopes = new Stack<>();

    public ASTBuilder() {
        super();
        Scope GlobalScope = new Scope("Global", null);
        scopes.push(GlobalScope);
    }

    @Override
    public Node visitProgram(MxGrammarParser.ProgramContext ctx) {
        Scope curScope = scopes.peek();
        List<ASTNode.Class> classes = new ArrayList<>();
        List<ASTNode.Function> functions = new ArrayList<>();
        List<ASTNode.Variable> variables = new ArrayList<>();
        ASTNode.Program cur = new ASTNode.Program(curScope, classes, functions, variables);

        ctx.classDeclaration().forEach((c) -> {
            ASTNode.Class n = (ASTNode.Class) visit(c);
            classes.add(n);
        });

        ctx.functionDeclaration().forEach((func) -> {
            ASTNode.Function n = (ASTNode.Function) visit(func);
            functions.add(n);
        });

        ctx.variableDeclaration().forEach((var) -> {
            ASTNode.VariableList vars = (ASTNode.VariableList) visit(var);
            variables.addAll(vars.variables);
        });
        return cur;
    }

    @Override
    public Node visitClassDeclaration(MxGrammarParser.ClassDeclarationContext ctx) {
        String className = ctx.name.getText();
        Scope newScope = new Scope(className, scopes.peek());
        scopes.push(newScope);
        List<ASTNode.Variable> variables = new ArrayList<>();
        List<ASTNode.Function> functions = new ArrayList<>();
        ASTNode.Class ret = new ASTNode.Class(newScope, className, variables, functions);

        ctx.variableDeclaration().forEach((var) -> {
            ASTNode.VariableList vars = (ASTNode.VariableList) visit(var);
            variables.addAll(vars.variables);
        });
        ctx.functionDeclaration().forEach((func) -> {
            Node n = visit(func);
            functions.add((ASTNode.Function) n);
        });
        scopes.pop();
        return ret;
    }

    @Override
    public Node visitFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx) {
        Scope newScope = new Scope(ctx.functionIdentifier.getText(), scopes.peek());
        scopes.push(newScope);
        ASTNode.TypeNode type = (ASTNode.TypeNode) visit(ctx.returnType);
        String id = ctx.functionIdentifier.getText();
        ASTNode.ParamList paramList = null;
        if (ctx.parameterList() != null)
            paramList = (ASTNode.ParamList) visit(ctx.parameterList());
        ASTNode.Block block = (ASTNode.Block) visit(ctx.block());
        scopes.pop();
        return new ASTNode.Function(newScope, type.type, id, paramList, block);
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
            return new ASTNode.TypeNode(type);
        } else {
            return visit(ctx.arrayType());
        }
    }

    @Override
    public Node visitArrayType(MxGrammarParser.ArrayTypeContext ctx) {
        int dimension = ctx.getChildCount() - 1;
        Type type = new Type(ctx.Identifier().getText(), dimension);
        return new ASTNode.TypeNode(type);
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
        ASTNode.Block ret = new ASTNode.Block(newScope, statements);

        ctx.statement().forEach((statement) -> {
            Node n = visit(statement);
            statements.add((ASTNode.Statement) n);
        });
        scopes.pop();
        return ret;
    }

    @Override
    public Node visitForStatement(MxGrammarParser.ForStatementContext ctx) {
        ASTNode.Expression init = (ASTNode.Expression) visit(ctx.init);
        ASTNode.Expression cond = (ASTNode.Expression) visit(ctx.condition);
        ASTNode.Expression incr = (ASTNode.Expression) visit(ctx.increment);
        ASTNode.Statement statement = (ASTNode.Statement) visit(ctx.statement());
        return new ASTNode.ForStatement(scopes.peek(), init, cond, incr, statement);
    }

    @Override
    public Node visitWhileStatement(MxGrammarParser.WhileStatementContext ctx) {
        ASTNode.Expression cond = (ASTNode.Expression) visit(ctx.condition);
        ASTNode.Statement statement = (ASTNode.Statement) visit(ctx.statement());
        return new ASTNode.WhileStatement(scopes.peek(), cond, statement);
    }

    @Override
    public Node visitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx) {
        return new ASTNode.ConditionalStatement(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()),
                (ASTNode.Statement) visit(ctx.statement(0)),
                (ASTNode.Statement) visit(ctx.statement(1)));
    }

    @Override
    public Node visitReturnStmt(MxGrammarParser.ReturnStmtContext ctx) {
        return new ASTNode.ReturnStatement(scopes.peek(), (ASTNode.Expression) visit(ctx.expression()));
    }

    @Override
    public Node visitBreakStmt(MxGrammarParser.BreakStmtContext ctx) {
        return new ASTNode.BreakStatement();
    }

    @Override
    public Node visitContinueStmt(MxGrammarParser.ContinueStmtContext ctx) {
        return new ASTNode.ContinueStatement();
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
        ASTNode.ExpressionList expressionList = null;
        if (ctx.expressionList() != null)
            expressionList = (ASTNode.ExpressionList) visit(ctx.expressionList());
        return new ASTNode.FuncExpression(scopes.peek(), ctx.Identifier().getText(), expressionList);
    }

    @Override
    public Node visitArrayExpr(MxGrammarParser.ArrayExprContext ctx) {
        return new ASTNode.ArrayExpression(scopes.peek(),
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)));
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
        return new ASTNode.BinaryExpression(scopes.peek(),
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
        else {
            if (ctx.StringLiteral() != null)
                return new ASTNode.LiteralExpression(scopes.peek(), ctx.StringLiteral().getText(), false);
            else if (ctx.BoolLiteral() != null)
                return new ASTNode.LiteralExpression(scopes.peek(), ctx.BoolLiteral().getText(), false);
            else return new ASTNode.LiteralExpression(scopes.peek(), ctx.NullLiteral().getText(), false);
        }

    }

    @Override
    public Node visitBasicExpression(MxGrammarParser.BasicExpressionContext ctx) {
        if (ctx.This() != null) return new ASTNode.LiteralExpression(scopes.peek(), "null", false);
        else if (ctx.Identifier() != null)
            return new ASTNode.LiteralExpression(scopes.peek(), ctx.Identifier().getText(), true);
        else return visit(ctx.literal());
    }

    @Override
    public Node visitNewExpression(MxGrammarParser.NewExpressionContext ctx) {
        List<ASTNode.Expression> expressions = new ArrayList<>();
        int dimension_total = ctx.getChildCount() - 4;
        ctx.expression().forEach((expr) -> {
            expressions.add((ASTNode.Expression) visit(expr));
        });
        return new ASTNode.NewExpression(scopes.peek(), new Type(ctx.type.getText(), dimension_total),
                expressions, dimension_total - expressions.size());
    }
}

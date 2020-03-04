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
        List<Node> classes = new ArrayList<>();
        List<Node> functions = new ArrayList<>();
        List<Node> variables = new ArrayList<>();
        ASTNode.Program cur = new ASTNode.Program(curScope, classes, functions, variables);

        ctx.classDeclaration().forEach((c) -> {
            Node n = visit(c);
            cur.addChild(n);
            classes.add(n);
        });

        ctx.functionDeclaration().forEach((func) -> {
            Node n = visit(func);
            cur.addChild(n);
            functions.add(n);
        });

        ctx.variableDeclaration().forEach((var) -> {
            List<ASTNode.Variable> vars = (List<ASTNode.Variable>) visit(var);
            vars.forEach((n) -> {
                cur.addChild(n);
                variables.add(n);
            });
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
        ASTNode.Class ret = new ASTNode.Class(className, newScope, variables, functions);

        ctx.variableDeclaration().forEach((var) -> {
            List<ASTNode.Variable> vars = (List<ASTNode.Variable>) visit(var);
            vars.forEach((n) -> {
                ret.addChild(n);
                variables.add(n);
            });
        });
        ctx.functionDeclaration().forEach((func) -> {
            Node n = visit(func);
            functions.add((ASTNode.Function) n);
            ret.addChild(n);
        });
        scopes.pop();
        return ret;
    }

    @Override
    public Node visitVariableDeclaration(MxGrammarParser.VariableDeclarationContext ctx) {
        Scope curScope = scopes.peek();
        ASTNode.TypeNode type = (ASTNode.TypeNode) visit(ctx.typename());
        if (ctx.expression() != null) {
            ASTNode.Expression expr = (ASTNode.Expression) visit(ctx.expression());
            ASTNode.Variable ret = new ASTNode.Variable(type.type, ctx.Identifier(0).getText(), expr);
            curScope.addSymbol(ctx.Identifier(0).getText(), type.type);
            return new ASTNode.VariableList(Collections.singletonList(ret));
        } else { //multiple declarations
            List<ASTNode.Variable> variables = new ArrayList<>();
            ctx.Identifier().forEach((id) -> {
                variables.add(new ASTNode.Variable(type.type, id.getText(), null));
                curScope.addSymbol(id.getText(), type.type);
            });
            return new ASTNode.VariableList(variables);
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
    public Node visitFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx) {
        Scope newScope = new Scope(ctx.functionIdentifier.getText(), scopes.peek());
        scopes.push(newScope);
        ASTNode.TypeNode type = (ASTNode.TypeNode) visit(ctx.returnType);
        String id = ctx.functionIdentifier.getText();
        ASTNode.ParamList paramList = (ASTNode.ParamList) visit(ctx.parameterList());
        ASTNode.Block block = (ASTNode.Block) visit(ctx.block());
        scopes.pop();
        return new ASTNode.Function(newScope, type.type, id, paramList, block);
    }

    @Override
    public Node visitParameter(MxGrammarParser.ParameterContext ctx) {
        Scope curScope = scopes.peek();
        String id = ctx.Identifier().getText();
        Type type = ((ASTNode.TypeNode) visit(ctx.typename())).type;
        curScope.addSymbol(id, type);
        return new ASTNode.Param(id, type);
    }

    @Override
    public Node visitParameterList(MxGrammarParser.ParameterListContext ctx) {
        List<ASTNode.Param> list = new ArrayList<>();
        ctx.parameter().forEach((param) -> {
            list.add((ASTNode.Param) visit(param));
        });
        return new ASTNode.ParamList(list);
    }

    @Override
    public Node visitBlock(MxGrammarParser.BlockContext ctx) {
        Scope newScope = new Scope("", scopes.peek());
        scopes.push(newScope);
        List<ASTNode.Statement> statements = new ArrayList<>();
        ASTNode.Block ret = new ASTNode.Block(newScope, statements);

        ctx.statement().forEach((statement) -> {
            Node n = visit(statement);
            ret.addChild(n);
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
        return new ASTNode.ForStatement(init, cond, incr, statement);
    }

    @Override
    public Node visitWhileStatement(MxGrammarParser.WhileStatementContext ctx) {
        ASTNode.Expression cond = (ASTNode.Expression) visit(ctx.condition);
        ASTNode.Statement statement = (ASTNode.Statement) visit(ctx.statement());
        return new ASTNode.WhileStatement(cond, statement);
    }

    @Override
    public Node visitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx) {
        return new ASTNode.ConditionalStatement((ASTNode.Expression) visit(ctx.expression()),
                (ASTNode.Statement) visit(ctx.statement(0)),
                (ASTNode.Statement) visit(ctx.statement(1)));
    }

    @Override
    public Node visitReturnStmt(MxGrammarParser.ReturnStmtContext ctx) {
        return new ASTNode.ReturnStatement((ASTNode.Expression) visit(ctx.expression()));
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
        return new ASTNode.AssignExpression(
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)));
    }

    @Override
    public Node visitUnaryExpr(MxGrammarParser.UnaryExprContext ctx) {
        return new ASTNode.UnaryExpression((ASTNode.Expression) visit(ctx.expression()), ctx.op.getText());
    }

    @Override
    public Node visitFuncExpr(MxGrammarParser.FuncExprContext ctx) {
        return new ASTNode.FuncExpression((ASTNode.Expression) visit(ctx.expression()),
                (ASTNode.ExpressionList) visit(ctx.expressionList()));
    }

    @Override
    public Node visitArrayExpr(MxGrammarParser.ArrayExprContext ctx) {
        return new ASTNode.ArrayExpression(
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)));
    }

    @Override
    public Node visitMemberExpr(MxGrammarParser.MemberExprContext ctx) {
        return new ASTNode.MemberExpression((ASTNode.Expression) visit(ctx.expression()), ctx.Identifier().getText());
    }

    @Override
    public Node visitSuffixExpr(MxGrammarParser.SuffixExprContext ctx) {
        return new ASTNode.SuffixExpression((ASTNode.Expression) visit(ctx.expression()), ctx.op.getText());
    }

    @Override
    public Node visitBinaryExpr(MxGrammarParser.BinaryExprContext ctx) {
        return new ASTNode.BinaryExpression(
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)),
                ctx.op.getText());
    }

    @Override
    public Node visitCmpExpr(MxGrammarParser.CmpExprContext ctx) {
        return new ASTNode.BinaryExpression(
                (ASTNode.Expression) visit(ctx.expression(0)),
                (ASTNode.Expression) visit(ctx.expression(1)),
                ctx.op.getText());
    }

    @Override
    public Node visitLogicExpr(MxGrammarParser.LogicExprContext ctx) {
        return new ASTNode.LogicExpression(
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
        return new ASTNode.ExpressionList(list);
    }

    @Override
    public Node visitLiteral(MxGrammarParser.LiteralContext ctx) {
        if (ctx.NumLiteral() != null)
            return new ASTNode.ConstantExpression(Integer.parseInt(ctx.NumLiteral().getText()));
        else {
            if (ctx.StringLiteral() != null)
                return new ASTNode.ConstantExpression(ctx.StringLiteral().getText(), false);
            else if (ctx.BoolLiteral() != null)
                return new ASTNode.ConstantExpression(ctx.BoolLiteral().getText(), false);
            else return new ASTNode.ConstantExpression(ctx.NullLiteral().getText(), false);
        }

    }

    @Override
    public Node visitBasicExpression(MxGrammarParser.BasicExpressionContext ctx) {
        if (ctx.This() != null) return new ASTNode.ConstantExpression("null", false);
        else if (ctx.Identifier() != null) return new ASTNode.ConstantExpression(ctx.Identifier().getText(), true);
        else return visit(ctx.literal());
    }

    @Override
    public Node visitNewExpression(MxGrammarParser.NewExpressionContext ctx) {
        List<ASTNode.Expression> expressions = new ArrayList<>();
        int dimension_total = ctx.getChildCount() - 4;
        ctx.expression().forEach((expr) -> {
            expressions.add((ASTNode.Expression) visit(expr));
        });
        return new ASTNode.NewExpression(new Type(ctx.type.getText(), dimension_total),
                expressions, dimension_total - expressions.size());
    }
}

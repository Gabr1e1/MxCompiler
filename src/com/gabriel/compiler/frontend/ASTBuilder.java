package com.gabriel.compiler.frontend;

import com.gabriel.compiler.parser.MxGrammarBaseVisitor;
import com.gabriel.compiler.parser.MxGrammarParser;

import java.util.ArrayList;
import java.util.Arrays;
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
            return new ASTNode.VariableList(Arrays.asList(ret));
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
            Node n = (ASTNode.Statement) visit(statement);
            ret.addChild(n);
            statements.add((ASTNode.Statement) n);
        });
        scopes.pop();
        return ret;
    }

    @Override
    public Node visitForStatement(MxGrammarParser.ForStatementContext ctx) {
        return super.visitForStatement(ctx);
    }

    @Override
    public Node visitWhileStatement(MxGrammarParser.WhileStatementContext ctx) {
        return super.visitWhileStatement(ctx);
    }

    @Override
    public Node visitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx) {
        return null;
    }

    @Override
    public Node visitJumpStatement(MxGrammarParser.JumpStatementContext ctx) {
        return null;
    }

    @Override
    public Node visitAssignmentExpr(MxGrammarParser.AssignmentExprContext ctx) {
        return super.visitAssignmentExpr(ctx);
    }

    @Override
    public Node visitBasicExpr(MxGrammarParser.BasicExprContext ctx) {
        return super.visitBasicExpr(ctx);
    }

    @Override
    public Node visitUnaryExpr(MxGrammarParser.UnaryExprContext ctx) {
        return super.visitUnaryExpr(ctx);
    }

    @Override
    public Node visitFuncExpr(MxGrammarParser.FuncExprContext ctx) {
        return super.visitFuncExpr(ctx);
    }

    @Override
    public Node visitArrayExpr(MxGrammarParser.ArrayExprContext ctx) {
        return super.visitArrayExpr(ctx);
    }

    @Override
    public Node visitMemberExpr(MxGrammarParser.MemberExprContext ctx) {
        return super.visitMemberExpr(ctx);
    }

    @Override
    public Node visitSuffixExpr(MxGrammarParser.SuffixExprContext ctx) {
        return super.visitSuffixExpr(ctx);
    }

    @Override
    public Node visitBinaryExpr(MxGrammarParser.BinaryExprContext ctx) {
        return super.visitBinaryExpr(ctx);
    }

    @Override
    public Node visitParenthesesExpr(MxGrammarParser.ParenthesesExprContext ctx) {
        return super.visitParenthesesExpr(ctx);
    }

    @Override
    public Node visitCreatorExpr(MxGrammarParser.CreatorExprContext ctx) {
        return super.visitCreatorExpr(ctx);
    }

    @Override
    public Node visitCmpExpr(MxGrammarParser.CmpExprContext ctx) {
        return super.visitCmpExpr(ctx);
    }

    @Override
    public Node visitExpressionList(MxGrammarParser.ExpressionListContext ctx) {
        return super.visitExpressionList(ctx);
    }

    @Override
    public Node visitLiteral(MxGrammarParser.LiteralContext ctx) {
        return super.visitLiteral(ctx);
    }

    @Override
    public Node visitBasicExpression(MxGrammarParser.BasicExpressionContext ctx) {
        return super.visitBasicExpression(ctx);
    }

    @Override
    public Node visitUnaryExpression(MxGrammarParser.UnaryExpressionContext ctx) {
        return super.visitUnaryExpression(ctx);
    }

    @Override
    public Node visitNewExpression(MxGrammarParser.NewExpressionContext ctx) {
        return super.visitNewExpression(ctx);
    }
}

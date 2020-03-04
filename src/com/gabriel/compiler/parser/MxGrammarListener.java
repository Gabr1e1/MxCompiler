// Generated from MxGrammar.g4 by ANTLR 4.8
package com.gabriel.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxGrammarParser}.
 */
public interface MxGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MxGrammarParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MxGrammarParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MxGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MxGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#typename}.
	 * @param ctx the parse tree
	 */
	void enterTypename(MxGrammarParser.TypenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#typename}.
	 * @param ctx the parse tree
	 */
	void exitTypename(MxGrammarParser.TypenameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpr(MxGrammarParser.AssignmentExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpr(MxGrammarParser.AssignmentExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code basicExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpr(MxGrammarParser.BasicExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code basicExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpr(MxGrammarParser.BasicExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(MxGrammarParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(MxGrammarParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(MxGrammarParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(MxGrammarParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
     * @param ctx the parse tree
     */
    void enterArrayExpr(MxGrammarParser.ArrayExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code arrayExpr}
     * labeled alternative in {@link MxGrammarParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitArrayExpr(MxGrammarParser.ArrayExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code logicExpr}
     * labeled alternative in {@link MxGrammarParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterLogicExpr(MxGrammarParser.LogicExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code logicExpr}
     * labeled alternative in {@link MxGrammarParser#expression}.
     *
     * @param ctx the parse tree
     */
    void exitLogicExpr(MxGrammarParser.LogicExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code memberExpr}
     * labeled alternative in {@link MxGrammarParser#expression}.
     *
     * @param ctx the parse tree
     */
    void enterMemberExpr(MxGrammarParser.MemberExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code memberExpr}
     * labeled alternative in {@link MxGrammarParser#expression}.
     *
     * @param ctx the parse tree
     */
	void exitMemberExpr(MxGrammarParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MxGrammarParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MxGrammarParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxGrammarParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxGrammarParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCreatorExpr(MxGrammarParser.CreatorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCreatorExpr(MxGrammarParser.CreatorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesesExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesesExpr(MxGrammarParser.ParenthesesExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesesExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesesExpr(MxGrammarParser.ParenthesesExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cmpExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCmpExpr(MxGrammarParser.CmpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cmpExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCmpExpr(MxGrammarParser.CmpExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(MxGrammarParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(MxGrammarParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MxGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MxGrammarParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#basicExpression}.
	 * @param ctx the parse tree
	 */
	void enterBasicExpression(MxGrammarParser.BasicExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#basicExpression}.
	 * @param ctx the parse tree
	 */
	void exitBasicExpression(MxGrammarParser.BasicExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(MxGrammarParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(MxGrammarParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxGrammarParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxGrammarParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MxGrammarParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#parameterList}.
	 * @param ctx the parse tree
     */
    void exitParameterList(MxGrammarParser.ParameterListContext ctx);

    /**
     * Enter a parse tree produced by {@link MxGrammarParser#block}.
     *
     * @param ctx the parse tree
     */
    void enterBlock(MxGrammarParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link MxGrammarParser#block}.
     *
     * @param ctx the parse tree
     */
    void exitBlock(MxGrammarParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link MxGrammarParser#statement}.
     *
     * @param ctx the parse tree
     */
    void enterStatement(MxGrammarParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxGrammarParser#statement}.
     *
     * @param ctx the parse tree
     */
    void exitStatement(MxGrammarParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by the {@code forStatement}
     * labeled alternative in {@link MxGrammarParser#controlStatement}.
     *
     * @param ctx the parse tree
     */
    void enterForStatement(MxGrammarParser.ForStatementContext ctx);

    /**
     * Exit a parse tree produced by the {@code forStatement}
     * labeled alternative in {@link MxGrammarParser#controlStatement}.
     *
     * @param ctx the parse tree
     */
    void exitForStatement(MxGrammarParser.ForStatementContext ctx);

    /**
     * Enter a parse tree produced by the {@code whileStatement}
     * labeled alternative in {@link MxGrammarParser#controlStatement}.
     *
     * @param ctx the parse tree
     */
    void enterWhileStatement(MxGrammarParser.WhileStatementContext ctx);

    /**
     * Exit a parse tree produced by the {@code whileStatement}
     * labeled alternative in {@link MxGrammarParser#controlStatement}.
     *
     * @param ctx the parse tree
     */
    void exitWhileStatement(MxGrammarParser.WhileStatementContext ctx);

    /**
     * Enter a parse tree produced by {@link MxGrammarParser#conditionalStatement}.
     *
     * @param ctx the parse tree
     */
    void enterConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx);

    /**
     * Exit a parse tree produced by {@link MxGrammarParser#conditionalStatement}.
     *
     * @param ctx the parse tree
     */
    void exitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx);

    /**
     * Enter a parse tree produced by the {@code returnStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void enterReturnStmt(MxGrammarParser.ReturnStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code returnStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void exitReturnStmt(MxGrammarParser.ReturnStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code breakStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void enterBreakStmt(MxGrammarParser.BreakStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code breakStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void exitBreakStmt(MxGrammarParser.BreakStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code continueStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void enterContinueStmt(MxGrammarParser.ContinueStmtContext ctx);

    /**
     * Exit a parse tree produced by the {@code continueStmt}
     * labeled alternative in {@link MxGrammarParser#jumpStatement}.
     *
     * @param ctx the parse tree
     */
    void exitContinueStmt(MxGrammarParser.ContinueStmtContext ctx);

    /**
     * Enter a parse tree produced by {@link MxGrammarParser#arrayType}.
     *
     * @param ctx the parse tree
     */
    void enterArrayType(MxGrammarParser.ArrayTypeContext ctx);

    /**
     * Exit a parse tree produced by {@link MxGrammarParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(MxGrammarParser.ArrayTypeContext ctx);
}
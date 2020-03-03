// Generated from MxGrammar.g4 by ANTLR 4.8
package com.gabriel.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxGrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(MxGrammarParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MxGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(MxGrammarParser.TypenameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpr(MxGrammarParser.AssignmentExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code basicExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicExpr(MxGrammarParser.BasicExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(MxGrammarParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(MxGrammarParser.FuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxGrammarParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(MxGrammarParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(MxGrammarParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxGrammarParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code creatorExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatorExpr(MxGrammarParser.CreatorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesesExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesesExpr(MxGrammarParser.ParenthesesExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cmpExpr}
	 * labeled alternative in {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpExpr(MxGrammarParser.CmpExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(MxGrammarParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxGrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#basicExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicExpression(MxGrammarParser.BasicExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(MxGrammarParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(MxGrammarParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(MxGrammarParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxGrammarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MxGrammarParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxGrammarParser.BlockContext ctx);

	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#statement}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxGrammarParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by the {@code forStatement}
	 * labeled alternative in {@link MxGrammarParser#controlStatement}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxGrammarParser.ForStatementContext ctx);

	/**
	 * Visit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MxGrammarParser#controlStatement}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxGrammarParser.WhileStatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#conditionalStatement}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalStatement(MxGrammarParser.ConditionalStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(MxGrammarParser.JumpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(MxGrammarParser.ArrayTypeContext ctx);
}
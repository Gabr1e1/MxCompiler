// Generated from MxGrammar.g4 by ANTLR 4.8
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
	 * Visit a parse tree produced by {@link MxGrammarParser#class_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_definition(MxGrammarParser.Class_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#variable_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_definition(MxGrammarParser.Variable_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(MxGrammarParser.TypenameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MxGrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_list(MxGrammarParser.Expression_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxGrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#basic_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasic_expression(MxGrammarParser.Basic_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(MxGrammarParser.Unary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#new_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew_expression(MxGrammarParser.New_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(MxGrammarParser.Function_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxGrammarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#parameter_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter_list(MxGrammarParser.Parameter_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxGrammarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxGrammarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#control_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControl_statement(MxGrammarParser.Control_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#conditional_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional_statement(MxGrammarParser.Conditional_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJump_statement(MxGrammarParser.Jump_statementContext ctx);
}
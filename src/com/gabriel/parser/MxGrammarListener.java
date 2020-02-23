// Generated from MxGrammar.g4 by ANTLR 4.8
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
	 * Enter a parse tree produced by {@link MxGrammarParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void enterClass_definition(MxGrammarParser.Class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void exitClass_definition(MxGrammarParser.Class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void enterVariable_definition(MxGrammarParser.Variable_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#variable_definition}.
	 * @param ctx the parse tree
	 */
	void exitVariable_definition(MxGrammarParser.Variable_definitionContext ctx);
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
	 * Enter a parse tree produced by {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MxGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MxGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(MxGrammarParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(MxGrammarParser.Expression_listContext ctx);
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
	 * Enter a parse tree produced by {@link MxGrammarParser#basic_expression}.
	 * @param ctx the parse tree
	 */
	void enterBasic_expression(MxGrammarParser.Basic_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#basic_expression}.
	 * @param ctx the parse tree
	 */
	void exitBasic_expression(MxGrammarParser.Basic_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(MxGrammarParser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(MxGrammarParser.Unary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#new_expression}.
	 * @param ctx the parse tree
	 */
	void enterNew_expression(MxGrammarParser.New_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#new_expression}.
	 * @param ctx the parse tree
	 */
	void exitNew_expression(MxGrammarParser.New_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(MxGrammarParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(MxGrammarParser.Function_definitionContext ctx);
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
	 * Enter a parse tree produced by {@link MxGrammarParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void enterParameter_list(MxGrammarParser.Parameter_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#parameter_list}.
	 * @param ctx the parse tree
	 */
	void exitParameter_list(MxGrammarParser.Parameter_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxGrammarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxGrammarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#control_statement}.
	 * @param ctx the parse tree
	 */
	void enterControl_statement(MxGrammarParser.Control_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#control_statement}.
	 * @param ctx the parse tree
	 */
	void exitControl_statement(MxGrammarParser.Control_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#conditional_statement}.
	 * @param ctx the parse tree
	 */
	void enterConditional_statement(MxGrammarParser.Conditional_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#conditional_statement}.
	 * @param ctx the parse tree
	 */
	void exitConditional_statement(MxGrammarParser.Conditional_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterJump_statement(MxGrammarParser.Jump_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitJump_statement(MxGrammarParser.Jump_statementContext ctx);
}
// Generated from MxGrammar.g4 by ANTLR 4.8
package com.gabriel.compiler.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
			T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
			T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
			T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31,
			T__31 = 32, T__32 = 33, T__33 = 34, T__34 = 35, T__35 = 36, T__36 = 37, T__37 = 38,
			T__38 = 39, T__39 = 40, T__40 = 41, T__41 = 42, Identifier = 43, PrimitiveType = 44,
			NumLiteral = 45, StringLiteral = 46, BoolLiteral = 47, NullLiteral = 48, This = 49,
			WS = 50, BlockComment = 51, LineComment = 52;
	public static final int
			RULE_program = 0, RULE_classDeclaration = 1, RULE_variableDeclaration = 2,
			RULE_typename = 3, RULE_expression = 4, RULE_expressionList = 5, RULE_literal = 6,
			RULE_basicExpression = 7, RULE_newExpression = 8, RULE_functionDeclaration = 9,
			RULE_parameter = 10, RULE_parameterList = 11, RULE_block = 12, RULE_statement = 13,
			RULE_controlStatement = 14, RULE_conditionalStatement = 15, RULE_jumpStatement = 16,
			RULE_arrayType = 17;

	private static String[] makeRuleNames() {
		return new String[]{
				"program", "classDeclaration", "variableDeclaration", "typename", "expression",
				"expressionList", "literal", "basicExpression", "newExpression", "functionDeclaration",
				"parameter", "parameterList", "block", "statement", "controlStatement",
				"conditionalStatement", "jumpStatement", "arrayType"
		};
	}

	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "'='", "';'", "','", "'('", "')'", "'.'", 
			"'['", "']'", "'++'", "'--'", "'+'", "'-'", "'~'", "'!'", "'*'", "'/'", 
			"'%'", "'<<'", "'>>'", "'<'", "'>'", "'>='", "'<='", "'=='", "'!='", 
			"'&'", "'^'", "'|'", "'&&'", "'||'", "'new'", "'[]'", "'for'", "'while'", 
			"'if'", "'else'", "'return'", "'break;'", "'continue;'", null, null, 
			null, null, null, "'null'", "'this'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "Identifier", "PrimitiveType", 
			"NumLiteral", "StringLiteral", "BoolLiteral", "NullLiteral", "This", 
			"WS", "BlockComment", "LineComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<ClassDeclarationContext> classDeclaration() {
			return getRuleContexts(ClassDeclarationContext.class);
		}
		public ClassDeclarationContext classDeclaration(int i) {
			return getRuleContext(ClassDeclarationContext.class,i);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0 || _la == Identifier) {
					{
						setState(39);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
							case 1: {
								setState(36);
								classDeclaration();
							}
							break;
							case 2: {
								setState(37);
								functionDeclaration();
							}
							break;
							case 3: {
								setState(38);
								variableDeclaration();
							}
							break;
						}
					}
					setState(43);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public List<VariableDeclarationContext> variableDeclaration() {
			return getRuleContexts(VariableDeclarationContext.class);
		}
		public VariableDeclarationContext variableDeclaration(int i) {
			return getRuleContext(VariableDeclarationContext.class,i);
		}
		public List<FunctionDeclarationContext> functionDeclaration() {
			return getRuleContexts(FunctionDeclarationContext.class);
		}
		public FunctionDeclarationContext functionDeclaration(int i) {
			return getRuleContext(FunctionDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(44);
				match(T__0);
				setState(45);
				((ClassDeclarationContext) _localctx).name = match(Identifier);
				setState(46);
				match(T__1);
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == Identifier) {
					{
						setState(49);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
							case 1: {
								setState(47);
								variableDeclaration();
							}
							break;
							case 2: {
								setState(48);
								functionDeclaration();
							}
							break;
						}
					}
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(54);
				match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxGrammarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxGrammarParser.Identifier, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variableDeclaration);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(56);
					typename();
					setState(57);
					match(Identifier);
					setState(60);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la == T__3) {
						{
							setState(58);
							match(T__3);
							setState(59);
							expression(0);
						}
					}

					setState(62);
					match(T__4);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
			{
				setState(64);
				typename();
				setState(67);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
					case 1: {
						setState(65);
						match(Identifier);
						setState(66);
						match(T__5);
					}
					break;
				}
				setState(69);
				match(Identifier);
				setState(70);
				match(T__4);
			}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypenameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public TypenameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typename; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterTypename(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitTypename(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitTypename(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypenameContext typename() throws RecognitionException {
		TypenameContext _localctx = new TypenameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typename);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 7, _ctx)) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(74);
					match(Identifier);
				}
				break;
				case 2:
				enterOuterAlt(_localctx, 2);
				{
					setState(75);
					arrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignmentExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssignmentExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterAssignmentExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitAssignmentExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitAssignmentExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BasicExprContext extends ExpressionContext {
		public BasicExpressionContext basicExpression() {
			return getRuleContext(BasicExpressionContext.class,0);
		}
		public BasicExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterBasicExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitBasicExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitBasicExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitUnaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FuncExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterFuncExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitFuncExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitFuncExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterArrayExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitArrayExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitArrayExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class LogicExprContext extends ExpressionContext {
		public Token op;

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public LogicExprContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterLogicExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitLogicExpr(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitLogicExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class MemberExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public TerminalNode Identifier() {
			return getToken(MxGrammarParser.Identifier, 0);
		}

		public MemberExprContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterMemberExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitMemberExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitMemberExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuffixExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SuffixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterSuffixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitSuffixExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitSuffixExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitBinaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitBinaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CreatorExprContext extends ExpressionContext {
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public CreatorExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterCreatorExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitCreatorExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitCreatorExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesesExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesesExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterParenthesesExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitParenthesesExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitParenthesesExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CmpExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CmpExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterCmpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitCmpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitCmpExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(89);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case Identifier:
					case NumLiteral:
					case StringLiteral:
					case BoolLiteral:
					case NullLiteral:
					case This: {
						_localctx = new BasicExprContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;

						setState(79);
						basicExpression();
					}
				break;
			case T__6: {
				_localctx = new ParenthesesExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(80);
				match(T__6);
				setState(81);
				expression(0);
				setState(82);
				match(T__7);
			}
				break;
			case T__33: {
				_localctx = new CreatorExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(84);
				newExpression();
			}
				break;
			case T__11:
			case T__12:
			case T__13:
			case T__14: {
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(85);
				((UnaryExprContext) _localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0))) {
					((UnaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(86);
				expression(13);
			}
				break;
			case T__15:
			case T__16: {
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87);
				((UnaryExprContext) _localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if (!(_la == T__15 || _la == T__16)) {
					((UnaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(88);
				expression(12);
			}
			break;
					default:
						throw new NoViableAltException(this);
				}
				_ctx.stop = _input.LT(-1);
				setState(141);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null) triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(139);
							_errHandler.sync(this);
							switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
								case 1: {
									_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(91);
									if (!(precpred(_ctx, 11)))
										throw new FailedPredicateException(this, "precpred(_ctx, 11)");
									setState(92);
									((BinaryExprContext) _localctx).op = _input.LT(1);
									_la = _input.LA(1);
									if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19))) != 0))) {
										((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
									} else {
										if (_input.LA(1) == Token.EOF) matchedEOF = true;
										_errHandler.reportMatch(this);
										consume();
									}
									setState(93);
									expression(12);
								}
						break;
					case 2: {
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(94);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(95);
						((BinaryExprContext) _localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if (!(_la == T__13 || _la == T__14)) {
							((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(96);
						expression(11);
					}
						break;
					case 3: {
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(97);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(98);
						((BinaryExprContext) _localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if (!(_la == T__20 || _la == T__21)) {
							((BinaryExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(99);
						expression(10);
					}
						break;
					case 4: {
						_localctx = new CmpExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(100);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(101);
						((CmpExprContext) _localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0))) {
							((CmpExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(102);
						expression(9);
					}
						break;
					case 5: {
						_localctx = new CmpExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(103);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(104);
						((CmpExprContext) _localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if (!(_la == T__26 || _la == T__27)) {
							((CmpExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(105);
						expression(8);
					}
						break;
					case 6: {
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(106);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(107);
						((BinaryExprContext) _localctx).op = match(T__28);
						setState(108);
						expression(7);
					}
						break;
					case 7: {
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(109);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(110);
						((BinaryExprContext) _localctx).op = match(T__29);
						setState(111);
						expression(6);
					}
						break;
					case 8: {
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(112);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(113);
						((BinaryExprContext) _localctx).op = match(T__30);
						setState(114);
						expression(5);
					}
						break;
					case 9: {
						_localctx = new LogicExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(115);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(116);
						((LogicExprContext) _localctx).op = match(T__31);
						setState(117);
						expression(4);
					}
						break;
					case 10: {
						_localctx = new LogicExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(118);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(119);
						((LogicExprContext) _localctx).op = match(T__32);
						setState(120);
						expression(3);
					}
						break;
					case 11: {
						_localctx = new AssignmentExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(121);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(122);
						match(T__3);
						setState(123);
						expression(1);
					}
						break;
					case 12: {
						_localctx = new MemberExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(124);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(125);
						match(T__8);
						setState(126);
						match(Identifier);
					}
						break;
					case 13: {
						_localctx = new ArrayExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(127);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(128);
						match(T__9);
						setState(129);
						expression(0);
						setState(130);
						match(T__10);
					}
						break;
					case 14: {
						_localctx = new FuncExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(132);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(133);
						match(T__6);
						setState(134);
						expressionList();
						setState(135);
						match(T__7);
					}
						break;
					case 15: {
						_localctx = new SuffixExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(137);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(138);
						((SuffixExprContext) _localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if (!(_la == T__11 || _la == T__12)) {
							((SuffixExprContext) _localctx).op = (Token) _errHandler.recoverInline(this);
						} else {
							if (_input.LA(1) == Token.EOF) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
					}
					break;
							}
						}
					}
					setState(143);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(144);
				expression(0);
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__5) {
					{
						{
							setState(145);
							match(T__5);
							setState(146);
							expression(0);
						}
					}
					setState(151);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode NumLiteral() { return getToken(MxGrammarParser.NumLiteral, 0); }
		public TerminalNode StringLiteral() { return getToken(MxGrammarParser.StringLiteral, 0); }
		public TerminalNode BoolLiteral() { return getToken(MxGrammarParser.BoolLiteral, 0); }
		public TerminalNode NullLiteral() { return getToken(MxGrammarParser.NullLiteral, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(152);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral))) != 0))) {
					_errHandler.recoverInline(this);
				} else {
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BasicExpressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode This() { return getToken(MxGrammarParser.This, 0); }
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public BasicExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterBasicExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitBasicExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitBasicExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BasicExpressionContext basicExpression() throws RecognitionException {
		BasicExpressionContext _localctx = new BasicExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_basicExpression);
		try {
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case NumLiteral:
				case StringLiteral:
				case BoolLiteral:
				case NullLiteral:
					enterOuterAlt(_localctx, 1);
				{
					setState(154);
					literal();
				}
				break;
			case This:
				enterOuterAlt(_localctx, 2);
			{
				setState(155);
				match(This);
			}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 3);
			{
				setState(156);
				match(Identifier);
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public Token type;
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitNewExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_newExpression);
		try {
			int _alt;
			setState(177);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(159);
					match(T__33);
					setState(160);
					((NewExpressionContext) _localctx).type = match(Identifier);
					setState(165);
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
							case 1: {
								{
									setState(161);
									match(T__9);
									setState(162);
									expression(0);
									setState(163);
									match(T__10);
								}
							}
							break;
							default:
								throw new NoViableAltException(this);
						}
						setState(167);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
					} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
					setState(172);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
					while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
						if (_alt == 1) {
							{
								{
									setState(169);
									match(T__34);
								}
							}
						}
						setState(174);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
					}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
			{
				setState(175);
				match(T__33);
				setState(176);
				((NewExpressionContext) _localctx).type = match(Identifier);
			}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TypenameContext returnType;
		public Token functionIdentifier;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(179);
				((FunctionDeclarationContext) _localctx).returnType = typename();
				setState(180);
				((FunctionDeclarationContext) _localctx).functionIdentifier = match(Identifier);
				setState(181);
				match(T__6);
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == Identifier) {
					{
						setState(182);
						parameterList();
					}
				}

				setState(185);
				match(T__7);
				setState(186);
				block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(188);
				typename();
				setState(189);
				match(Identifier);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(191);
				parameter();
				setState(196);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__5) {
					{
						{
							setState(192);
							match(T__5);
							setState(193);
							parameter();
						}
					}
					setState(198);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(199);
				match(T__1);
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
					{
						{
							setState(200);
							statement();
						}
					}
					setState(205);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(206);
				match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ControlStatementContext controlStatement() {
			return getRuleContext(ControlStatementContext.class,0);
		}
		public ConditionalStatementContext conditionalStatement() {
			return getRuleContext(ConditionalStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_statement);
		int _la;
		try {
			setState(217);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(208);
					block();
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(209);
					variableDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
			{
				setState(210);
				controlStatement();
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
			{
				setState(211);
				conditionalStatement();
			}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
			{
				setState(212);
				jumpStatement();
			}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
			{
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
					{
						setState(213);
						expression(0);
					}
				}

				setState(216);
				match(T__4);
			}
			break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ControlStatementContext extends ParserRuleContext {
		public ControlStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_controlStatement;
		}

		public ControlStatementContext() {
		}

		public void copyFrom(ControlStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class WhileStatementContext extends ControlStatementContext {
		public ExpressionContext condition;

		public StatementContext statement() {
			return getRuleContext(StatementContext.class, 0);
		}

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public WhileStatementContext(ControlStatementContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterWhileStatement(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitWhileStatement(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForStatementContext extends ControlStatementContext {
		public ExpressionContext init;
		public ExpressionContext condition;
		public ExpressionContext increment;

		public StatementContext statement() {
			return getRuleContext(StatementContext.class, 0);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public ForStatementContext(ControlStatementContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterForStatement(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitForStatement(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ControlStatementContext controlStatement() throws RecognitionException {
		ControlStatementContext _localctx = new ControlStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_controlStatement);
		int _la;
		try {
			setState(241);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case T__35:
					_localctx = new ForStatementContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(219);
					match(T__35);
					setState(220);
					match(T__6);
					setState(222);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
						{
							setState(221);
							((ForStatementContext) _localctx).init = expression(0);
						}
					}

					setState(224);
					match(T__4);
					setState(226);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
						{
							setState(225);
							((ForStatementContext) _localctx).condition = expression(0);
						}
					}

					setState(228);
					match(T__4);
					setState(230);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
						{
							setState(229);
							((ForStatementContext) _localctx).increment = expression(0);
						}
					}

					setState(232);
					match(T__7);
					setState(233);
					statement();
				}
				break;
				case T__36:
					_localctx = new WhileStatementContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(234);
					match(T__36);
					setState(235);
					match(T__6);
					setState(237);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
						{
							setState(236);
							((WhileStatementContext) _localctx).condition = expression(0);
						}
					}

					setState(239);
					match(T__7);
					setState(240);
					statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ConditionalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterConditionalStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitConditionalStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitConditionalStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalStatementContext conditionalStatement() throws RecognitionException {
		ConditionalStatementContext _localctx = new ConditionalStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_conditionalStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(243);
				match(T__37);
				setState(244);
				match(T__6);
				setState(245);
				expression(0);
				setState(246);
				match(T__7);
				setState(247);
				statement();
				setState(250);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
					case 1: {
						setState(248);
						match(T__38);
						setState(249);
						statement();
					}
					break;
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_jumpStatement;
		}

		public JumpStatementContext() {
		}

		public void copyFrom(JumpStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class BreakStmtContext extends JumpStatementContext {
		public BreakStmtContext(JumpStatementContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterBreakStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitBreakStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitBreakStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ReturnStmtContext extends JumpStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public ReturnStmtContext(JumpStatementContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterReturnStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitReturnStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ContinueStmtContext extends JumpStatementContext {
		public ContinueStmtContext(JumpStatementContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).enterContinueStmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof MxGrammarListener) ((MxGrammarListener) listener).exitContinueStmt(this);
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if (visitor instanceof MxGrammarVisitor)
				return ((MxGrammarVisitor<? extends T>) visitor).visitContinueStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_jumpStatement);
		int _la;
		try {
			setState(259);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case T__39:
					_localctx = new ReturnStmtContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(252);
					match(T__39);
					setState(254);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__33) | (1L << Identifier) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << This))) != 0)) {
						{
							setState(253);
							expression(0);
						}
					}

					setState(256);
					match(T__4);
				}
				break;
				case T__40:
					_localctx = new BreakStmtContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(257);
					match(T__40);
				}
				break;
				case T__41:
					_localctx = new ContinueStmtContext(_localctx);
					enterOuterAlt(_localctx, 3);
				{
					setState(258);
					match(T__41);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterArrayType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitArrayType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_arrayType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(261);
				match(Identifier);
				setState(263);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(262);
							match(T__34);
						}
					}
					setState(265);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__34 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		case 10:
			return precpred(_ctx, 1);
		case 11:
			return precpred(_ctx, 18);
		case 12:
			return precpred(_ctx, 16);
		case 13:
			return precpred(_ctx, 15);
		case 14:
			return precpred(_ctx, 14);
		}
		return true;
	}

	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\66\u010e\4\2\t\2" +
					"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
					"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
					"\4\23\t\23\3\2\3\2\3\2\7\2*\n\2\f\2\16\2-\13\2\3\3\3\3\3\3\3\3\3\3\7\3" +
					"\64\n\3\f\3\16\3\67\13\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4?\n\4\3\4\3\4\3\4" +
					"\3\4\3\4\5\4F\n\4\3\4\3\4\3\4\5\4K\n\4\3\5\3\5\5\5O\n\5\3\6\3\6\3\6\3" +
					"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\\\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3" +
					"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6" +
					"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3" +
					"\6\3\6\3\6\3\6\3\6\3\6\7\6\u008e\n\6\f\6\16\6\u0091\13\6\3\7\3\7\3\7\7" +
					"\7\u0096\n\7\f\7\16\7\u0099\13\7\3\b\3\b\3\t\3\t\3\t\5\t\u00a0\n\t\3\n" +
					"\3\n\3\n\3\n\3\n\3\n\6\n\u00a8\n\n\r\n\16\n\u00a9\3\n\7\n\u00ad\n\n\f" +
					"\n\16\n\u00b0\13\n\3\n\3\n\5\n\u00b4\n\n\3\13\3\13\3\13\3\13\5\13\u00ba" +
					"\n\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\7\r\u00c5\n\r\f\r\16\r\u00c8" +
					"\13\r\3\16\3\16\7\16\u00cc\n\16\f\16\16\16\u00cf\13\16\3\16\3\16\3\17" +
					"\3\17\3\17\3\17\3\17\3\17\5\17\u00d9\n\17\3\17\5\17\u00dc\n\17\3\20\3" +
					"\20\3\20\5\20\u00e1\n\20\3\20\3\20\5\20\u00e5\n\20\3\20\3\20\5\20\u00e9" +
					"\n\20\3\20\3\20\3\20\3\20\3\20\5\20\u00f0\n\20\3\20\3\20\5\20\u00f4\n" +
					"\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00fd\n\21\3\22\3\22\5\22" +
					"\u0101\n\22\3\22\3\22\3\22\5\22\u0106\n\22\3\23\3\23\6\23\u010a\n\23\r" +
					"\23\16\23\u010b\3\23\2\3\n\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 " +
					"\"$\2\13\3\2\16\21\3\2\22\23\3\2\24\26\3\2\20\21\3\2\27\30\3\2\31\34\3" +
					"\2\35\36\3\2\16\17\3\2/\62\2\u0130\2+\3\2\2\2\4.\3\2\2\2\6J\3\2\2\2\b" +
					"N\3\2\2\2\n[\3\2\2\2\f\u0092\3\2\2\2\16\u009a\3\2\2\2\20\u009f\3\2\2\2" +
					"\22\u00b3\3\2\2\2\24\u00b5\3\2\2\2\26\u00be\3\2\2\2\30\u00c1\3\2\2\2\32" +
					"\u00c9\3\2\2\2\34\u00db\3\2\2\2\36\u00f3\3\2\2\2 \u00f5\3\2\2\2\"\u0105" +
					"\3\2\2\2$\u0107\3\2\2\2&*\5\4\3\2\'*\5\24\13\2(*\5\6\4\2)&\3\2\2\2)\'" +
					"\3\2\2\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\3\3\2\2\2-+\3\2\2\2" +
					"./\7\3\2\2/\60\7-\2\2\60\65\7\4\2\2\61\64\5\6\4\2\62\64\5\24\13\2\63\61" +
					"\3\2\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\668\3" +
					"\2\2\2\67\65\3\2\2\289\7\5\2\29\5\3\2\2\2:;\5\b\5\2;>\7-\2\2<=\7\6\2\2" +
					"=?\5\n\6\2><\3\2\2\2>?\3\2\2\2?@\3\2\2\2@A\7\7\2\2AK\3\2\2\2BE\5\b\5\2" +
					"CD\7-\2\2DF\7\b\2\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\7-\2\2HI\7\7\2\2I" +
					"K\3\2\2\2J:\3\2\2\2JB\3\2\2\2K\7\3\2\2\2LO\7-\2\2MO\5$\23\2NL\3\2\2\2" +
					"NM\3\2\2\2O\t\3\2\2\2PQ\b\6\1\2Q\\\5\20\t\2RS\7\t\2\2ST\5\n\6\2TU\7\n" +
					"\2\2U\\\3\2\2\2V\\\5\22\n\2WX\t\2\2\2X\\\5\n\6\17YZ\t\3\2\2Z\\\5\n\6\16" +
					"[P\3\2\2\2[R\3\2\2\2[V\3\2\2\2[W\3\2\2\2[Y\3\2\2\2\\\u008f\3\2\2\2]^\f" +
					"\r\2\2^_\t\4\2\2_\u008e\5\n\6\16`a\f\f\2\2ab\t\5\2\2b\u008e\5\n\6\rcd" +
					"\f\13\2\2de\t\6\2\2e\u008e\5\n\6\ffg\f\n\2\2gh\t\7\2\2h\u008e\5\n\6\13" +
					"ij\f\t\2\2jk\t\b\2\2k\u008e\5\n\6\nlm\f\b\2\2mn\7\37\2\2n\u008e\5\n\6" +
					"\top\f\7\2\2pq\7 \2\2q\u008e\5\n\6\brs\f\6\2\2st\7!\2\2t\u008e\5\n\6\7" +
					"uv\f\5\2\2vw\7\"\2\2w\u008e\5\n\6\6xy\f\4\2\2yz\7#\2\2z\u008e\5\n\6\5" +
					"{|\f\3\2\2|}\7\6\2\2}\u008e\5\n\6\3~\177\f\24\2\2\177\u0080\7\13\2\2\u0080" +
					"\u008e\7-\2\2\u0081\u0082\f\22\2\2\u0082\u0083\7\f\2\2\u0083\u0084\5\n" +
					"\6\2\u0084\u0085\7\r\2\2\u0085\u008e\3\2\2\2\u0086\u0087\f\21\2\2\u0087" +
					"\u0088\7\t\2\2\u0088\u0089\5\f\7\2\u0089\u008a\7\n\2\2\u008a\u008e\3\2" +
					"\2\2\u008b\u008c\f\20\2\2\u008c\u008e\t\t\2\2\u008d]\3\2\2\2\u008d`\3" +
					"\2\2\2\u008dc\3\2\2\2\u008df\3\2\2\2\u008di\3\2\2\2\u008dl\3\2\2\2\u008d" +
					"o\3\2\2\2\u008dr\3\2\2\2\u008du\3\2\2\2\u008dx\3\2\2\2\u008d{\3\2\2\2" +
					"\u008d~\3\2\2\2\u008d\u0081\3\2\2\2\u008d\u0086\3\2\2\2\u008d\u008b\3" +
					"\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090" +
					"\13\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0097\5\n\6\2\u0093\u0094\7\b\2" +
					"\2\u0094\u0096\5\n\6\2\u0095\u0093\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095" +
					"\3\2\2\2\u0097\u0098\3\2\2\2\u0098\r\3\2\2\2\u0099\u0097\3\2\2\2\u009a" +
					"\u009b\t\n\2\2\u009b\17\3\2\2\2\u009c\u00a0\5\16\b\2\u009d\u00a0\7\63" +
					"\2\2\u009e\u00a0\7-\2\2\u009f\u009c\3\2\2\2\u009f\u009d\3\2\2\2\u009f" +
					"\u009e\3\2\2\2\u00a0\21\3\2\2\2\u00a1\u00a2\7$\2\2\u00a2\u00a7\7-\2\2" +
					"\u00a3\u00a4\7\f\2\2\u00a4\u00a5\5\n\6\2\u00a5\u00a6\7\r\2\2\u00a6\u00a8" +
					"\3\2\2\2\u00a7\u00a3\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9" +
					"\u00aa\3\2\2\2\u00aa\u00ae\3\2\2\2\u00ab\u00ad\7%\2\2\u00ac\u00ab\3\2" +
					"\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af" +
					"\u00b4\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b2\7$\2\2\u00b2\u00b4\7-\2" +
					"\2\u00b3\u00a1\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\23\3\2\2\2\u00b5\u00b6" +
					"\5\b\5\2\u00b6\u00b7\7-\2\2\u00b7\u00b9\7\t\2\2\u00b8\u00ba\5\30\r\2\u00b9" +
					"\u00b8\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\7\n" +
					"\2\2\u00bc\u00bd\5\32\16\2\u00bd\25\3\2\2\2\u00be\u00bf\5\b\5\2\u00bf" +
					"\u00c0\7-\2\2\u00c0\27\3\2\2\2\u00c1\u00c6\5\26\f\2\u00c2\u00c3\7\b\2" +
					"\2\u00c3\u00c5\5\26\f\2\u00c4\u00c2\3\2\2\2\u00c5\u00c8\3\2\2\2\u00c6" +
					"\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\31\3\2\2\2\u00c8\u00c6\3\2\2" +
					"\2\u00c9\u00cd\7\4\2\2\u00ca\u00cc\5\34\17\2\u00cb\u00ca\3\2\2\2\u00cc" +
					"\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\3\2" +
					"\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d1\7\5\2\2\u00d1\33\3\2\2\2\u00d2\u00dc" +
					"\5\32\16\2\u00d3\u00dc\5\6\4\2\u00d4\u00dc\5\36\20\2\u00d5\u00dc\5 \21" +
					"\2\u00d6\u00dc\5\"\22\2\u00d7\u00d9\5\n\6\2\u00d8\u00d7\3\2\2\2\u00d8" +
					"\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dc\7\7\2\2\u00db\u00d2\3\2" +
					"\2\2\u00db\u00d3\3\2\2\2\u00db\u00d4\3\2\2\2\u00db\u00d5\3\2\2\2\u00db" +
					"\u00d6\3\2\2\2\u00db\u00d8\3\2\2\2\u00dc\35\3\2\2\2\u00dd\u00de\7&\2\2" +
					"\u00de\u00e0\7\t\2\2\u00df\u00e1\5\n\6\2\u00e0\u00df\3\2\2\2\u00e0\u00e1" +
					"\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4\7\7\2\2\u00e3\u00e5\5\n\6\2\u00e4" +
					"\u00e3\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\7\7" +
					"\2\2\u00e7\u00e9\5\n\6\2\u00e8\u00e7\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9" +
					"\u00ea\3\2\2\2\u00ea\u00eb\7\n\2\2\u00eb\u00f4\5\34\17\2\u00ec\u00ed\7" +
					"\'\2\2\u00ed\u00ef\7\t\2\2\u00ee\u00f0\5\n\6\2\u00ef\u00ee\3\2\2\2\u00ef" +
					"\u00f0\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\7\n\2\2\u00f2\u00f4\5\34" +
					"\17\2\u00f3\u00dd\3\2\2\2\u00f3\u00ec\3\2\2\2\u00f4\37\3\2\2\2\u00f5\u00f6" +
					"\7(\2\2\u00f6\u00f7\7\t\2\2\u00f7\u00f8\5\n\6\2\u00f8\u00f9\7\n\2\2\u00f9" +
					"\u00fc\5\34\17\2\u00fa\u00fb\7)\2\2\u00fb\u00fd\5\34\17\2\u00fc\u00fa" +
					"\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd!\3\2\2\2\u00fe\u0100\7*\2\2\u00ff\u0101" +
					"\5\n\6\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0102\3\2\2\2\u0102" +
					"\u0106\7\7\2\2\u0103\u0106\7+\2\2\u0104\u0106\7,\2\2\u0105\u00fe\3\2\2" +
					"\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106#\3\2\2\2\u0107\u0109" +
					"\7-\2\2\u0108\u010a\7%\2\2\u0109\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b" +
					"\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c%\3\2\2\2 )+\63\65>EJN[\u008d" +
					"\u008f\u0097\u009f\u00a9\u00ae\u00b3\u00b9\u00c6\u00cd\u00d8\u00db\u00e0" +
					"\u00e4\u00e8\u00ef\u00f3\u00fc\u0100\u0105\u010b";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
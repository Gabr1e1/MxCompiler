// Generated from MxGrammar.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, NumLiteral=43, StringLiteral=44, 
		BoolLiteral=45, NullLiteral=46, Identifier=47, This=48, Primitive_type=49, 
		Array_type=50, WS=51, Block_comment=52, Line_comment=53;
	public static final int
		RULE_program = 0, RULE_class_definition = 1, RULE_variable_definition = 2, 
		RULE_typename = 3, RULE_expression = 4, RULE_expression_list = 5, RULE_literal = 6, 
		RULE_basic_expression = 7, RULE_unary_expression = 8, RULE_new_expression = 9, 
		RULE_function_definition = 10, RULE_parameter = 11, RULE_parameter_list = 12, 
		RULE_block = 13, RULE_statement = 14, RULE_control_statement = 15, RULE_conditional_statement = 16, 
		RULE_jump_statement = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "class_definition", "variable_definition", "typename", "expression", 
			"expression_list", "literal", "basic_expression", "unary_expression", 
			"new_expression", "function_definition", "parameter", "parameter_list", 
			"block", "statement", "control_statement", "conditional_statement", "jump_statement"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class_definition'", "'{'", "'}'", "'='", "';'", "','", "'.'", 
			"'['", "']'", "'('", "')'", "'~'", "'!'", "'*'", "'/'", "'%'", "'+'", 
			"'-'", "'<<'", "'>>'", "'<'", "'>'", "'>='", "'<='", "'=='", "'!='", 
			"'&'", "'^'", "'|'", "'&&'", "'||'", "'++'", "'--'", "'new'", "'[]'", 
			"'for'", "'while'", "'if'", "'else'", "'return'", "'break;'", "'continue;'", 
			null, null, null, "'null'", null, "'this'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "NumLiteral", "StringLiteral", 
			"BoolLiteral", "NullLiteral", "Identifier", "This", "Primitive_type", 
			"Array_type", "WS", "Block_comment", "Line_comment"
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
		public List<Class_definitionContext> class_definition() {
			return getRuleContexts(Class_definitionContext.class);
		}
		public Class_definitionContext class_definition(int i) {
			return getRuleContext(Class_definitionContext.class,i);
		}
		public List<Function_definitionContext> function_definition() {
			return getRuleContexts(Function_definitionContext.class);
		}
		public Function_definitionContext function_definition(int i) {
			return getRuleContext(Function_definitionContext.class,i);
		}
		public List<Variable_definitionContext> variable_definition() {
			return getRuleContexts(Variable_definitionContext.class);
		}
		public Variable_definitionContext variable_definition(int i) {
			return getRuleContext(Variable_definitionContext.class,i);
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
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << Primitive_type) | (1L << Array_type))) != 0)) {
				{
				setState(39);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(36);
					class_definition();
					}
					break;
				case 2:
					{
					setState(37);
					function_definition();
					}
					break;
				case 3:
					{
					setState(38);
					variable_definition();
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

	public static class Class_definitionContext extends ParserRuleContext {
		public Token name;
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public List<Variable_definitionContext> variable_definition() {
			return getRuleContexts(Variable_definitionContext.class);
		}
		public Variable_definitionContext variable_definition(int i) {
			return getRuleContext(Variable_definitionContext.class,i);
		}
		public List<Function_definitionContext> function_definition() {
			return getRuleContexts(Function_definitionContext.class);
		}
		public Function_definitionContext function_definition(int i) {
			return getRuleContext(Function_definitionContext.class,i);
		}
		public Class_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterClass_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitClass_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitClass_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Class_definitionContext class_definition() throws RecognitionException {
		Class_definitionContext _localctx = new Class_definitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_class_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(T__0);
			setState(45);
			((Class_definitionContext)_localctx).name = match(Identifier);
			setState(46);
			match(T__1);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Primitive_type || _la==Array_type) {
				{
				setState(49);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(47);
					variable_definition();
					}
					break;
				case 2:
					{
					setState(48);
					function_definition();
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

	public static class Variable_definitionContext extends ParserRuleContext {
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
		public Variable_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterVariable_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitVariable_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitVariable_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_definitionContext variable_definition() throws RecognitionException {
		Variable_definitionContext _localctx = new Variable_definitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable_definition);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
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
				if (_la==T__3) {
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
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
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
		public TerminalNode Primitive_type() { return getToken(MxGrammarParser.Primitive_type, 0); }
		public TerminalNode Array_type() { return getToken(MxGrammarParser.Array_type, 0); }
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_la = _input.LA(1);
			if ( !(_la==Primitive_type || _la==Array_type) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
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

	public static class ExpressionContext extends ParserRuleContext {
		public Token op;
		public Basic_expressionContext basic_expression() {
			return getRuleContext(Basic_expressionContext.class,0);
		}
		public New_expressionContext new_expression() {
			return getRuleContext(New_expressionContext.class,0);
		}
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitExpression(this);
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
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(77);
				basic_expression();
				}
				break;
			case 2:
				{
				setState(78);
				new_expression();
				}
				break;
			case 3:
				{
				setState(79);
				unary_expression(0);
				}
				break;
			case 4:
				{
				setState(80);
				match(T__9);
				setState(81);
				expression(0);
				setState(82);
				match(T__10);
				}
				break;
			case 5:
				{
				setState(84);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(85);
				expression(11);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(133);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(131);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(88);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(89);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__13) | (1L << T__14) | (1L << T__15))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(90);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(91);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(92);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__16 || _la==T__17) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(93);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(94);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(95);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__18 || _la==T__19) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(96);
						expression(9);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(97);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(98);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(99);
						expression(8);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(100);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(101);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(102);
						expression(7);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(103);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(104);
						((ExpressionContext)_localctx).op = match(T__26);
						setState(105);
						expression(6);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(106);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(107);
						((ExpressionContext)_localctx).op = match(T__27);
						setState(108);
						expression(5);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(109);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(110);
						((ExpressionContext)_localctx).op = match(T__28);
						setState(111);
						expression(4);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(112);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(113);
						match(T__29);
						setState(114);
						expression(3);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(115);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(116);
						match(T__30);
						setState(117);
						expression(2);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(118);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(119);
						match(T__6);
						setState(120);
						match(Identifier);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(121);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(122);
						match(T__7);
						setState(123);
						expression(0);
						setState(124);
						match(T__8);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(126);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(127);
						match(T__9);
						setState(128);
						expression_list();
						setState(129);
						match(T__10);
						}
						break;
					}
					} 
				}
				setState(135);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

	public static class Expression_listContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterExpression_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitExpression_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitExpression_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression_listContext expression_list() throws RecognitionException {
		Expression_listContext _localctx = new Expression_listContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expression_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			expression(0);
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(137);
				match(T__5);
				setState(138);
				expression(0);
				}
				}
				setState(143);
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
			setState(144);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
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

	public static class Basic_expressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public TerminalNode This() { return getToken(MxGrammarParser.This, 0); }
		public Basic_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basic_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterBasic_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitBasic_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitBasic_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Basic_expressionContext basic_expression() throws RecognitionException {
		Basic_expressionContext _localctx = new Basic_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_basic_expression);
		try {
			setState(149);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NumLiteral:
			case StringLiteral:
			case BoolLiteral:
			case NullLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				literal();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				match(Identifier);
				}
				break;
			case This:
				enterOuterAlt(_localctx, 3);
				{
				setState(148);
				match(This);
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

	public static class Unary_expressionContext extends ParserRuleContext {
		public Token op;
		public Basic_expressionContext basic_expression() {
			return getRuleContext(Basic_expressionContext.class,0);
		}
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterUnary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitUnary_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitUnary_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		return unary_expression(0);
	}

	private Unary_expressionContext unary_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, _parentState);
		Unary_expressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_unary_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NumLiteral:
			case StringLiteral:
			case BoolLiteral:
			case NullLiteral:
			case Identifier:
			case This:
				{
				setState(152);
				basic_expression();
				}
				break;
			case T__16:
			case T__17:
			case T__31:
			case T__32:
				{
				setState(153);
				((Unary_expressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32))) != 0)) ) {
					((Unary_expressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(154);
				unary_expression(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(161);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Unary_expressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_unary_expression);
					setState(157);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(158);
					((Unary_expressionContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__31 || _la==T__32) ) {
						((Unary_expressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					} 
				}
				setState(163);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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

	public static class New_expressionContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public New_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterNew_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitNew_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitNew_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final New_expressionContext new_expression() throws RecognitionException {
		New_expressionContext _localctx = new New_expressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_new_expression);
		try {
			int _alt;
			setState(182);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				match(T__33);
				setState(165);
				typename();
				setState(170); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(166);
						match(T__7);
						setState(167);
						expression(0);
						setState(168);
						match(T__8);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(172); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(177);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(174);
						match(T__34);
						}
						} 
					}
					setState(179);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(180);
				match(T__33);
				setState(181);
				typename();
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

	public static class Function_definitionContext extends ParserRuleContext {
		public TypenameContext return_type;
		public Token function_definition_identifier;
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxGrammarParser.Identifier, 0); }
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterFunction_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitFunction_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitFunction_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_function_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			((Function_definitionContext)_localctx).return_type = typename();
			setState(185);
			((Function_definitionContext)_localctx).function_definition_identifier = match(Identifier);
			setState(186);
			match(T__9);
			setState(187);
			parameter_list();
			setState(188);
			match(T__10);
			setState(189);
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
		enterRule(_localctx, 22, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			typename();
			setState(192);
			match(Identifier);
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

	public static class Parameter_listContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterParameter_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitParameter_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitParameter_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_parameter_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			parameter();
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(195);
				match(T__5);
				setState(196);
				parameter();
				}
				}
				setState(201);
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
		enterRule(_localctx, 26, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(T__1);
			setState(206);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This) | (1L << Primitive_type) | (1L << Array_type))) != 0)) {
				{
				{
				setState(203);
				statement();
				}
				}
				setState(208);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(209);
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
		public Variable_definitionContext variable_definition() {
			return getRuleContext(Variable_definitionContext.class,0);
		}
		public Control_statementContext control_statement() {
			return getRuleContext(Control_statementContext.class,0);
		}
		public Conditional_statementContext conditional_statement() {
			return getRuleContext(Conditional_statementContext.class,0);
		}
		public Jump_statementContext jump_statement() {
			return getRuleContext(Jump_statementContext.class,0);
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
		enterRule(_localctx, 28, RULE_statement);
		int _la;
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				block();
				}
				break;
			case Primitive_type:
			case Array_type:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				variable_definition();
				}
				break;
			case T__35:
			case T__36:
				enterOuterAlt(_localctx, 3);
				{
				setState(213);
				control_statement();
				}
				break;
			case T__37:
				enterOuterAlt(_localctx, 4);
				{
				setState(214);
				conditional_statement();
				}
				break;
			case T__39:
			case T__40:
			case T__41:
				enterOuterAlt(_localctx, 5);
				{
				setState(215);
				jump_statement();
				}
				break;
			case T__4:
			case T__9:
			case T__11:
			case T__12:
			case T__16:
			case T__17:
			case T__31:
			case T__32:
			case T__33:
			case NumLiteral:
			case StringLiteral:
			case BoolLiteral:
			case NullLiteral:
			case Identifier:
			case This:
				enterOuterAlt(_localctx, 6);
				{
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(216);
					expression(0);
					}
				}

				setState(219);
				match(T__4);
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

	public static class Control_statementContext extends ParserRuleContext {
		public ExpressionContext init;
		public ExpressionContext condition;
		public ExpressionContext increment;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Control_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_control_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterControl_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitControl_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitControl_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Control_statementContext control_statement() throws RecognitionException {
		Control_statementContext _localctx = new Control_statementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_control_statement);
		int _la;
		try {
			setState(244);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__35:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				match(T__35);
				setState(223);
				match(T__9);
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(224);
					((Control_statementContext)_localctx).init = expression(0);
					}
				}

				setState(227);
				match(T__4);
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(228);
					((Control_statementContext)_localctx).condition = expression(0);
					}
				}

				setState(231);
				match(T__4);
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(232);
					((Control_statementContext)_localctx).increment = expression(0);
					}
				}

				setState(235);
				match(T__10);
				setState(236);
				statement();
				}
				break;
			case T__36:
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				match(T__36);
				setState(238);
				match(T__9);
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(239);
					((Control_statementContext)_localctx).condition = expression(0);
					}
				}

				setState(242);
				match(T__10);
				setState(243);
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

	public static class Conditional_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Conditional_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterConditional_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitConditional_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitConditional_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Conditional_statementContext conditional_statement() throws RecognitionException {
		Conditional_statementContext _localctx = new Conditional_statementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_conditional_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(T__37);
			setState(247);
			match(T__9);
			setState(248);
			expression(0);
			setState(249);
			match(T__10);
			setState(250);
			statement();
			setState(253);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(251);
				match(T__38);
				setState(252);
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

	public static class Jump_statementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Jump_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).enterJump_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MxGrammarListener ) ((MxGrammarListener)listener).exitJump_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxGrammarVisitor ) return ((MxGrammarVisitor<? extends T>)visitor).visitJump_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Jump_statementContext jump_statement() throws RecognitionException {
		Jump_statementContext _localctx = new Jump_statementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_jump_statement);
		int _la;
		try {
			setState(262);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__39:
				enterOuterAlt(_localctx, 1);
				{
				setState(255);
				match(T__39);
				setState(257);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << T__16) | (1L << T__17) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << NumLiteral) | (1L << StringLiteral) | (1L << BoolLiteral) | (1L << NullLiteral) | (1L << Identifier) | (1L << This))) != 0)) {
					{
					setState(256);
					expression(0);
					}
				}

				setState(259);
				match(T__4);
				}
				break;
			case T__40:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				match(T__40);
				}
				break;
			case T__41:
				enterOuterAlt(_localctx, 3);
				{
				setState(261);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 8:
			return unary_expression_sempred((Unary_expressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 1);
		case 10:
			return precpred(_ctx, 17);
		case 11:
			return precpred(_ctx, 15);
		case 12:
			return precpred(_ctx, 14);
		}
		return true;
	}
	private boolean unary_expression_sempred(Unary_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 13:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\67\u010b\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\7\2*\n\2\f\2\16\2-\13\2\3\3\3\3\3\3\3\3\3\3\7\3"+
		"\64\n\3\f\3\16\3\67\13\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4?\n\4\3\4\3\4\3\4"+
		"\3\4\3\4\5\4F\n\4\3\4\3\4\3\4\5\4K\n\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\5\6Y\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u0086\n"+
		"\6\f\6\16\6\u0089\13\6\3\7\3\7\3\7\7\7\u008e\n\7\f\7\16\7\u0091\13\7\3"+
		"\b\3\b\3\t\3\t\3\t\5\t\u0098\n\t\3\n\3\n\3\n\3\n\5\n\u009e\n\n\3\n\3\n"+
		"\7\n\u00a2\n\n\f\n\16\n\u00a5\13\n\3\13\3\13\3\13\3\13\3\13\3\13\6\13"+
		"\u00ad\n\13\r\13\16\13\u00ae\3\13\7\13\u00b2\n\13\f\13\16\13\u00b5\13"+
		"\13\3\13\3\13\5\13\u00b9\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\7\16\u00c8\n\16\f\16\16\16\u00cb\13\16\3\17\3\17\7\17"+
		"\u00cf\n\17\f\17\16\17\u00d2\13\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00dc\n\20\3\20\5\20\u00df\n\20\3\21\3\21\3\21\5\21\u00e4\n"+
		"\21\3\21\3\21\5\21\u00e8\n\21\3\21\3\21\5\21\u00ec\n\21\3\21\3\21\3\21"+
		"\3\21\3\21\5\21\u00f3\n\21\3\21\3\21\5\21\u00f7\n\21\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\5\22\u0100\n\22\3\23\3\23\5\23\u0104\n\23\3\23\3\23"+
		"\3\23\5\23\u0109\n\23\3\23\2\4\n\22\24\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$\2\f\3\2\63\64\3\2\16\17\3\2\20\22\3\2\23\24\3\2\25\26\3\2\27"+
		"\32\3\2\33\34\3\2-\60\4\2\23\24\"#\3\2\"#\2\u012a\2+\3\2\2\2\4.\3\2\2"+
		"\2\6J\3\2\2\2\bL\3\2\2\2\nX\3\2\2\2\f\u008a\3\2\2\2\16\u0092\3\2\2\2\20"+
		"\u0097\3\2\2\2\22\u009d\3\2\2\2\24\u00b8\3\2\2\2\26\u00ba\3\2\2\2\30\u00c1"+
		"\3\2\2\2\32\u00c4\3\2\2\2\34\u00cc\3\2\2\2\36\u00de\3\2\2\2 \u00f6\3\2"+
		"\2\2\"\u00f8\3\2\2\2$\u0108\3\2\2\2&*\5\4\3\2\'*\5\26\f\2(*\5\6\4\2)&"+
		"\3\2\2\2)\'\3\2\2\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2\2\2,\3\3\2\2\2"+
		"-+\3\2\2\2./\7\3\2\2/\60\7\61\2\2\60\65\7\4\2\2\61\64\5\6\4\2\62\64\5"+
		"\26\f\2\63\61\3\2\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66"+
		"\3\2\2\2\668\3\2\2\2\67\65\3\2\2\289\7\5\2\29\5\3\2\2\2:;\5\b\5\2;>\7"+
		"\61\2\2<=\7\6\2\2=?\5\n\6\2><\3\2\2\2>?\3\2\2\2?@\3\2\2\2@A\7\7\2\2AK"+
		"\3\2\2\2BE\5\b\5\2CD\7\61\2\2DF\7\b\2\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2"+
		"GH\7\61\2\2HI\7\7\2\2IK\3\2\2\2J:\3\2\2\2JB\3\2\2\2K\7\3\2\2\2LM\t\2\2"+
		"\2M\t\3\2\2\2NO\b\6\1\2OY\5\20\t\2PY\5\24\13\2QY\5\22\n\2RS\7\f\2\2ST"+
		"\5\n\6\2TU\7\r\2\2UY\3\2\2\2VW\t\3\2\2WY\5\n\6\rXN\3\2\2\2XP\3\2\2\2X"+
		"Q\3\2\2\2XR\3\2\2\2XV\3\2\2\2Y\u0087\3\2\2\2Z[\f\f\2\2[\\\t\4\2\2\\\u0086"+
		"\5\n\6\r]^\f\13\2\2^_\t\5\2\2_\u0086\5\n\6\f`a\f\n\2\2ab\t\6\2\2b\u0086"+
		"\5\n\6\13cd\f\t\2\2de\t\7\2\2e\u0086\5\n\6\nfg\f\b\2\2gh\t\b\2\2h\u0086"+
		"\5\n\6\tij\f\7\2\2jk\7\35\2\2k\u0086\5\n\6\blm\f\6\2\2mn\7\36\2\2n\u0086"+
		"\5\n\6\7op\f\5\2\2pq\7\37\2\2q\u0086\5\n\6\6rs\f\4\2\2st\7 \2\2t\u0086"+
		"\5\n\6\5uv\f\3\2\2vw\7!\2\2w\u0086\5\n\6\4xy\f\23\2\2yz\7\t\2\2z\u0086"+
		"\7\61\2\2{|\f\21\2\2|}\7\n\2\2}~\5\n\6\2~\177\7\13\2\2\177\u0086\3\2\2"+
		"\2\u0080\u0081\f\20\2\2\u0081\u0082\7\f\2\2\u0082\u0083\5\f\7\2\u0083"+
		"\u0084\7\r\2\2\u0084\u0086\3\2\2\2\u0085Z\3\2\2\2\u0085]\3\2\2\2\u0085"+
		"`\3\2\2\2\u0085c\3\2\2\2\u0085f\3\2\2\2\u0085i\3\2\2\2\u0085l\3\2\2\2"+
		"\u0085o\3\2\2\2\u0085r\3\2\2\2\u0085u\3\2\2\2\u0085x\3\2\2\2\u0085{\3"+
		"\2\2\2\u0085\u0080\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\13\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008f\5\n\6"+
		"\2\u008b\u008c\7\b\2\2\u008c\u008e\5\n\6\2\u008d\u008b\3\2\2\2\u008e\u0091"+
		"\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\r\3\2\2\2\u0091"+
		"\u008f\3\2\2\2\u0092\u0093\t\t\2\2\u0093\17\3\2\2\2\u0094\u0098\5\16\b"+
		"\2\u0095\u0098\7\61\2\2\u0096\u0098\7\62\2\2\u0097\u0094\3\2\2\2\u0097"+
		"\u0095\3\2\2\2\u0097\u0096\3\2\2\2\u0098\21\3\2\2\2\u0099\u009a\b\n\1"+
		"\2\u009a\u009e\5\20\t\2\u009b\u009c\t\n\2\2\u009c\u009e\5\22\n\3\u009d"+
		"\u0099\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a3\3\2\2\2\u009f\u00a0\f\4"+
		"\2\2\u00a0\u00a2\t\13\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3"+
		"\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\23\3\2\2\2\u00a5\u00a3\3\2\2"+
		"\2\u00a6\u00a7\7$\2\2\u00a7\u00ac\5\b\5\2\u00a8\u00a9\7\n\2\2\u00a9\u00aa"+
		"\5\n\6\2\u00aa\u00ab\7\13\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00a8\3\2\2\2"+
		"\u00ad\u00ae\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b3"+
		"\3\2\2\2\u00b0\u00b2\7%\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3"+
		"\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b9\3\2\2\2\u00b5\u00b3\3\2"+
		"\2\2\u00b6\u00b7\7$\2\2\u00b7\u00b9\5\b\5\2\u00b8\u00a6\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b9\25\3\2\2\2\u00ba\u00bb\5\b\5\2\u00bb\u00bc\7\61\2"+
		"\2\u00bc\u00bd\7\f\2\2\u00bd\u00be\5\32\16\2\u00be\u00bf\7\r\2\2\u00bf"+
		"\u00c0\5\34\17\2\u00c0\27\3\2\2\2\u00c1\u00c2\5\b\5\2\u00c2\u00c3\7\61"+
		"\2\2\u00c3\31\3\2\2\2\u00c4\u00c9\5\30\r\2\u00c5\u00c6\7\b\2\2\u00c6\u00c8"+
		"\5\30\r\2\u00c7\u00c5\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2"+
		"\u00c9\u00ca\3\2\2\2\u00ca\33\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc\u00d0"+
		"\7\4\2\2\u00cd\u00cf\5\36\20\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2\2\2"+
		"\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2\u00d0"+
		"\3\2\2\2\u00d3\u00d4\7\5\2\2\u00d4\35\3\2\2\2\u00d5\u00df\5\34\17\2\u00d6"+
		"\u00df\5\6\4\2\u00d7\u00df\5 \21\2\u00d8\u00df\5\"\22\2\u00d9\u00df\5"+
		"$\23\2\u00da\u00dc\5\n\6\2\u00db\u00da\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00df\7\7\2\2\u00de\u00d5\3\2\2\2\u00de\u00d6\3\2"+
		"\2\2\u00de\u00d7\3\2\2\2\u00de\u00d8\3\2\2\2\u00de\u00d9\3\2\2\2\u00de"+
		"\u00db\3\2\2\2\u00df\37\3\2\2\2\u00e0\u00e1\7&\2\2\u00e1\u00e3\7\f\2\2"+
		"\u00e2\u00e4\5\n\6\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5"+
		"\3\2\2\2\u00e5\u00e7\7\7\2\2\u00e6\u00e8\5\n\6\2\u00e7\u00e6\3\2\2\2\u00e7"+
		"\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00eb\7\7\2\2\u00ea\u00ec\5\n"+
		"\6\2\u00eb\u00ea\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed"+
		"\u00ee\7\r\2\2\u00ee\u00f7\5\36\20\2\u00ef\u00f0\7\'\2\2\u00f0\u00f2\7"+
		"\f\2\2\u00f1\u00f3\5\n\6\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\u00f5\7\r\2\2\u00f5\u00f7\5\36\20\2\u00f6\u00e0\3"+
		"\2\2\2\u00f6\u00ef\3\2\2\2\u00f7!\3\2\2\2\u00f8\u00f9\7(\2\2\u00f9\u00fa"+
		"\7\f\2\2\u00fa\u00fb\5\n\6\2\u00fb\u00fc\7\r\2\2\u00fc\u00ff\5\36\20\2"+
		"\u00fd\u00fe\7)\2\2\u00fe\u0100\5\36\20\2\u00ff\u00fd\3\2\2\2\u00ff\u0100"+
		"\3\2\2\2\u0100#\3\2\2\2\u0101\u0103\7*\2\2\u0102\u0104\5\n\6\2\u0103\u0102"+
		"\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0109\7\7\2\2\u0106"+
		"\u0109\7+\2\2\u0107\u0109\7,\2\2\u0108\u0101\3\2\2\2\u0108\u0106\3\2\2"+
		"\2\u0108\u0107\3\2\2\2\u0109%\3\2\2\2\37)+\63\65>EJX\u0085\u0087\u008f"+
		"\u0097\u009d\u00a3\u00ae\u00b3\u00b8\u00c9\u00d0\u00db\u00de\u00e3\u00e7"+
		"\u00eb\u00f2\u00f6\u00ff\u0103\u0108";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
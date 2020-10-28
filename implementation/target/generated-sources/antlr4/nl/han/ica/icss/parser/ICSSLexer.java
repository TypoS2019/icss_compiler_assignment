// Generated from nl\han\ica\icss\parser\ICSS.g4 by ANTLR 4.8
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ICSSLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, BOX_BRACKET_OPEN=3, BOX_BRACKET_CLOSE=4, TRUE=5, FALSE=6, 
		PIXELSIZE=7, PERCENTAGE=8, SCALAR=9, COLOR=10, ID_IDENT=11, CLASS_IDENT=12, 
		LOWER_IDENT=13, CAPITAL_IDENT=14, WS=15, OPEN_BRACE=16, CLOSE_BRACE=17, 
		SEMICOLON=18, COLON=19, PLUS=20, MIN=21, MUL=22, EQUALS=23, SMALLER=24, 
		BIGGER=25, NOT=26, ASSIGNMENT_OPERATOR=27;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "TRUE", "FALSE", 
			"PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", "ID_IDENT", "CLASS_IDENT", 
			"LOWER_IDENT", "CAPITAL_IDENT", "WS", "OPEN_BRACE", "CLOSE_BRACE", "SEMICOLON", 
			"COLON", "PLUS", "MIN", "MUL", "EQUALS", "SMALLER", "BIGGER", "NOT", 
			"ASSIGNMENT_OPERATOR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'['", "']'", "'TRUE'", "'FALSE'", null, null, 
			null, null, null, null, null, null, null, "'{'", "'}'", "';'", "':'", 
			"'+'", "'-'", "'*'", "'=='", "'<'", "'>'", "'!'", "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "BOX_BRACKET_OPEN", "BOX_BRACKET_CLOSE", "TRUE", 
			"FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", "COLOR", "ID_IDENT", "CLASS_IDENT", 
			"LOWER_IDENT", "CAPITAL_IDENT", "WS", "OPEN_BRACE", "CLOSE_BRACE", "SEMICOLON", 
			"COLON", "PLUS", "MIN", "MUL", "EQUALS", "SMALLER", "BIGGER", "NOT", 
			"ASSIGNMENT_OPERATOR"
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


	public ICSSLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ICSS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u00a7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\6\bR\n"+
		"\b\r\b\16\bS\3\b\3\b\3\b\3\t\6\tZ\n\t\r\t\16\t[\3\t\3\t\3\n\6\na\n\n\r"+
		"\n\16\nb\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\6\fo\n\f\r\f"+
		"\16\fp\3\r\3\r\6\ru\n\r\r\r\16\rv\3\16\3\16\7\16{\n\16\f\16\16\16~\13"+
		"\16\3\17\3\17\7\17\u0082\n\17\f\17\16\17\u0085\13\17\3\20\6\20\u0088\n"+
		"\20\r\20\16\20\u0089\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\34\2\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\35\3\2\t\3\2\62;\4\2\62;ch\5\2//\62;c|\3\2c|\3\2C\\\6"+
		"\2\62;C\\aac|\5\2\13\f\17\17\"\"\2\u00ae\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5<\3\2\2\2\7A\3\2\2\2\tC\3\2\2\2\13"+
		"E\3\2\2\2\rJ\3\2\2\2\17Q\3\2\2\2\21Y\3\2\2\2\23`\3\2\2\2\25d\3\2\2\2\27"+
		"l\3\2\2\2\31r\3\2\2\2\33x\3\2\2\2\35\177\3\2\2\2\37\u0087\3\2\2\2!\u008d"+
		"\3\2\2\2#\u008f\3\2\2\2%\u0091\3\2\2\2\'\u0093\3\2\2\2)\u0095\3\2\2\2"+
		"+\u0097\3\2\2\2-\u0099\3\2\2\2/\u009b\3\2\2\2\61\u009e\3\2\2\2\63\u00a0"+
		"\3\2\2\2\65\u00a2\3\2\2\2\67\u00a4\3\2\2\29:\7k\2\2:;\7h\2\2;\4\3\2\2"+
		"\2<=\7g\2\2=>\7n\2\2>?\7u\2\2?@\7g\2\2@\6\3\2\2\2AB\7]\2\2B\b\3\2\2\2"+
		"CD\7_\2\2D\n\3\2\2\2EF\7V\2\2FG\7T\2\2GH\7W\2\2HI\7G\2\2I\f\3\2\2\2JK"+
		"\7H\2\2KL\7C\2\2LM\7N\2\2MN\7U\2\2NO\7G\2\2O\16\3\2\2\2PR\t\2\2\2QP\3"+
		"\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\7r\2\2VW\7z\2\2W\20\3"+
		"\2\2\2XZ\t\2\2\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\]\3\2\2\2]"+
		"^\7\'\2\2^\22\3\2\2\2_a\t\2\2\2`_\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2"+
		"\2c\24\3\2\2\2de\7%\2\2ef\t\3\2\2fg\t\3\2\2gh\t\3\2\2hi\t\3\2\2ij\t\3"+
		"\2\2jk\t\3\2\2k\26\3\2\2\2ln\7%\2\2mo\t\4\2\2nm\3\2\2\2op\3\2\2\2pn\3"+
		"\2\2\2pq\3\2\2\2q\30\3\2\2\2rt\7\60\2\2su\t\4\2\2ts\3\2\2\2uv\3\2\2\2"+
		"vt\3\2\2\2vw\3\2\2\2w\32\3\2\2\2x|\t\5\2\2y{\t\4\2\2zy\3\2\2\2{~\3\2\2"+
		"\2|z\3\2\2\2|}\3\2\2\2}\34\3\2\2\2~|\3\2\2\2\177\u0083\t\6\2\2\u0080\u0082"+
		"\t\7\2\2\u0081\u0080\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084\36\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0088\t\b\2"+
		"\2\u0087\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a"+
		"\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\b\20\2\2\u008c \3\2\2\2\u008d"+
		"\u008e\7}\2\2\u008e\"\3\2\2\2\u008f\u0090\7\177\2\2\u0090$\3\2\2\2\u0091"+
		"\u0092\7=\2\2\u0092&\3\2\2\2\u0093\u0094\7<\2\2\u0094(\3\2\2\2\u0095\u0096"+
		"\7-\2\2\u0096*\3\2\2\2\u0097\u0098\7/\2\2\u0098,\3\2\2\2\u0099\u009a\7"+
		",\2\2\u009a.\3\2\2\2\u009b\u009c\7?\2\2\u009c\u009d\7?\2\2\u009d\60\3"+
		"\2\2\2\u009e\u009f\7>\2\2\u009f\62\3\2\2\2\u00a0\u00a1\7@\2\2\u00a1\64"+
		"\3\2\2\2\u00a2\u00a3\7#\2\2\u00a3\66\3\2\2\2\u00a4\u00a5\7<\2\2\u00a5"+
		"\u00a6\7?\2\2\u00a68\3\2\2\2\13\2S[bpv|\u0083\u0089\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
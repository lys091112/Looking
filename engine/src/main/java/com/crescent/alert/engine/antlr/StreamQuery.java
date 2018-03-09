// Generated from /Users/langle/xianyue/workspace/javawork/Looking/engine/src/main/antlr/StreamQuery.g4 by ANTLR 4.7
package com.crescent.alert.engine.antlr;

import java.util.List;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class StreamQuery extends Parser {

    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    public static final int
        SELECT = 1, DELETE = 2, INSERT = 3, UPDATE = 4, FROM = 5, HAVING = 6, WHERE = 7, ORDER = 8,
        BY = 9, GROUP = 10, INTO = 11, AS = 12, SOUNDS = 13, REGEXP = 14, FILTER = 15, DURING = 16,
        CREATE = 17, ALTER = 18, DROP = 19, SET = 20, NULL = 21, NOT = 22, DISTINCT = 23, DISTINCTROW = 24,
        HIGH_PRIORITY = 25, MAX_STATEMENT_TIME = 26, TABLE = 27, TABLESPACE = 28, VIEW = 29,
        SEQUENCE = 30, TRIGGER = 31, USER = 32, INDEX = 33, SESSION = 34, PROCEDURE = 35,
        FUNCTION = 36, PRIMARY = 37, KEY = 38, DEFAULT = 39, CONSTRAINT = 40, CHECK = 41,
        UNIQUE = 42, FOREIGN = 43, REFERENCES = 44, EXPLAIN = 45, FOR = 46, IF = 47, ALL = 48,
        UNION = 49, EXCEPT = 50, INTERSECT = 51, MINUS = 52, INNER = 53, LEFT = 54, RIGHT = 55,
        FULL = 56, OUTER = 57, JOIN = 58, ON = 59, SCHEMA = 60, CAST = 61, COLUMN = 62, USE = 63,
        DATABASE = 64, TO = 65, AND = 66, OR = 67, XOR = 68, CASE = 69, WHEN = 70, THEN = 71,
        ELSE = 72, END = 73, EXISTS = 74, IN = 75, NEW = 76, ASC = 77, DESC = 78, IS = 79, LIKE = 80,
        ESCAPE = 81, BETWEEN = 82, VALUES = 83, INTERVAL = 84, TRUE = 85, FALSE = 86, LIMIT = 87,
        BREAK = 88, LAST = 89, NONE = 90, MINUTE = 91, HOUR = 92, DAY = 93, WEEK = 94, EVENTS = 95,
        FIFTEEN_MINUTE = 96, NOW = 97, LPAREN = 98, RPAREN = 99, LBRACE = 100, RBRACE = 101,
        LBRACKET = 102, RBRACKET = 103, SEMI = 104, COMMA = 105, DOT = 106, DOTDOT = 107,
        DOTDOTDOT = 108, EQ = 109, GT = 110, LT = 111, BANG = 112, BANGBANG = 113, TILDE = 114,
        QUES = 115, COLON = 116, COLONCOLON = 117, COLONEQ = 118, EQEQ = 119, LTEQ = 120,
        LTEQGT = 121, LTGT = 122, GTEQ = 123, NEQ = 124, BANGEQ = 125, BANGGT = 126, BANGLT = 127,
        AMPAMP = 128, BARBAR = 129, BARBARSLASH = 130, BARSLASH = 131, PLUS = 132, SUB = 133,
        STAR = 134, SLASH = 135, AMP = 136, BAR = 137, CARET = 138, PERCENT = 139, LTLT = 140,
        GTGT = 141, MONKEYS_AT = 142, POUND = 143, DIV = 144, MOD = 145, UNDERLINE = 146,
        QUOTES = 147, INT = 148, FLOAT = 149, NEG_INT = 150, NEG_FLOAT = 151, STRING = 152,
        ID = 153, PARENTHESIS = 154, WS = 155;
    public static final int
        RULE_prog = 0, RULE_columnList = 1, RULE_nameOperand = 2, RULE_name = 3,
        RULE_parenthesis = 4, RULE_identity = 5, RULE_tableRef = 6, RULE_whereCluster = 7,
        RULE_boolExpr = 8, RULE_basicBoolExpr = 9, RULE_collection = 10, RULE_durationExpr = 11,
        RULE_filterByExpr = 12;
    public static final String[] ruleNames = {
        "prog", "columnList", "nameOperand", "name", "parenthesis", "identity",
        "tableRef", "whereCluster", "boolExpr", "basicBoolExpr", "collection",
        "durationExpr", "filterByExpr"
    };

    private static final String[] _LITERAL_NAMES = {
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "','", "'.'",
        "'..'", "'..,'", "'='", "'>'", "'<'", "'!'", "'!!'", "'~'", "'?'", null,
        null, "':='", "'=='", "'<='", "'<=>'", null, "'>='", "'!='", null, "'!>'",
        "'!<'", "'&&'", "'||'", "'||/'", "'|/'", "'+'", "'-'", "'*'", "'/'", "'&'",
        "'|'", "'^'", null, "'<<'", "'>>'", "'@'", "'#'", null, null, "'_'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, "SELECT", "DELETE", "INSERT", "UPDATE", "FROM", "HAVING", "WHERE",
        "ORDER", "BY", "GROUP", "INTO", "AS", "SOUNDS", "REGEXP", "FILTER", "DURING",
        "CREATE", "ALTER", "DROP", "SET", "NULL", "NOT", "DISTINCT", "DISTINCTROW",
        "HIGH_PRIORITY", "MAX_STATEMENT_TIME", "TABLE", "TABLESPACE", "VIEW",
        "SEQUENCE", "TRIGGER", "USER", "INDEX", "SESSION", "PROCEDURE", "FUNCTION",
        "PRIMARY", "KEY", "DEFAULT", "CONSTRAINT", "CHECK", "UNIQUE", "FOREIGN",
        "REFERENCES", "EXPLAIN", "FOR", "IF", "ALL", "UNION", "EXCEPT", "INTERSECT",
        "MINUS", "INNER", "LEFT", "RIGHT", "FULL", "OUTER", "JOIN", "ON", "SCHEMA",
        "CAST", "COLUMN", "USE", "DATABASE", "TO", "AND", "OR", "XOR", "CASE",
        "WHEN", "THEN", "ELSE", "END", "EXISTS", "IN", "NEW", "ASC", "DESC", "IS",
        "LIKE", "ESCAPE", "BETWEEN", "VALUES", "INTERVAL", "TRUE", "FALSE", "LIMIT",
        "BREAK", "LAST", "NONE", "MINUTE", "HOUR", "DAY", "WEEK", "EVENTS", "FIFTEEN_MINUTE",
        "NOW", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET",
        "SEMI", "COMMA", "DOT", "DOTDOT", "DOTDOTDOT", "EQ", "GT", "LT", "BANG",
        "BANGBANG", "TILDE", "QUES", "COLON", "COLONCOLON", "COLONEQ", "EQEQ",
        "LTEQ", "LTEQGT", "LTGT", "GTEQ", "NEQ", "BANGEQ", "BANGGT", "BANGLT",
        "AMPAMP", "BARBAR", "BARBARSLASH", "BARSLASH", "PLUS", "SUB", "STAR",
        "SLASH", "AMP", "BAR", "CARET", "PERCENT", "LTLT", "GTGT", "MONKEYS_AT",
        "POUND", "DIV", "MOD", "UNDERLINE", "QUOTES", "INT", "FLOAT", "NEG_INT",
        "NEG_FLOAT", "STRING", "ID", "PARENTHESIS", "WS"
    };
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
    public String getGrammarFileName() {
        return "StreamQuery.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public StreamQuery(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ProgContext extends ParserRuleContext {

        public TerminalNode SELECT() {
            return getToken(StreamQuery.SELECT, 0);
        }

        public ColumnListContext columnList() {
            return getRuleContext(ColumnListContext.class, 0);
        }

        public TerminalNode FROM() {
            return getToken(StreamQuery.FROM, 0);
        }

        public TableRefContext tableRef() {
            return getRuleContext(TableRefContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(StreamQuery.EOF, 0);
        }

        public WhereClusterContext whereCluster() {
            return getRuleContext(WhereClusterContext.class, 0);
        }

        public ProgContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prog;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitProg(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final ProgContext prog() throws RecognitionException {
        ProgContext _localctx = new ProgContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_prog);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(26);
                match(SELECT);
                setState(27);
                columnList();
                setState(28);
                match(FROM);
                setState(29);
                tableRef();
                setState(31);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == WHERE) {
                    {
                        setState(30);
                        whereCluster();
                    }
                }

                setState(33);
                match(EOF);
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

    public static class ColumnListContext extends ParserRuleContext {

        public List<NameOperandContext> nameOperand() {
            return getRuleContexts(NameOperandContext.class);
        }

        public NameOperandContext nameOperand(int i) {
            return getRuleContext(NameOperandContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(StreamQuery.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(StreamQuery.COMMA, i);
        }

        public ColumnListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_columnList;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitColumnList(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final ColumnListContext columnList() throws RecognitionException {
        ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_columnList);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(35);
                nameOperand();
                setState(40);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == COMMA) {
                    {
                        {
                            setState(36);
                            match(COMMA);
                            setState(37);
                            nameOperand();
                        }
                    }
                    setState(42);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
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

    public static class NameOperandContext extends ParserRuleContext {

        public Token tableName;
        public NameContext columnName;
        public Token alias;

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode DOT() {
            return getToken(StreamQuery.DOT, 0);
        }

        public TerminalNode AS() {
            return getToken(StreamQuery.AS, 0);
        }

        public List<TerminalNode> ID() {
            return getTokens(StreamQuery.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(StreamQuery.ID, i);
        }

        public NameOperandContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nameOperand;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitNameOperand(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final NameOperandContext nameOperand() throws RecognitionException {
        NameOperandContext _localctx = new NameOperandContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_nameOperand);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(45);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                    case 1: {
                        setState(43);
                        ((NameOperandContext) _localctx).tableName = match(ID);
                        setState(44);
                        match(DOT);
                    }
                    break;
                }
                setState(47);
                ((NameOperandContext) _localctx).columnName = name(0);
                setState(50);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == AS) {
                    {
                        setState(48);
                        match(AS);
                        setState(49);
                        ((NameOperandContext) _localctx).alias = match(ID);
                    }
                }

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

    public static class NameContext extends ParserRuleContext {

        public NameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_name;
        }

        public NameContext() {
        }

        public void copyFrom(NameContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class MulNameContext extends NameContext {

        public NameContext left;
        public Token op;
        public NameContext right;

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public TerminalNode STAR() {
            return getToken(StreamQuery.STAR, 0);
        }

        public TerminalNode SLASH() {
            return getToken(StreamQuery.SLASH, 0);
        }

        public TerminalNode MOD() {
            return getToken(StreamQuery.MOD, 0);
        }

        public MulNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitMulName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class AggregationNameContext extends NameContext {

        public NameContext columnName;
        public BoolExprContext predicate;

        public TerminalNode ID() {
            return getToken(StreamQuery.ID, 0);
        }

        public TerminalNode LPAREN() {
            return getToken(StreamQuery.LPAREN, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(StreamQuery.RPAREN, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode COMMA() {
            return getToken(StreamQuery.COMMA, 0);
        }

        public BoolExprContext boolExpr() {
            return getRuleContext(BoolExprContext.class, 0);
        }

        public AggregationNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitAggregationName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class AddNameContext extends NameContext {

        public NameContext left;
        public Token op;
        public NameContext right;

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public TerminalNode PLUS() {
            return getToken(StreamQuery.PLUS, 0);
        }

        public TerminalNode SUB() {
            return getToken(StreamQuery.SUB, 0);
        }

        public AddNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitAddName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class ParenthesisNameContext extends NameContext {

        public ParenthesisContext parenthesis() {
            return getRuleContext(ParenthesisContext.class, 0);
        }

        public ParenthesisNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitParenthesisName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class LRNameContext extends NameContext {

        public TerminalNode LPAREN() {
            return getToken(StreamQuery.LPAREN, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(StreamQuery.RPAREN, 0);
        }

        public LRNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitLRName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class ColumnNameContext extends NameContext {

        public IdentityContext identity() {
            return getRuleContext(IdentityContext.class, 0);
        }

        public ColumnNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitColumnName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class BitwiseNameContext extends NameContext {

        public NameContext left;
        public Token op;
        public NameContext right;

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public TerminalNode AMP() {
            return getToken(StreamQuery.AMP, 0);
        }

        public TerminalNode BAR() {
            return getToken(StreamQuery.BAR, 0);
        }

        public TerminalNode CARET() {
            return getToken(StreamQuery.CARET, 0);
        }

        public TerminalNode LTLT() {
            return getToken(StreamQuery.LTLT, 0);
        }

        public TerminalNode GTGT() {
            return getToken(StreamQuery.GTGT, 0);
        }

        public BitwiseNameContext(NameContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitBitwiseName(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final NameContext name() throws RecognitionException {
        return name(0);
    }

    private NameContext name(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        NameContext _localctx = new NameContext(_ctx, _parentState);
        NameContext _prevctx = _localctx;
        int _startState = 6;
        enterRecursionRule(_localctx, 6, RULE_name, _p);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(76);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
                    case 1: {
                        _localctx = new LRNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(53);
                        match(LPAREN);
                        setState(54);
                        name(0);
                        setState(55);
                        match(RPAREN);
                    }
                    break;
                    case 2: {
                        _localctx = new AggregationNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(57);
                        match(ID);
                        setState(58);
                        match(LPAREN);
                        setState(59);
                        ((AggregationNameContext) _localctx).columnName = name(0);
                        setState(60);
                        match(RPAREN);
                    }
                    break;
                    case 3: {
                        _localctx = new AggregationNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(62);
                        match(ID);
                        setState(63);
                        match(LPAREN);
                        setState(64);
                        ((AggregationNameContext) _localctx).columnName = name(0);
                        setState(65);
                        match(COMMA);
                        setState(66);
                        ((AggregationNameContext) _localctx).predicate = boolExpr(0);
                        setState(67);
                        match(RPAREN);
                    }
                    break;
                    case 4: {
                        _localctx = new AggregationNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(69);
                        match(ID);
                        setState(70);
                        match(LPAREN);
                        setState(71);
                        ((AggregationNameContext) _localctx).predicate = boolExpr(0);
                        setState(72);
                        match(RPAREN);
                    }
                    break;
                    case 5: {
                        _localctx = new ColumnNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(74);
                        identity();
                    }
                    break;
                    case 6: {
                        _localctx = new ParenthesisNameContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(75);
                        parenthesis();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(89);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) {
                            triggerExitRuleEvent();
                        }
                        _prevctx = _localctx;
                        {
                            setState(87);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
                                case 1: {
                                    _localctx = new MulNameContext(new NameContext(_parentctx, _parentState));
                                    ((MulNameContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_name);
                                    setState(78);
                                    if (!(precpred(_ctx, 8))) {
                                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                                    }
                                    setState(79);
                                    ((MulNameContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(((((_la - 134)) & ~0x3f) == 0 &&
                                        ((1L << (_la - 134)) & ((1L << (STAR - 134)) | (1L << (SLASH - 134)) | (1L << (
                                            MOD - 134)))) != 0))) {
                                        ((MulNameContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) {
                                            matchedEOF = true;
                                        }
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(80);
                                    ((MulNameContext) _localctx).right = name(9);
                                }
                                break;
                                case 2: {
                                    _localctx = new AddNameContext(new NameContext(_parentctx, _parentState));
                                    ((AddNameContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_name);
                                    setState(81);
                                    if (!(precpred(_ctx, 7))) {
                                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                                    }
                                    setState(82);
                                    ((AddNameContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == PLUS || _la == SUB)) {
                                        ((AddNameContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) {
                                            matchedEOF = true;
                                        }
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(83);
                                    ((AddNameContext) _localctx).right = name(8);
                                }
                                break;
                                case 3: {
                                    _localctx = new BitwiseNameContext(new NameContext(_parentctx, _parentState));
                                    ((BitwiseNameContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_name);
                                    setState(84);
                                    if (!(precpred(_ctx, 6))) {
                                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                                    }
                                    setState(85);
                                    ((BitwiseNameContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(((((_la - 136)) & ~0x3f) == 0 &&
                                        ((1L << (_la - 136)) & ((1L << (AMP - 136)) | (1L << (BAR - 136)) | (1L << (
                                            CARET - 136)) | (1L << (LTLT - 136)) | (1L << (GTGT - 136)))) != 0))) {
                                        ((BitwiseNameContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) {
                                            matchedEOF = true;
                                        }
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(86);
                                    ((BitwiseNameContext) _localctx).right = name(7);
                                }
                                break;
                            }
                        }
                    }
                    setState(91);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public static class ParenthesisContext extends ParserRuleContext {

        public TerminalNode PARENTHESIS() {
            return getToken(StreamQuery.PARENTHESIS, 0);
        }

        public ParenthesisContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_parenthesis;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitParenthesis(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final ParenthesisContext parenthesis() throws RecognitionException {
        ParenthesisContext _localctx = new ParenthesisContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_parenthesis);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(92);
                match(PARENTHESIS);
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

    public static class IdentityContext extends ParserRuleContext {

        public IdentityContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_identity;
        }

        public IdentityContext() {
        }

        public void copyFrom(IdentityContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class IdEleContext extends IdentityContext {

        public TerminalNode ID() {
            return getToken(StreamQuery.ID, 0);
        }

        public IdEleContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitIdEle(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class NegativeFloatELeContext extends IdentityContext {

        public TerminalNode NEG_FLOAT() {
            return getToken(StreamQuery.NEG_FLOAT, 0);
        }

        public NegativeFloatELeContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitNegativeFloatELe(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class NegativeIntEleContext extends IdentityContext {

        public TerminalNode NEG_INT() {
            return getToken(StreamQuery.NEG_INT, 0);
        }

        public NegativeIntEleContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitNegativeIntEle(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class StringEleContext extends IdentityContext {

        public TerminalNode STRING() {
            return getToken(StreamQuery.STRING, 0);
        }

        public StringEleContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitStringEle(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class FloatEleContext extends IdentityContext {

        public TerminalNode FLOAT() {
            return getToken(StreamQuery.FLOAT, 0);
        }

        public FloatEleContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitFloatEle(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class IntEleContext extends IdentityContext {

        public TerminalNode INT() {
            return getToken(StreamQuery.INT, 0);
        }

        public IntEleContext(IdentityContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitIntEle(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final IdentityContext identity() throws RecognitionException {
        IdentityContext _localctx = new IdentityContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_identity);
        try {
            setState(100);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    _localctx = new IdEleContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(94);
                    match(ID);
                }
                break;
                case INT:
                    _localctx = new IntEleContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(95);
                    match(INT);
                }
                break;
                case FLOAT:
                    _localctx = new FloatEleContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(96);
                    match(FLOAT);
                }
                break;
                case NEG_INT:
                    _localctx = new NegativeIntEleContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(97);
                    match(NEG_INT);
                }
                break;
                case NEG_FLOAT:
                    _localctx = new NegativeFloatELeContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(98);
                    match(NEG_FLOAT);
                }
                break;
                case STRING:
                    _localctx = new StringEleContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                {
                    setState(99);
                    match(STRING);
                }
                break;
                default:
                    throw new NoViableAltException(this);
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

    public static class TableRefContext extends ParserRuleContext {

        public Token tableName;
        public Token alias;

        public List<TerminalNode> ID() {
            return getTokens(StreamQuery.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(StreamQuery.ID, i);
        }

        public TerminalNode AS() {
            return getToken(StreamQuery.AS, 0);
        }

        public TableRefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tableRef;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitTableRef(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final TableRefContext tableRef() throws RecognitionException {
        TableRefContext _localctx = new TableRefContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_tableRef);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(102);
                ((TableRefContext) _localctx).tableName = match(ID);
                setState(105);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == AS) {
                    {
                        setState(103);
                        match(AS);
                        setState(104);
                        ((TableRefContext) _localctx).alias = match(ID);
                    }
                }

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

    public static class WhereClusterContext extends ParserRuleContext {

        public TerminalNode WHERE() {
            return getToken(StreamQuery.WHERE, 0);
        }

        public BoolExprContext boolExpr() {
            return getRuleContext(BoolExprContext.class, 0);
        }

        public FilterByExprContext filterByExpr() {
            return getRuleContext(FilterByExprContext.class, 0);
        }

        public DurationExprContext durationExpr() {
            return getRuleContext(DurationExprContext.class, 0);
        }

        public WhereClusterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_whereCluster;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitWhereCluster(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final WhereClusterContext whereCluster() throws RecognitionException {
        WhereClusterContext _localctx = new WhereClusterContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_whereCluster);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(107);
                match(WHERE);
                setState(108);
                boolExpr(0);
                setState(110);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == FILTER) {
                    {
                        setState(109);
                        filterByExpr();
                    }
                }

                setState(113);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == FOR) {
                    {
                        setState(112);
                        durationExpr();
                    }
                }

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

    public static class BoolExprContext extends ParserRuleContext {

        public BoolExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_boolExpr;
        }

        public BoolExprContext() {
        }

        public void copyFrom(BoolExprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class BasicExprContext extends BoolExprContext {

        public BasicBoolExprContext basicBoolExpr() {
            return getRuleContext(BasicBoolExprContext.class, 0);
        }

        public BasicExprContext(BoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitBasicExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class LrExprContext extends BoolExprContext {

        public TerminalNode LPAREN() {
            return getToken(StreamQuery.LPAREN, 0);
        }

        public BoolExprContext boolExpr() {
            return getRuleContext(BoolExprContext.class, 0);
        }

        public TerminalNode RPAREN() {
            return getToken(StreamQuery.RPAREN, 0);
        }

        public LrExprContext(BoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitLrExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class AndOprContext extends BoolExprContext {

        public BoolExprContext left;
        public BoolExprContext right;

        public TerminalNode AND() {
            return getToken(StreamQuery.AND, 0);
        }

        public List<BoolExprContext> boolExpr() {
            return getRuleContexts(BoolExprContext.class);
        }

        public BoolExprContext boolExpr(int i) {
            return getRuleContext(BoolExprContext.class, i);
        }

        public AndOprContext(BoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitAndOpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class OrOprContext extends BoolExprContext {

        public BoolExprContext left;
        public BoolExprContext right;

        public TerminalNode OR() {
            return getToken(StreamQuery.OR, 0);
        }

        public List<BoolExprContext> boolExpr() {
            return getRuleContexts(BoolExprContext.class);
        }

        public BoolExprContext boolExpr(int i) {
            return getRuleContext(BoolExprContext.class, i);
        }

        public OrOprContext(BoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitOrOpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final BoolExprContext boolExpr() throws RecognitionException {
        return boolExpr(0);
    }

    private BoolExprContext boolExpr(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        BoolExprContext _localctx = new BoolExprContext(_ctx, _parentState);
        BoolExprContext _prevctx = _localctx;
        int _startState = 16;
        enterRecursionRule(_localctx, 16, RULE_boolExpr, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(121);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                    case 1: {
                        _localctx = new LrExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;

                        setState(116);
                        match(LPAREN);
                        setState(117);
                        boolExpr(0);
                        setState(118);
                        match(RPAREN);
                    }
                    break;
                    case 2: {
                        _localctx = new BasicExprContext(_localctx);
                        _ctx = _localctx;
                        _prevctx = _localctx;
                        setState(120);
                        basicBoolExpr();
                    }
                    break;
                }
                _ctx.stop = _input.LT(-1);
                setState(131);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) {
                            triggerExitRuleEvent();
                        }
                        _prevctx = _localctx;
                        {
                            setState(129);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                                case 1: {
                                    _localctx = new AndOprContext(new BoolExprContext(_parentctx, _parentState));
                                    ((AndOprContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
                                    setState(123);
                                    if (!(precpred(_ctx, 3))) {
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    }
                                    setState(124);
                                    match(AND);
                                    setState(125);
                                    ((AndOprContext) _localctx).right = boolExpr(4);
                                }
                                break;
                                case 2: {
                                    _localctx = new OrOprContext(new BoolExprContext(_parentctx, _parentState));
                                    ((OrOprContext) _localctx).left = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
                                    setState(126);
                                    if (!(precpred(_ctx, 2))) {
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    }
                                    setState(127);
                                    match(OR);
                                    setState(128);
                                    ((OrOprContext) _localctx).right = boolExpr(3);
                                }
                                break;
                            }
                        }
                    }
                    setState(133);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public static class BasicBoolExprContext extends ParserRuleContext {

        public BasicBoolExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_basicBoolExpr;
        }

        public BasicBoolExprContext() {
        }

        public void copyFrom(BasicBoolExprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class InExprContext extends BasicBoolExprContext {

        public NameContext left;
        public Token option;
        public CollectionContext right;

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode IN() {
            return getToken(StreamQuery.IN, 0);
        }

        public CollectionContext collection() {
            return getRuleContext(CollectionContext.class, 0);
        }

        public InExprContext(BasicBoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitInExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public static class CompareExprContext extends BasicBoolExprContext {

        public NameContext left;
        public Token option;
        public NameContext right;

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public TerminalNode EQ() {
            return getToken(StreamQuery.EQ, 0);
        }

        public TerminalNode GT() {
            return getToken(StreamQuery.GT, 0);
        }

        public TerminalNode LT() {
            return getToken(StreamQuery.LT, 0);
        }

        public TerminalNode GTEQ() {
            return getToken(StreamQuery.GTEQ, 0);
        }

        public TerminalNode LTEQ() {
            return getToken(StreamQuery.LTEQ, 0);
        }

        public TerminalNode NEQ() {
            return getToken(StreamQuery.NEQ, 0);
        }

        public CompareExprContext(BasicBoolExprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitCompareExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final BasicBoolExprContext basicBoolExpr() throws RecognitionException {
        BasicBoolExprContext _localctx = new BasicBoolExprContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_basicBoolExpr);
        int _la;
        try {
            setState(142);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
                case 1:
                    _localctx = new CompareExprContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(134);
                    ((CompareExprContext) _localctx).left = name(0);
                    setState(135);
                    ((CompareExprContext) _localctx).option = _input.LT(1);
                    _la = _input.LA(1);
                    if (!(((((_la - 109)) & ~0x3f) == 0 &&
                        ((1L << (_la - 109)) & ((1L << (EQ - 109)) | (1L << (GT - 109)) | (1L << (LT - 109)) | (1L << (
                            LTEQ - 109)) | (1L << (GTEQ - 109)) | (1L << (NEQ - 109)))) != 0))) {
                        ((CompareExprContext) _localctx).option = (Token) _errHandler.recoverInline(this);
                    } else {
                        if (_input.LA(1) == Token.EOF) {
                            matchedEOF = true;
                        }
                        _errHandler.reportMatch(this);
                        consume();
                    }
                    setState(136);
                    ((CompareExprContext) _localctx).right = name(0);
                }
                break;
                case 2:
                    _localctx = new InExprContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(138);
                    ((InExprContext) _localctx).left = name(0);
                    setState(139);
                    ((InExprContext) _localctx).option = match(IN);
                    setState(140);
                    ((InExprContext) _localctx).right = collection();
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

    public static class CollectionContext extends ParserRuleContext {

        public TerminalNode LPAREN() {
            return getToken(StreamQuery.LPAREN, 0);
        }

        public List<IdentityContext> identity() {
            return getRuleContexts(IdentityContext.class);
        }

        public IdentityContext identity(int i) {
            return getRuleContext(IdentityContext.class, i);
        }

        public TerminalNode RPAREN() {
            return getToken(StreamQuery.RPAREN, 0);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(StreamQuery.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(StreamQuery.COMMA, i);
        }

        public CollectionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_collection;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitCollection(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final CollectionContext collection() throws RecognitionException {
        CollectionContext _localctx = new CollectionContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_collection);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(144);
                match(LPAREN);
                setState(145);
                identity();
                setState(150);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == COMMA) {
                    {
                        {
                            setState(146);
                            match(COMMA);
                            setState(147);
                            identity();
                        }
                    }
                    setState(152);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(153);
                match(RPAREN);
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

    public static class DurationExprContext extends ParserRuleContext {

        public NameContext number;
        public Token unit;

        public TerminalNode FOR() {
            return getToken(StreamQuery.FOR, 0);
        }

        public TerminalNode LAST() {
            return getToken(StreamQuery.LAST, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode MINUTE() {
            return getToken(StreamQuery.MINUTE, 0);
        }

        public TerminalNode EVENTS() {
            return getToken(StreamQuery.EVENTS, 0);
        }

        public DurationExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_durationExpr;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitDurationExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final DurationExprContext durationExpr() throws RecognitionException {
        DurationExprContext _localctx = new DurationExprContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_durationExpr);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(155);
                match(FOR);
                setState(156);
                match(LAST);
                setState(157);
                ((DurationExprContext) _localctx).number = name(0);
                setState(158);
                ((DurationExprContext) _localctx).unit = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == MINUTE || _la == EVENTS)) {
                    ((DurationExprContext) _localctx).unit = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) {
                        matchedEOF = true;
                    }
                    _errHandler.reportMatch(this);
                    consume();
                }
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

    public static class FilterByExprContext extends ParserRuleContext {

        public TerminalNode FILTER() {
            return getToken(StreamQuery.FILTER, 0);
        }

        public TerminalNode BY() {
            return getToken(StreamQuery.BY, 0);
        }

        public List<TerminalNode> ID() {
            return getTokens(StreamQuery.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(StreamQuery.ID, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(StreamQuery.COMMA);
        }

        public TerminalNode COMMA(int i) {
            return getToken(StreamQuery.COMMA, i);
        }

        public FilterByExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_filterByExpr;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof StreamQueryVisitor) {
                return ((StreamQueryVisitor<? extends T>) visitor).visitFilterByExpr(this);
            } else {
                return visitor.visitChildren(this);
            }
        }
    }

    public final FilterByExprContext filterByExpr() throws RecognitionException {
        FilterByExprContext _localctx = new FilterByExprContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_filterByExpr);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(160);
                match(FILTER);
                setState(161);
                match(BY);
                setState(162);
                match(ID);
                setState(167);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == COMMA) {
                    {
                        {
                            setState(163);
                            match(COMMA);
                            setState(164);
                            match(ID);
                        }
                    }
                    setState(169);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
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

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
        switch (ruleIndex) {
            case 3:
                return name_sempred((NameContext) _localctx, predIndex);
            case 8:
                return boolExpr_sempred((BoolExprContext) _localctx, predIndex);
        }
        return true;
    }

    private boolean name_sempred(NameContext _localctx, int predIndex) {
        switch (predIndex) {
            case 0:
                return precpred(_ctx, 8);
            case 1:
                return precpred(_ctx, 7);
            case 2:
                return precpred(_ctx, 6);
        }
        return true;
    }

    private boolean boolExpr_sempred(BoolExprContext _localctx, int predIndex) {
        switch (predIndex) {
            case 3:
                return precpred(_ctx, 3);
            case 4:
                return precpred(_ctx, 2);
        }
        return true;
    }

    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u009d\u00ad\4\2\t" +
            "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
            "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\5\2\"\n\2\3\2\3\2" +
            "\3\3\3\3\3\3\7\3)\n\3\f\3\16\3,\13\3\3\4\3\4\5\4\60\n\4\3\4\3\4\3\4\5" +
            "\4\65\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5" +
            "\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5O\n\5\3\5\3\5\3\5\3\5\3\5\3\5" +
            "\3\5\3\5\3\5\7\5Z\n\5\f\5\16\5]\13\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5" +
            "\7g\n\7\3\b\3\b\3\b\5\bl\n\b\3\t\3\t\3\t\5\tq\n\t\3\t\5\tt\n\t\3\n\3\n" +
            "\3\n\3\n\3\n\3\n\5\n|\n\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0084\n\n\f\n\16" +
            "\n\u0087\13\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0091\n\13" +
            "\3\f\3\f\3\f\3\f\7\f\u0097\n\f\f\f\16\f\u009a\13\f\3\f\3\f\3\r\3\r\3\r" +
            "\3\r\3\r\3\16\3\16\3\16\3\16\3\16\7\16\u00a8\n\16\f\16\16\16\u00ab\13" +
            "\16\3\16\2\4\b\22\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\7\4\2\u0088\u0089" +
            "\u0093\u0093\3\2\u0086\u0087\4\2\u008a\u008c\u008e\u008f\5\2oqzz}~\4\2" +
            "]]aa\2\u00b9\2\34\3\2\2\2\4%\3\2\2\2\6/\3\2\2\2\bN\3\2\2\2\n^\3\2\2\2" +
            "\ff\3\2\2\2\16h\3\2\2\2\20m\3\2\2\2\22{\3\2\2\2\24\u0090\3\2\2\2\26\u0092" +
            "\3\2\2\2\30\u009d\3\2\2\2\32\u00a2\3\2\2\2\34\35\7\3\2\2\35\36\5\4\3\2" +
            "\36\37\7\7\2\2\37!\5\16\b\2 \"\5\20\t\2! \3\2\2\2!\"\3\2\2\2\"#\3\2\2" +
            "\2#$\7\2\2\3$\3\3\2\2\2%*\5\6\4\2&\'\7k\2\2\')\5\6\4\2(&\3\2\2\2),\3\2" +
            "\2\2*(\3\2\2\2*+\3\2\2\2+\5\3\2\2\2,*\3\2\2\2-.\7\u009b\2\2.\60\7l\2\2" +
            "/-\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61\64\5\b\5\2\62\63\7\16\2\2\63\65" +
            "\7\u009b\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\7\3\2\2\2\66\67\b\5\1\2\67" +
            "8\7d\2\289\5\b\5\29:\7e\2\2:O\3\2\2\2;<\7\u009b\2\2<=\7d\2\2=>\5\b\5\2" +
            ">?\7e\2\2?O\3\2\2\2@A\7\u009b\2\2AB\7d\2\2BC\5\b\5\2CD\7k\2\2DE\5\22\n" +
            "\2EF\7e\2\2FO\3\2\2\2GH\7\u009b\2\2HI\7d\2\2IJ\5\22\n\2JK\7e\2\2KO\3\2" +
            "\2\2LO\5\f\7\2MO\5\n\6\2N\66\3\2\2\2N;\3\2\2\2N@\3\2\2\2NG\3\2\2\2NL\3" +
            "\2\2\2NM\3\2\2\2O[\3\2\2\2PQ\f\n\2\2QR\t\2\2\2RZ\5\b\5\13ST\f\t\2\2TU" +
            "\t\3\2\2UZ\5\b\5\nVW\f\b\2\2WX\t\4\2\2XZ\5\b\5\tYP\3\2\2\2YS\3\2\2\2Y" +
            "V\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\t\3\2\2\2][\3\2\2\2^_\7\u009c" +
            "\2\2_\13\3\2\2\2`g\7\u009b\2\2ag\7\u0096\2\2bg\7\u0097\2\2cg\7\u0098\2" +
            "\2dg\7\u0099\2\2eg\7\u009a\2\2f`\3\2\2\2fa\3\2\2\2fb\3\2\2\2fc\3\2\2\2" +
            "fd\3\2\2\2fe\3\2\2\2g\r\3\2\2\2hk\7\u009b\2\2ij\7\16\2\2jl\7\u009b\2\2" +
            "ki\3\2\2\2kl\3\2\2\2l\17\3\2\2\2mn\7\t\2\2np\5\22\n\2oq\5\32\16\2po\3" +
            "\2\2\2pq\3\2\2\2qs\3\2\2\2rt\5\30\r\2sr\3\2\2\2st\3\2\2\2t\21\3\2\2\2" +
            "uv\b\n\1\2vw\7d\2\2wx\5\22\n\2xy\7e\2\2y|\3\2\2\2z|\5\24\13\2{u\3\2\2" +
            "\2{z\3\2\2\2|\u0085\3\2\2\2}~\f\5\2\2~\177\7D\2\2\177\u0084\5\22\n\6\u0080" +
            "\u0081\f\4\2\2\u0081\u0082\7E\2\2\u0082\u0084\5\22\n\5\u0083}\3\2\2\2" +
            "\u0083\u0080\3\2\2\2\u0084\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086" +
            "\3\2\2\2\u0086\23\3\2\2\2\u0087\u0085\3\2\2\2\u0088\u0089\5\b\5\2\u0089" +
            "\u008a\t\5\2\2\u008a\u008b\5\b\5\2\u008b\u0091\3\2\2\2\u008c\u008d\5\b" +
            "\5\2\u008d\u008e\7M\2\2\u008e\u008f\5\26\f\2\u008f\u0091\3\2\2\2\u0090" +
            "\u0088\3\2\2\2\u0090\u008c\3\2\2\2\u0091\25\3\2\2\2\u0092\u0093\7d\2\2" +
            "\u0093\u0098\5\f\7\2\u0094\u0095\7k\2\2\u0095\u0097\5\f\7\2\u0096\u0094" +
            "\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099" +
            "\u009b\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7e\2\2\u009c\27\3\2\2\2" +
            "\u009d\u009e\7\60\2\2\u009e\u009f\7[\2\2\u009f\u00a0\5\b\5\2\u00a0\u00a1" +
            "\t\6\2\2\u00a1\31\3\2\2\2\u00a2\u00a3\7\21\2\2\u00a3\u00a4\7\13\2\2\u00a4" +
            "\u00a9\7\u009b\2\2\u00a5\u00a6\7k\2\2\u00a6\u00a8\7\u009b\2\2\u00a7\u00a5" +
            "\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa" +
            "\33\3\2\2\2\u00ab\u00a9\3\2\2\2\23!*/\64NY[fkps{\u0083\u0085\u0090\u0098" +
            "\u00a9";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
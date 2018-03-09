package com.crescent.alert.engine.provider.parse;

import com.crescent.alert.engine.antlr.StreamLexer;
import com.crescent.alert.engine.antlr.StreamQuery;
import com.crescent.alert.engine.antlr.StreamQuery.ProgContext;
import com.crescent.alert.engine.exception.StreamQueryParseError;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.lang3.Validate;

public class ParserEngine implements IParserEngine {

    protected ParserEngine() {
    }

    public RuleTemplate parse(String sql) throws StreamQueryParseError {
        Validate.notBlank(sql);

        StreamQuery streamQueryParser = createParser(sql);
        ProgContext tree = streamQueryParser.prog();
        ConditionVisitor visitor = new ConditionVisitor();
        try {
            Boolean isQuery = visitor.visit(tree);

            if (isQuery == null || !isQuery) {
                throw new StreamQueryParseError("Query not supported: " + sql, null);
            }
        } catch (RuntimeException e) {
            throw new StreamQueryParseError(e.getMessage(), e);
        }

        return visitor.getRuleTemplate();
    }

    private StreamQuery createParser(final String queryText) {
        StreamLexer lexer = newStreamQueryLexer(queryText);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        StreamQuery parser = new StreamQuery(tokenStream);
        parser.removeErrorListeners();
        return parser;
    }

    private static StreamLexer newStreamQueryLexer(final String queryText) {
        CharStream input = CharStreams.fromString(queryText);
        StreamLexer lexer = new StreamLexer(input);
        lexer.removeErrorListeners();
        return lexer;
    }
}

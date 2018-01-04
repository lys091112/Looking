package com.crescent.alert.engine.provider.parse;

public final class ParserEngineFactory {

    private static IParserEngine parserEngine = new ParserEngine();

    private ParserEngineFactory() {
        // no construct
    }

    public static IParserEngine getParseEngine() {
        return parserEngine;
    }
}

package com.crescent.alert.engine.provider.parse;


import com.crescent.alert.engine.exception.StreamQueryParseError;

public interface IParserEngine {

    /**
     * parse SQL in rule criteria.
     */
    RuleTemplate parse(String sql) throws StreamQueryParseError;
}

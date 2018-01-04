package com.crescent.alert.engine.exception;

public class StreamParseException extends RuntimeException {

    private static final long serialVersionUID = -4709561400676666787L;

    public StreamParseException(String errorMsg) {
        super(errorMsg);
    }

}

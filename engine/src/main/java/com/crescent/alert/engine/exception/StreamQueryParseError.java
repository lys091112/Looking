package com.crescent.alert.engine.exception;

public class StreamQueryParseError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StreamQueryParseError(String message, Throwable cause) {
        super(message, cause);
    }
}
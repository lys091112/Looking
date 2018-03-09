package com.crescent.alert.engine.exception;

public class ProcessingException extends RuntimeException {

    public ProcessingException(String message, Exception cause) {
        super(message, cause);
    }
}

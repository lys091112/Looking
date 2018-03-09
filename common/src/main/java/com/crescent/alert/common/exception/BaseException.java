package com.crescent.alert.common.exception;

import java.util.UUID;

/**
 */
public class BaseException extends RuntimeException {

    private String traceId;
    private ErrorCode errorCode;

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.traceId = UUID.randomUUID().toString();
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "{ traceId: " + traceId + " ,errorInfo: " + errorCode.getMessage() + ", message: "
            + getMessage() + " }";
    }
}

package com.crescent.alert.common.exception;

/**
 */
public class NotFountException extends BaseException {

    public NotFountException(String message) {
        super(ErrorCode.NotFoundException, message);
    }
}

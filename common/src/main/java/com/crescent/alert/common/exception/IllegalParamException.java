package com.crescent.alert.common.exception;

public class IllegalParamException extends BaseException {

    public IllegalParamException(String message) {
        super(ErrorCode.IllegalParamException, message);
    }
}

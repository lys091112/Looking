package com.crescent.alert.common.exception;


/**
 * 序列化异常
 */
public class UserDeserializetaionException extends BaseException {

    public UserDeserializetaionException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

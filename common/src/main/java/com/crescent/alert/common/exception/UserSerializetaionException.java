package com.crescent.alert.common.exception;


/**
 * 序列化异常
 */
public class UserSerializetaionException extends BaseException {

    public UserSerializetaionException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

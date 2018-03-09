package com.crescent.alert.common.exception;

/**
 * 初始化失败错误异常
 */
public class InitializationException extends BaseException {

    public InitializationException(String message) {
        super(ErrorCode.InitializeConfigError, message);
    }
}

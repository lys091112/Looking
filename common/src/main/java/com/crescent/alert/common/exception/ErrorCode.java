package com.crescent.alert.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    InitializeConfigError(100001, "初始化配置文件失败！"),
    IllegalParamException(100002, "不合法的参数！"),
    SerializeException(100003, "序列化失败"),
    DeserializeException(100003, "反序列化失败");

    private int code;
    private String message;

}

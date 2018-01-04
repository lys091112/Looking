package com.crescent.alert.engine;


import java.util.Map;
import java.util.Optional;

public interface Event {

    /**
     * 获取事件所属规则标识
     */
    String getStreamId();

    /**
     * 事件信息的对象标识
     */
    String getKey();

    /**
     * 事件传递的指标数据
     */
    Map<String, String> getMetrics();

    /**
     * 事件发送时间
     */
    long getTimestamp();

    Optional<String> readValue(String fieldName);
}

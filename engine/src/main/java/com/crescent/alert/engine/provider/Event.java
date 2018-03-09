package com.crescent.alert.engine.provider;


import java.util.Map;

public interface Event {

    /**
     * 获取事件所属规则标识
     */
    String getRuleId();

    /**
     * 事件信息的对象标识
     */
    String getStreamId();

    /**
     * 事件传递的指标数据
     */
    Map<String, String> getMetrics();

    /**
     * 事件发送时间
     */
    long getTimestamp();
}

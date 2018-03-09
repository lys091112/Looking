package com.crescent.alert.core.domain;

import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.engine.provider.Event;
import java.util.Map;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class AlertEvent implements Event {

    private String ruleId;
    private String streamId;
    private long timestamp;
    private Map<String, String> metrics;

    /**
     * 告警级别
     */
    private String severityLevel;

    /**
     * 事件类型
     */
    private EventCategory eventCategory;

    /**
     * 告警所属域
     */
    private String region;

    /**
     * 数据的有效时间
     */
    private long ttl;
}

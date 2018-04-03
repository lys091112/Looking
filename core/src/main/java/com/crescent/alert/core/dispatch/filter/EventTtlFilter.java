package com.crescent.alert.core.dispatch.filter;

import com.crescent.alert.core.rule.AlertEvent;

/**
 * 事件的有效期监测
 */
public class EventTtlFilter implements AlertFilter {

    @Override
    public boolean filter(AlertEvent event) {
        boolean isFit = System.currentTimeMillis() - event.getTimestamp() >= event.getTtl();
        if (!isFit) {
            LOGGER.error("Event time out!, eventTime:{},  ttl={}", event.getTimestamp(), event.getTtl());
        }
        return isFit;
    }
}

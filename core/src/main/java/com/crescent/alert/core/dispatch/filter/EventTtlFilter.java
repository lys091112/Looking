package com.crescent.alert.core.dispatch.filter;

import com.crescent.alert.core.domain.AlertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件的有效期监测
 */
public class EventTtlFilter implements AlertFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventTtlFilter.class);

    @Override
    public boolean filter(AlertEvent event) {
        boolean isFit = System.currentTimeMillis() - event.getTimestamp() >= event.getTtl();
        if (!isFit) {
            LOGGER.error("event time out!, eventTime:{},  ttl={}", event.getTimestamp(), event.getTtl());
        }
        return isFit;
    }
}

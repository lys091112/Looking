package com.crescent.alert.core.dispatch.filter;

import com.crescent.alert.core.rule.AlertEvent;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理乱序数据
 */
public class DisOrderEventFilter implements AlertFilter {

    private ConcurrentHashMap<String, Long> recordComingEventTime = new ConcurrentHashMap<>();

    /**
     * 如果当前事件的产生时间小于已经处理过的事件最大时间，那么进行丢弃处理
     */
    @Override
    public boolean filter(AlertEvent event) {

        if (event.getTimestamp() < recordComingEventTime.getOrDefault(event.getStreamId(), 0L)) {
            LOGGER.error("DisOrder event! event streamId is {}, eventTime:{}", event.getStreamId(), event.getTimestamp());
            return false;
        }
        recordComingEventTime.put(event.getStreamId(), event.getTimestamp());
        return true;
    }
}

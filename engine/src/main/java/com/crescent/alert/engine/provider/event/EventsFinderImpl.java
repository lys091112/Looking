package com.crescent.alert.engine.provider.event;

import static com.crescent.alert.common.util.Constants.CONTINUE_IGNORE_EVENT;

import com.crescent.alert.common.util.Constants;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.event.boundingBox.BoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.SizeBoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.TimeBoundingBox;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

public class EventsFinderImpl extends EventsFinder {

    public EventsFinderImpl(BoundingBox boundingBox) {
        super(boundingBox);
    }

    @Override
    public List<Event> backwardFrom(Event currentEvent, Map<String, String> params, AbstractEventsProvider provider) {
        if (provider == null) {
            throw new NullPointerException("EventsProvider instance not found.");
        }

        if (boundingBox == null) {
            throw new IllegalArgumentException("BoundingBox instance not found");
        }

        // 判断是否需要全量匹配event buffer数据
        boolean totalMatching = null == params || "true".equalsIgnoreCase(params.getOrDefault(Constants.TOTAL_MATCHING, ""));

        List<Event> events;
        if (boundingBox instanceof SizeBoundingBox) {
            long count = ((SizeBoundingBox) boundingBox).getSize();
            events = provider.findEventsByFixedCount(currentEvent.getStreamId(), (int) count);
            if (totalMatching && !fitCountBuffer(events, count)) {
                return Collections.emptyList();
            }
        } else if (boundingBox instanceof TimeBoundingBox) {
            Duration duration = ((TimeBoundingBox) boundingBox).getDuration(params);
            events = provider
                .findEventsByTimeWindow(currentEvent.getStreamId(), currentEvent.getTimestamp() - duration.toMillis(),
                    currentEvent
                        .getTimestamp());
            if (totalMatching && !fitTimeBuffer(events, currentEvent.getTimestamp() - duration.toMillis(),
                currentEvent.getTimestamp())) {
                return Collections.emptyList();
            }
        } else {
            throw new IllegalArgumentException("Invalid BoundingBox Type");
        }

        // 忽略掉由CONTINUE_IGNORE_EVENT标识的事件
        if (totalMatching) {
            events = events.stream()
                .filter(e -> !"true".equalsIgnoreCase(e.getMetrics().getOrDefault(CONTINUE_IGNORE_EVENT, "")))
                .collect(Collectors.toList());
        }

        return Optional.ofNullable(events).orElse(Collections.emptyList());
    }

    private boolean fitTimeBuffer(List<Event> events, long startTime, long endTime) {
        if (CollectionUtils.isEmpty(events)) {
            return false;
        }
        return events.get(0).getTimestamp() <= startTime + TimeUnit.MINUTES.toMillis(1) &&
            events.get(events.size() - 1).getTimestamp() >= endTime;
    }

    protected boolean fitCountBuffer(List<Event> events, long count) {
        return CollectionUtils.isNotEmpty(events) && events.size() >= count;
    }
}

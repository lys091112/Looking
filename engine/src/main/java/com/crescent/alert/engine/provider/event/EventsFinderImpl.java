package com.crescent.alert.engine.provider.event;

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

public class EventsFinderImpl extends EventsFinder {

    public EventsFinderImpl(BoundingBox boundingBox) {
        super(boundingBox);
    }

    @Override
    public List<Event> backwardFrom(Event currentEvent, Map<String, String> params, IEventsProvider provider) {
        if (provider == null) {
            throw new NullPointerException("EventsProvider not found.");
        }

        if (boundingBox == null) {
            throw new IllegalArgumentException("BoundingBox instance not found");
        }

        // 判断是否需要全量匹配event buffer数据
        String severity = params.getOrDefault(Constants.CONCURRENT_PARAMETER_SEVERITY, "");

        List<Event> events;
        if (boundingBox instanceof SizeBoundingBox) {
            long count = ((SizeBoundingBox) boundingBox).getSize();
            events = provider.findEventsByFixedCount(currentEvent.getStreamId(), severity, (int) count);
        } else if (boundingBox instanceof TimeBoundingBox) {
            Duration duration = ((TimeBoundingBox) boundingBox).getDuration(params);
            events = provider.findEventsByTimeWindow(currentEvent.getStreamId(), severity,
                currentEvent.getTimestamp() - duration.toMillis(), currentEvent.getTimestamp());
        } else {
            throw new IllegalArgumentException("Invalid BoundingBox Type");
        }

        return Optional.ofNullable(events).orElse(Collections.emptyList());
    }
}

package com.crescent.alert.event.provider.memory;

import com.crescent.alert.core.dispatch.event.BaseEventsProvider;
import com.crescent.alert.engine.provider.Event;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class MemoryEventsProvider extends BaseEventsProvider {

    public MemoryEventsProvider() {
    }

    private ConcurrentHashMap<String, EventCycle> events = new ConcurrentHashMap<>();

    protected void setEvents(ConcurrentHashMap<String, EventCycle> events) {
        this.events = events;
    }

    @Override
    public void clear(String streamId) {
        if (events.containsKey(streamId)) {
            events.get(streamId).clear();
        }
    }

    @Override
    public String eventWindowType() {
        return "memory";
    }

    @Override
    protected List<Event> doFindEventsByFixedCount(String streamId, int count) {
        if (!events.containsKey(streamId)) {
            return Collections.emptyList();
        }

        return events.get(streamId).getEventsByCount(count);
    }

    @Override
    protected List<Event> doFindEventsByTimeWindow(String streamId, long startTime, long endTime) {
        if (!events.containsKey(streamId)) {
            return Collections.emptyList();
        }

        return events.get(streamId).getEventsByWindow(new TimeWindow(startTime, endTime));
    }

    @Override
    public boolean sink(Event event) {
        if (!events.containsKey(event.getStreamId())) {
            events.put(event.getRuleId(), new EventCycle(bufferSize(event.getStreamId()) * 2));
        }
        events.get(event.getRuleId()).sink(event);
        return true;
    }
}

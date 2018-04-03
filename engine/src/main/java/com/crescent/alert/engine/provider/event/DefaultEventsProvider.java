package com.crescent.alert.engine.provider.event;

import static java.util.stream.StreamSupport.stream;

import com.crescent.alert.engine.provider.Event;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultEventsProvider implements IEventsProvider {

    private List<Event> events;

    public DefaultEventsProvider(List<Event> events) {
        this.events = events;
    }

    @Override
    public List<Event> findEventsByFixedCount(String streamId, String severity, int count) {
        List<Event> eventList = Lists.newLinkedList(events);

        int endIndex = eventList.size();
        int startIndex = (endIndex - count > 0) ? endIndex - count : 0;
        return Lists.newArrayList(eventList.subList(startIndex, endIndex));
    }

    @Override
    public List<Event> findEventsByTimeWindow(String streamId, String severity, long startTime, long endTime) {
        return stream(events.spliterator(), false)
            .filter(event -> startTime < event.getTimestamp() && event.getTimestamp() <= endTime)
            .collect(Collectors.toList());
    }

}

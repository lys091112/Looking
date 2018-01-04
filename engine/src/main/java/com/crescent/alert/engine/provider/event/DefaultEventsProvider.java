package com.crescent.alert.engine.provider.event;

import static java.util.stream.StreamSupport.stream;

import com.google.common.collect.Lists;
import com.crescent.alert.engine.Event;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;

public class DefaultEventsProvider implements IEventsProvider {

    private Iterable<Event> events;

    public DefaultEventsProvider(@NonNull Iterable<Event> events) {
        this.events = events;
    }

    @Override
    public List<Event> getEventsByFixedCount(int count) {

        List<Event> eventList = Lists.newLinkedList(events);

        int endIndex = eventList.size();
        int startIndex = (endIndex - count > 0) ? endIndex - count : 0;
        return Lists.newArrayList(eventList.subList(startIndex, endIndex));
    }

    @Override
    public List<Event> getEventsByTimeWindow(String key, long startTime, long endTime) {
        return stream(events.spliterator(), false)
            .filter(event -> startTime < event.getTimestamp() && event.getTimestamp() <= endTime)
            .collect(Collectors.toList());
    }

    @Override
    public Event getThePreviousEvent(@NonNull Event event) {
        Iterator<Event> iterator = events.iterator();
        Event previousEvent;
        if (iterator.hasNext()) {
            previousEvent = iterator.next();
            while (iterator.hasNext()) {
                Event currentEvent = iterator.next();
                if (event.equals(currentEvent)) {
                    return previousEvent;
                } else {
                    previousEvent = currentEvent;
                }
            }
        }
        return null;
    }

    @Override
    public Event getEventBySourceEventId(String sourceEventId) {

        return null;
    }
}

package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.Event;
import java.util.List;

public interface IEventsProvider {

    /**
     * query fixed count events
     * @param count
     * @return
     */
    List<Event> getEventsByFixedCount(int count);

    /**
     * query events in time window (startTime, endTime)
     * @param key
     * @param startTime
     * @param endTime
     * @return
     */
    List<Event> getEventsByTimeWindow(String key, long startTime, long endTime);

    /**
     * Query the event before current event
     * @param event
     * @return {@link Event}
     */
    Event getThePreviousEvent(Event event);

    /**
     * All the HealthStatusEvent Would have it's source event (RawMetricEvent) id in its tags
     */
    Event getEventBySourceEventId(String sourceEventId);
}

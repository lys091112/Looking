package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.provider.Event;
import java.util.List;

public interface AbstractEventsProvider {

    /**
     * query fixed count events
     */
    public List<Event> findEventsByFixedCount(String streamId, int count);


    /**
     * query events in time window (startTime, endTime)
     */
    public List<Event> findEventsByTimeWindow(String streamId, long startTime, long endTime);


}

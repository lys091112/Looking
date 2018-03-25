package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.provider.Event;
import java.util.List;

public abstract class AbstractEventsProvider {

    /**
     * query fixed count events
     */
    public abstract List<Event> findEventsByFixedCount(String streamId, int count);


    /**
     * query events in time window (startTime, endTime)
     */
    public abstract List<Event> findEventsByTimeWindow(String streamId, long startTime, long endTime);


}

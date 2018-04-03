package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.provider.Event;
import java.util.List;

public interface IEventsProvider {

    /**
     * query fixed count events
     */
    List<Event> findEventsByFixedCount(String streamId, String severity, int count);


    /**
     * query events in time window (startTime, endTime)
     */
    List<Event> findEventsByTimeWindow(String streamId, String severity, long startTime, long endTime);


}

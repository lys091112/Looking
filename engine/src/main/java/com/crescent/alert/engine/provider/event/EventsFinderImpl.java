package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.event.boundingBox.BoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.SizeBoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.TimeBoundingBox;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.omg.CORBA.TIMEOUT;

public class EventsFinderImpl extends EventsFinder {


    public EventsFinderImpl(BoundingBox boundingBox) {
        super(boundingBox);
    }

    @Override
    public List<Event> backwardFrom(Event event, Map<String, String> params) {
        if (provider == null) {
            throw new NullPointerException("EventsProvider instance not found.");
        }

        if (boundingBox == null) {
            throw new IllegalArgumentException("BoundBox instance not found");
        }

        List<Event> events;
        if (boundingBox instanceof SizeBoundingBox) {
            long count = ((SizeBoundingBox) boundingBox).getSize();
            events = provider.getEventsByFixedCount((int) count);
        } else if (boundingBox instanceof TimeBoundingBox) {
            Duration duration = ((TimeBoundingBox) boundingBox).getDuration();
            events = provider.getEventsByTimeWindow(event.getKey(), event.getTimestamp() - duration.toMillis(), event
                .getTimestamp());
        } else {
            throw new IllegalArgumentException("Invaild BoundingBox Type");
        }

        return Optional.ofNullable(events).orElse(Collections.emptyList());
    }
}

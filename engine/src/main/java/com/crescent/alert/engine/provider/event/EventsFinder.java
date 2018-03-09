package com.crescent.alert.engine.provider.event;

import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.event.boundingBox.BoundingBox;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class EventsFinder {

    protected EventsFinder(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    protected BoundingBox boundingBox;

    public abstract List<Event> backwardFrom(Event event, Map<String, String> params, AbstractEventsProvider provider);
}

package com.crescent.alert.core.dispatch.provider;

import com.crescent.alert.core.RuleManager;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.event.AbstractEventsProvider;

public abstract class BaseEventsProvider extends AbstractEventsProvider {

    public abstract boolean addEvent(Event event);

    public abstract void clear(Event event);

    protected int bufferSize(String streamId) {
        return RuleManager.getInstance().getBufferSizeByStreamId(streamId);
    }

}

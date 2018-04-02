package com.crescent.alert.core.dispatch.provider;

import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.event.AbstractEventsProvider;

public abstract class BaseEventsProvider implements AbstractEventsProvider {

    private AlertProvider alertProvider;

    public BaseEventsProvider() {
    }

    public abstract boolean addEvent(Event event);

    public void clear(Event event) {
        this.clear(event.getStreamId());
        addEvent(event);
    }

    public abstract void clear(String streamId);

    public abstract String eventWindowType();

    public void setAlertProvider(AlertProvider alertProvider) {
        this.alertProvider = alertProvider;
    }

    protected int bufferSize(String streamId) {
        return alertProvider.getRuleManager().getBufferSizeByStreamId(streamId);
    }

}

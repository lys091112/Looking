package com.crescent.alert.core.dispatch;

import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.domain.AlertEvent;

public abstract class EventDispatcher {

    protected RuleHandler handler;

    public abstract void dispatcher(AlertEvent event);

}

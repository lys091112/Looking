package com.crescent.alert.core.dispatch;

import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.domain.AlertEvent;
import lombok.Setter;

public abstract class EventDispatcher {

    protected RuleHandler handler;

    public abstract void dispatcher(AlertEvent event);

}

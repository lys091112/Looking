package com.crescent.alert.core.dispatch;

import com.crescent.alert.core.dispatch.filter.AlertFilter;
import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.rule.AlertEvent;
import com.google.common.base.Preconditions;
import java.util.List;

public abstract class EventDispatcher {

    protected RuleHandler handler;

    protected List<AlertFilter> filters;

    public abstract void dispatcher(AlertEvent event);

    public void addEventFilters(List<AlertFilter> externalFilters) {
        Preconditions.checkNotNull(externalFilters, "Filter can't be null");
        if (externalFilters.isEmpty()) {
            return;
        }
        filters.addAll(externalFilters);
    }

}

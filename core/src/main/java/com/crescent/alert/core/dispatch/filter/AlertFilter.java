package com.crescent.alert.core.dispatch.filter;

import com.crescent.alert.core.domain.AlertEvent;

public interface AlertFilter {

    boolean filter(AlertEvent event);

}

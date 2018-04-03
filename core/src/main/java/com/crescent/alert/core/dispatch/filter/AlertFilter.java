package com.crescent.alert.core.dispatch.filter;

import com.crescent.alert.core.rule.AlertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface AlertFilter {

    Logger LOGGER = LoggerFactory.getLogger(EventTtlFilter.class);

    boolean filter(AlertEvent event);

}

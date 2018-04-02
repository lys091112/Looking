package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RuleHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RuleHandler.class);

    private RuleHandler childHandler;

    protected AlertProvider alertProvider;


    public RuleHandler(AlertProvider alertProvider, RuleHandler childHandler) {
        this.childHandler = childHandler;
        this.alertProvider = alertProvider;
    }

    public void execute(AlertEvent event) {
        Optional<AlertEvent> next = handle(event);

        if (childHandler != null && next.isPresent()) {
            childHandler.handle(next.get());
        }
    }

    protected abstract Optional<AlertEvent> handle(AlertEvent event);


}

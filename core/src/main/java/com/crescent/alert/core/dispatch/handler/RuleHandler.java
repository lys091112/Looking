package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.domain.AlertEvent;
import java.util.Optional;

public abstract class RuleHandler {

    private RuleHandler childHandler;


    public RuleHandler(RuleHandler childHandler) {
        this.childHandler = childHandler;
    }

    public void execute(AlertEvent event) {
        Optional<AlertEvent> next = handle(event);

        if (childHandler != null && next.isPresent()) {
            childHandler.handle(next.get());
        }
    }

    protected abstract Optional<AlertEvent> handle(AlertEvent event);


}

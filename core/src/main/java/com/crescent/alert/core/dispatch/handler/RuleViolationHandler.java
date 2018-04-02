package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.core.collector.producer.Sender;
import com.crescent.alert.core.dispatch.provider.EventProviderFactory;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuleViolationHandler extends RuleHandler {

    private Sender sender;

    public RuleViolationHandler(AlertProvider alertProvider, EventProviderFactory factory) {
        super(alertProvider, null);
        this.sender = alertProvider.getSender();

    }

    @Override
    public Optional<AlertEvent> handle(AlertEvent event) {
        sender.send(event);
        return Optional.empty();
    }

}

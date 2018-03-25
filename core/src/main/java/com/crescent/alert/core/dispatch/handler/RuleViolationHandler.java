package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.collector.producer.LookingProducerManager;
import com.crescent.alert.core.domain.AlertEvent;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuleViolationHandler extends RuleHandler {

    private LookingProducerManager producer;

    public RuleViolationHandler(LookingProducerManager producer) {
        super(null);
        this.producer = producer;
    }

    @Override
    public Optional<AlertEvent> handle(AlertEvent event) {
        producer.send(event);
        return Optional.empty();
    }

}

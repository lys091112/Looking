package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.collector.producer.Sender;
import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.core.rule.RuleProvider;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuleViolationHandler extends RuleHandler {

    private Sender sender;

    public RuleViolationHandler(RuleProvider ruleProvider, Sender sender) {
        super(ruleProvider);
        this.sender = sender;

    }

    @Override
    public Optional<AlertEvent> handle(AlertEvent event) {
        sender.send(event);
        return Optional.empty();
    }

}

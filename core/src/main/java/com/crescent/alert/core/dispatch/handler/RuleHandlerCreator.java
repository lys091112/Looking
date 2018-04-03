package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.collector.producer.Sender;
import com.crescent.alert.core.dispatch.event.BaseEventsProvider;
import com.crescent.alert.core.dispatch.event.EventProviderFactory;
import com.crescent.alert.core.rule.RuleProvider;

public class RuleHandlerCreator {

    public static RuleHandler createRuleHandler(RuleProvider ruleProvider, Sender sender, String eventWindowType) {
        EventProviderFactory factory = new EventProviderFactory() {
            @Override
            public BaseEventsProvider createEventProvider() {
                return createEventProvider(ruleProvider, eventWindowType);
            }
        };

        RuleHandler violationHandler = new RuleViolationHandler(ruleProvider, sender);

        RuleHandler healthStatusHandler = new RuleHealthStatusHandler(ruleProvider, factory.createEventProvider(), sender);
        healthStatusHandler.setChildHandler(violationHandler);

        RuleHandler ruleHandler = new RuleRawMetricHandler(ruleProvider, factory.createEventProvider());
        ruleHandler.setChildHandler(healthStatusHandler);

        return ruleHandler;
    }

}

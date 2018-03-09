package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.core.collector.producer.LookingProducer;
import com.crescent.alert.core.dispatch.provider.memory.EventsProvider;

public class RuleHandlerProvider {

    public static RuleHandler defaultRuleHandler(LookingProducer producer) {
        return new RuleRawMetricHandler(new RuleHealthStatusHandler(new RuleViolationHandler(producer),
            new EventsProvider()), new EventsProvider());
    }

}

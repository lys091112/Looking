package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.config.PolicyInfo.Policy;
import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.collector.producer.Sender;
import com.crescent.alert.core.dispatch.event.BaseEventsProvider;
import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.core.rule.RuleProvider;
import com.crescent.alert.engine.provider.parse.RuleResult;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class RuleHealthStatusHandler extends RuleHandler {

    private BaseEventsProvider eventsProvider;
    private Sender sender;

    public RuleHealthStatusHandler(RuleProvider ruleProvider, BaseEventsProvider eventsProvider, Sender sender) {
        super(ruleProvider);
        this.sender = sender;
        this.eventsProvider = eventsProvider;
    }

    @Override
    protected Optional<AlertEvent> handle(AlertEvent event) {

        Optional<Map<String, String>> dslPeriodInfo = ruleProvider.getRuleDslPeriodInfo(event.getRuleId());
        if (!dslPeriodInfo.isPresent()) {
            log.error("Can't find ruleDurationInfo by RuleId. ruleId={}", event.getRuleId());
            return Optional.empty();
        }

        this.eventsProvider.pushEvent(event);

        List<Pair<String, RuleTemplate>> templates = ruleProvider.findFixedRuleTemplate(event.getSeverityLevel());

        RuleResult res;
        for (Pair<String, RuleTemplate> t : templates) {
            Policy policy = ruleProvider.findPolicy(t.getKey());
            res = t.getValue().getResult(event, eventsProvider, fileSeverity(policy.getSeverity(), dslPeriodInfo.get()));
            if (res.isSuccess()) {
                if (policy.isIgnoreBeforeTrigger()) {
                    event.getMetrics().put(Constants.CONTINUE_IGNORE_EVENT, "true");
                    this.eventsProvider.clear(event);
                }
                return generateHealthRuleViolateEvent(t.getKey(), event);
            }
        }

        return Optional.empty();
    }

    private Optional<AlertEvent> generateHealthRuleViolateEvent(String severity, AlertEvent event) {
        if (null != event.getMetrics()) {
            event.getMetrics().put(Constants.SEVERITY_VARIABLE, severity);
        }
        return Optional.of(new AlertEvent().setRuleId(event.getRuleId()).setStreamId(event.getStreamId())
            .setMetrics(event.getMetrics())
            .setSeverityLevel(severity).setTimestamp(event.getTimestamp()).setEventCategory(EventCategory
                .HealthViolation));
    }
}

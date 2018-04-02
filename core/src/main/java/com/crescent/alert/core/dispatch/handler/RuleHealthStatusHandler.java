package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.config.PolicyInfo.Policy;
import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.core.dispatch.provider.BaseEventsProvider;
import com.crescent.alert.core.dispatch.provider.EventProviderFactory;
import com.crescent.alert.core.domain.AlertEvent;
import com.crescent.alert.engine.provider.parse.RuleResult;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

/**
 * TODO {crescent} 添加health status 中间过程的事件存储，用来指示告警状态
 */
@Slf4j
public class RuleHealthStatusHandler extends RuleHandler {

    private BaseEventsProvider eventsProvider;

    public RuleHealthStatusHandler(AlertProvider alertProvider, EventProviderFactory factory) {
        super(alertProvider, new RuleViolationHandler(alertProvider, factory));
        this.eventsProvider = factory.createEventProvider();
    }

    @Override
    protected Optional<AlertEvent> handle(AlertEvent event) {

        Optional<Map<String, String>> dslPeriodInfo = alertProvider.getRuleManager().getRuleDslPeriodInfo(event.getRuleId());
        if (!dslPeriodInfo.isPresent()) {
            log.error("Can't find ruleDurationInfo by RuleId. ruleId={}", event.getRuleId());
            return Optional.empty();
        }

        this.eventsProvider.addEvent(event);

        List<Pair<String, RuleTemplate>> templates = alertProvider.getStateTransitionProvider()
            .findFixedRuleTemplate(event.getSeverityLevel());

        RuleResult res;
        for (Pair<String, RuleTemplate> t : templates) {
            Policy policy = alertProvider.getStateTransitionProvider().findPolicy(t.getKey());
            res = t.getValue()
                .getResult(event, eventsProvider, checkTotalMatching(dslPeriodInfo.get(), policy.isIgnoreBeforeTrigger()));
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

    private Map<String, String> checkTotalMatching(Map<String, String> durationInfo, boolean ignoreBoforeTrigger) {
        if (!ignoreBoforeTrigger) {
            Map<String, String> res = new HashMap<>();
            res.putAll(durationInfo);
            res.put(Constants.TOTAL_MATCHING, "false");
            return res;
        }
        return durationInfo;
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

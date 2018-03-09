package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.RuleManager;
import com.crescent.alert.core.StateTransitionProvider;
import com.crescent.alert.core.config.PolicyInfo.Policy;
import com.crescent.alert.core.domain.AlertEvent;
import com.crescent.alert.core.dispatch.provider.memory.EventsProvider;
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

    private EventsProvider eventsProvider;

    public RuleHealthStatusHandler(RuleHandler childHandler, EventsProvider eventsProvider) {
        super(childHandler);
        this.eventsProvider = eventsProvider;
    }

    @Override
    protected Optional<AlertEvent> handle(AlertEvent event) {

        Optional<Map<String, String>> dslPeriodInfo = RuleManager.getInstance().getRuleDslPeriodInfo(event.getRuleId());
        if (!dslPeriodInfo.isPresent()) {
            log.error("can't find ruleDurationInfo by RuleId. ruleId={}", event.getRuleId());
            return Optional.empty();
        }

        this.eventsProvider.addEvent(event);

        List<Pair<String, RuleTemplate>> templates = StateTransitionProvider.getInstance()
            .findFixedRuleTemplate(event.getSeverityLevel());

        RuleResult res;
        for (Pair<String, RuleTemplate> t : templates) {
            Policy policy = StateTransitionProvider.getInstance().findPolicy(t.getKey());
            res = t.getValue()
                .getResult(event, eventsProvider, checkTotalMatching(dslPeriodInfo.get(), policy.isIgnoreBeforeTrigger()));
            if (res.isSuccess()) {
                if (policy.isIgnoreBeforeTrigger()) {
                    event.getMetrics().put(Constants.MATCHING_IGNORE_EVENT, "true");
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

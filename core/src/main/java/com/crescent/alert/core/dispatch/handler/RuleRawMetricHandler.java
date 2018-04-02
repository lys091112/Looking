package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.config.PolicyInfo.PriorityStatus;
import com.crescent.alert.common.dto.Rule;
import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.core.RuleManager.TransGrammar;
import com.crescent.alert.core.dispatch.provider.BaseEventsProvider;
import com.crescent.alert.core.dispatch.provider.EventProviderFactory;
import com.crescent.alert.core.domain.AlertEvent;
import com.crescent.alert.engine.provider.parse.RuleResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RuleRawMetricHandler extends RuleHandler {

    private BaseEventsProvider eventsProvider;

    public RuleRawMetricHandler(AlertProvider alertProvider, EventProviderFactory factory) {
        super(alertProvider, new RuleHealthStatusHandler(alertProvider, factory));
        this.eventsProvider = factory.createEventProvider();
    }

    @Override
    protected Optional<AlertEvent> handle(AlertEvent event) {
        Optional<List<TransGrammar>> optional = alertProvider.getRuleManager().getRuleTemplates(event.getRuleId());
        Optional<Rule> ruleOptional = alertProvider.getRuleManager().getRule(event.getRuleId());
        if (!optional.isPresent() || !ruleOptional.isPresent()) {
            LOGGER.error("Can't find ruleTemplates by ruleId. ruleId={}", event.getRuleId());
            return Optional.empty();
        }

        event.setRegion(ruleOptional.get().getRegion());
        eventsProvider.addEvent(event);

        RuleResult res;
        for (TransGrammar grammar : optional.get()) {
            res = grammar.getRuleTemplate().getResult(event, eventsProvider, grammar.getParameters());
            if (res.isSuccess()) {
                return generateHealthStatusEvent(event, grammar.getPriorityStatus(), res.getContent());
            }
        }

        Optional<PriorityStatus> defaultStatus = alertProvider.getStateTransitionProvider().findDefaultStatus();
        if (!defaultStatus.isPresent()) {
            LOGGER.error("Can't find default priorityStatus!");
            return Optional.empty();
        }

        return generateHealthStatusEvent(event, defaultStatus.get(), new HashMap<>());
    }

    private Optional<AlertEvent> generateHealthStatusEvent(AlertEvent event, PriorityStatus priorityStatus,
        Map<String, String> content) {
        // 存储事件的severity， 用于告警状态转换计算
        content.put(Constants.SEVERITY_VARIABLE, priorityStatus.getSeverity());
        return Optional.of(new AlertEvent().setEventCategory(EventCategory.HealthStatus)
            .setSeverityLevel(priorityStatus.getSeverity()).setMetrics(content)
            .setRuleId(event.getRuleId()).setStreamId(event.getStreamId())
            .setTimestamp(event.getTimestamp()));
    }
}

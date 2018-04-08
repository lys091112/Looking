package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.config.PolicyInfo.OriginStatus;
import com.crescent.alert.common.enums.EventCategory;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.dispatch.event.BaseEventsProvider;
import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.core.rule.RuleProvider;
import com.crescent.alert.core.rule.RuleProvider.TransGrammar;
import com.crescent.alert.engine.provider.parse.RuleResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RuleRawMetricHandler extends RuleHandler {

    private BaseEventsProvider eventsProvider;

    public RuleRawMetricHandler(RuleProvider ruleProvider, BaseEventsProvider eventsProvider) {
        super(ruleProvider);
        this.eventsProvider = eventsProvider;
    }

    @Override
    protected Optional<AlertEvent> handle(AlertEvent event) {
        Optional<List<TransGrammar>> optional = ruleProvider.getRuleTemplates(event.getRuleId());
        if (!optional.isPresent()) {
            LOGGER.error("Can't find ruleTemplates by ruleId. ruleId={}", event.getRuleId());
            return Optional.empty();
        }

        eventsProvider.sink(event);

        RuleResult res;
        for (TransGrammar grammar : optional.get()) {
            res = grammar.getRuleTemplate().getResult(event, eventsProvider, fileSeverity(grammar.getOriginStatus()
                .getOriginSeverity(), grammar.getParameters()));
            if (res.isSuccess()) {
                return generateHealthStatusEvent(event, grammar.getOriginStatus(), res.getContent());
            }
        }

        Optional<OriginStatus> defaultStatus = ruleProvider.findDefaultStatus();
        if (!defaultStatus.isPresent()) {
            LOGGER.error("Can't find default priorityStatus!");
            return Optional.empty();
        }

        return generateHealthStatusEvent(event, defaultStatus.get(), new HashMap<>());
    }

    private Optional<AlertEvent> generateHealthStatusEvent(AlertEvent event, OriginStatus originStatus,
        Map<String, String> content) {
        // 存储事件的severity， 用于告警状态转换计算
        content.put(Constants.SEVERITY_VARIABLE, originStatus.getOriginSeverity());
        return Optional.of(new AlertEvent().setEventCategory(EventCategory.HealthStatus)
            .setSeverityLevel(originStatus.getOriginSeverity()).setMetrics(content)
            .setRuleId(event.getRuleId()).setStreamId(event.getStreamId())
            .setTimestamp(event.getTimestamp()));
    }
}

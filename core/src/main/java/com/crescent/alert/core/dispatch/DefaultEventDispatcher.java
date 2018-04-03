package com.crescent.alert.core.dispatch;

import com.crescent.alert.common.dto.Rule;
import com.crescent.alert.core.dispatch.filter.DisOrderEventFilter;
import com.crescent.alert.core.dispatch.filter.EventTtlFilter;
import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.core.rule.AbstractRuleProvider;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息转发路由
 */
public class DefaultEventDispatcher extends EventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEventDispatcher.class);

    private AbstractRuleProvider ruleProvider;

    public DefaultEventDispatcher(AbstractRuleProvider alertProvider, RuleHandler ruleHandler) {
        Preconditions.checkNotNull(alertProvider, "rule handler can't be null");
        Preconditions.checkNotNull(ruleHandler, "ruleProvider is null");
        this.ruleProvider = alertProvider;
        this.handler = ruleHandler;

        addEventFilters(Arrays.asList(new DisOrderEventFilter(), new EventTtlFilter()));
    }

    @Override
    public void dispatcher(AlertEvent event) {
        /**
         *  添加一些公共的过滤条件(filter模式）
         *  格式正确性验证
         */
        if (!filters.stream().allMatch(f -> f.filter(event))) {
            return;
        }

        Set<String> ruleIds = ruleProvider.getRuleIdsByStreamId(event.getStreamId());
        if (CollectionUtils.isEmpty(ruleIds)) {
            LOGGER.warn("invalid event! can't find ruleInfo by event streamId, streamId:{}", event.getStreamId());
            return;
        }
        for (String ruleId : ruleIds) {
            if (!fillEvent(event, ruleId)) {
                continue;
            }
            handler.execute(event);

        }

        ruleIds.stream().forEach(ruleId -> {
        });
    }

    /**
     * 填充告警规则基本数据
     */
    private boolean fillEvent(AlertEvent event, String ruleId) {
        Optional<Rule> ruleOptional = ruleProvider.getRule(event.getRuleId());
        if (!ruleOptional.isPresent()) {
            LOGGER.error("Can't find ruleInfo, May have been deleted. ruleId={}", event.getRuleId());
            return false;
        }
        event.setRegion(ruleOptional.get().getRegion());
        event.setRuleId(ruleId);
        return true;
    }
}

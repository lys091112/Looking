package com.crescent.alert.core.dispatch;

import com.crescent.alert.core.AlertProvider;
import com.crescent.alert.core.dispatch.filter.AlertFilter;
import com.crescent.alert.core.dispatch.filter.DisOrderEventFilter;
import com.crescent.alert.core.dispatch.filter.EventTtlFilter;
import com.crescent.alert.core.dispatch.handler.RuleHandler;
import com.crescent.alert.core.domain.AlertEvent;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息转发路由
 */
public class DefaultEventDispatcher extends EventDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEventDispatcher.class);

    private List<AlertFilter> filters = new ArrayList<>();

    private AlertProvider alertProvider;

    public DefaultEventDispatcher(AlertProvider alertProvider, RuleHandler ruleHandler) {
        Preconditions.checkNotNull(alertProvider, "rule handler can't be null");
        Preconditions.checkNotNull(ruleHandler, "alertProvider is null");
        this.alertProvider = alertProvider;
        this.handler = ruleHandler;
        filters.add(new DisOrderEventFilter());
        filters.add(new EventTtlFilter());
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

        Set<String> ruleIds = alertProvider.getRuleManager().getRuleIdsByStreamId(event.getStreamId());
        if (CollectionUtils.isEmpty(ruleIds)) {
            LOGGER.warn("invalid event! can't find ruleInfo by event streamId, streamId:{}", event.getStreamId());
            return;
        }

        ruleIds.stream().forEach(ruleId -> {
            event.setRuleId(ruleId);
            handler.execute(event);
        });
    }
}

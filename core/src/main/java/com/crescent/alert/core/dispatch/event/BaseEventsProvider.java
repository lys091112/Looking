package com.crescent.alert.core.dispatch.event;

import static com.crescent.alert.common.util.Constants.CONTINUE_IGNORE_EVENT;

import com.crescent.alert.core.rule.AbstractRuleProvider;
import com.crescent.alert.core.rule.StateTransitionProvider.SeverityParam;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.event.IEventsProvider;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseEventsProvider implements IEventsProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEventsProvider.class);

    private AbstractRuleProvider ruleProvider;

    public BaseEventsProvider() {
    }

    public abstract boolean sink(Event event);

    public void clear(Event event) {
        this.clear(event.getStreamId());
        sink(event);
    }

    public abstract void clear(String streamId);

    /**
     * events provider 的类型标识, 用来制定event provider 数据存储的底层实现
     */
    public abstract String eventWindowType();

    public void setRuleProvider(AbstractRuleProvider ruleProvider) {
        Preconditions.checkNotNull(ruleProvider, "RuleProvider is null");
        this.ruleProvider = ruleProvider;
    }

    protected int bufferSize(String streamId) {
        return ruleProvider.getBufferSizeByStreamId(streamId);
    }

    @Override
    public List<Event> findEventsByFixedCount(String streamId, String severity, int count) {
        SeverityParam param = ruleProvider.severityParam(severity);
        if (null == param) {
            LOGGER.error("Can't find severity info! streamId={}, severity={}", streamId, severity);
            return Collections.emptyList();
        }
        List<Event> events = doFindEventsByFixedCount(streamId, count);
        if (param.isTotalMatch() && !fitCountBuffer(events, count)) {
            return Collections.emptyList();
        }

        return ignoreSpecialEvent(events, param);
    }

    private boolean fitCountBuffer(List<Event> events, long count) {
        return CollectionUtils.isNotEmpty(events) && events.size() >= count;
    }

    protected abstract List<Event> doFindEventsByFixedCount(String streamId, int count);


    @Override
    public List<Event> findEventsByTimeWindow(String streamId, String severity, long startTime, long endTime) {
        SeverityParam param = ruleProvider.severityParam(severity);
        if (null == param) {
            LOGGER.error("Can't find severity info! streamId={}, severity={}", streamId, severity);
            return Collections.emptyList();
        }
        List<Event> events = doFindEventsByTimeWindow(streamId, startTime, endTime);

        if (param.isTotalMatch() && !fitTimeBuffer(events, startTime, endTime)) {
            return Collections.emptyList();
        }
        return ignoreSpecialEvent(events, param);
    }

    // TODO
    private boolean fitTimeBuffer(List<Event> events, long startTime, long endTime) {
        if (CollectionUtils.isEmpty(events)) {
            return false;
        }
        return events.get(0).getTimestamp() <= startTime + TimeUnit.MINUTES.toMillis(1) &&
            events.get(events.size() - 1).getTimestamp() >= endTime;
    }

    protected abstract List<Event> doFindEventsByTimeWindow(String streamId, long startTime1, long endTime);


    // 忽略掉由CONTINUE_IGNORE_EVENT标识的事件
    private List<Event> ignoreSpecialEvent(List<Event> events, SeverityParam param) {
        if (param.isIgnoreContinueTrigger()) {
            events = events.stream()
                .filter(e -> !"true".equalsIgnoreCase(e.getMetrics().getOrDefault(CONTINUE_IGNORE_EVENT, "")))
                .collect(Collectors.toList());
        }
        return events;
    }
}

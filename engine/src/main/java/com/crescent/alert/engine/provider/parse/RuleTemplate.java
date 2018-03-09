package com.crescent.alert.engine.provider.parse;

import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.AliasOperand;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.crescent.alert.engine.provider.event.AbstractEventsProvider;
import com.crescent.alert.engine.provider.event.EventsFinder;
import com.crescent.alert.engine.provider.event.boundingBox.BoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.SizeBoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.TimeBoundingBox;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@Builder
public class RuleTemplate {

    private static Logger logger = LoggerFactory.getLogger("RuleTemplate");
    private final List<String> streams;
    private final IBooleanExpression whereClause;
    private final List<Operand> columns;
    private final List<NameOperand> filterKeys;

    private final EventsFinder eventsFinder;

    public RuleResult getResult(Event currEvent, AbstractEventsProvider provider, Map<String, String> params) {
        try {
            List<Event> events = findEvents(currEvent, provider, params);
            if (CollectionUtils.isEmpty(events)) {
                return new RuleResult(false);
            }
            return getResult(currEvent, filterByKeys(currEvent, events), params);
        } catch (IllegalArgumentException e) {
            logger.error(
                "Error occurred when obtaining the 'whereClause' success. Rule id:" + currEvent.getRuleId() + "\r\n"
                    + e.getMessage(), e);
            return new RuleResult(false);
        }
    }

    /**
     * 获取该规则计算的持续时间, 并返回dsl中的单位记录
     */
    public Pair<Long, TimeUnit> getBoundingPeriodWithTimeUnit(Map<String, String> params) {
        BoundingBox boundingBox = this.eventsFinder.getBoundingBox();
        if (boundingBox instanceof TimeBoundingBox) {
            TimeBoundingBox timeBoundingBox = (TimeBoundingBox) boundingBox;
            return Pair.of(timeBoundingBox.getPeriodWithSameTimeUnit(params), timeBoundingBox.getBufferUnit());

        } else if (boundingBox instanceof SizeBoundingBox) {
            return Pair.of(((SizeBoundingBox) boundingBox).getSize(), null);

        }
        throw new UnsupportedOperationException("unknown bounding box type!");
    }

    private List<Event> findEvents(Event currEvent, AbstractEventsProvider provider, Map<String, String> params)
        throws IllegalArgumentException {
        if (null == eventsFinder) {
            return Collections.emptyList();
        }
        return eventsFinder.backwardFrom(currEvent, params, provider);
    }

    public RuleResult getResult(Event currEvent, List<Event> events, Map<String, String> params) {
        try {
            List<Event> fileterEvents = filterByKeys(currEvent, events);
            if (this.getWhereClause().getValue(new ProcessingContext(currEvent, fileterEvents, params))) {
                return new RuleResult(true, getContext(currEvent, fileterEvents, params));
            }
        } catch (NotFoundException e) {
            // 对于不存在的监控字段,不触发告警，直接返回false
            logger.error(e.getMessage(), e);
        }
        return new RuleResult(false);
    }

    private List<Event> filterByKeys(Event currEvent, List<Event> events) {
        if (filterKeys != null) {
            return events.stream().filter(e -> {
                boolean filter = true;
                for (Operand operand : filterKeys) {
                    filter = filter
                        && (operand.getValue(new ProcessingContext(e, Collections.singletonList(e), null)).
                        equals(operand.getValue(new ProcessingContext(currEvent, Collections.singletonList(
                            events.get(events.size() - 1)), null))));
                }
                return filter;
            }).collect(Collectors.toList());
        }
        return events;
    }

    public Map<String, String> getContext(Event currEvent, List<Event> events, Map<String, String> params) {
        return this.columns.stream().collect(Collectors.toMap(op -> {
            if (NameOperand.class.isInstance(op)) {
                return ((NameOperand) op).getFieldName();
            } else if (AliasOperand.class.isInstance(op)) {
                return ((AliasOperand) op).getAlias();
            }
            throw new RuntimeException("use an aggregation function as a column should specify an alias: " + op);
        }, op -> {
            try {
                return op.getValue(new ProcessingContext(currEvent, filterByKeys(currEvent, events), params))
                    .toString();
            } catch (NotFoundException e) {
                // dsl使用不存在的字段做select查询条件的时候, 返回空值.
                return "";
            }
        }));
    }

    @Override
    public String toString() {
        return "RuleTemplate [streams=" + streams + "]";
    }

    public Set<AggregationOperandBase> getAggregationOperands() {
        Set<AggregationOperandBase> operands = columns.stream().map(op -> op.getAggregationOperands())
            .reduce((s1, s2) -> {
                s1.addAll(s2);
                return s1;
            }).orElseGet(HashSet::new);
        operands.addAll(whereClause.getAggregationOperands());
        return operands;
    }
}

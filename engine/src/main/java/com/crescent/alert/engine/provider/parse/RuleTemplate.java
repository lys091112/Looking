package com.crescent.alert.engine.provider.parse;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.AliasOperand;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.dynamic.PeriodOperand;
import com.crescent.alert.engine.provider.event.EventsFinder;
import com.crescent.alert.engine.provider.event.IEventsProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

    private final List<PeriodOperand> periodOperands;

    public boolean getResult(Event currEvent, IEventsProvider provider, Map<String, String> params) {
        try {
            List<Event> events = findEvents(currEvent, provider, params);
            return getResult(currEvent, events, params);
        } catch (IllegalArgumentException e) {
            logger.error("Err'or occurred when obtaining the 'whereclause' result. Rule id:" + currEvent.getStreamId() + "\r\n" + e.getMessage(), e);
            return false;
        }
    }

    private List<Event> findEvents(Event currEvent, IEventsProvider provider, Map<String, String> params)
        throws IllegalArgumentException {
        if (null == eventsFinder) {
            return Collections.emptyList();
        }
        eventsFinder.setProvider(provider);
        return eventsFinder.backwardFrom(currEvent, params);
    }

    public boolean getResult(Event currEvent, List<Event> events, Map<String, String> params) {
        try {
            return this.getWhereClause().getValue(currEvent, filterByKeys(currEvent, events), params);
        } catch (NotFoundException e) {
            // dsl使用不存在的字段做触发告警条件的时候, 记录异常同时不触发告警
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private List<Event> filterByKeys(Event currEvent, List<Event> events) {
        if (filterKeys != null) {
            return events.stream().filter(e -> {
                boolean filter = true;
                for (Operand operand : filterKeys) {
                    filter = filter
                        && (operand.getValue(e, Collections.singletonList(e), null).
                        equals(operand.getValue(currEvent, Collections.singletonList(
                            events.get(events.size() - 1)), null)));
                }
                return filter;
            }).collect(Collectors.toList());
        }
        return events;
    }

    public Map<String, String> getContext(Event currEvent, IEventsProvider provider, Map<String, String> params) {
        List<Event> events = findEvents(currEvent, provider, params);
        try {
            return getContext(currEvent, events, params);
        } catch (IllegalArgumentException e) {
            logger.error("Error occurred when obtaining the selected context. Rule Id:" + currEvent.getStreamId() + "\r\n" + e.getMessage(), e);
            return new HashMap<>();
        }
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
                return op.getValue(currEvent, filterByKeys(currEvent, events), params).toString();
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

    public Set<AbstractAggregationOperand> getAggregationOperands() {
        Set<AbstractAggregationOperand> operands = columns.stream().map(op -> op.getAggregationOperands()).reduce((s1, s2) -> {
            s1.addAll(s2);
            return s1;
        }).orElseGet(HashSet::new);
        operands.addAll(whereClause.getAggregationOperands());
        return operands;
    }
}

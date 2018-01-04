package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PreviousOperand extends AbstractAggregationOperand {

    public PreviousOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public PreviousOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public PreviousOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }


    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {

        Class metricValueClazz = getOperand().getValue(currEvent, events, parameters).getClass();

        Stream<Event> stream = events.stream();

        // 和其他聚合函数有一点区别: 当前事件不能被过滤条件处理
        stream = stream.filter(e -> !e.equals(events.get(events.size() - 1)));

        if(getPredicate() != null) {
            stream = stream.filter(e -> getPredicate().getValue(e, Arrays.asList(e), null));
        }

        List<Event> filtered = stream.collect(Collectors.toList());

        // 如果无法获取到当前event的前一个event, 需要根据当前event中对应metric的类型返回默认值.
        if(filtered.isEmpty()) {
            if(metricValueClazz.equals(String.class))
                return "";
            else
                return 0.0;
        }
        return getOperand().getValue(filtered.get(filtered.size() - 1), filtered, parameters);

    }

    @Override
    public Object getValue(ProcessingContext context) {
        Class metricValueClazz = getOperand().getValue(context).getClass();

        List<Event> filtered = context.getPreviousEvents().stream()
            .filter(e -> !e.equals(context.getCurrentEvent()))
            .filter(e -> null == getPredicate() || getPredicate().getValue(new ProcessingContext(e, Arrays.asList(e), null)))
            .collect(Collectors.toList());

        // 如果无法获取到当前event的前一个event, 需要根据当前event中对应metric的类型返回默认值.
        if(filtered.isEmpty()) {
            return metricValueClazz.equals(String.class) ? "" : 0.0;
        }
        return getOperand().getValue(new ProcessingContext(filtered.get(filtered.size() - 1), filtered, context.getThreshold()));
    }

    @Override
    public String toReadableString()
    {
        return String.format("PREV(%s)", this.getInnerOperand().toReadableString());
    }

    @Override
    protected String getAggregationType() {
        return "PERV";
    }

}

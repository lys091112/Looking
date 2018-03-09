package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PreviousOperand extends AggregationOperandBase {

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
    public Object getValue(ProcessingContext context) {
        Class metricValueClazz = getOperand().getValue(context).getClass();

        List<Event> filtered = context.getPreviousEvents().stream()
            .filter(e -> !e.equals(context.getCurrentEvent()))
            .filter(e -> null == getPredicate() || getPredicate()
                .getValue(new ProcessingContext(e, Arrays.asList(e), null)))
            .collect(Collectors.toList());

        // 如果无法获取到当前event的前一个event, 需要根据当前event中对应metric的类型返回默认值.
        if (filtered.isEmpty()) {
            return metricValueClazz.equals(String.class) ? "" : 0.0;
        }
        return getOperand()
            .getValue(new ProcessingContext(filtered.get(filtered.size() - 1), filtered, context.getThreshold()));
    }

    @Override
    public String toReadableString() {
        return String.format("PREV(%s)", this.getInnerOperand().toReadableString());
    }

    @Override
    protected String getAggregationType() {
        return "PERV";
    }

}

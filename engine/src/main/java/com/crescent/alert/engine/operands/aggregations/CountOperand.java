package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountOperand extends AbstractAggregationOperand {

    public CountOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public CountOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public CountOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {

        Long value = filter(events, parameters).count();

        return value.doubleValue();

    }

    @Override
    public Object getValue(ProcessingContext context) {
        Long value = filter(context.getPreviousEvents(), context.getThreshold()).count();
        return value.doubleValue();
    }


    @Override
    protected String getAggregationType() {
        return "COUNT";
    }
}


package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MinOperand extends AbstractAggregationOperand {

    public MinOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public MinOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public MinOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {

        final Operand innerOpt = this.getOperand();
        Event value = filterForCalculus(filter(events, parameters), parameters).min((event, t1) -> {
            BigDecimal b1 = new BigDecimal(innerOpt.getValue(event, Arrays.asList(event), parameters).toString());
            BigDecimal b2 = new BigDecimal(innerOpt.getValue(t1, Arrays.asList(t1), parameters).toString());
            return b1.compareTo(b2);
        }).orElse(null);

        if (value == null)
            return 0.0;

        return innerOpt.getValue(value, Arrays.asList(value), parameters);

    }

    @Override
    public Object getValue(ProcessingContext context) {
        final Map<String,String> parameters = context.getThreshold();
        final Operand innerOpt = this.getOperand();
        Event value = filterForCalculus(filter(context.getPreviousEvents(), parameters), parameters).min((event, t1) -> {
            BigDecimal b1 = new BigDecimal(innerOpt.getValue(new ProcessingContext(event, Arrays.asList(event), parameters)).toString());
            BigDecimal b2 = new BigDecimal(innerOpt.getValue(new ProcessingContext(t1, Arrays.asList(t1), parameters)).toString());
            return b1.compareTo(b2);
        }).orElse(null);

        if (value == null)
            return 0.0;

        return innerOpt.getValue(new ProcessingContext(value, Arrays.asList(value), parameters));
    }

    @Override
    protected String getAggregationType()
    {
        return "MIN";
    }

}

package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SumOperand extends AbstractAggregationOperand {

    public SumOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public SumOperand(IBooleanExpression predicate)
    {
        super(predicate);
    }

    public SumOperand(Operand innerOperand, IBooleanExpression predicate)
    {
        super(innerOperand, predicate);
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {

        final Operand innerOpt = this.getOperand();
        return filterForCalculus(filter(events, parameters), parameters).reduce(
                0D,
                (d, r) -> d + (Double) innerOpt.getValue(r, Arrays.asList(r), parameters),
                (d1, d2) -> d1 + d2);
    }

    @Override
    public Object getValue(ProcessingContext context) {
        Map<String, String> parameters= context.getThreshold();
        final Operand innerOpt = this.getOperand();
        return filterForCalculus(filter(context.getPreviousEvents(), parameters), parameters).reduce(
            0D,
            (d, r) -> d + (Double) innerOpt.getValue(new ProcessingContext(r, Arrays.asList(r), parameters)),
            (d1, d2) -> d1 + d2);
    }

    @Override
    protected String getAggregationType() {
        return "SUM";
    }

}

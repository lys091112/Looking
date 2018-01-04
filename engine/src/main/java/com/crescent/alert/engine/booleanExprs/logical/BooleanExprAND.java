package com.crescent.alert.engine.booleanExprs.logical;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * and operator for boolean expressions.
 *
 */
@Data
@AllArgsConstructor
public class BooleanExprAND implements IBooleanExpression {
    private final IBooleanExpression left;
    private final IBooleanExpression right;

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        return left.getValue(currEvent, events, parameters) && right.getValue(currEvent, events, parameters);
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        return left.getValue(context) && right.getValue(context);
    }

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        Set<AbstractAggregationOperand> aggregationOperands = left.getAggregationOperands();
        aggregationOperands.addAll(right.getAggregationOperands());
        return aggregationOperands;
    }

    @Override
    public String toReadableString()
    {
        return String.format("(%s) and (%s)", this.getLeft().toReadableString(), this.getRight().toReadableString());
    }
}
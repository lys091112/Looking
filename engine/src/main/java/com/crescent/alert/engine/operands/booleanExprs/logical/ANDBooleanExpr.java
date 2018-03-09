package com.crescent.alert.engine.operands.booleanExprs.logical;

import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * and operator for boolean expressions.
 */
@Data
@AllArgsConstructor
public class ANDBooleanExpr implements IBooleanExpression {

    private final IBooleanExpression left;
    private final IBooleanExpression right;

    @Override
    public Boolean getValue(ProcessingContext context) {
        return left.getValue(context) && right.getValue(context);
    }

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        Set<AggregationOperandBase> aggregationOperands = left.getAggregationOperands();
        aggregationOperands.addAll(right.getAggregationOperands());
        return aggregationOperands;
    }

    @Override
    public String toReadableString() {
        return String.format("(%s) and (%s)", this.getLeft().toReadableString(), this.getRight().toReadableString());
    }
}

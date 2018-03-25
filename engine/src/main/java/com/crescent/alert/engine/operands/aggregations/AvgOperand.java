package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;

public class AvgOperand extends AggregationOperandBase {

    public AvgOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public AvgOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public AvgOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }

    @Override
    public Object getValue(ProcessingContext context) {
        return filterForCalculate(context)
            .mapToDouble(e -> e)
            .average().orElse(0);
    }

    @Override
    protected String getAggregationType() {
        return "AVG";
    }
}

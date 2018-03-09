package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Arrays;
import java.util.Map;

public class SumOperand extends AggregationOperandBase {

    public SumOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public SumOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public SumOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }

    @Override
    public Object getValue(ProcessingContext context) {
        return filterForCalculate(context)
            .reduce(0D, (d, r) -> d + r, (d1, d2) -> d1 + d2);
    }

    @Override
    protected String getAggregationType() {
        return "SUM";
    }

}

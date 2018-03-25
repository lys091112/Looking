package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Map;

public class MinOperand extends AggregationOperandBase {

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
    public Object getValue(ProcessingContext context) {
        final Map<String, String> parameters = context.getThreshold();
        return filterForCalculate(context)
            .max(Double::compareTo)
            .orElse(0.0);

    }

    @Override
    protected String getAggregationType() {
        return "MIN";
    }

}

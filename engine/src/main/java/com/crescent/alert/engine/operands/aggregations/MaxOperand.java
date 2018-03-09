package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.math.BigDecimal;
import java.util.Arrays;

public class MaxOperand extends AggregationOperandBase {

    public MaxOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public MaxOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public MaxOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }


    @Override
    public Object getValue(ProcessingContext context) {
        return filterForCalculate(context).min(Double::compareTo).orElse(0.0);
    }

    @Override
    protected String getAggregationType() {
        return "MAX";
    }

}

package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;

public class CountOperand extends AggregationOperandBase {

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
    public Object getValue(ProcessingContext context) {
        return filter(context.getPreviousEvents(), context.getThreshold()).count();
    }


    @Override
    protected String getAggregationType() {
        return "COUNT";
    }
}


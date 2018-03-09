package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Set;

public interface Operand<R> {

    R getValue(ProcessingContext context);

    /**
     * get possible aggregation operands
     */
    Set<AggregationOperandBase> getAggregationOperands();


    String toReadableString();
}

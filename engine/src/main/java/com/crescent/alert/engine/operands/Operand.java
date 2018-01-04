package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.aggregations.AbstractAggregationOperand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Operand<R> {

    @Deprecated
    R getValue(Event currEvent, List<Event> events, Map<String, String> parameters);

    R getValue(ProcessingContext context);

    /**
     * get possible aggregation operands
     */
    Set<AbstractAggregationOperand> getAggregationOperands();

    String toReadableString();
}

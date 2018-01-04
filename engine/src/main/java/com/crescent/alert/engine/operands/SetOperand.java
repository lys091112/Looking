package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetOperand implements Operand<Object> {

    private Set<Object> value;

    public SetOperand(Set<Object> elements) {
        this.value = elements;
    }

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        return value;
    }

    @Override
    public Object getValue(ProcessingContext context)
    {
        return value;
    }
}

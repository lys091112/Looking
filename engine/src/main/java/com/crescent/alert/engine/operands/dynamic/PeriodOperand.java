package com.crescent.alert.engine.operands.dynamic;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.aggregations.AbstractAggregationOperand;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.crescent.alert.engine.operands.dynamic.MoMDuration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

@EqualsAndHashCode
public class PeriodOperand implements Operand<Object> {

    @Getter
    private MoMDuration duration;

    public PeriodOperand(MoMDuration duration) {
        this.duration = duration;
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        if (duration == null) {
            throw new IllegalArgumentException("Illegal initial status, duration shouldn't be empty");
        }
        Event event = currEvent == null ? events.get(events.size() - 1) : currEvent;
        return null;
//        return baseLineComputeService.get(event.getKey(), duration.getMetricName(), event.getTimestamp(), BaseLineComputeType.AVG)
//            .orElseThrow(() -> new NotFoundException("No history baseline data."));
    }

    @Override
    public Object getValue(ProcessingContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toReadableString() {
        throw new NotImplementedException("");
    }
}

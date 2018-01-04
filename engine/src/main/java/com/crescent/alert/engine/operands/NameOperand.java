package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
public class NameOperand implements Operand<Object> {

    private final String streamName;
    private final String fieldName;

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString() {
        return StringUtils.isEmpty(streamName) ? fieldName : String.format("%s.%s", streamName, fieldName);
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) throws NotFoundException {
        String valueStr = currEvent != null ? currEvent.getMetrics().get(fieldName) :
            events.get(events.size() - 1).getMetrics().get(fieldName);

        if (valueStr == null) {
            throw new NotFoundException("field:[" + fieldName +
                "] in event metrics set: " + events.get(events.size() - 1).getMetrics() + ".");
        }
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return valueStr; // 支持字符串作为metric值
        }
    }

    @Override
    public Object getValue(ProcessingContext context) {
        final Event event = context.getCurrentEvent();
        Optional<String> result = event.readValue(this.fieldName);
        if (!result.isPresent()) {
            result = event.readValue(getFullName());
        }
        return result.orElse(null);
    }

    private String getFullName() {
        return String.format("%s.%s", streamName, fieldName);
    }
}

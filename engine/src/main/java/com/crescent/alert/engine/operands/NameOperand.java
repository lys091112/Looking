package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
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
    public Set<AggregationOperandBase> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString() {
        return StringUtils.isEmpty(streamName) ? fieldName : String.format("%s.%s", streamName, fieldName);
    }

    @Override
    public Object getValue(ProcessingContext context) {
        String valueStr = context.getCurrentEvent().getMetrics().get(fieldName);

        if (valueStr == null) {
            throw new NotFoundException("field:[" + fieldName +
                "] in event metrics set: " + context.getCurrentEvent().getMetrics() + ".");
        }
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return valueStr;
        }
    }
}

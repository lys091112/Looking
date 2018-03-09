package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;

@Data
@AllArgsConstructor
public class ParenthesisOperand implements Operand<Object> {

    private String fieldName;

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString() {
        throw new NotImplementedException("");
    }

    @Override
    public Object getValue(ProcessingContext context) {
        Map<String, String> parameters = context.getThreshold();
        if (fieldName.startsWith("?")) {
            fieldName = fieldName.substring(1);
        }
        if (parameters == null || parameters.size() == 0 || !parameters.containsKey(fieldName)) {
            throw new NotFoundException("field:[" + fieldName + "] in params set: " + parameters.toString() + ".");
        }
        String valueStr = parameters.get(fieldName);
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return valueStr;
        }
    }


}

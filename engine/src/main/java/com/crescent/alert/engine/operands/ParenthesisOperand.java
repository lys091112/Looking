package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
import java.util.List;
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
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString() {
        throw new NotImplementedException("");
    }

    @Override
    public Object getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        fieldName = fieldName.replace("?", "");
        if (parameters == null || parameters.size() == 0 || !parameters.containsKey(fieldName)) {
            throw new NotFoundException("field:[" + fieldName +
                "] in params set: " + parameters.toString() + ".");
        }
        String valueStr = parameters.get(fieldName);
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return valueStr; // 支持字符串作为metric值
        }
    }

    // TODO 替换的应该是name开头的第一个问号，name中的问号不应被处理
    @Override
    public Object getValue(ProcessingContext context) {
        Map<String, String> parameters =  context.getThreshold();
        fieldName = fieldName.replace("?", "");
        if (parameters == null || parameters.size() == 0 || !parameters.containsKey(fieldName)) {
            throw new NotFoundException("field:[" + fieldName +
                "] in params set: " + parameters.toString() + ".");
        }
        String valueStr = parameters.get(fieldName);
        try {
            return Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            return valueStr; // 支持字符串作为metric值
        }
    }

}

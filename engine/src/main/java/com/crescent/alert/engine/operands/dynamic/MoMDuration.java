package com.crescent.alert.engine.operands.dynamic;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.aggregations.AbstractAggregationOperand;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoMDuration {

    private Operand metricOperand;

    private Duration windowTime;

    private Duration cycleTime;

    public String getMetricName() {
        NameOperand nameOperand;
        if (metricOperand instanceof NameOperand) {
            nameOperand = NameOperand.class.cast(metricOperand);
        } else {
            nameOperand = NameOperand.class.cast(AbstractAggregationOperand.class.cast(metricOperand).getOperand());
        }
        return nameOperand.getFieldName();
    }
}

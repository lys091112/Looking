package com.crescent.alert.engine.provider.event.boundingBox;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.ParenthesisOperand;
import com.crescent.alert.engine.operands.primitives.IntegerOperand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeBoundingBox implements BoundingBox {

    private final Operand operand;
    private final TimeUnit timeUnit;

    public TimeBoundingBox(Operand operand, TimeUnit timeUnit) {
        this.operand = operand;
        this.timeUnit = timeUnit;
    }

    /**
     * 获取该timeBoundingBox的duration对象
     */
    public Duration getDuration(Map<String, String> param) {
        return Duration.ofMillis(timeUnit.toMillis(1) * value(param));
    }

    public TimeUnit getBufferUnit() {
        return timeUnit;
    }

    /**
     * 获取与该BoundingBox相同timeUnit的原始值
     */
    public long getPeriodWithSameTimeUnit(Map<String, String> param) {
        return value(param);
    }

    private int value(Map<String, String> param) {
        int value;
        if (operand instanceof IntegerOperand) {
            value = ((IntegerOperand) operand).getValue().intValue();
        } else if (operand instanceof ParenthesisOperand) {
            Object tmp = operand.getValue(new ProcessingContext(null, null, param));
            value = tmp instanceof String ? 0 : ((Double) tmp).intValue();
        } else {
            throw new IllegalArgumentException("invalid timeBoundingBox operand! operand class:" + operand.getClass()
                .getName());
        }
        return value;
    }

}

package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.Event;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class BitwiseOrOperand extends AbstractBitwiseOperand
{

    public BitwiseOrOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Double getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Double left = (Double) this.getLeft().getValue(currEvent, events, parameters);
        Double right = (Double) this.getRight().getValue(currEvent, events, parameters);

        int value = (left == null ? 0 : left.intValue()) | (right == null ? 0 : right.intValue());
        return Double.parseDouble(value + "");
    }

    @Override
    protected int operateResult(int left, int right) {
        return left | right;
    }

    @Override
    protected String getArithmeticName()
    {
        return "|";
    }
}

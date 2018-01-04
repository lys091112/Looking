package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public class PlusOperand extends AbstractConstantOperand
{
    public PlusOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Double getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Double right = (Double) this.getRight().getValue(currEvent, events, parameters);
        Double left = (Double) this.getLeft().getValue(currEvent, events, parameters);
        return (left == null ? 0.0 : left) + (right == null ? 0.0 : right);
    }

    @Override
    protected Double operateResult(Double left, Double right) {
        return left + right;
    }

    @Override
    protected String getArithmeticName()
    {
        return "+";
    }

}

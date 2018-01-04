package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import sun.jvm.hotspot.jdi.IntegerTypeImpl;

public abstract class AbstractBitwiseOperand extends AbstractArithmeticOperand<Number, Number, Double> {

    public AbstractBitwiseOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Double getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected BiFunction<Number, Number, Double> getArithmeticOperation() {
        return (left, right) -> Double.parseDouble(operateResult(null == left ? 0 : left.intValue() , null == right ? 0 : right.intValue()) + "");
    }

    protected  abstract int operateResult(int left , int right);

}

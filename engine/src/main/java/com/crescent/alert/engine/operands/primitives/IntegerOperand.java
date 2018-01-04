package com.crescent.alert.engine.operands.primitives;

public class IntegerOperand extends AbstractPrimitiveOperand<Double>
{
    public IntegerOperand(Long value) {
       super(Double.valueOf(value));
    }
}

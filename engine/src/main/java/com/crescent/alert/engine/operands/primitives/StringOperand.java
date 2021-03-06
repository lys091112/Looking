package com.crescent.alert.engine.operands.primitives;

public class StringOperand extends PrimitiveOperandBase<String> {

    public StringOperand(String value) {
        super(value);
    }

    @Override
    public String toReadableString() {
        return value == null ? "null" : String.format("'%s'", value.toString());
    }
}

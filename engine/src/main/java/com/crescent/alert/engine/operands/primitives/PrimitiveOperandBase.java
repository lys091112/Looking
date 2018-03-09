package com.crescent.alert.engine.operands.primitives;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;

public abstract class PrimitiveOperandBase<T> implements Operand<T> {

    protected T value;

    public T getValue() {
        return value;
    }

    public PrimitiveOperandBase(T value) {
        this.value = value;
    }

    @Override
    public T getValue(ProcessingContext context) {
        return value;
    }

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        return Sets.newHashSet();
    }

    @Override
    public String toReadableString() {
        return value == null ? "null" : value.toString();
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.getClass().getSimpleName(), this.toReadableString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            final PrimitiveOperandBase operand = (PrimitiveOperandBase) obj;
            return Objects.equals(this.getValue(), operand.getValue());
        }
        return false;
    }
}

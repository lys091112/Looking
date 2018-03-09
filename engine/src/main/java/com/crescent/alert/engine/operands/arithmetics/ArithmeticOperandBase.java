package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class ArithmeticOperandBase<L, R, G> implements Operand<G> {

    @Getter
    @Setter(AccessLevel.PACKAGE)
    protected Operand left;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    protected Operand right;

    @Override
    public G getValue(ProcessingContext context) {
        final L leftValue = (L) left.getValue(context);
        final R rightValue = (R) right.getValue(context);
        return getArithmeticOperation().apply(leftValue, rightValue);
    }

    protected abstract BiFunction<L, R, G> getArithmeticOperation();

    protected abstract String getArithmeticName();

    @Override
    public String toReadableString() {
        return String
            .format("(%s)%s(%s)", getLeft().toReadableString(), getArithmeticName(), getRight().toReadableString());
    }

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        Set<AggregationOperandBase> aggregationOperands = right.getAggregationOperands();
        aggregationOperands.addAll(left.getAggregationOperands());
        return aggregationOperands;
    }

    @Override
    public String toString() {
        return String.format(
            "%s(left=%s, right=%s)",
            this.getClass().getSimpleName(),
            Objects.toString(getLeft()),
            Objects.toString(getRight())
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            final ArithmeticOperandBase operand = (ArithmeticOperandBase) obj;
            return Objects.equals(operand.getLeft(), this.getLeft()) &&
                Objects.equals(operand.getRight(), this.getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getArithmeticName(), this.getLeft(), this.getRight());
    }
}

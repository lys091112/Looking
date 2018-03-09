package com.crescent.alert.engine.operands.booleanExprs.logical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class LogicalBooleanExprBase implements IBooleanExpression {

    private final Operand left;
    private final Operand right;

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        Set<AggregationOperandBase> aggregationOperands = left.getAggregationOperands();
        aggregationOperands.addAll(right.getAggregationOperands());
        return aggregationOperands;
    }

    public abstract String getExpressionName();

    @Override
    public String toReadableString() {
        return String.format(
            "%s %s %s",
            this.getLeft().toReadableString(),
            this.getExpressionName(),
            this.getRight().toReadableString()
        );
    }

    @Override
    public String toString() {
        return String.format(
            "%s(left=%s, right=%s)",
            this.getClass().getSimpleName(),
            Objects.toString(this.getLeft()),
            Objects.toString(this.getRight())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLeft(), this.getRight(), this.getExpressionName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            final LogicalBooleanExprBase expr = (LogicalBooleanExprBase) obj;
            return Objects.equals(this.getLeft(), expr.getLeft()) &&
                Objects.equals(this.getRight(), expr.getRight());
        }
        return false;
    }
}

package com.crescent.alert.engine.operands.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.logical.LogicalBooleanExprBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.base.Preconditions;

public abstract class NumericalBooleanExprBase extends LogicalBooleanExprBase {

    public NumericalBooleanExprBase(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        final Object leftValue = getLeft().getValue(context);
        final Object rightValue = getRight().getValue(context);

        Preconditions.checkNotNull(leftValue);
        Preconditions.checkNotNull(rightValue);
        Preconditions.checkArgument(leftValue instanceof Number, "operand value should be number");
        Preconditions.checkArgument(rightValue instanceof Number, "operand value should be number");
        final double leftNumber = ((Number) leftValue).doubleValue();
        final double rightNumber = ((Number) rightValue).doubleValue();

        return predication(leftNumber, rightNumber);
    }

    protected abstract boolean predication(double leftNumber, double rightNumber);

    /**
     * 在有效精度范围内相等
     */
    protected boolean isEqualWithinPrecision(double leftNumber, double rightNumber) {
        return Math.abs(leftNumber - rightNumber) < 0.000001;
    }
}

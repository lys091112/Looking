package com.crescent.alert.engine.operands.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;

/**
 * boolean expression less than.
 * '<'
 */
public class LTBooleanExpr extends NumericalBooleanExprBase {

    public LTBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected boolean predication(double leftNumber, double rightNumber) {
        return !isEqualWithinPrecision(leftNumber, rightNumber) && leftNumber < rightNumber;
    }

    @Override
    public String getExpressionName() {
        return "<";
    }

}

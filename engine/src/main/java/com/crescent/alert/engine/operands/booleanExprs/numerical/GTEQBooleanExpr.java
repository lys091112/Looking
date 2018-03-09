package com.crescent.alert.engine.operands.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;

/**
 * boolean expression greater than and equal
 * '>='
 */
public class GTEQBooleanExpr extends NumericalBooleanExprBase {

    public GTEQBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected boolean predication(double leftNumber, double rightNumber) {
        return isEqualWithinPrecision(leftNumber, rightNumber) || leftNumber >= rightNumber;
    }

    @Override
    public String getExpressionName() {
        return ">=";
    }
}

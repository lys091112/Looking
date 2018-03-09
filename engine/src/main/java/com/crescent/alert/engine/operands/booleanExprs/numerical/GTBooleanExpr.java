package com.crescent.alert.engine.operands.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;

/**
 * boolean expression greater than
 * '>'
 */
public class GTBooleanExpr extends NumericalBooleanExprBase {

    public GTBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    protected boolean predication(double leftNumber, double rightNumber) {
        return !isEqualWithinPrecision(leftNumber, rightNumber) && leftNumber > rightNumber;
    }

    @Override
    public String getExpressionName() {
        return ">";
    }

}

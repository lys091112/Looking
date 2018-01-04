package com.crescent.alert.engine.booleanExprs.numerical;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import java.util.List;
import java.util.Map;

/**
 * boolean expression less than.
 */
public class BooleanExprLTEQ extends AbstractNumericalBooleanExpr {

    public BooleanExprLTEQ(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected boolean predication(double leftNumber, double rightNumber) {
        return isEqualWithinPrecision(leftNumber, rightNumber) || leftNumber <= rightNumber;
    }

    @Override
    public String getExpressionName() {
        return "<=";
    }

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Object left = leftValue(currEvent, events, parameters);
        Object right = rightValue(currEvent, events, parameters);
        return sameType(left, right)
            && compare((Comparable) left, (Comparable) right) <= 0;
    }
}

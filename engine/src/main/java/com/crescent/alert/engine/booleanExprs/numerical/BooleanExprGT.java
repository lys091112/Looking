package com.crescent.alert.engine.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.Event;
import java.util.List;
import java.util.Map;

/**
 * boolean expression greater than
 * '>'
 */
public class BooleanExprGT extends AbstractNumericalBooleanExpr {

    public BooleanExprGT(Operand left, Operand right) {
        super(left, right);
    }

    protected boolean predication(double leftNumber, double rightNumber) {
        return !isEqualWithinPrecision(leftNumber, rightNumber) && leftNumber > rightNumber;
    }

    @Override
    public String getExpressionName() {
        return ">";
    }

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Object left = leftValue(currEvent, events, parameters);
        Object right = rightValue(currEvent, events, parameters);
        return sameType(left, right)
            && compare((Comparable) left, (Comparable) right) > 0;
    }
}

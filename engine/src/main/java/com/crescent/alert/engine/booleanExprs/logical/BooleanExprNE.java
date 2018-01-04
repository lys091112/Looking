package com.crescent.alert.engine.booleanExprs.logical;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BooleanExprNE extends BooleanExprBase {

    public BooleanExprNE(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "!=";
    }

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Object left = leftValue(currEvent, events, parameters);
        Object right = rightValue(currEvent, events, parameters);
        return !sameType(left, right) || (sameType(left, right) && compare((Comparable) left, (Comparable) right) != 0);
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        final Object leftValue = getLeft().getValue(context);
        final Object rightValue = getRight().getValue(context);
        return !Objects.equals(leftValue, rightValue);
    }
}

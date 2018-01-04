package com.crescent.alert.engine.booleanExprs.numerical;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.booleanExprs.logical.BooleanExprBase;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * boolean operation equals.
 * "="
 */
public class BooleanExprEQ extends BooleanExprBase {

    public BooleanExprEQ(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "=";
    }

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        return Objects.equals(this.getLeft().getValue(currEvent, events, parameters), this.getRight().getValue(currEvent, events, parameters));
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        return Objects.equals(getLeft().getValue(context), getRight().getValue(context));
    }
}

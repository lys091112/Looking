package com.crescent.alert.engine.booleanExprs.logical;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.base.Preconditions;
import com.crescent.alert.engine.operands.SetOperand;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BooleanExprIN extends BooleanExprBase {

    public BooleanExprIN(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "in";
    }

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Object val = this.getLeft().getValue(currEvent, events, parameters);
        Set<Object> elements = (Set<Object>) this.getRight().getValue(currEvent, events, parameters);
        return elements != null && !elements.isEmpty() && elements.contains(val);
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        Preconditions.checkArgument(this.getRight() instanceof SetOperand);
        final Object value = this.getLeft().getValue(context);
        final Set<Object> expectedValues = (Set<Object>) this.getRight().getValue(context);
        return expectedValues.contains(value);
    }
}

package com.crescent.alert.engine.booleanExprs.logical;

import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.crescent.alert.engine.Event;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooleanExprEXISTS implements IBooleanExpression {

    private final Operand operand;

    @Override
    public Boolean getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        throw new UnsupportedOperationException("EXISTS operator not supported.");
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        throw new UnsupportedOperationException("EXISTS operator not supported.");
    }

    @Override
    public String toReadableString() {
        throw new UnsupportedOperationException();
    }
}

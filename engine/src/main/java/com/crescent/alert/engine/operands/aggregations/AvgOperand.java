package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.crescent.alert.engine.Event;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AvgOperand extends AbstractAggregationOperand {

    public AvgOperand(Operand innerOperand) {
        super(innerOperand);
    }

    public AvgOperand(IBooleanExpression predicate) {
        super(predicate);
    }

    public AvgOperand(Operand innerOperand, IBooleanExpression predicate) {
        super(innerOperand, predicate);
    }

    @Override
    public Double getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {

        Double value = filterForCalculus(filter(events, parameters), parameters).mapToDouble(e ->
            Double.parseDouble(
                getOperand().getValue(new ProcessingContext(e, Arrays.asList(e), parameters)).toString()))
//        getOperand().getValue(e, Arrays.asList(e), parameters).toString()))
            .average().orElse(0);

        return value;

    }

    @Override
    public Object getValue(ProcessingContext context) {
        Double value = filterForCalculus(filter(context.getPreviousEvents(), context.getThreshold()), context.getThreshold()).mapToDouble(e ->
            Double.parseDouble(
                getOperand().getValue(context).toString()))
            .average().orElse(0);

        return value;
    }

    @Override
    public String toReadableString() {
        return String.format("AVG(%s)", this.getInnerOperand().toReadableString());
    }

    @Override
    public String toString() {
        return String.format(
            "%s(innerOperand=%s, predicate=%s)",
            this.getClass().getSimpleName(),
            Objects.toString(this.getInnerOperand()),
            Objects.toString(this.getPredicate())
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            final AvgOperand operand = (AvgOperand) obj;
            return Objects.equals(this.getInnerOperand(), operand.getInnerOperand()) &&
                Objects.equals(this.getPredicate(), operand.getPredicate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getInnerOperand(), this.getPredicate());
    }

    @Override
    protected String getAggregationType() {
        return "AVG";
    }
}

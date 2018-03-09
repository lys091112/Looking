package com.crescent.alert.engine.operands;

import com.crescent.alert.engine.operands.aggregations.AggregationOperandBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@AllArgsConstructor
@EqualsAndHashCode
public class AliasOperand implements Operand<String> {

    private final Operand<String> operand;
    private final String alias;

    @Override
    public String getValue(ProcessingContext context) {
        return operand.getValue(context);
    }

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        return operand.getAggregationOperands();
    }

    @Override
    public String toReadableString() {
        return this.getAlias();
    }
}

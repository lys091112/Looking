package com.crescent.alert.engine.operands.booleanExprs.logical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.SetOperand;
import com.crescent.alert.engine.provider.ProcessingContext;
import com.google.common.base.Preconditions;
import java.util.Set;

public class INBooleanExpr extends LogicalBooleanExprBase {

    public INBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "in";
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        Preconditions.checkArgument(this.getRight() instanceof SetOperand);
        final Object value = this.getLeft().getValue(context);
        final Set<Object> expectedValues = (Set<Object>) this.getRight().getValue(context);
        return expectedValues.contains(value);
    }
}

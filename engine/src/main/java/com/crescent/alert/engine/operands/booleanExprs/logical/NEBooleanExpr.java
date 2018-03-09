package com.crescent.alert.engine.operands.booleanExprs.logical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Objects;

public class NEBooleanExpr extends LogicalBooleanExprBase {

    public NEBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "!=";
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        final Object leftValue = getLeft().getValue(context);
        final Object rightValue = getRight().getValue(context);
        return !Objects.equals(leftValue, rightValue);
    }
}

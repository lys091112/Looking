package com.crescent.alert.engine.operands.booleanExprs.numerical;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.logical.LogicalBooleanExprBase;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Objects;

/**
 * boolean operation equals.
 * "="
 */
public class EQBooleanExpr extends LogicalBooleanExprBase {

    public EQBooleanExpr(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public String getExpressionName() {
        return "=";
    }

    @Override
    public Boolean getValue(ProcessingContext context) {
        return Objects.equals(getLeft().getValue(context), getRight().getValue(context));
    }
}

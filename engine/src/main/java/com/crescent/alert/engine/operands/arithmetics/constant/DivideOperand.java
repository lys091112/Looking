package com.crescent.alert.engine.operands.arithmetics.constant;

import com.crescent.alert.engine.operands.Operand;
import com.google.common.base.Preconditions;

public class DivideOperand extends ConstantOperandBase {

    public DivideOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected Double operateResult(Double left, Double right) {
        Preconditions.checkArgument(right != 0, "divider cannot be zero");
        return left / right;
    }

    @Override
    protected String getArithmeticName() {
        return "/";
    }
}

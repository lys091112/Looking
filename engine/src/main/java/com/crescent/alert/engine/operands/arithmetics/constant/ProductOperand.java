package com.crescent.alert.engine.operands.arithmetics.constant;

import com.crescent.alert.engine.operands.Operand;

public class ProductOperand extends ConstantOperandBase {

    public ProductOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected Double operateResult(Double left, Double right) {
        return left * right;
    }

    @Override
    protected String getArithmeticName() {
        return "*";
    }
}

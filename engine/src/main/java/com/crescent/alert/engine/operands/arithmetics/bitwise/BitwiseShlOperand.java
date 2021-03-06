package com.crescent.alert.engine.operands.arithmetics.bitwise;

import com.crescent.alert.engine.operands.Operand;

public class BitwiseShlOperand extends BitwiseOperandBase {

    public BitwiseShlOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected int operateResult(int left, int right) {
        return left << right;
    }

    @Override
    protected String getArithmeticName() {
        return "<<";
    }
}

package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.operands.Operand;
import com.google.common.base.Preconditions;
import java.util.function.BiFunction;

public abstract class AbstractConstantOperand extends AbstractArithmeticOperand<Number, Number, Double>{

    public AbstractConstantOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected BiFunction<Number, Number, Double> getArithmeticOperation() {
        return (left, right) -> {
            Preconditions.checkNotNull(left);
            Preconditions.checkNotNull(right);
            return operateResult(left.doubleValue(), right.doubleValue());
        };
    }

    protected  abstract Double operateResult(Double left, Double right);

}

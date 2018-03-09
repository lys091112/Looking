package com.crescent.alert.engine.operands.arithmetics.constant;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.arithmetics.ArithmeticOperandBase;
import com.google.common.base.Preconditions;
import java.util.function.BiFunction;

/**
 * 常量操作的基础类
 */
public abstract class ConstantOperandBase extends ArithmeticOperandBase<Number, Number, Double> {

    public ConstantOperandBase(Operand left, Operand right) {
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

    protected abstract Double operateResult(Double left, Double right);

}

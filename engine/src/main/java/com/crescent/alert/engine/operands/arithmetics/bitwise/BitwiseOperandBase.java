package com.crescent.alert.engine.operands.arithmetics.bitwise;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.arithmetics.ArithmeticOperandBase;
import java.util.function.BiFunction;

/**
 * 位移操作的基础类
 */
public abstract class BitwiseOperandBase extends ArithmeticOperandBase<Number, Number, Double> {

    public BitwiseOperandBase(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    protected BiFunction<Number, Number, Double> getArithmeticOperation() {
        return (left, right) -> Double
            .parseDouble(operateResult(null == left ? 0 : left.intValue(), null == right ? 0 : right.intValue()) + "");
    }

    protected abstract int operateResult(int left, int right);

}

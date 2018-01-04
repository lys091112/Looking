package com.crescent.alert.engine.operands.arithmetics;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.operands.Operand;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class ProductOperand extends AbstractConstantOperand {

    public ProductOperand(Operand left, Operand right) {
        super(left, right);
    }

    @Override
    public Double getValue(Event currEvent, List<Event> events, Map<String, String> parameters) {
        Double right = (Double) this.getRight().getValue(currEvent, events, parameters);
        if (right != null && right != 0.0) {
            Double left = (Double) this.getLeft().getValue(currEvent, events, parameters);
            if (left != null) {
                return left * right;
            }
        }
        return 0D;
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

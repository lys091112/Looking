package com.crescent.alert.engine.operands.aggregations.function;

import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import java.lang.reflect.Constructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonFunction {

    private Constructor<? extends Operand> constructor;
    private String name;
    private Class<? extends Operand> operandClazz;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CommonFunction(Class<? extends Operand> operandClazz, String name) {
        this.operandClazz = operandClazz;
        this.name = name;
    }

    public Object createInstance(Object... args) {
        try {
            if (args.length == 1) {
                if (args[0] instanceof Operand) {
                    constructor = operandClazz.getConstructor(Operand.class);
                    return constructor.newInstance(args[0]);

                } else if (args[0] instanceof IBooleanExpression) {
                    constructor = operandClazz.getConstructor(IBooleanExpression.class);
                    return constructor.newInstance(args[0]);
                }
            } else if (args.length == 2) {
                if (args[0] instanceof Operand
                    && args[1] instanceof IBooleanExpression) {
                    constructor = operandClazz.getConstructor(Operand.class, IBooleanExpression.class);
                    return constructor.newInstance(args[0], args[1]);
                }
            }
        } catch (Exception e) {
            logger.error("call internal function {} error", name, e);
        }

        throw new RuntimeException("parser sql aggregators error");
    }
}

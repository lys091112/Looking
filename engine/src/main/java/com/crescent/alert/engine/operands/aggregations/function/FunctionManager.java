package com.crescent.alert.engine.operands.aggregations.function;

import com.crescent.alert.engine.exception.StreamParseException;
import com.crescent.alert.engine.operands.aggregations.AvgOperand;
import com.crescent.alert.engine.operands.aggregations.CountOperand;
import com.crescent.alert.engine.operands.aggregations.MaxOperand;
import com.crescent.alert.engine.operands.aggregations.MinOperand;
import com.crescent.alert.engine.operands.aggregations.PreviousOperand;
import com.crescent.alert.engine.operands.aggregations.SumOperand;
import com.google.common.collect.Maps;
import java.util.Map;


public class FunctionManager {

    private static final Map<String, CommonFunction> funs = Maps.newConcurrentMap();

    static {
        funs.put("sum", new CommonFunction(SumOperand.class, "sum"));
        funs.put("avg", new CommonFunction(AvgOperand.class, "avg"));
        funs.put("maximum", new CommonFunction(MaxOperand.class, "maximum"));
        funs.put("minimum", new CommonFunction(MinOperand.class, "minimum"));
        funs.put("count", new CommonFunction(CountOperand.class, "count"));
        funs.put("previous", new CommonFunction(PreviousOperand.class, "previous"));
    }

    public static CommonFunction getAggregateFunction(String functionName) {
        if (!funs.containsKey(functionName.toLowerCase())) {
            throw new StreamParseException("parse sql error:not found " + functionName + " funcation");
        }
        return funs.get(functionName.toLowerCase());
    }
}

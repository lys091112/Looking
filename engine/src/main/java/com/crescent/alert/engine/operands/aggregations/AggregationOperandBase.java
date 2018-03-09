package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.arithmetics.ArithmeticOperandBase;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.operands.booleanExprs.logical.ANDBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.logical.LogicalBooleanExprBase;
import com.crescent.alert.engine.operands.booleanExprs.logical.ORBooleanExpr;
import com.crescent.alert.engine.provider.Event;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Slf4j
public abstract class AggregationOperandBase implements Operand<Object> {

    private Operand innerOperand;
    private IBooleanExpression predicate;

    public AggregationOperandBase(Operand innerOperand, IBooleanExpression predicate) {
        this.innerOperand = innerOperand;
        this.predicate = predicate;
    }

    public AggregationOperandBase(Operand innerOperand) {
        this.innerOperand = innerOperand;
    }

    public AggregationOperandBase(IBooleanExpression predicate) {
        this.predicate = predicate;
    }

    public Operand getOperand() {
        if (getInnerOperand() != null) {
            return getInnerOperand();
        }

        if (getPredicate() != null) {
            if (predicate instanceof LogicalBooleanExprBase) {
                return getNameOperandFromBooleanExprBase((LogicalBooleanExprBase) predicate);
            } else {
                IBooleanExpression p = predicate;
                while (p instanceof ANDBooleanExpr || p instanceof ORBooleanExpr) {
                    p = p instanceof ANDBooleanExpr ? ((ANDBooleanExpr) p).getLeft()
                        : ((ORBooleanExpr) p).getLeft();
                }
            }
        }

        return null;
    }

    private Operand getNameOperandFromBooleanExprBase(LogicalBooleanExprBase booleanExprBase) {
        if (booleanExprBase.getLeft() instanceof NameOperand
            || booleanExprBase.getLeft() instanceof ArithmeticOperandBase) {
            return booleanExprBase.getLeft();
        } else if (booleanExprBase.getRight() instanceof NameOperand
            || booleanExprBase.getRight() instanceof ArithmeticOperandBase) {
            return booleanExprBase.getRight();
        }
        return null;
    }

    /**
     * 聚合函数需要过滤不存在指定metric的event
     */
    protected Stream<String> filter(List<Event> events, Map<String, String> parameters) {
        return events.stream()
            .filter(e -> null == getPredicate() || getPredicate()
                .getValue(new ProcessingContext(e, Collections.singletonList(e), parameters)))
            .map(e -> {
                try {
                    return getOperand().getValue(new ProcessingContext(e, Collections.singletonList(e), parameters))
                        .toString();
                } catch (NotFoundException ex) {
                    return "";
                }
            }).filter(str -> StringUtils.isNoneBlank(str));
    }

    /**
     * 需要做数值计算的的聚合函数需要过滤metric值为字符串的event
     */
    protected Stream<Double> filterForCalculate(ProcessingContext context) {
        return filter(context.getPreviousEvents(), context.getThreshold())
            .map(str -> {
                try {
                    return Double.parseDouble(str);
                } catch (NumberFormatException ex) {
                    return -1D;
                }
            }).filter(e -> e.doubleValue() >= 0D);
    }

    @Override
    public String toReadableString() {
        if (this.getPredicate() != null) {
            return String.format("%s(%s) if %s", this.getAggregationType().toLowerCase(),
                this.getInnerOperand().toReadableString(), this.getPredicate().toReadableString());
        }

        return String.format("%s(%s)", this.getAggregationType().toLowerCase(),
            this.getInnerOperand().toReadableString());
    }

    @Override
    public String toString() {
        return String.format("%s(innerOperand=%s, predicate=%s)", this.getClass().getSimpleName(),
            Objects.toString(this.getInnerOperand()), Objects.toString(this.getPredicate()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && this.getClass() == obj.getClass()) {
            final AggregationOperandBase operand = (AggregationOperandBase) obj;
            return Objects.equals(this.getInnerOperand(), operand.getInnerOperand()) &&
                Objects.equals(this.getPredicate(), operand.getPredicate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getAggregationType(), this.getInnerOperand(), this.getPredicate());
    }

    protected abstract String getAggregationType();

    @Override
    public Set<AggregationOperandBase> getAggregationOperands() {
        throw new UnsupportedOperationException();
    }
}

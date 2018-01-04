package com.crescent.alert.engine.operands.aggregations;

import com.crescent.alert.engine.Event;
import com.crescent.alert.engine.booleanExprs.logical.BooleanExprAND;
import com.crescent.alert.engine.booleanExprs.logical.BooleanExprBase;
import com.crescent.alert.engine.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.exception.NotFoundException;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.arithmetics.AbstractArithmeticOperand;
import com.crescent.alert.engine.booleanExprs.logical.BooleanExprOR;
import com.crescent.alert.engine.provider.ProcessingContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractAggregationOperand implements Operand<Object> {

    private Operand innerOperand;
    private IBooleanExpression predicate;

    public AbstractAggregationOperand(Operand innerOperand, IBooleanExpression predicate) {
        this.innerOperand = innerOperand;
        this.predicate = predicate;
    }

    public AbstractAggregationOperand(Operand innerOperand) {
        this.innerOperand = innerOperand;
    }

    public AbstractAggregationOperand(IBooleanExpression predicate) {
        this.predicate = predicate;
    }

    public Operand getOperand() {
        if (getInnerOperand() != null) {
            return getInnerOperand();
        } else if (getPredicate() != null) {
            if (predicate instanceof BooleanExprBase) {
                return getNameOperandFromBooleanExprBase(predicate);
            } else {
                IBooleanExpression p = predicate;
                while (p instanceof BooleanExprAND || p instanceof BooleanExprOR) {
                    if (p instanceof BooleanExprAND) {
                        p = ((BooleanExprAND) p).getLeft();
                    }
                    if (p instanceof BooleanExprOR) {
                        p = ((BooleanExprOR) p).getLeft();
                    }
                }
                return getNameOperandFromBooleanExprBase(p);
            }
        }

        return null;
    }

    private Operand getNameOperandFromBooleanExprBase(IBooleanExpression expr) {
        if (!(expr instanceof BooleanExprBase)) {
            return null;
        }

        BooleanExprBase booleanExprBase = (BooleanExprBase) expr;

        if (booleanExprBase.getLeft() instanceof NameOperand
            || booleanExprBase.getLeft() instanceof AbstractArithmeticOperand) {
            return booleanExprBase.getLeft();
        } else if (booleanExprBase.getRight() instanceof NameOperand
            || booleanExprBase.getRight() instanceof AbstractArithmeticOperand) {
            return booleanExprBase.getRight();
        }
        return null;
    }

    protected Stream<Event> filter(List<Event> events, Map<String, String> parameters) {
        return  events.stream()
            .filter(e -> {
                try {
                    // 聚合函数需要过滤不存在指定metric的event
                    getOperand().getValue(new ProcessingContext(e, Collections.singletonList(e), parameters)).toString();
                    return true;
                } catch (NotFoundException ex) {
                    return false;
                }
        }).filter(e -> null == getPredicate() || getPredicate().getValue(new ProcessingContext(e, Collections.singletonList(e), parameters)));
    }

    // 需要做数值计算的的聚合函数需要过滤metric值为字符串的event
    protected Stream<Event> filterForCalculus(Stream<Event> stream, Map<String, String> parameters) {
        return stream
            .filter(e -> {
                try {
                    Double.parseDouble(getOperand().getValue(new ProcessingContext(e, Collections.singletonList(e), parameters)).toString());
                    return true;
                } catch (NumberFormatException ex) {
                    return false;
                }
            });
    }

    @Override
    public String toReadableString()
    {
        if (this.getPredicate() != null) {
            return String.format(
                "%s(%s) if %s",
                this.getAggregationType().toLowerCase(),
                this.getInnerOperand().toReadableString(),
                this.getPredicate().toReadableString()
            );
        } else {
            return String.format(
                "%s(%s)",
                this.getAggregationType().toLowerCase(),
                this.getInnerOperand().toReadableString()
            );
        }
    }

    @Override
    public String toString()
    {
        return String.format(
            "%s(innerOperand=%s, predicate=%s)",
            this.getClass().getSimpleName(),
            Objects.toString(this.getInnerOperand()),
            Objects.toString(this.getPredicate())
        );
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            final AbstractAggregationOperand operand = (AbstractAggregationOperand) obj;
            return Objects.equals(this.getInnerOperand(), operand.getInnerOperand()) &&
                Objects.equals(this.getPredicate(), operand.getPredicate());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.getAggregationType(), this.getInnerOperand(), this.getPredicate());
    }

    protected abstract String getAggregationType();

    @Override
    public Set<AbstractAggregationOperand> getAggregationOperands() {
        throw new UnsupportedOperationException();
    }
}

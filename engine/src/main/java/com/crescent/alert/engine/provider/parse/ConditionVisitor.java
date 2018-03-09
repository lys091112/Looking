package com.crescent.alert.engine.provider.parse;

import static java.util.stream.Collectors.toList;

import com.crescent.alert.engine.antlr.StreamLexer;
import com.crescent.alert.engine.antlr.StreamQuery.AddNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.AggregationNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.AndOprContext;
import com.crescent.alert.engine.antlr.StreamQuery.BasicBoolExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.BasicExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.BitwiseNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.BoolExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.CollectionContext;
import com.crescent.alert.engine.antlr.StreamQuery.ColumnListContext;
import com.crescent.alert.engine.antlr.StreamQuery.ColumnNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.CompareExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.DurationExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.FilterByExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.FloatEleContext;
import com.crescent.alert.engine.antlr.StreamQuery.IdEleContext;
import com.crescent.alert.engine.antlr.StreamQuery.IdentityContext;
import com.crescent.alert.engine.antlr.StreamQuery.InExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.IntEleContext;
import com.crescent.alert.engine.antlr.StreamQuery.LRNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.LrExprContext;
import com.crescent.alert.engine.antlr.StreamQuery.MulNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.NameContext;
import com.crescent.alert.engine.antlr.StreamQuery.NameOperandContext;
import com.crescent.alert.engine.antlr.StreamQuery.NegativeFloatELeContext;
import com.crescent.alert.engine.antlr.StreamQuery.NegativeIntEleContext;
import com.crescent.alert.engine.antlr.StreamQuery.OrOprContext;
import com.crescent.alert.engine.antlr.StreamQuery.ParenthesisContext;
import com.crescent.alert.engine.antlr.StreamQuery.ParenthesisNameContext;
import com.crescent.alert.engine.antlr.StreamQuery.ProgContext;
import com.crescent.alert.engine.antlr.StreamQuery.StringEleContext;
import com.crescent.alert.engine.antlr.StreamQuery.TableRefContext;
import com.crescent.alert.engine.antlr.StreamQuery.WhereClusterContext;
import com.crescent.alert.engine.antlr.StreamQueryVisitor;
import com.crescent.alert.engine.exception.StreamParseException;
import com.crescent.alert.engine.operands.AliasOperand;
import com.crescent.alert.engine.operands.NameOperand;
import com.crescent.alert.engine.operands.Operand;
import com.crescent.alert.engine.operands.ParenthesisOperand;
import com.crescent.alert.engine.operands.SetOperand;
import com.crescent.alert.engine.operands.aggregations.function.CommonFunction;
import com.crescent.alert.engine.operands.aggregations.function.FunctionManager;
import com.crescent.alert.engine.operands.arithmetics.bitwise.BitwiseAndOperand;
import com.crescent.alert.engine.operands.arithmetics.bitwise.BitwiseOrOperand;
import com.crescent.alert.engine.operands.arithmetics.bitwise.BitwiseShlOperand;
import com.crescent.alert.engine.operands.arithmetics.bitwise.BitwiseShrOperand;
import com.crescent.alert.engine.operands.arithmetics.bitwise.BitwiseXorOperand;
import com.crescent.alert.engine.operands.arithmetics.constant.DivideOperand;
import com.crescent.alert.engine.operands.arithmetics.constant.MinusOperand;
import com.crescent.alert.engine.operands.arithmetics.constant.PlusOperand;
import com.crescent.alert.engine.operands.arithmetics.constant.ProductOperand;
import com.crescent.alert.engine.operands.booleanExprs.IBooleanExpression;
import com.crescent.alert.engine.operands.booleanExprs.logical.ANDBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.logical.INBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.logical.NEBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.logical.ORBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.numerical.EQBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.numerical.GTBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.numerical.GTEQBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.numerical.LTBooleanExpr;
import com.crescent.alert.engine.operands.booleanExprs.numerical.LTEQBooleanExpr;
import com.crescent.alert.engine.operands.primitives.FloatOperand;
import com.crescent.alert.engine.operands.primitives.IntegerOperand;
import com.crescent.alert.engine.operands.primitives.StringOperand;
import com.crescent.alert.engine.provider.event.EventsFinderImpl;
import com.crescent.alert.engine.provider.event.boundingBox.BoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.SizeBoundingBox;
import com.crescent.alert.engine.provider.event.boundingBox.TimeBoundingBox;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ConditionVisitor implements StreamQueryVisitor<Boolean> {

    private Deque<Object> stack = new ArrayDeque<>();

    private RuleTemplate.RuleTemplateBuilder builder = RuleTemplate.builder();

    private String defaultTableName;

    @Override
    public Boolean visit(ParseTree parseTree) {
        return parseTree instanceof ProgContext && visitProg((ProgContext) parseTree);
    }

    @Override
    public Boolean visitChildren(RuleNode node) {
        return false;
    }

    @Override
    public Boolean visitTerminal(TerminalNode node) {
        return false;
    }

    @Override
    public Boolean visitErrorNode(ErrorNode node) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean visitProg(ProgContext ctx) {
        if (ctx.tableRef() != null) {
            if (!visitTableRef(ctx.tableRef())) {
                return false;
            }
            builder.streams((List<String>) stack.pop());
        }
        if (ctx.columnList() != null) {
            if (!visitColumnList(ctx.columnList())) {
                return false;
            }
            builder.columns((List<Operand>) stack.pop());
        }

        if (ctx.whereCluster() != null) {
            if (!visitWhereCluster(ctx.whereCluster())) {
                return false;
            }
            builder.whereClause((IBooleanExpression) stack.pop());
        }
        return true;
    }

    @Override
    public Boolean visitColumnList(ColumnListContext ctx) {
        List<Operand> operands = new ArrayList<>();
        List<NameOperandContext> list = ctx.nameOperand();
        for (NameOperandContext nameOperandContext : list) {
            if (!visitNameOperand(nameOperandContext)) {
                return false;
            }
            Operand operand = (Operand) stack.pop();
            operands.add(operand);
        }
        stack.push(operands);
        return true;
    }

    @Override
    public Boolean visitNameOperand(NameOperandContext ctx) {
        // 每次都将默认的表名压入栈中
        stack.push(defaultTableName);
        if (ctx.tableName != null) {
            defaultTableName = ctx.tableName.getText();
        }
        if (visitName(ctx.columnName)) {
            // 选择的列
            Operand inOperand = (Operand) stack.pop();

            // 选择热数据源
            defaultTableName = String.valueOf(stack.pop());

            if (ctx.alias != null) {
                stack.push(new AliasOperand(inOperand, ctx.alias.getText()));
            } else {
                stack.push(inOperand);
            }
            return true;
        }
        return false;
    }

    @Override
    public Boolean visitTableRef(TableRefContext ctx) {
        this.defaultTableName = ctx.tableName.getText();
        // TODO:多数据源时进行扩展
        stack.push(Collections.singletonList(defaultTableName));
        return true;
    }

    private Boolean visitBoolExpr(BoolExprContext ctx) {
        if (ctx instanceof LrExprContext) {
            return visitLrExpr((LrExprContext) ctx);
        } else if (ctx instanceof AndOprContext) {
            return visitAndOpr((AndOprContext) ctx);
        } else if (ctx instanceof OrOprContext) {
            return visitOrOpr((OrOprContext) ctx);
        } else if (ctx instanceof BasicExprContext) {
            return visitBasicExpr((BasicExprContext) ctx);
        }
        return false;
    }

    @Override
    public Boolean visitLrExpr(LrExprContext ctx) {
        return visitBoolExpr(ctx.boolExpr());
    }

    @Override
    public Boolean visitAndOpr(AndOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            IBooleanExpression left = (IBooleanExpression) stack.pop();
            if (visitBoolExpr(ctx.right)) {
                IBooleanExpression right = (IBooleanExpression) stack.pop();
                stack.push(new ANDBooleanExpr(left, right));
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitOrOpr(OrOprContext ctx) {
        if (visitBoolExpr(ctx.left)) {
            IBooleanExpression left = (IBooleanExpression) stack.pop();
            if (visitBoolExpr(ctx.right)) {
                IBooleanExpression right = (IBooleanExpression) stack.pop();
                stack.push(new ORBooleanExpr(left, right));
                return true;
            }
        }
        return false;
    }

    private Boolean visitName(NameContext ctx) {

        if (ctx instanceof LRNameContext) {
            return visitLRName((LRNameContext) ctx);
        } else if (ctx instanceof MulNameContext) {
            return visitMulName((MulNameContext) ctx);
        } else if (ctx instanceof AddNameContext) {
            return visitAddName((AddNameContext) ctx);
        } else if (ctx instanceof AggregationNameContext) {
            return visitAggregationName((AggregationNameContext) ctx);
        } else if (ctx instanceof ColumnNameContext) {
            return visitColumnName((ColumnNameContext) ctx);
        } else if (ctx instanceof BitwiseNameContext) {
            return visitBitwiseName((BitwiseNameContext) ctx);
        } else if (ctx instanceof ParenthesisNameContext) {
            return visitParenthesisName((ParenthesisNameContext) ctx);
        }

        return false;
    }

    @Override
    public Boolean visitLRName(LRNameContext ctx) {
        return visitName(ctx.name());
    }


    @Override
    public Boolean visitMulName(MulNameContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand) stack.pop();
            if (visitName(ctx.right)) {
                Operand right = (Operand) stack.pop();
                int type = ctx.op.getType();
                switch (type) {
                    case StreamLexer.STAR:
                        stack.push(new ProductOperand(left, right));
                        return true;
                    case StreamLexer.SLASH:
                        stack.push(new DivideOperand(left, right));
                        return true;
                    case StreamLexer.MOD:
                        throw new StreamParseException("nonsupport %");
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean visitAddName(AddNameContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand) stack.pop();
            if (visitName(ctx.right)) {
                Operand right = (Operand) stack.pop();
                int type = ctx.op.getType();
                switch (type) {
                    case StreamLexer.PLUS:
                        stack.push(new PlusOperand(left, right));
                        return true;
                    case StreamLexer.SUB:
                        stack.push(new MinusOperand(left, right));
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean visitParenthesisName(ParenthesisNameContext ctx) {
        ParenthesisContext parenthesisContext = ctx.parenthesis();
        return visitParenthesis(parenthesisContext);
    }

    @Override
    public Boolean visitColumnName(ColumnNameContext ctx) {
        IdentityContext identity = ctx.identity();
        return visitIdentity(identity);
    }

    @Override
    public Boolean visitBitwiseName(BitwiseNameContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand) stack.pop();
            if (visitName(ctx.right)) {
                Operand right = (Operand) stack.pop();
                int type = ctx.op.getType();
                switch (type) {
                    case StreamLexer.AMP:
                        stack.push(new BitwiseAndOperand(left, right));
                        return true;
                    case StreamLexer.BAR:
                        stack.push(new BitwiseOrOperand(left, right));
                        return true;
                    case StreamLexer.CARET:
                        stack.push(new BitwiseXorOperand(left, right));
                        return true;
                    case StreamLexer.LTLT:
                        stack.push(new BitwiseShlOperand(left, right));
                        return true;
                    case StreamLexer.GTGT:
                        stack.push(new BitwiseShrOperand(left, right));
                        return true;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean visitParenthesis(ParenthesisContext ctx) {
        stack.push(new ParenthesisOperand(ctx.PARENTHESIS().getText()));
        return true;
    }


    @Override
    public Boolean visitIdEle(IdEleContext ctx) {
        stack.push(new NameOperand(defaultTableName, ctx.ID().getText()));
        return true;
    }


    @Override
    public Boolean visitIntEle(IntEleContext ctx) {
        stack.push(new IntegerOperand(Long.valueOf(ctx.getText())));
        return true;
    }


    @Override
    public Boolean visitFloatEle(FloatEleContext ctx) {
        stack.push(new FloatOperand(Double.valueOf(ctx.getText())));
        return true;
    }

    @Override
    public Boolean visitNegativeIntEle(NegativeIntEleContext ctx) {
        stack.push(new IntegerOperand(Long.valueOf(ctx.getText())));
        return true;
    }

    @Override
    public Boolean visitNegativeFloatELe(NegativeFloatELeContext ctx) {
        stack.push(new FloatOperand(Double.valueOf(ctx.getText())));
        return true;
    }


    @Override
    public Boolean visitStringEle(StringEleContext ctx) {
        String str = ctx.STRING().getText();
        stack.push(new StringOperand(str.substring(1, str.length() - 1)));
        return true;
    }


    @Override
    public Boolean visitAggregationName(AggregationNameContext ctx) {

        String aggFun = ctx.ID().getText();
        // aggregate operand
        CommonFunction fun = FunctionManager.getAggregateFunction(aggFun);
        if (fun == null) {
            throw new StreamParseException(String.format("aggregate function: [%s] not exists.", aggFun));
        }

        if (visitName(ctx.columnName)) {
            // inner operand
            Operand innerOperand = (Operand) stack.pop();
            Operand aggregationOperand;

            if (ctx.COMMA() != null) {
                if (visitBoolExpr(ctx.predicate)) {
                    IBooleanExpression predicate = (IBooleanExpression) stack.pop();
                    aggregationOperand = (Operand) fun.createInstance(innerOperand, predicate);
                } else {
                    throw new StreamParseException("aggregate failed, no such define supported");
                }
            } else {
                aggregationOperand = (Operand) fun.createInstance(innerOperand);
            }

            // 入栈
            stack.push(aggregationOperand);
            return true;
        } else if (visitBoolExpr(ctx.predicate)) {
            // 里面返回的operand
            IBooleanExpression predicate = (IBooleanExpression) stack.pop();

            Operand aggregationOperand = (Operand) fun.createInstance(predicate);
            // 入栈
            stack.push(aggregationOperand);
            return true;
        }
        return false;
    }


    @Override
    public Boolean visitWhereCluster(WhereClusterContext ctx) {
        if (ctx.durationExpr() != null) {
            return visitBoolExpr(ctx.boolExpr())
                && visitFilterByExpr(ctx.filterByExpr())
                && visitDurationExpr(ctx.durationExpr());
        } else if (visitBoolExpr(ctx.boolExpr())) {
            return visitFilterByExpr(ctx.filterByExpr());
        }
        return false;
    }

    @Override
    public Boolean visitDurationExpr(DurationExprContext ctx) {

        if (visitName(ctx.number)) {

            final Operand numberOperand = (Operand) stack.pop();

            if (ctx.unit.getStartIndex() == -1) {
                throw new StreamParseException("Currently only support units: [events, min].");
            }

            BoundingBox boundingBox;
            int type = ctx.unit.getType();
            if (type == StreamLexer.MINUTE) {
                boundingBox = new TimeBoundingBox(numberOperand, TimeUnit.MINUTES);
            } else if (type == StreamLexer.EVENTS) {
                int value = numberOperand instanceof IntegerOperand
                    ? ((IntegerOperand) numberOperand).getValue().intValue() : 0;
                boundingBox = new SizeBoundingBox((long) value);
            } else {
                throw new StreamParseException("Currently only support units: [events, min].");
            }
            builder.eventsFinder(new EventsFinderImpl(boundingBox));
            return true;
        }
        return false;
    }

    @Override
    public Boolean visitFilterByExpr(FilterByExprContext ctx) {
        if (ctx != null) {
            List<NameOperand> operands = ctx.ID().stream().map(id -> new NameOperand(defaultTableName, id.getText()))
                .collect(toList());
            builder.filterKeys(operands);
        }
        return true;
    }


    @Override
    public Boolean visitBasicExpr(BasicExprContext ctx) {
        return visitBasicBoolExpr(ctx.basicBoolExpr());
    }


    private Boolean visitBasicBoolExpr(BasicBoolExprContext ctx) {
        if (ctx instanceof CompareExprContext) {
            return visitCompareExpr((CompareExprContext) ctx);
        } else if (ctx instanceof InExprContext) {
            return visitInExpr((InExprContext) ctx);
        }
        throw new StreamParseException("Parse basic boolean expression error.");
    }

    @Override
    public Boolean visitCompareExpr(CompareExprContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand) stack.pop();
            if (visitName(ctx.right)) {

                Operand right = (Operand) stack.pop();

                int type = ctx.option.getType();
                switch (type) {
                    case StreamLexer.GT:
                        stack.push(new GTBooleanExpr(left, right));
                        break;
                    case StreamLexer.GTEQ:
                        stack.push(new GTEQBooleanExpr(left, right));
                        break;
                    case StreamLexer.EQ:
                        stack.push(new EQBooleanExpr(left, right));
                        break;
                    case StreamLexer.LT:
                        stack.push(new LTBooleanExpr(left, right));
                        break;
                    case StreamLexer.LTEQ:
                        stack.push(new LTEQBooleanExpr(left, right));
                        break;
                    case StreamLexer.NEQ:
                        stack.push(new NEBooleanExpr(left, right));
                        break;

                    default:
                        return false;
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean visitInExpr(InExprContext ctx) {
        if (visitName(ctx.left)) {
            Operand left = (Operand) stack.pop();
            if (visitCollection(ctx.collection())) {
                SetOperand right = (SetOperand) stack.pop();
                stack.push(new INBooleanExpr(left, right));
                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean visitCollection(CollectionContext ctx) {
        Set<Object> elements = new HashSet<>();
        for (IdentityContext o : ctx.identity()) {
            visitIdentity(o);
            Operand operand = (Operand) stack.pop();
            elements.add(operand.getValue(null));
        }
        stack.push(new SetOperand(elements));
        return true;
    }

    private Boolean visitIdentity(IdentityContext identity) {
        if (identity instanceof IdEleContext) {
            return visitIdEle((IdEleContext) identity);
        } else if (identity instanceof IntEleContext) {
            return visitIntEle((IntEleContext) identity);
        } else if (identity instanceof FloatEleContext) {
            return visitFloatEle((FloatEleContext) identity);
        } else if (identity instanceof StringEleContext) {
            return visitStringEle((StringEleContext) identity);
        } else if (identity instanceof NegativeIntEleContext) {
            return visitNegativeIntEle((NegativeIntEleContext) identity);
        } else if (identity instanceof NegativeFloatELeContext) {
            return visitNegativeFloatELe((NegativeFloatELeContext) identity);
        }

        throw new StreamParseException("Parse identity error: " + identity.getText() + " .");
    }

    public RuleTemplate getRuleTemplate() {
        return builder.build();
    }
}

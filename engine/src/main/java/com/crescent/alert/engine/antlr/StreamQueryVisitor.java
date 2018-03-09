// Generated from /Users/langle/xianyue/workspace/javawork/Looking/engine/src/main/antlr/StreamQuery.g4 by ANTLR 4.7
package com.crescent.alert.engine.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link StreamQuery}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return type.
 */
public interface StreamQueryVisitor<T> extends ParseTreeVisitor<T> {

    /**
     * Visit a parse tree produced by {@link StreamQuery#prog}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProg(StreamQuery.ProgContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#columnList}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnList(StreamQuery.ColumnListContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#nameOperand}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNameOperand(StreamQuery.NameOperandContext ctx);

    /**
     * Visit a parse tree produced by the {@code MulName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMulName(StreamQuery.MulNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code AggregationName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAggregationName(StreamQuery.AggregationNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code AddName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAddName(StreamQuery.AddNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code parenthesisName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParenthesisName(StreamQuery.ParenthesisNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code LRName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLRName(StreamQuery.LRNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code columnName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitColumnName(StreamQuery.ColumnNameContext ctx);

    /**
     * Visit a parse tree produced by the {@code BitwiseName}
     * labeled alternative in {@link StreamQuery#name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBitwiseName(StreamQuery.BitwiseNameContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#parenthesis}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitParenthesis(StreamQuery.ParenthesisContext ctx);

    /**
     * Visit a parse tree produced by the {@code idEle}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIdEle(StreamQuery.IdEleContext ctx);

    /**
     * Visit a parse tree produced by the {@code intEle}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIntEle(StreamQuery.IntEleContext ctx);

    /**
     * Visit a parse tree produced by the {@code floatEle}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloatEle(StreamQuery.FloatEleContext ctx);

    /**
     * Visit a parse tree produced by the {@code negativeIntEle}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNegativeIntEle(StreamQuery.NegativeIntEleContext ctx);

    /**
     * Visit a parse tree produced by the {@code negativeFloatELe}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitNegativeFloatELe(StreamQuery.NegativeFloatELeContext ctx);

    /**
     * Visit a parse tree produced by the {@code stringEle}
     * labeled alternative in {@link StreamQuery#identity}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStringEle(StreamQuery.StringEleContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#tableRef}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTableRef(StreamQuery.TableRefContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#whereCluster}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitWhereCluster(StreamQuery.WhereClusterContext ctx);

    /**
     * Visit a parse tree produced by the {@code basicExpr}
     * labeled alternative in {@link StreamQuery#boolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBasicExpr(StreamQuery.BasicExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code lrExpr}
     * labeled alternative in {@link StreamQuery#boolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLrExpr(StreamQuery.LrExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code andOpr}
     * labeled alternative in {@link StreamQuery#boolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAndOpr(StreamQuery.AndOprContext ctx);

    /**
     * Visit a parse tree produced by the {@code orOpr}
     * labeled alternative in {@link StreamQuery#boolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOrOpr(StreamQuery.OrOprContext ctx);

    /**
     * Visit a parse tree produced by the {@code compareExpr}
     * labeled alternative in {@link StreamQuery#basicBoolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompareExpr(StreamQuery.CompareExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code inExpr}
     * labeled alternative in {@link StreamQuery#basicBoolExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInExpr(StreamQuery.InExprContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#collection}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCollection(StreamQuery.CollectionContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#durationExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDurationExpr(StreamQuery.DurationExprContext ctx);

    /**
     * Visit a parse tree produced by {@link StreamQuery#filterByExpr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFilterByExpr(StreamQuery.FilterByExprContext ctx);
}
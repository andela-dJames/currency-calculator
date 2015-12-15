package com.andela.currencycalculator.parser.expressionnodes;

/**
 * An interface for the visitor design pattern.
 *An <code>ExpressionNodeVisitor</code> that visits <code>ExpressionNode</code>by calling their
 * accept methods. The expression nodes, in turn, call the appropriate visit
 * method of the expression node visitor.
 */
public interface ExpressionNodeVisitor
{
    /**
     * Visit a VariableExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(VariableExpressionNode node);

    /**
     * Visit a ConstantExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(ConstantExpressionNode node);

    /**
     * Visit a AdditionExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(AdditionExpressionNode node);

    /**
     * Visit a MultiplicationExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(MultiplicationExpressionNode node);

    /**
     * Visit a ExponentiationExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(ExponentiationExpressionNode node);

    /**
     * Visit a FunctionExpressionNode
     * @param node an <code>ExpressionNode</code>
     */
    public void visit(FunctionExpressionNode node);
}


package com.andela.currencycalculator.parser.expressionnodes;


public class ExponentiationExpressionNode implements ExpressionNode{

    /**
     * An <code>ExpressionNode</code> base
     */
    private ExpressionNode base;
    /**
     * The  an <code>ExpressionNode</code> node containing the exponent
     */
    private ExpressionNode exponent;

    /**
     * Ceates the ExponentiationExpressionNode with base and exponent
     * @param base the an <code>ExpressionNode</code> containing the base
     * @param exponent the an <code>ExpressionNode</code> containing the exponent
     */
    public ExponentiationExpressionNode(ExpressionNode base, ExpressionNode exponent) {

        this.base = base;
        this.exponent = exponent;
    }

    /**
     * Returns the type of the an <code>ExpressionNode</code>,
     * in this case <code>ExpressionNode.EXPONENTIATION_NODE</code>
     */
    public int getType() {
        return ExpressionNode.EXPONENTIATION_NODE;
    }

    /**
     * Returns the value of the sub-expression that is rooted at this an <code>ExpressionNode</code>
     * Calculates base^exponent
     */
    public double getValue() {
        return Math.pow(base.getValue(), exponent.getValue());
    }

    /**
     * Implementation of the visitor design pattern.
     * Calls visit on the visitor and then passes the visitor on to the accept
     * method of the base and the exponent.
     * @param visitor the visitor
     *
     */
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        base.accept(visitor);
        exponent.accept(visitor);
    }
}

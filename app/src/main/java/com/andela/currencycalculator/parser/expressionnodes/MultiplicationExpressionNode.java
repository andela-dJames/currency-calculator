
package com.andela.currencycalculator.parser.expressionnodes;


public class MultiplicationExpressionNode extends SequenceExpressionNode  {


    public MultiplicationExpressionNode() {

    }

    /**
     * Constructor to create a multiplication.
     * @param node the term to be added
     * @param positive a flag indicating whether the term is multiplied or divided
     */
    public MultiplicationExpressionNode(ExpressionNode node, boolean positive) {
        super(node, positive);
    }

    /**
     * Returns the type of the node, in this case <code>ExpressionNode.MULTIPLICATION_NODE</code>
     */
    public int getType() {
        return MULTIPLICATION_NODE;
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     */
    public double getValue() {
        double product = 1.0;
        for (Term term : terms)
        {
            if (term.positive)
                product *= term.expression.getValue();
            else
                product /= term.expression.getValue();
        }
        return product;
    }

    /**
     * Implementation of the visitor design pattern.
     * Calls visit on the visitor and then passes the visitor on to the accept
     * method of all the terms in the product.
     *
     * @param visitor the visitor
     *
     */
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        for (Term term: terms)
            term.expression.accept(visitor);
    }
}

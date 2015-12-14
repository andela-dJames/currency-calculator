package com.andela.currencycalculator.parser.expressionnodes;

import java.util.ArrayList;


public abstract class SequenceExpressionNode implements ExpressionNode {

    /**
     * An inner class that defines a pair containing an ExpressionNode and a
     * boolean flag.
     */
    public class Term {
        /**
         * The boolean flag
         */
        public boolean positive;
        /**
         * An <code>ExpressionNode</code>
         */
        public ExpressionNode expression;

        /**
         * Construct the Term object with some values.
         * @param positive the boolean flag
         * @param expression an <code>ExpressionNode</code>
         */
        public Term(boolean positive, ExpressionNode expression) {
            super();
            this.positive = positive;
            this.expression = expression;
        }
    }

    /**
     * The list of terms in the sequence
     */
    protected ArrayList<Term> terms;

    public SequenceExpressionNode() {
        this.terms = new ArrayList<Term>();
    }

    /**
     * Constructor to create a sequence with the first term already added.
     *
     * @param a
     *          the term to be added
     * @param positive
     *          a boolean flag
     */
    public SequenceExpressionNode(ExpressionNode a, boolean positive)
    {
        this.terms = new ArrayList<Term>();
        this.terms.add(new Term(positive, a));
    }

    /**
     * Adds another term to the sequence
     * @param node the term to be added
     *
     * @param positive a boolean flag
     *
     */
    public void add(ExpressionNode node, boolean positive) {
        this.terms.add(new Term(positive, node));
    }
}

package com.andela.currencycalculator.parcer.expressionnodes;

import java.util.ArrayList;

/**
 * Created by Oluwatosin on 11/27/2015.
 */
public abstract class SequenceExpressionNode implements ExpressionNode {

    /**
     * An inner class that defines a pair containing an ExpressionNode and a
     * boolean flag.
     */
    public class Term
    {
        /** the boolean flag */
        public boolean positive;
        /** the expression node */
        public ExpressionNode expression;

        /**
         * Construct the Term object with some values.
         * @param positive the boolean flag
         * @param expression the expression node
         */
        public Term(boolean positive, ExpressionNode expression)
        {
            super();
            this.positive = positive;
            this.expression = expression;
        }
    }

    /** the list of terms in the sequence */
    protected ArrayList<Term> terms;

    /**
     * Default constructor.
     */
    public SequenceExpressionNode()
    {
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
     * Add another term to the sequence
     * @param node
     *          the term to be added
     * @param positive
     *          a boolean flag
     */
    public void add(ExpressionNode node, boolean positive)
    {
        this.terms.add(new Term(positive, node));
    }
}

package com.andela.currencycalculator.parser.expressionnodes;


import com.andela.currencycalculator.parser.exception.EvaluationException;

/**
 * Created by Oluwatosin on 11/27/2015.
 */
public class VariableExpressionNode implements ExpressionNode {

    /**
     * The name of the variable
     */
    private String name;
    /**
     * The value of the variable
     */
    private double value;
    /**
     * indicates if the value has been set
     */
    private boolean valueSet;

    public VariableExpressionNode(String name) {
        this.name = name;
        valueSet = false;
    }

    /**
     * @return the name of the variable
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the node, in this case ExpressionNode.VARIABLE_NODE
     */
    public int getType() {
        return VARIABLE_NODE;
    }

    /**
     * Sets the value of the variable
     * @param value the value of the variable
     *
     */
    public void setValue(double value) {
        this.value = value;
        this.valueSet = true;
    }

    /**
     * Returns the value of the variable but throws an exception if the value has
     * not been set
     */
    public double getValue() {
        if (valueSet)
            return value;
        else
            throw new EvaluationException("Variable '" + name + "' was not initialized.");
    }
    /**
     * Implementation of the visitor design pattern.
     * Calls visit on the visitor.
     * @param visitor the visitor
     *
     */
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
    }
}

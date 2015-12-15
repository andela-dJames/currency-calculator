package com.andela.currencycalculator.parser.expressionnodes;


public class SetVariable implements ExpressionNodeVisitor {
    private String name;
    private double value;


    public SetVariable(String name, double value) {
        super();
        this.name = name;
        this.value = value;
    }

    /**
     * Checks the nodes name against the name to set and sets the value if the two
     * strings match
     */
    public void visit(VariableExpressionNode node) {
        if (node.getName().equals(name))
            node.setValue(value);
    }

    public void visit(ConstantExpressionNode node) {

    }

    public void visit(AdditionExpressionNode node) {

    }

    public void visit(MultiplicationExpressionNode node) {

    }

    public void visit(ExponentiationExpressionNode node) {

    }
    public void visit(FunctionExpressionNode node) {

    }
}

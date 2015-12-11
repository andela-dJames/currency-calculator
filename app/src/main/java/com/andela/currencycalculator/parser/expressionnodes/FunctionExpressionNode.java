package com.andela.currencycalculator.parser.expressionnodes;

import com.andela.currencycalculator.parser.exception.EvaluationException;
import com.andela.currencycalculator.parser.exception.ParserException;


public class FunctionExpressionNode implements ExpressionNode {
    /**
     * function id for the sin function
     */
    public static final int SIN = 1;
    /**
     * function id for the cos function
     */
    public static final int COS = 2;
    /**
     * function id for the tan function
     */
    public static final int TAN = 3;

    /**
     * function id for the asin function
     */
    public static final int ASIN = 4;
    /**
     * function id for the acos function
     */
    public static final int ACOS = 5;
    /**
     * function id for the atan function
     */
    public static final int ATAN = 6;
    /**
     * function id for the sqrt function
     */
    public static final int SQRT = 7;
    /**
     * Function id for the exp function
     */
    public static final int EXP = 8;
    /**
     * Function id for the ln function
     */
    public static final int LN = 9;
    /**
     * Function id for the log function
     */
    public static final int LOG = 10;
    /**
     * Function id for the log2 function
     */
    public static final int LOG2 = 11;

    /** The id of the function to apply to the argument
     *
     */
    private int function;

    /** n
     * The argument of the functio
     */
    private ExpressionNode argument;

    /**
     * Construct a function by id and argument.
     * @param function the id of the function to apply
     * @param argument the argument of the function
     *
     */
    public FunctionExpressionNode(int function, ExpressionNode argument) {
        super();
        this.function = function;
        this.argument = argument;
    }

    /**
     * Returns the type of the node, in this case ExpressionNode.FUNCTION_NODE
     */
    public int getType() {
        return ExpressionNode.FUNCTION_NODE;
    }

    /**
     * Converts a string to a function id.
     * If the function is not found this method throws an error.
     * @param function the name of the function
     * @return the id of the function
     */
    public static int stringToFunction(String function) {
        if (function.equals("sin"))
            return FunctionExpressionNode.SIN;
        if (function.equals("cos"))
            return FunctionExpressionNode.COS;
        if (function.equals("tan"))
            return FunctionExpressionNode.TAN;

        if (function.equals("asin"))
            return FunctionExpressionNode.ASIN;
        if (function.equals("acos"))
            return FunctionExpressionNode.ACOS;
        if (function.equals("atan"))
            return FunctionExpressionNode.ATAN;

        if (function.equals("sqrt"))
            return FunctionExpressionNode.SQRT;
        if (function.equals("exp"))
            return FunctionExpressionNode.EXP;

        if (function.equals("ln"))
            return FunctionExpressionNode.LN;
        if (function.equals("log"))
            return FunctionExpressionNode.LOG;
        if (function.equals("log2"))
            return FunctionExpressionNode.LOG2;

        throw new ParserException("Unexpected Function " + function + " found");
    }

    /**
     * Returns a string with all the function names concatenated by a | symbol.
     * @return a string containing all the function names
     */
    public static String getAllFunctions() {
        return "sin|cos|tan|asin|acos|atan|sqrt|exp|ln|log|log2";
    }

    /**
     * Returns the value of the sub-expression that is rooted at this node.
     * The argument is evaluated and then the function is applied to the resulting
     * value.
     */
    public double getValue() {
        switch (function)
        {
            case SIN:
                return Math.sin(argument.getValue());
            case COS:
                return Math.cos(argument.getValue());
            case TAN:
                return Math.tan(argument.getValue());
            case ASIN:
                return Math.asin(argument.getValue());
            case ACOS:
                return Math.acos(argument.getValue());
            case ATAN:
                return Math.atan(argument.getValue());
            case SQRT:
                return Math.sqrt(argument.getValue());
            case EXP:
                return Math.exp(argument.getValue());
            case LN:
                return Math.log(argument.getValue());
            case LOG:
                return Math.log(argument.getValue()) * 0.43429448190325182765;
            case LOG2:
                return Math.log(argument.getValue()) * 1.442695040888963407360;
        }

        throw new EvaluationException("Invalid function id "+function+"!");
    }

    /**
     * Calls visit on the visitor and then passes the visitor on to the accept
     * method of the argument
     * @param visitor the visitor
     *
     */
    public void accept(ExpressionNodeVisitor visitor) {
        visitor.visit(this);
        argument.accept(visitor);
    }
}

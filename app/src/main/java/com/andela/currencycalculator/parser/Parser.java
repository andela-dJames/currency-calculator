package com.andela.currencycalculator.parser;

import com.andela.currencycalculator.parser.Token.Token;
import com.andela.currencycalculator.parser.Token.Tokenizer;
import com.andela.currencycalculator.parser.exception.ParserException;
import com.andela.currencycalculator.parser.expressionnodes.AdditionExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.ConstantExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.ExponentiationExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.ExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.FunctionExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.MultiplicationExpressionNode;
import com.andela.currencycalculator.parser.expressionnodes.VariableExpressionNode;

import java.util.LinkedList;

public class Parser {
    /**
     * Tokens to parse
     */
    LinkedList<Token> tokens;
    /**
     * The next token
     */
    Token lookahead;

    /**
     * Parse a mathematical expression in a string and return an ExpressionNode.
     * @param expression the string to parse
     *
     * @return the internal representation of the expression in form of an
     *         expression tree made out of ExpressionNode objects
     */
    public ExpressionNode parse(String expression) {
        Tokenizer tokenizer = Tokenizer.getExpressionTokenizer();
        tokenizer.tokenize(expression);
        LinkedList<Token> tokens = tokenizer.getTokens();
        return this.parse(tokens);
    }

    /**
     * Parse a mathematical expression in contained in a list of tokens and return an <code>ExpressionNode</code>
     * @param tokens a list of tokens holding the tokenized input

     * @return the internal representation of the expression in form of an
     *         expression tree made out of ExpressionNode objects
     */
    public ExpressionNode parse(LinkedList<Token> tokens) {
        this.tokens = (LinkedList<Token>) tokens.clone();
        lookahead = this.tokens.getFirst();

        ExpressionNode expr = expression();

        if (lookahead.token != Token.EPSILON)
            throw new ParserException("Unexpected symbol %s found", lookahead);

        return expr;
    }

    /**
     * handles the non-terminal expression
     * @return the expression
     */
    private ExpressionNode expression() {
        ExpressionNode expr = signedTerm();
        expr = sumOp(expr);
        return expr;
    }

    /**
     * Handles the non-terminal sum_op
     * @param expressionNode an <code>ExpressionNode</code>
     * @return an <code>ExpressionNode</code>
     */
    private ExpressionNode sumOp(ExpressionNode expressionNode) {
        if (lookahead.token == Token.PLUSMINUS)
        {
            AdditionExpressionNode sum;
            if (expressionNode.getType() == ExpressionNode.ADDITION_NODE)
                sum = (AdditionExpressionNode) expressionNode;
            else
                sum = new AdditionExpressionNode(expressionNode, true);

            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode term = term();
            sum.add(term, positive);

            return sumOp(sum);
        }

        return expressionNode;
    }

    /**
     * Handles the non-terminal signed_term
     * @return
     */
    private ExpressionNode signedTerm() {
        if (lookahead.token == Token.PLUSMINUS)
        {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode term = term();
            if (positive)
                return term;
            else
                return new AdditionExpressionNode(term, false);
        }

        return term();
    }

    /**
     * Handles the non-terminal term
     * @return
     */
    private ExpressionNode term() {
        ExpressionNode factor = factor();
        return termOp(factor);
    }

    /**
     *  Handles the non-terminal term_op
     * @param expression an <code>ExpressionNode</code>
     * @return
     */
    private ExpressionNode termOp(ExpressionNode expression)
    {
        if (lookahead.token == Token.MULTDIV)
        {
            MultiplicationExpressionNode prod;

            if (expression.getType() == ExpressionNode.MULTIPLICATION_NODE)
                prod = (MultiplicationExpressionNode) expression;
            else
                prod = new MultiplicationExpressionNode(expression, true);

            boolean positive = lookahead.sequence.equals("*");
            nextToken();
            ExpressionNode factor = signedFactor();
            prod.add(factor, positive);

            return termOp(prod);
        }

        return expression;
    }

    /**
     * Handles the non-terminal signed_factor
     * @return an <code>ExpressionNode</code>
     */
    private ExpressionNode signedFactor() {
        if (lookahead.token == Token.PLUSMINUS)
        {
            boolean positive = lookahead.sequence.equals("+");
            nextToken();
            ExpressionNode factor = factor();
            if (positive)
                return factor;
            else
                return new AdditionExpressionNode(factor, false);
        }

        return factor();
    }

    /**
     * Handles the non-terminal factor
     * @return an <code>ExpressionNode</code>
     */
    private ExpressionNode factor() {
        ExpressionNode arg = argument();
        return factorOp(arg);
    }


    /**
     *Handles the non-terminal factor_op
     * @param expressionNode an <code>ExpressionNode</code>
     * @return an <code>ExpressionNode</code>
     */
    private ExpressionNode factorOp(ExpressionNode expressionNode) {
        if (lookahead.token == Token.RAISED)
        {
            nextToken();
            ExpressionNode exponent = signedFactor();

            return new ExponentiationExpressionNode(expressionNode, exponent);
        }

        return expressionNode;
    }

    /**
     * Handles the non-terminal argument
     * @return
     */
    private ExpressionNode argument() {
        if (lookahead.token == Token.FUNCTION)
        {
            int function = FunctionExpressionNode.stringToFunction(lookahead.sequence);
            nextToken();
            ExpressionNode expr = argument();
            return new FunctionExpressionNode(function, expr);
        }

        else if (lookahead.token == Token.OPEN_BRACKET)
        {
            nextToken();
            ExpressionNode expr = expression();
            if (lookahead.token != Token.CLOSE_BRACKET)
                throw new ParserException("Closing brackets expected", lookahead);
            nextToken();
            return expr;
        }

        return value();
    }

    /**
     * Handles the non-terminal value
     * @return an <code>ExpressionNode</code>
     */
    private ExpressionNode value() {
        if (lookahead.token == Token.NUMBER)
        {
            ExpressionNode expr = new ConstantExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.VARIABLE)
        {
            ExpressionNode expr = new VariableExpressionNode(lookahead.sequence);
            nextToken();
            return expr;
        }

        if (lookahead.token == Token.EPSILON) {
            throw new ParserException("Unexpected end of input");
        }
            throw new ParserException("Unexpected symbol %s found", lookahead);
    }

    /**
     * Remove the first token from the list and store the next token in lookahead
     */
    private void nextToken()
    {
        tokens.pop();
        if (tokens.isEmpty()) {
            lookahead = new Token(Token.EPSILON, "", -1);
        }
          else
            lookahead = tokens.getFirst();
    }
}

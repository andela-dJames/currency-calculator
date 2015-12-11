package com.andela.currencycalculator.parser.Token;

import com.andela.currencycalculator.parser.exception.ParserException;
import com.andela.currencycalculator.parser.expressionnodes.FunctionExpressionNode;


import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A class for reading an input string and separating it into tokens that can be
 * fed into Parser.
 *
 */
public class Tokenizer {


    /**
     * A list of TokenInfo objects
     *
     */
    private LinkedList<TokenInfo> tokenInfos;

    /**
     * A list of tokens
     */
    private LinkedList<Token> tokens;

    /**
     *  A tokenizer that can handle mathematical expressions
     */
    private static Tokenizer expressionTokenizer = null;

    /**
     * Default constructor
     */
    public Tokenizer() {
        super();
        tokenInfos = new LinkedList<TokenInfo>();
        tokens = new LinkedList<Token>();
    }

    /**
     * A method that returns a tokenizer for mathematical expressions
     * @return a tokenizer that can handle mathematical expressions
     */
    public static Tokenizer getExpressionTokenizer() {
        if (expressionTokenizer == null)
            expressionTokenizer = createExpressionTokenizer();
        return expressionTokenizer;
    }

    /**
     * A  method that creates a tokenizer for mathematical expressions
     * @return a tokenizer that can handle mathematical expressions
     */
    private static Tokenizer createExpressionTokenizer() {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.add("[+-]", Token.PLUSMINUS);
        tokenizer.add("[*/]", Token.MULTDIV);
        tokenizer.add("\\^", Token.RAISED);

        String funcs = FunctionExpressionNode.getAllFunctions();
        tokenizer.add("(" + funcs + ")(?!\\w)", Token.FUNCTION);

        tokenizer.add("\\(", Token.OPEN_BRACKET);
        tokenizer.add("\\)", Token.CLOSE_BRACKET);
        tokenizer.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Token.NUMBER);
        tokenizer.add("[a-zA-Z]\\w*", Token.VARIABLE);

        return tokenizer;
    }

    /**
     * Adds a regular expression and a token id to the internal list of recognized tokens
     * @param regex the regular expression to match against
     * @param token the token id that the regular expression is linked to
     */
    public void add(String regex, int token) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex+")"), token));
    }

    /**
     *Gets a token from a string using regex
     * @param str the string to tokenize
     */
    public void tokenize(String str) {
         str = str.trim();
        int totalLength = str.length();
        tokens.clear();
        while (!str.equals(""))
        {
            int remaining = str.length();
            boolean match = false;
            for (TokenInfo info : tokenInfos)
            {
                Matcher m = info.regex.matcher(str);
                if (m.find())
                {
                    match = true;
                    String tok = m.group().trim();
                    str = m.replaceFirst("").trim();
                    tokens.add(new Token(info.token, tok, totalLength - remaining));
                    break;
                }
            }
            if (!match)
                throw new ParserException("Unexpected character in input: " + str);
        }
    }

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    /**
     * A class that holds the information about a token type.
     */
    private class TokenInfo {
        /**
         * The regular expression to match against
         */
        public final Pattern regex;

        /**
         * A token
         */
        public final int token;

        /**
         * Creates a new <code>TokenInfo</code>
         * @param regex regular expression
         * @param token token
         */
        public TokenInfo(Pattern regex, int token) {
            super();
            this.regex = regex;
            this.token = token;
        }
    }

}

package com.andela.currencycalculator.parser.exception;

import com.andela.currencycalculator.parser.Token.Token;

/**
 * A simple subclass of RuntimeException that indicates errors when trying to
 * parse the input to Parser.
 * <p/>
 * The exception stores the token that caused the error.
 */
public class ParserException extends RuntimeException
{

    /**
     * the token that caused the error
     */
    private Token token = null;

    /**
     * Creates the evaluation exception with a message.
     * @param message the message containing the cause of the exception
     */
    public ParserException(String message) {
        super(message);
    }

    /**
     * Creates the evaluation exception with a message and a token.
     * @param message the message containing the cause of the exception
     * @param token the token that caused the exception
     */
    public ParserException(String message, Token token) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    /**
     * Overrides <code>RuntimeException.getMessage</code> to add the token information
     * into the error message.
     *
     *  @return the error message
     */
    public String getMessage() {
        String msg = super.getMessage();
        if (token != null)
        {
            msg = msg.replace("%s", token.sequence);
        }
        return msg;
    }

}

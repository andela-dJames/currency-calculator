package com.andela.currencycalculator.parser.exception;


public class EvaluationException extends RuntimeException {

    /**
     * Creates an evaluation exception with a message.
     * @param message the message containing the cause of the exception
     */
    public EvaluationException(String message) {
        super(message);
    }

}

package com.andela.currency_calculator.parcer.exception;

/**
 * Created by Oluwatosin on 11/27/2015.
 */
public class EvaluationException extends RuntimeException {
    private static final long serialVersionUID = 4794094610927358603L;

    /**
     * Construct the evaluation exception with a message.
     * @param message the message containing the cause of the exception
     */
    public EvaluationException(String message)
    {
        super(message);
    }

}

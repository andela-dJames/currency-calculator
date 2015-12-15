package com.andela.currencycalculator.keypad;

/**
 * Created by Oluwatosin on 12/15/2015.
 */
public class EqualityKey implements KeyPress {
    @Override
    public String operate(String expression, KeyPadButton keyPadButton) {
        if (expression.equals("") || expression.endsWith(".") ||
                expression.endsWith("+") || expression.endsWith("-") ||
                expression.endsWith("/") || expression.endsWith("*")){
            return "";
        }
        return expression;
    }
}

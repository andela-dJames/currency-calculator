package com.andela.currencycalculator.activities;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class SeparatorKey implements KeyPress {

        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression.contains(".") || expression.endsWith("+") ||
                    expression.endsWith("-") || expression.endsWith("/") ||
                    expression.endsWith("*")  ){
                return expression;
            }

            return expression.concat(keyPadButton.getKeyString());
        }
}

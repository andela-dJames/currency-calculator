package com.andela.currencycalculator.activities;

public class OperatorKey implements KeyPress {


        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression.equals("") || expression.endsWith(".") ||
                    expression.endsWith("+") || expression.endsWith("-") ||
                    expression.endsWith("/") || expression.endsWith("*")){
                return expression;
            }
            else
            return expression.concat(keyPadButton.getKeyString());
        }
}

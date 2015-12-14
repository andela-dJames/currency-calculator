package com.andela.currencycalculator.Activities;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class OperatorKey implements KeyPress {


        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression == "" || expression.endsWith(".") ||
                    expression.endsWith("+") || expression.endsWith("-") ||
                    expression.endsWith("/") || expression.endsWith("*")){
                return "";
            }
            return expression.concat(keyPadButton.getKeyString());
        }
}

package com.andela.currencycalculator.keypad;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class SeparatorKey implements KeyPress {

        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression.contains(".") || expression.endsWith("+") ||
                    expression.endsWith("-") || expression.endsWith("/") ||
                    expression.endsWith("*") || Character.isLetter(expression.charAt(expression.length()-1)) ){
                return "";
            }


            return keyPadButton.getKeyString();
        }
}

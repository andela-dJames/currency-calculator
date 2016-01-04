package com.andela.currencycalculator.keypad;

public class OperatorKey implements KeyPress {


        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression.equals("") || expression.endsWith(".") ||
                    expression.endsWith("+") || expression.endsWith("-") ||
                    expression.endsWith("/") || expression.endsWith("*")){
                return "";
            }
            else
            return keyPadButton.getKeyString();
        }
}

package com.andela.currencycalculator.Activities;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class SeparatorKey implements KeyPress {

        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if (expression.contains(".")){
                return "";
            }
            return expression.concat(keyPadButton.getKeyString());
        }
}

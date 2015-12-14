package com.andela.currencycalculator.activities;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class ZeroKey implements KeyPress {
        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            if(expression.equals("0")){
                return expression;
            }
            return expression.concat(keyPadButton.getKeyString());
        }
}

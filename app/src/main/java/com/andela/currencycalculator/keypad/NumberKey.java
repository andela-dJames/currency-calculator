package com.andela.currencycalculator.keypad;

/**
 * Created by Oluwatosin on 12/14/2015.
 */
public class NumberKey implements KeyPress {


        @Override
        public String operate(String expression, KeyPadButton keyPadButton) {
            return expression.concat(keyPadButton.getKeyString());
        }
}

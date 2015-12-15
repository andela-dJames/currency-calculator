package com.andela.currencycalculator.keypad;

/**
 * Created by Oluwatosin on 12/15/2015.
 */
public class DeleteKey implements KeyPress {
    @Override
    public String operate(String expression, KeyPadButton keyPadButton) {
        int index = expression.length() - 1;
        if (index < 1){
            return "";
        }

        return expression.subSequence(0, index).toString();
    }
}

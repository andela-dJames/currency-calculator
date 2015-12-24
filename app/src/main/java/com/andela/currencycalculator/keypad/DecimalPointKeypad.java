package com.andela.currencycalculator.keypad;

public class DecimalPointKeypad extends KeyPadButton {

    public DecimalPointKeypad() {
        keyType = new SeparatorKey();
    }

    public void accept(OperatorVisitor visitor) {
        visitor.visit(this);
    }
}

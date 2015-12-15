package com.andela.currencycalculator.keypad;

public class KeyPadButton {

    private String keyString;

    public KeyPress keyType;

    public KeyPadButton() {
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public KeyPress getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyPress keyType) {
        this.keyType = keyType;
    }

    public String onKeyPress(String expression) {
        return keyType.operate(expression, this);

    }
}

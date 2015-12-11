package com.andela.currencycalculator.activities;

public class KeypadController {

    private String keyPressed;

    public KeypadController() {
    }
    public boolean isKeyDelete() {
        return keyPressed == "del";
    }

    public boolean isKeyClear() {
        return keyPressed == "C";
    }

    public boolean isKeyOne() {
        return keyPressed == "1";
    }
    public boolean isKeyTwo() {
        return keyPressed == "2";
    }

    public boolean isKeyThree() {
        return keyPressed == "3";
    }

    public boolean isKeyFour() {
        return keyPressed == "4";
    }

    public boolean isKeyFive() {
        return keyPressed == "5";
    }

    public boolean isKeySix() {
        return keyPressed == "6";
    }

    public boolean isKeySeven() {
        return keyPressed == "7";
    }

    public boolean isKeyNine() {
        return keyPressed == "9";
    }

    public boolean isKeyZero() {
        return keyPressed == "0";
    }

    public boolean isKeyPlus() {
        return keyPressed == "+";
    }

    public boolean isKeyMinus() {
        return keyPressed == "-";
    }

    public boolean isKeyDivide(){
        return keyPressed == "/";
    }

    public boolean isKeyMultiply(){
        return keyPressed == "*";
    }

    public boolean isKeyDecimalSeparator(){
        return keyPressed == ".";
    }

}

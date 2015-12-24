package com.andela.currencycalculator.keypad;

/**
 * Created by Oluwatosin on 12/23/2015.
 */
public interface OperatorVisitor {
     void visit(NumberKeyPad numberKeyPad);
     void visit(OperatorButton operatorButton);
     void visit(DecimalPointKeypad decimalPointKeypad);
     void visit(KeyZero keyZero);
     void visit(EqualityKeyPad equalityKeyPad);
     void visit(DeleteButton deleteButton);
}

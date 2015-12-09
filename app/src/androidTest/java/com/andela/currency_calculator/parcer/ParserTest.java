package com.andela.currency_calculator.parcer;

import com.andela.currency_calculator.parcer.expressionnodes.ExpressionNode;
import com.andela.currency_calculator.parcer.expressionnodes.SetVariable;

import junit.framework.TestCase;

/**
 * Created by Oluwatosin on 12/9/2015.
 */
public class ParserTest extends TestCase {
    String exprstr = "2*(1+sin(pi/2))^2";
    Parser parser = new Parser();


    public void testParse() throws Exception {
        ExpressionNode expr = parser.parse(exprstr);
        expr.accept(new SetVariable("pi", Math.PI));
        assertEquals(expr.getValue(), 8.0);
    }


}
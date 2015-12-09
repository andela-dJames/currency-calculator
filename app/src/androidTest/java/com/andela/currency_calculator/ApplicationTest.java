package com.andela.currency_calculator;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.andela.currency_calculator.Activities.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }
    MainActivity activity = getActivity();
    public void testIfActivityExist(){

        assertNotNull(activity);
    }

    public void testCalculatorScreen(){
        final TextView expressionText = (TextView) activity.findViewById(R.id.expression_screen);
        final TextView resultText = (TextView) activity.findViewById(R.id.evaluation_screen);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                expressionText.requestFocus();
                resultText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("1234567");

        getInstrumentation().sendStringSync("1234567");

    }
}
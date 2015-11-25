package com.andela.currency_calculator.operations;


import android.util.Log;

public class ArithmeticOperator {

    public double add(double...vals){
        double sum = 0;
        for (int i = 0; i < vals.length; i++) {
            sum += vals[i];
        }
        return sum;
    }

    public double minus(double...vals){
        double sum = 0;
        for (int i = 0; i < vals.length; i++) {
            sum -= vals[i];
        }
        return sum;
    }

    public double multiply(double...vals){
        double sum = 0;
        for (int i = 0; i < vals.length; i++) {
            sum *= vals[i];
        }
        return sum;
    }

    public double divide(double...vals){
        double sum = 0;
        for (int i = 0; i < vals.length; i++) {
            try {
                sum /= vals[i];
            }
            catch (ArithmeticException e)
            {
                Log.d("divide by zero", e.toString());
            }

        }
        return sum;
    }
}

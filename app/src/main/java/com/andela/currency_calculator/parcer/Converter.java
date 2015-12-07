package com.andela.currency_calculator.parcer;

import com.andela.currency_calculator.models.Currency.Rate;

/**
 * Created by Oluwatosin on 12/6/2015.
 */
public class Converter {
    private Rate rate;

    private double argument;

    public Converter(Rate rate) {
        this.rate = rate;
        this.argument = argument;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public double getArgument() {
        return argument;
    }

    public void setArgument(double argument) {
        this.argument = argument;
    }

    public double convert(double val) {
        double exRate = rate.getExchangeRate();
        return exRate * val;

    }
}

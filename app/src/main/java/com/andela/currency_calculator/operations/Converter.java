package com.andela.currency_calculator.operations;


import com.andela.currency_calculator.models.Currency.Rate;

public class Converter {
    private Rate rate;

    public Converter() {
    }

    public Converter(Rate rate) {
        this.rate = rate;
    }

    public double convert(double val) {
        double ex = rate.getExchangeRate();
        return val * ex;
    }

    public double reverseConvert(double val) {
        double ex = rate.getExchangeRate();
        return val / ex;
    }
}

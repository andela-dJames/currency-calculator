package com.andela.currencycalculator.models.dal;

import com.andela.currencycalculator.models.Currency.Rate;

import junit.framework.TestCase;

/**
 * Created by Oluwatosin on 12/11/2015.
 */
public class ExchangeRateAPITest extends TestCase {
    ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI();
    Rate rate = new Rate("USD", "NGN");
    private Rate rate1 = new Rate("GBP", "NGN");

    public void testFetchExchangeRateDollarToNaira() throws Exception {

        String str = exchangeRateAPI.fetchExchangeRate(rate);
        double data = Double.parseDouble(str);

        assertEquals(199.010100, data);

    }
    public void testFetchExchangeRatePoundsToNaira() throws Exception {

        String str = exchangeRateAPI.fetchExchangeRate(rate1);
        double data = Double.parseDouble(str);

        assertEquals(301.533944, data);

    }
}
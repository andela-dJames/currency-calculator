package com.andela.currencycalculator.models.dal;

import com.andela.currencycalculator.models.currency.Rate;

import junit.framework.TestCase;

/**
 * Created by Oluwatosin on 12/18/2015.
 */
public class ExchangeRateAPITest extends TestCase {

    Rate rate = new Rate("USD", "NGN");
    ExchangeRateAPI api = new ExchangeRateAPI();

    public void testFetchExchangeRate() throws Exception {
        String val = api.fetchExchangeRate(rate);
        assertEquals("198.822137" , val);
    }


}
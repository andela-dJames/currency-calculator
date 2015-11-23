package com.andela.currency_calculator.models.dal;

import com.andela.currency_calculator.models.Currency.Rate;

import junit.framework.TestCase;

import java.net.URL;

/**
 * Created by Oluwatosin on 11/22/2015.
 */
public class EchangeRateAPICollectionTest extends TestCase {

    public void testBuildUrl() throws Exception {
        EchangeRateAPICollection<Rate>exchangeRateAPICollection = new EchangeRateAPICollection<Rate>();
        URL url = exchangeRateAPICollection.buildUrl("NGN", "USD");

    }
}
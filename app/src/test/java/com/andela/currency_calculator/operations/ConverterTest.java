package com.andela.currency_calculator.operations;

import android.test.AndroidTestCase;

import com.andela.currency_calculator.models.Currency.Rate;
import com.andela.currency_calculator.models.dal.ExchangeRateAPICollection;

import junit.framework.TestCase;

import java.text.DecimalFormat;

/**
 * Created by Oluwatosin on 11/26/2015.
 */
public class ConverterTest extends AndroidTestCase {
    Rate rate = new Rate("NGN", "USD");
    ExchangeRateAPICollection exchangeRateAPICollection = new ExchangeRateAPICollection();


    @Override
    public void setUp() throws Exception {
        exchangeRateAPICollection.execute(rate);
        System.out.print(rate.getExchangeRate());

    }

    public void testConvert() throws Exception {
        Converter converter = new Converter(rate);
       double result =  converter.convert(100);
        double newResult = Math.round(result * 100)/100;
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        assertEquals(19925.00, newResult );

    }

    public void testReverseConvert() throws Exception {

    }
}
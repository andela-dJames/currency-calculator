package com.andela.currencycalculator.operations;

import android.test.AndroidTestCase;

import com.andela.currencycalculator.models.Currency.Rate;
import com.andela.currencycalculator.models.dal.FetchTask;

/**
 * Created by Oluwatosin on 11/26/2015.
 */
public class ConverterTest extends AndroidTestCase {
    Rate rate = new Rate("NGN", "USD");
    FetchTask fetchTask = new FetchTask();


//    @Override
//    public void setUp() throws Exception {
//        fetchTask.execute(rate);
//        System.out.print(rate.getExchangeRate());
//
//    }
//
//    public void testConvert() throws Exception {
//        Converter converter = new Converter(rate);
//       double result =  converter.convert(100);
//        double newResult = Math.round(result * 100)/100;
//        DecimalFormat decimalFormat = new DecimalFormat("###.##");
//        assertEquals(19925.00, newResult );
//
//    }
//
//    public void testReverseConvert() throws Exception {
//
//    }
}
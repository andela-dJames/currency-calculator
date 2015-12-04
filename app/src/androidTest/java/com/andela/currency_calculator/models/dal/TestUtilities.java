package com.andela.currency_calculator.models.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.andela.currency_calculator.models.dal.CurrencyConverterContract;

import java.util.Map;
import java.util.Set;


public class TestUtilities extends AndroidTestCase {

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createExchangeRateValues() {
        ContentValues exchangeRateValues = new ContentValues();
        //exchangeRateValues.put(CurrencyConverterContract.ExchangeRates._ID, 1);
        exchangeRateValues.put(CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, "NGN");
        exchangeRateValues.put(CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY, "USD");
        exchangeRateValues.put(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE, 0.00501883);
        return exchangeRateValues;
    }

}

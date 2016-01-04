package com.andela.currencycalculator;

import android.content.ContentValues;
import android.content.Context;

import com.andela.currencycalculator.models.currency.Rate;
import com.andela.currencycalculator.models.dal.CurrencyConverterContract;
import com.andela.currencycalculator.models.dal.ExchangeRateAPI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResourceProvider {

    /**
     * An array of content values
     */

    private Context context;

    private ExchangeRateAPI exchangeRateAPI;

    public ResourceProvider(Context context) {
        this.context = context;
        exchangeRateAPI = new ExchangeRateAPI();
    }

    public List<String> getCurrencyCodesFromResource() {

        String[] array = context.getResources().getStringArray(R.array.currency_code);
        return Arrays.asList(array);

    }

    public Rate combineCodes(String base, String target) {
        return new Rate(base, target);
    }

    public ContentValues getRateContents(Rate rate, ContentValues values) {

         values = new ContentValues();

        values.put(CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, rate.getBaseCurrency());

        values.put(CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY, rate.getTargetCurrency());

        values.put(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE, rate.getExchangeRate());

        return values;
    }

    public ContentValues[] runInLoop(Rate rate, List<String> list,
                                     ContentValues values, ContentValues[] contentValues) throws IOException {
        int count = 0;
        values = new ContentValues();
        contentValues = new ContentValues[count];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {

                rate = combineCodes(list.get(i), list.get(i));

                exchangeRateAPI = new ExchangeRateAPI();

                String str = exchangeRateAPI.fetchExchangeRate(rate);

                double data = Double.parseDouble(str);

                rate.setExchangeRate(data);

                getRateContents(rate, values);
                contentValues = increaseArraySize(contentValues, count);
                contentValues[count] = values;
                count++;
            }
        }
    return contentValues;
    }

    /**
     * Increases an array size
     * @param values
     * @param count
     * @return
     */
    private ContentValues[] increaseArraySize(ContentValues[] values, int count) {

        if (values.length == count){

            ContentValues[] items = new ContentValues[values.length + 50];

            System.arraycopy(values,0, items, 0, values.length);

            values = items;
        }
        return values;
    }
}
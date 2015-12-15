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

    public ContentValues getRateContents(Rate rate) {

        ContentValues rateContents = new ContentValues();

        rateContents.put(CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, rate.getBaseCurrency());

        rateContents.put(CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY, rate.getTargetCurrency());

        rateContents.put(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE, rate.getExchangeRate());

        return rateContents;
    }

    public void doSomething(Rate rate, int initial, List<String> list,
                            ContentValues values) throws IOException {
        for (int i = initial; i < list.size(); i++) {

            rate = combineCodes(list.get(initial), list.get(i));

            exchangeRateAPI = new ExchangeRateAPI();

            String str = exchangeRateAPI.fetchExchangeRate(rate);

            double data = Double.parseDouble(str);

            rate.setExchangeRate(data);

            values = getRateContents(rate);
        }

    }
}
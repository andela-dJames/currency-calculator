package com.andela.currencycalculator.models.dal;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.models.currency.Rate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateTask extends AsyncTask<Void, Void, Void> {

    private List<Rate> rates;
    /**
     * Content values
     */
    private ContentValues rateContents;

    private Context context;

    private SqlLiteDataAccess sqlLiteDataAccess;

    private ExchangeRateAPI exchangeRateAPI;

    public UpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        List<String> curr_codes = new ArrayList();
        curr_codes = getCurrencyCodes(context);
        sqlLiteDataAccess = new SqlLiteDataAccess(context);
        String selection = "";


        int i,j, count = 0;
        Rate rate;
        rates = new ArrayList<Rate>();
        for( i=0; i<curr_codes.size(); i++){
            for( j= i; j <= (curr_codes.size()-1); j++ ){

                rate = new Rate(curr_codes.get(i),curr_codes.get(j));


                try {
                    String str = exchangeRateAPI.fetchExchangeRate(rate);
                    double d = Double.parseDouble(str);
                    rate.setExchangeRate(d);
                    rateContents = new ContentValues();
                    rateContents.put(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE, rate.getExchangeRate());
                    selection = CurrencyConverterContract.ExchangeRates.BASE_CURRENCY + " = ? AND "+
                            CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY + " = ? ";
                    String[] selectionArgs = {rate.getBaseCurrency(), rate.getTargetCurrency()};
                    sqlLiteDataAccess.update(CurrencyConverterContract.ExchangeRates.CONTENT_URI, rateContents,selection, selectionArgs );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rates.add(rate);
            }
        }
        return null;
    }

    public List<String>getCurrencyCodes(Context context){

        String[] array = context.getResources().getStringArray(R.array.currency_code);

        return Arrays.asList(array);

    }
}

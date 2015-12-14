package com.andela.currencycalculator.models.dal;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.models.currency.Rate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A class for carrying out ASYNc Task in the background
 * used for making HTTP calls and Onetime insertion of data into database
 */

public class FetchTask extends AsyncTask<Context, String, List<Rate>>{

    private static final String TAG = "BACKGROUND TASK" ;
    /**
     * A list of rates
     */
    private List<Rate> rates;
    /**
     * Content values
     */
    private ContentValues rateContents;
    /**
     * An array of content values
     */
    private ContentValues[] contentValues;
    /**
     * SqliteDataAccess object
     */
    private SqlLiteDataAccess access;

    /**
     * A URl object
     */
    private ExchangeRateAPI exchangeRateAPI;

    /**
     * The background task runner
     * @param params
     * @return
     */
    @Override
    protected List<Rate> doInBackground(Context... params) {
        exchangeRateAPI = new ExchangeRateAPI();
        List<String> curr_codes = new ArrayList();
        String[] array = params[0].getResources().getStringArray(R.array.currency_code);
        curr_codes = Arrays.asList(array);
        access = new SqlLiteDataAccess(params[0]);
        int i,j, count = 0;
        Rate rate;
        contentValues = new ContentValues[count];
        rates = new ArrayList<Rate>();
        for( i=0; i<curr_codes.size(); i++){
            for( j= i; j <= (curr_codes.size()-1); j++ ){

                rate = new Rate(curr_codes.get(i),curr_codes.get(j));


                try {
                    String str = exchangeRateAPI.fetchExchangeRate(rate);
                    double d = Double.parseDouble(str);
                    rate.setExchangeRate(d);
                    saveSharedData(params[0], rate);
                    rateContents = new ContentValues();
                    rateContents.put(CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, rate.getBaseCurrency());
                    rateContents.put(CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY, rate.getTargetCurrency());
                    rateContents.put(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE, rate.getExchangeRate());
                    contentValues = increaseArraySize(contentValues, count);
                    contentValues[count] = rateContents;
                    count++;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                rates.add(rate);
            }
        }

       access.bulkInsert(CurrencyConverterContract.ExchangeRates.CONTENT_URI, contentValues);
        return   rates;
    }


    /**
     * Task for execution after background task
     * @param list
     */
    @Override
    protected void onPostExecute(List<Rate> list){

       for (Rate rate : list){
           Log.d(TAG, rate.getBaseCurrency() + " : " + rate.getTargetCurrency() + ": "+String.valueOf(rate.getExchangeRate()));
       }

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

    private void saveSharedData(Context context, Rate rate) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(rate.getExchangeRate() + "-"+ rate.getTargetCurrency(), (float) rate.getExchangeRate());
        editor.commit();
    }



}

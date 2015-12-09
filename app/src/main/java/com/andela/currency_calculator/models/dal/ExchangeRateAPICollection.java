package com.andela.currency_calculator.models.dal;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.andela.currency_calculator.Constants;
import com.andela.currency_calculator.R;
import com.andela.currency_calculator.models.Currency.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jonathanfinerty.once.Once;

/**
 * A class for carrying out ASYNc Task in the background
 * used for making HTTP calls and Onetime insertion of data into database
 */

public class ExchangeRateAPICollection extends AsyncTask<Context, String, List<Rate>>{

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
     * A URL separator
     */
    private  String separator = "/";
    /**
     * A string for making API calls
     */
    private  String questionMark = "?";
    /**
     * A URl object
     */
    private URL url;
    private HttpURLConnection connection;
    private BufferedReader reader;

    /**
     * The background task runner
     * @param params
     * @return
     */
    @Override
    protected List<Rate> doInBackground(Context... params) {


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
                    String str = fetchExchangeRate(rate);
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

//       for (Rate rate : list){
//           Log.d(TAG, rate.getBaseCurrency() + " : " + rate.getTargetCurrency() + ": "+String.valueOf(rate.getExchangeRate()));
//       }

    }

    /**
     * fetches the exchange rate from the API
     * @param rate
     * @return
     * @throws IOException
     */
    private String fetchExchangeRate(Rate rate) throws IOException {
        url = buildUrl(rate.getBaseCurrency(), rate.getTargetCurrency());
        connectToService(url);
       return exchangeRate(url);
    }

    /**
     * A URL builder method
     * @param currencyParams
     * @return
     * @throws MalformedURLException
     */
    private URL buildUrl(String...currencyParams) throws MalformedURLException {
       StringBuilder uri  = new StringBuilder();
        uri.append(Constants.API_URl)
                .append(currencyParams[0])
                .append(separator)
                .append(currencyParams[1])
                .append(questionMark)
                .append(Constants.API_KEY);
        return url = new URL(uri.toString());
    }

    /**
     * Connects to API SERvice
     * @param url
     * @throws IOException
     */
    private void connectToService(URL url) throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Constants.REQUEST_METHOD);
        connection.connect();

    }

    /**
     * returns the exchange rate from the API
     * @param url
     * @return
     * @throws IOException
     */
    private String exchangeRate(URL url) throws IOException {

        reader = new BufferedReader(new InputStreamReader(url.openStream()));

        return reader.readLine();
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

    public class  background extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }
    }

}

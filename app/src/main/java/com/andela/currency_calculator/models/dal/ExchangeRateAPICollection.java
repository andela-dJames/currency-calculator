package com.andela.currency_calculator.models.dal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.andela.currency_calculator.Constants;
import com.andela.currency_calculator.ContextProvider;
import com.andela.currency_calculator.models.Currency.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateAPICollection extends AsyncTask<List<String>, String, List<Rate>>{
    private List<Rate> rates;
    private ContentValues rateContents;
    private ContentValues[] contentValues;
    private ContentProvider provider;
    @Override
    protected List<Rate> doInBackground(List<String>... params) {
        provider = new ExchangeRateProvider();
        int i,j = 0;
        Rate rate;
        int count = 0;
        contentValues = new ContentValues[count];
        rates = new ArrayList<Rate>();
        for( i=0; i<params[0].size(); i++){
            for( j= i+1; j <= (params[0].size()-1); j++ ){

                rate = new Rate(params[0].get(i), params[0].get(j));
                try {
                    //setExchangeRate(rate);
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
        provider.bulkInsert(CurrencyConverterContract.ExchangeRates.CONTENT_URI, contentValues);
        return   rates;
    }

    @Override
    protected void onPostExecute(List<Rate> list){
       String str = String.valueOf(contentValues.length);
        Log.d("THIS ACTIVITY", str);


    }

    private  String separator = "/";
    private  String questionMark = "?";
    private URL url;
    private HttpURLConnection connection;
    private BufferedReader reader;


    public String fetchExchangeRate(Rate rate) throws IOException {
        url = buildUrl(rate.getBaseCurrency(), rate.getTargetCurrency());
        connectToService(url);
       return exchangeRate(url);
    }

    public void setExchangeRate(Rate rate) throws IOException {
        String ex = fetchExchangeRate(rate);
        Double exrate = Double.parseDouble(ex);
        rate.setExchangeRate(exrate);
    }

    public URL buildUrl(String...currencyParams) throws MalformedURLException {
       StringBuilder uri  = new StringBuilder();
        uri.append(Constants.API_URl)
                .append(currencyParams[0])
                .append(separator)
                .append(currencyParams[1])
                .append(questionMark)
                .append(Constants.API_KEY);
        return url = new URL(uri.toString());
    }

    public void connectToService(URL url) throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

    }

    public String exchangeRate(URL url) throws IOException {
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        return reader.readLine();
    }

    private ContentValues[] increaseArraySize(ContentValues[] values, int count) {

        if (values.length == count){
            ContentValues[] items = new ContentValues[values.length + 50];
            System.arraycopy(values,0, items, 0, values.length);
            values = items;
        }
        return values;
    }

}

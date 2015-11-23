package com.andela.currency_calculator.models.dal;


import android.os.AsyncTask;
import android.util.Log;

import com.andela.currency_calculator.Constants;
import com.andela.currency_calculator.models.Currency.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class EchangeRateAPICollection extends AsyncTask<Rate, String, String> implements DataCollection<Rate> {
    private  String result;


    @Override
    protected String doInBackground(Rate... params) {
        try {
           result = fetchExchangeRate(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("ACTIVITY", result);
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

    @Override
    public void save(Rate data, DataCallback callback) {

    }

    @Override
    public void get(Rate rate, DataCallback<List<Rate>> callback) {

    }

    @Override
    public void getAll(DataCollection<List<Rate>> callback) {

    }

    @Override
    public void update(Rate data, DataCallback callback) {

    }
}

package com.andela.currency_calculator.models.dal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.andela.currency_calculator.Constants;
import com.andela.currency_calculator.ContextProvider;
import com.andela.currency_calculator.R;
import com.andela.currency_calculator.models.Currency.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ExchangeRateAPICollection extends AsyncTask<Rate, String, Rate>{
    private  String result;
    private Rate rate;
    private Context context;

    @Override
    protected Rate doInBackground(Rate... params) {
        try {

           result = fetchExchangeRate(params[0]);
            Double d = Double.parseDouble(result);
            params[0].setExchangeRate(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params[0];
    }

    @Override
    protected void onPostExecute(Rate rate){

      List<String> list = ContextProvider.getInstance().getCodes();

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

}

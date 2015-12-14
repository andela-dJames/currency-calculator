package com.andela.currencycalculator.models.dal;

import com.andela.currencycalculator.Constants;
import com.andela.currencycalculator.models.currency.Rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Oluwatosin on 12/9/2015.
 */
public class ExchangeRateAPI {

    /**
     * A URL separator
     */
    private  String separator = "/";
    /**
     * A string for making API calls
     */
    private  String questionMark = "?";

    private URL url;
    private HttpURLConnection connection;
    private BufferedReader reader;

    /**
     * fetches the exchange rate from the API
     * @param rate
     * @return
     * @throws IOException
     */
    public String fetchExchangeRate(Rate rate) throws IOException {
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

}

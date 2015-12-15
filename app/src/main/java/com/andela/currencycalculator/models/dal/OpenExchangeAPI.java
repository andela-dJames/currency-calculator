package com.andela.currencycalculator.models.dal;

import com.andela.currencycalculator.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Oluwatosin on 12/15/2015.
 */
public class OpenExchangeAPI {

    private URL url;
    private HttpURLConnection connection;
    private BufferedReader reader;

    private URL buildUrl(String...currencyParams) throws MalformedURLException {

        return url = new URL(Constants.OPEN_EXCHANGE_API);
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

    public String fetchExchangeRate() throws IOException {
        url = buildUrl();
        connectToService(url);
        return exchangeRate(url);
    }

    public void fromJson(String json) {
        //ObjectMapper mapper = new ObjectMapper();
    }

}

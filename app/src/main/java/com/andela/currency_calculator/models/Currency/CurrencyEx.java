package com.andela.currency_calculator.models.Currency;

/**
 * Represents a currency
 */
public class CurrencyEx {
    /**
     * The code of the currency, eg. NGN, USD, GBP
     */
    private String currencyCode;
    /**
     * THE name of the currency
     */
    private String currencyName;
    /**
     * The country that owns thw currency
     */
    private String country;

    public CurrencyEx() {

    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

package com.andela.currency_calculator.models.Currency;

/**
 * Represents a Rates
 */
public class Rate extends Model{
    /**
     * The base currency
     */
    private String baseCurrency;
    /**
     * the target currency
     */
    private String targetCurrency;
    /**
     * the exchange rate value
     */
    private double exchangeRate;


    public Rate() {
    }

    /**
     * Creates a new rate withe the given parameters
     * @param baseCurrency the base currency
     * @param targetCurrency the target currency
     */
    public Rate(String baseCurrency, String targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        notifyChangeListeners(this,
                BASE_CURRENCY,
                this.baseCurrency,
                this.baseCurrency = baseCurrency);
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        notifyChangeListeners(this,
                TARGET_CURRENCY,
                this.targetCurrency,
                this.targetCurrency = targetCurrency);
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
       notifyChangeListeners(this, EXCHANGE_RATE, this.exchangeRate,  this.exchangeRate = exchangeRate);
    }

}

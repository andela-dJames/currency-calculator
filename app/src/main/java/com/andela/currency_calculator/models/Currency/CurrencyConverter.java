package com.andela.currency_calculator.models.Currency;

import android.provider.BaseColumns;


public final class CurrencyConverter {

    private static final String TEXT_TYPE = "TEXT";
    private static final String SEPARATOR =  ", ";

    public static final String CREATE_RATE_TABLE_QUERY = "CREATE TABLE IF NOT EXIST" +
            ExchangeRates.TABLE_NAME +
            "(" + ExchangeRates._ID +
            " INTEGER PRIMARY KEY " +
            SEPARATOR+
            ExchangeRates.BASE_CURRENCY
            + " " + TEXT_TYPE + ", "+
            ExchangeRates.TARGET_CURRENCY + " "+
            TEXT_TYPE + ", " + ExchangeRates.EXCHANGE_RATE + " );";

    public static final String CREATE_CURRENCY_TABLE_QUERY = "CREATE TABLE IF NOT EXIST "  +
            Currency.TABLE_NAME+
            " (" + Currency.COLUMN_CURRENCY_CODE +
            TEXT_TYPE + SEPARATOR+ Currency.COLUMN_COUNTRY_NAME +
            TEXT_TYPE + SEPARATOR + Currency.COLUMN_CURRENCY_NAME + " );" ;


    public CurrencyConverter() {
    }

    public static abstract class Currency implements BaseColumns{
        public static final String TABLE_NAME ="currency";

        public static final String COLUMN_CURRENCY_CODE = "currency_code";

        public static final String COLUMN_CURRENCY_NAME = "currency_name";

        public static final String COLUMN_COUNTRY_NAME = "country_name";
    }

    public static abstract class ExchangeRates implements BaseColumns {
        public static final String BASE_CURRENCY = "base_currency";

        public static final String TARGET_CURRENCY = "target_currency";

        public static final String EXCHANGE_RATE = "exchange_rate";
        public static final String TABLE_NAME = "rates";
    }


}

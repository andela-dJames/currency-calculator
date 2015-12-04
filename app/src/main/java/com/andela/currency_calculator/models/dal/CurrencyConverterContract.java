package com.andela.currency_calculator.models.dal;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;


public final class CurrencyConverterContract {
    public static final String CONTENT_AUTHORITY = "com.andela.currency_calculator";
    public static final Uri BASE_CONTENT_URI = Uri.parse("contents://" + CONTENT_AUTHORITY);

    public static final String PATH_RATES = "rates";

    public static final String PATH_CURRENCY = "currency";

    private static final String TEXT_TYPE = "TEXT";
    private static final String SEPARATOR =  ", ";

    public static final String CREATE_RATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " +
            ExchangeRates.TABLE_NAME +
            "(" + ExchangeRates._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
             ExchangeRates.BASE_CURRENCY
            + " " + TEXT_TYPE + ", "+
            ExchangeRates.TARGET_CURRENCY + " "+
            TEXT_TYPE + ", " + ExchangeRates.EXCHANGE_RATE +" REAL "+ " );";

    public static final String CREATE_CURRENCY_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "  +
            Currency.TABLE_NAME+
            " (" + Currency.COLUMN_CURRENCY_CODE +
            TEXT_TYPE + SEPARATOR+ Currency.COLUMN_COUNTRY_NAME +
            TEXT_TYPE + SEPARATOR + Currency.COLUMN_CURRENCY_NAME + TEXT_TYPE + " );" ;

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
       Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    public CurrencyConverterContract() {
    }

    public static abstract class Currency implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CURRENCY)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENCY;
        public static final String TABLE_NAME ="currency";

        public static final String COLUMN_CURRENCY_CODE = "currency_code";

        public static final String COLUMN_CURRENCY_NAME = "currency_name";

        public static final String COLUMN_COUNTRY_NAME = "country_name";

        public Uri buildCurrencyuri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static abstract class ExchangeRates implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RATES)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RATES;

        public static final String BASE_CURRENCY = "base_currency";

        public static final String TARGET_CURRENCY = "target_currency";

        public static final String EXCHANGE_RATE = "exchange_rate";
        public static final String TABLE_NAME = "rates";

        public static Uri buildRatesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildExchangeRateUri(String exchangeRate){
            return CONTENT_URI.buildUpon().appendPath(exchangeRate).build();

        }

    }


}

package com.andela.currency_calculator.models.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.File;

/**
 * A Class that contains Database Helper methods
 */
public class SqlLiteDataAccess extends SQLiteOpenHelper {
    /**
     * An Sqlite database
     */
    private SQLiteDatabase database;
    /**
     * The verion of the databse inorder to monitor upgrades and manage new versions
     */
    public final static int DATABASE_VERSION = 2;
    /**
     * The name of the database
     */
    public static final String DATABASE_NAME = "currency.db";

    public SqlLiteDataAccess(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CurrencyConverterContract.CREATE_RATE_TABLE_QUERY);
        db.execSQL(CurrencyConverterContract.CREATE_CURRENCY_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+ CurrencyConverterContract.ExchangeRates.TABLE_NAME);
    }

    /**
     * Inserts bulk data into database
     * @param uri
     * @param values
     */
    public void bulkInsert(Uri uri, ContentValues[] values){

        database = this.getReadableDatabase();

        database.beginTransaction();

        int count = 0;
        try {
            for (ContentValues value : values) {
                long _id = database.insert(CurrencyConverterContract.ExchangeRates.TABLE_NAME, null, value);
                if (_id != -1) {
                    count++;
                }
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

    }

    public void deletedb() {
        File file = new File("data/data/com.andela.currency_calculator/databases/currency.db");
        database.deleteDatabase(file);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    /**
     * A query method for geting exchange rate from the database
     *
     */
    public double query(String base, String target){

        database = getReadableDatabase();
        double exRate = 0;
        Cursor c = database.rawQuery("SELECT " +
                CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE + " FROM " +
                CurrencyConverterContract.ExchangeRates.TABLE_NAME +
        " WHERE " + CurrencyConverterContract.ExchangeRates.BASE_CURRENCY + " = " + "'" + base + "'" + " AND " +
        CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY  + " = " + "'" + target +"'" + " ;", null);

       while ( c.moveToNext()) {
           exRate = (c.getDouble(c.getColumnIndex(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE)));

       }
        return exRate;
    }
    public double reget(String base, String target) {
        double ex = 0;
        String[] columns = { CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY,
        CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE};
        String selection = CurrencyConverterContract.ExchangeRates.BASE_CURRENCY + " = ? AND " +
                CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY +" = ? ";
        String[] selectionArgs = { target, base };
        String sortOrder = CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE;

        Cursor cursor = database.query(CurrencyConverterContract.ExchangeRates.TABLE_NAME, columns, selection, selectionArgs, null, null, sortOrder);

        while (cursor.moveToNext()) {
//            Rate rate = new Rate(cursor.getString(RateColumns.FROM_INDEX),
//                    cursor.getString(RateColumns.TO_INDEX),
//                    cursor.getDouble(RateColumns.VALUE_INDEX),
//                    DateTime.parse(cursor.getString(RateColumns.LAST_UPDATED_AT_INDEX)));
            ex = cursor.getDouble(cursor.getColumnIndex(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE));


        }

        cursor.close();

        return ex;
    }

    public double get(String base, String target) {
        double ex = 0;
        String[] columns = { CurrencyConverterContract.ExchangeRates.BASE_CURRENCY, CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY,
                CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE};
        String selection = CurrencyConverterContract.ExchangeRates.BASE_CURRENCY + " = ? AND " +
                CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY +" = ? ";
        String[] selectionArgs = { base, target };
        String sortOrder = CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE;

        Cursor cursor = database.query(CurrencyConverterContract.ExchangeRates.TABLE_NAME, columns, selection, selectionArgs, null, null, sortOrder);

        while (cursor.moveToNext()) {
//            Rate rate = new Rate(cursor.getString(RateColumns.FROM_INDEX),
//                    cursor.getString(RateColumns.TO_INDEX),
//                    cursor.getDouble(RateColumns.VALUE_INDEX),
//                    DateTime.parse(cursor.getString(RateColumns.LAST_UPDATED_AT_INDEX)));
            ex = cursor.getDouble(cursor.getColumnIndex(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE));


        }

        cursor.close();

        return ex;
    }
    /**
     * A method for doing reverse query if the table does not contain exchange rates for a given combinaton
     * of currency
     * @param
     */
    public double reverseQuery(String base, String target){
        database = getReadableDatabase();
        double exRate = 0;
        Cursor c = database.rawQuery("SELECT " +
                CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE + " FROM " +
                CurrencyConverterContract.ExchangeRates.TABLE_NAME +
                " WHERE " + CurrencyConverterContract.ExchangeRates.BASE_CURRENCY + " = " + "'" + target + "'" + " AND " +
                CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY  + " = " + "'" + base +"'" + " ;", null);

        while ( c.moveToNext()) {
            exRate = (c.getDouble(c.getColumnIndex(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE)));
        }

        return 1.0000/exRate;



    }

}

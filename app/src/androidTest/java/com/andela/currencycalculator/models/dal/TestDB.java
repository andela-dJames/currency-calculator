package com.andela.currencycalculator.models.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.andela.currencycalculator.models.currency.Rate;

import java.util.HashSet;

/**
 * Created by Oluwatosin on 11/28/2015.
 */
public class TestDB extends AndroidTestCase {
    public static final String TAG = TestDB.class.getSimpleName();
    void deleteDb(){
        mContext.deleteDatabase(SqlLiteDataAccess.DATABASE_NAME);
    }

    @Override
    public void setUp() throws Exception {
        deleteDb();
    }

    public void testCreateDB(){
        final HashSet<String> tableNames = new HashSet<String>();
        tableNames.add(CurrencyConverterContract.ExchangeRates.TABLE_NAME);
        tableNames.add(CurrencyConverterContract.Currency.TABLE_NAME);
        mContext.deleteDatabase(SqlLiteDataAccess.DATABASE_NAME);
        SQLiteDatabase db = new SqlLiteDataAccess(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());
        Cursor cr = db.rawQuery("SELECT name FROM sqlite_master where type ='table'", null);
        assertTrue("Error: database has not been created correctly", cr.moveToFirst());

        do {
            tableNames.remove(cr.getString(0));
        }while (cr.moveToNext());
        assertTrue("Error: Your database was created without both the rates entry and currency entry tables",
                tableNames.isEmpty());

        cr = db.rawQuery("PRAGMA table_info(" + CurrencyConverterContract.ExchangeRates.TABLE_NAME + ")",
                null);

        assertTrue("Error: this means that we are not able to query database for table information", cr.moveToFirst());

        final HashSet<String> exchangeRatesTableColumnNames = new HashSet<>();
        exchangeRatesTableColumnNames.add(CurrencyConverterContract.ExchangeRates.BASE_CURRENCY);
        exchangeRatesTableColumnNames.add(CurrencyConverterContract.ExchangeRates.TARGET_CURRENCY);
        exchangeRatesTableColumnNames.add(CurrencyConverterContract.ExchangeRates.EXCHANGE_RATE);

        int columnNameIndex = cr.getColumnIndex("name");

        do {
            String columnName = cr.getString(columnNameIndex);
            exchangeRatesTableColumnNames.remove(columnName);
        } while(cr.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                exchangeRatesTableColumnNames.isEmpty());
        db.close();
    }

    public void testRateTable() {
        long id = insertRate();
        assertFalse("Error: Location Not Inserted Correctly", id == -1L);

        SqlLiteDataAccess sqlLiteDataAccess = new SqlLiteDataAccess(mContext);
        SQLiteDatabase db = sqlLiteDataAccess.getWritableDatabase();



    }

    public long insertRate() {
        SqlLiteDataAccess sqlLiteDataAccess = new SqlLiteDataAccess(mContext);
        SQLiteDatabase db = sqlLiteDataAccess.getWritableDatabase();

        ContentValues testValues = TestUtilities.createExchangeRateValues();
        long rowID = db.insert(CurrencyConverterContract.ExchangeRates.TABLE_NAME, null, testValues);
        assertTrue(rowID != -
                1);


        Cursor cursor = db.query(
                CurrencyConverterContract.ExchangeRates.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue( "Error: No Records returned from location query", cursor.moveToFirst() );

        return rowID;
    }

    public void testdbQuery() {
        Rate rate = new Rate("USD", "USD");
        SqlLiteDataAccess access = new SqlLiteDataAccess(mContext);
       double val =  access.get(rate.getBaseCurrency(), rate.getTargetCurrency());
        assertEquals(1.0, rate.getExchangeRate());
    }


}

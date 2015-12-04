package com.andela.currency_calculator.models.dal;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.HashSet;


public class TestDB extends AndroidTestCase{
    public static final String TAG = TestDB.class.getSimpleName();
     void deleteDb(){
         mContext.deleteDatabase(SqlLiteDataAccess.DATABASE_NAME);
     }

    @Override
    public void setUp() throws Exception {
        deleteDb();
    }

    @Test
    public void testCreateDB(){
        final HashSet<String> tableNames = new HashSet<String>();
        tableNames.add(CurrencyConverterContract.ExchangeRates.TABLE_NAME);
        tableNames.add(CurrencyConverterContract.Currency.TABLE_NAME);
        mContext.deleteDatabase(SqlLiteDataAccess.DATABASE_NAME);
        SQLiteDatabase db = new SqlLiteDataAccess(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

    }
}

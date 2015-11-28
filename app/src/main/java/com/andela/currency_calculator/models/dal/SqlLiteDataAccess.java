package com.andela.currency_calculator.models.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlLiteDataAccess extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "currency.db";
    public SqlLiteDataAccess(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CurrencyConverterContract.CREATE_RATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+ CurrencyConverterContract.ExchangeRates.TABLE_NAME);
    }
}

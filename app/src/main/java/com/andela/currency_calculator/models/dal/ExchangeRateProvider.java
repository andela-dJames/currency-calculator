package com.andela.currency_calculator.models.dal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Oluwatosin on 11/25/2015.
 */
public class ExchangeRateProvider extends ContentProvider {

    private SqlLiteDataAccess sqlLiteDataAccess;
    private UriMatcher uriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher() {
        return null;
    }

    public ExchangeRateProvider() {
        sqlLiteDataAccess = new SqlLiteDataAccess(getContext());
        Log.d("Proider", "onCretae was caled ");
    }

    @Override
    public boolean onCreate() {
        sqlLiteDataAccess = new SqlLiteDataAccess(getContext());
        Log.d("Proider", "onCretae was caled ");
        return true;

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = sqlLiteDataAccess.getWritableDatabase();
        db.beginTransaction();
        int count = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(CurrencyConverterContract.ExchangeRates.TABLE_NAME, null, value);
                if (_id != -1) {
                    count++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

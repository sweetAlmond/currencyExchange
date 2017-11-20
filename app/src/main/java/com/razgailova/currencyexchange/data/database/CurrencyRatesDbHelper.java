package com.razgailova.currencyexchange.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.razgailova.currencyexchange.data.database.CurrencyRateContract.RatesEntry;
import com.razgailova.currencyexchange.data.database.CurrencyRateContract.MetadataEntry;


/**
 * Created by Катерина on 19.11.2017.
 */

public class CurrencyRatesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String SQL_CREATE_RATES_TABLE =
            "CREATE TABLE " + RatesEntry.TABLE_NAME + " (" +
                    RatesEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RatesEntry.COLUMN_NAME_NUM_CODE + " INTEGER," +
                    RatesEntry.COLUMN_NAME_CHAR_CODE + " TEXT," +
                    RatesEntry.COLUMN_NAME_NOMINAL + " INTEGER," +
                    RatesEntry.COLUMN_NAME_VOLUTE_NAME + " TEXT," +
                    RatesEntry.COLUMN_NAME_VALUE + " TEXT)";

    private static final String SQL_CREATE_METADATA_TABLE =
            "CREATE TABLE " + RatesEntry.TABLE_NAME + " (" +
                    MetadataEntry.COLUMN_NAME_DATE + " INTEGER PRIMARY KEY)";

    protected static final String SQL_DELETE_RATES =
            "DROP TABLE IF EXISTS " + RatesEntry.TABLE_NAME;

    protected static final String SQL_DELETE_METADATA =
            "DROP TABLE IF EXISTS " + MetadataEntry.TABLE_NAME;

    public CurrencyRatesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RATES_TABLE);
        db.execSQL(SQL_CREATE_METADATA_TABLE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RATES);
        db.execSQL(SQL_DELETE_METADATA);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

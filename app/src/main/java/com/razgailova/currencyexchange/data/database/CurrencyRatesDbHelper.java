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
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CurrencyRates.db";

    private static final String SQL_CREATE_RATES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + RatesEntry.TABLE_NAME + " (" +
                    RatesEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    RatesEntry.COLUMN_NAME_NUM_CODE + " INTEGER," +
                    RatesEntry.COLUMN_NAME_CHAR_CODE + " TEXT," +
                    RatesEntry.COLUMN_NAME_NOMINAL + " INTEGER," +
                    RatesEntry.COLUMN_NAME_VOLUTE_NAME + " TEXT," +
                    RatesEntry.COLUMN_NAME_VALUE + " TEXT)";

    private static final String SQL_CREATE_METADATA_TABLE =
            "CREATE TABLE IF NOT EXISTS " + MetadataEntry.TABLE_NAME + " (" +
                    MetadataEntry.COLUMN_NAME_DATE + " INTEGER PRIMARY KEY)";

    private static final String SQL_DELETE_RATES_TABLE =
            "DROP TABLE IF EXISTS " + RatesEntry.TABLE_NAME;

    private static final String SQL_DELETE_METADATA_TABLE =
            "DROP TABLE IF EXISTS " + MetadataEntry.TABLE_NAME;

    public CurrencyRatesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RATES_TABLE);
        db.execSQL(SQL_CREATE_METADATA_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_RATES_TABLE);
        db.execSQL(SQL_DELETE_METADATA_TABLE);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void clearTables(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_RATES_TABLE);
        db.execSQL(SQL_CREATE_RATES_TABLE);

        db.execSQL(SQL_DELETE_METADATA_TABLE);
        db.execSQL(SQL_CREATE_METADATA_TABLE);

    }
}

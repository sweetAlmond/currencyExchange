package com.razgailova.currencyexchange.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.model.Volute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.razgailova.currencyexchange.data.database.CurrencyRateContract.RatesEntry;
import com.razgailova.currencyexchange.data.database.CurrencyRateContract.MetadataEntry;

/**
 * Created by Катерина on 19.11.2017.
 */

public class CurrencyRatesDatabase {
    private CurrencyRatesDbHelper dbHelper;

    public CurrencyRatesDatabase(CurrencyRatesDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void storeCurrencyRates(ExchangeRates exchangeRates){
        clearRates();
        clearMetadata();

        storeRates(exchangeRates.getCurrencyMap());
        storeMetadata(exchangeRates.getDate());
    }

    private void clearRates(){
        dbHelper.getWritableDatabase().execSQL(CurrencyRatesDbHelper.SQL_DELETE_RATES);
    }

    private void clearMetadata(){
        dbHelper.getWritableDatabase().execSQL(CurrencyRatesDbHelper.SQL_DELETE_METADATA);
    }

    private void storeRates(Map<String, Volute> volutes){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Volute volute : volutes.values()) {
            ContentValues values = new ContentValues();
            values.put(RatesEntry.COLUMN_NAME_ID, volute.getId());
            values.put(RatesEntry.COLUMN_NAME_NUM_CODE, volute.getNumCode());
            values.put(RatesEntry.COLUMN_NAME_CHAR_CODE, volute.getCharCode());
            values.put(RatesEntry.COLUMN_NAME_NOMINAL, volute.getNominal());
            values.put(RatesEntry.COLUMN_NAME_VOLUTE_NAME, volute.getName());
            values.put(RatesEntry.COLUMN_NAME_VALUE, volute.getValue().toPlainString());

            db.insert(RatesEntry.TABLE_NAME, null, values);
        }
    }

    private void storeMetadata(Date date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MetadataEntry.COLUMN_NAME_DATE, date.getTime());

        db.insert(MetadataEntry.TABLE_NAME, null, values);
    }

    public ExchangeRates readExchangeRates(){
        Map<String, Volute> voluteMap = readCurrencyRates();
        Date date = readRatesMetadata();

        return new ExchangeRates(date, voluteMap);
    }

    private Map<String, Volute> readCurrencyRates(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                RatesEntry.COLUMN_NAME_ID,
                RatesEntry.COLUMN_NAME_NUM_CODE,
                RatesEntry.COLUMN_NAME_CHAR_CODE,
                RatesEntry.COLUMN_NAME_NOMINAL,
                RatesEntry.COLUMN_NAME_VOLUTE_NAME,
                RatesEntry.COLUMN_NAME_VALUE
        };

        Cursor cursor = db.query(CurrencyRateContract.RatesEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        Map<String, Volute> currencyRates = new HashMap<>();
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_ID));
            int numCode = cursor.getInt(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_NUM_CODE));
            String charCode = cursor.getString(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_CHAR_CODE));
            int nominal = cursor.getInt(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_NOMINAL));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_VOLUTE_NAME));
            String strValue = cursor.getString(cursor.getColumnIndexOrThrow(RatesEntry.COLUMN_NAME_VALUE));
            BigDecimal value = new BigDecimal(strValue);

            Volute volute = new Volute(id, numCode, charCode, nominal, name, value);
            currencyRates.put(name, volute);
        }
        cursor.close();

        return currencyRates;
    }

    private Date readRatesMetadata(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                MetadataEntry.COLUMN_NAME_DATE
        };

        Cursor cursor = db.query(MetadataEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        Date date = null;
        while(cursor.moveToNext()) {
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(MetadataEntry.COLUMN_NAME_DATE));
            date = new Date(timestamp);
        }
        cursor.close();

        return date;
    }

    public void closeDatabase(){
        dbHelper.close();
    }
}

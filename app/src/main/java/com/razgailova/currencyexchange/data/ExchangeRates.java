package com.razgailova.currencyexchange.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Катерина on 18.11.2017.
 */

public class ExchangeRates {

    private Date mDate;

    private Map<String, Valute> mCurrencyMap;

    public ExchangeRates(Date date) {
        mDate = date;
        mCurrencyMap = new HashMap<>();
    }

    public void addCurrency(Valute currency) {
        mCurrencyMap.put(currency.getId(), currency);
    }

    public Date getDate() {
        return mDate;
    }

    public Map<String, Valute> getCurrencyMap() {
        return mCurrencyMap;
    }

    public Valute getCurrency(String id) {
        return mCurrencyMap.get(id);
    }

}

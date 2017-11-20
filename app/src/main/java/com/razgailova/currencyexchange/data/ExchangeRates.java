package com.razgailova.currencyexchange.data;

import com.razgailova.currencyexchange.data.model.Volute;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Катерина on 18.11.2017.
 */

public class ExchangeRates {

    private Date mDate;

    private Map<String, Volute> mCurrencyMap;

    public ExchangeRates(Date date, Map<String, Volute> currencyRates) {
        mDate = date;
        mCurrencyMap = currencyRates;
    }

    public ExchangeRates(Date date) {
        this(date, new HashMap<String, Volute>());
    }

    public void addCurrency(Volute currency) {
        mCurrencyMap.put(currency.getId(), currency);
    }

    public Date getDate() {
        return mDate;
    }

    public Map<String, Volute> getCurrencyMap() {
        return mCurrencyMap;
    }

    public Volute getCurrency(String id) {
        return mCurrencyMap.get(id);
    }

}

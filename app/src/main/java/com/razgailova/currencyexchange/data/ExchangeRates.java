package com.razgailova.currencyexchange.data;

import com.razgailova.currencyexchange.data.model.Volute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Катерина on 18.11.2017.
 */

public class ExchangeRates {

    private Date mDate;

    private ArrayList<Volute> mCurrencyList;

    public ExchangeRates(Date date, ArrayList<Volute> currencyRates) {
        mDate = date;
        mCurrencyList = currencyRates;
    }

    public ExchangeRates(Date date) {
        this(date, new ArrayList<Volute>());
    }

    public void addCurrency(Volute currency) {
        mCurrencyList.add(currency);
    }

    public Date getDate() {
        return mDate;
    }

    public ArrayList<Volute> getCurrencyList() {
        return mCurrencyList;
    }


}

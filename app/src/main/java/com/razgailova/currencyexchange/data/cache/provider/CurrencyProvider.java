package com.razgailova.currencyexchange.data.cache.provider;

import android.content.Context;

import com.razgailova.currencyexchange.data.cache.parser.IParser;
import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public abstract class CurrencyProvider {

    public ExchangeRates getCurrency(Context context, IParser parser) throws Exception {
        String xml = load(context);
        return parser.parse(xml);
    }

    protected abstract String load(Context context) throws Exception;
}

package com.razgailova.currencyexchange.cache.parser;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 18.11.2017.
 */

public interface IParser {

    ExchangeRates parse(@NonNull String xml) throws Exception;
}

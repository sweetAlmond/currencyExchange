package com.razgailova.currencyexchange.data.cache.loader;

import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public interface ILoadingListener {

    void onDataLoaded(ExchangeRates data);

    void onError(String string);
}

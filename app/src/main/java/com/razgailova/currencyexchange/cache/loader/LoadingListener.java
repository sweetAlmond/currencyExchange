package com.razgailova.currencyexchange.cache.loader;

import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public interface LoadingListener {

    void onDataLoaded(ExchangeRates data);

    void onError();
}

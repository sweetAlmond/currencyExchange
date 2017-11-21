package com.razgailova.currencyexchange.domain.usecase;

import com.razgailova.currencyexchange.data.cache.CacheInitListener;
import com.razgailova.currencyexchange.data.cache.CurrencyCache;
import com.razgailova.currencyexchange.data.model.Volute;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Катерина on 19.11.2017.
 */

public class ExchangeRatesUseCase {

    public ExchangeRatesUseCase() {}

    public ArrayList<Volute> getCurrencies() {
        return CurrencyCache.getInstance().getCurrencyCollection();
    }

    public void requestExchangeRates(ExchangeRateUpdateListener listener) {
        CurrencyCache.getInstance().initRemote(listener);
    }

    public void unSubscribeFromUpdates(ExchangeRateUpdateListener listener) {
        CurrencyCache.getInstance().removeCacheInitListener();
    }

    public interface ExchangeRateUpdateListener extends CacheInitListener {}
}

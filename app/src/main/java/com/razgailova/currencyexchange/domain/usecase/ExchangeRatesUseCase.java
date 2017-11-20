package com.razgailova.currencyexchange.domain.usecase;

import com.razgailova.currencyexchange.data.cache.CacheInitListener;
import com.razgailova.currencyexchange.data.cache.CurrencyCache;
import com.razgailova.currencyexchange.data.model.Volute;

import java.util.Collection;

/**
 * Created by Катерина on 19.11.2017.
 */

public class ExchangeRatesUseCase {

    public ExchangeRatesUseCase() {}

    public Collection<Volute> getCurrencies() {
        return CurrencyCache.getInstance().getCurrencyCollection();
    }

    public void requestExchangeRates(ExchangeRateUpdateListener listener) {
        CurrencyCache.getInstance().init(listener);
    }

    public void subscribeToUpdates(ExchangeRateUpdateListener listener) {
        CurrencyCache.getInstance().addCacheInitListener(listener);
    }

    public void unSubscribeFromUpdates(ExchangeRateUpdateListener listener) {
        CurrencyCache.getInstance().removeCacheInitListener(listener);
    }

    public interface ExchangeRateUpdateListener extends CacheInitListener {}
}

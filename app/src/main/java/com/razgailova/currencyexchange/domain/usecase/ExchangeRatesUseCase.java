package com.razgailova.currencyexchange.domain.usecase;

import com.razgailova.currencyexchange.data.cache.CacheInitListener;
import com.razgailova.currencyexchange.data.cache.ICurrencyCache;
import com.razgailova.currencyexchange.data.model.Volute;

import java.util.ArrayList;

/**
 * Created by Катерина on 19.11.2017.
 */

public class ExchangeRatesUseCase {
    private ICurrencyCache mCurrencyCache;

    public ExchangeRatesUseCase(ICurrencyCache currencyCache) {
        mCurrencyCache = currencyCache;
    }

    public boolean isLocalStorageEmpty() {
        ArrayList<Volute> currencyList = mCurrencyCache.getCurrencyCollection();
        return currencyList == null || currencyList.size() == 0;
    }

    public ArrayList<Volute> getCurrencies() {
        return mCurrencyCache.getCurrencyCollection();
    }

    public void requestExchangeRates(ExchangeRateUpdateListener listener) {
        mCurrencyCache.initRemote(listener);
    }

    public void removeRequestExchangeRatesListener() {
        mCurrencyCache.removeRemoteInitListener();
    }

    public interface ExchangeRateUpdateListener extends CacheInitListener {}
}

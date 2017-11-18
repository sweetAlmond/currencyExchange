package com.razgailova.currencyexchange.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.MyApplication;
import com.razgailova.currencyexchange.cache.loader.ILoader;
import com.razgailova.currencyexchange.cache.loader.LoadingListener;
import com.razgailova.currencyexchange.cache.loader.LoadingManager;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.Valute;

import java.util.Collection;

/**
 * Created by Катерина on 15.11.2017.
 */

public class CurrencyCache {

    private static CurrencyCache mInstance;

    private ExchangeRates mData;

    public static synchronized CurrencyCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyCache();
        }

        return mInstance;
    }

    public void init(@NonNull final CacheInitListener listener) {
        ILoader loader = new LoadingManager(MyApplication.getContext());

        loader.load(new LoadingListener() {
            @Override
            public void onDataLoaded(ExchangeRates data) {
                mData = data;
                listener.onInitFinished();
            }

            @Override
            public void onError() {
                // TODO
            }
        });
    }

    public Collection<Valute> getCurrencyCollection() {
        return mData.getCurrencyMap().values();
    }

    public Valute getCurrencyById(String id) {
        return mData.getCurrency(id);
    }

}

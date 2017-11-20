package com.razgailova.currencyexchange.data.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.MyApplication;
import com.razgailova.currencyexchange.data.cache.loader.ILoader;
import com.razgailova.currencyexchange.data.cache.loader.LoadingListener;
import com.razgailova.currencyexchange.data.cache.loader.LoadingManager;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.model.Volute;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Катерина on 15.11.2017.
 */

public class CurrencyCache {

    private static CurrencyCache mInstance;

    private List<SoftReference<CacheInitListener>> listeners = new ArrayList<>();

    private ExchangeRates mData;

    public static synchronized CurrencyCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyCache();
        }

        return mInstance;
    }

    public void init(@NonNull final CacheInitListener listener) {
        listeners.add(new SoftReference<>(listener));

        ILoader loader = new LoadingManager(MyApplication.getContext());

        loader.load(new LoadingListener() {
            @Override
            public void onDataLoaded(ExchangeRates data) {
                mData = data;

                for (SoftReference<CacheInitListener> listenerReference: listeners) {
                    CacheInitListener listener = listenerReference.get();
                    if (listener != null) {
                        listener.onInitFinished();
                    }
                }
            }

            @Override
            public void onError() {
                // TODO
            }
        });
    }

    public void addCacheInitListener(CacheInitListener listener){
        listeners.add(new SoftReference<>(listener));
    }

    public void removeCacheInitListener(CacheInitListener listener){
        for (Iterator<SoftReference<CacheInitListener>> iterator = listeners.iterator(); iterator.hasNext(); ) {
            SoftReference<CacheInitListener> softReference = iterator.next();
            if (softReference.get() == listener) {
                iterator.remove();
            }
        }
    }

    public Collection<Volute> getCurrencyCollection() {
        return mData.getCurrencyMap().values();
    }

    public Volute getCurrencyById(String id) {
        return mData.getCurrency(id);
    }

}

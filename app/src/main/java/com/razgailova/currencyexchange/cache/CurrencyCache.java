package com.razgailova.currencyexchange.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.cache.loader.LoadingListener;
import com.razgailova.currencyexchange.cache.loader.LoadingManager;
import com.razgailova.currencyexchange.data.ValCurs;

import java.util.List;

/**
 * Created by Катерина on 15.11.2017.
 */

public class CurrencyCache {

    private static CurrencyCache mInstance;

    private LoadingManager mLoader;
    private CurrencyCacheInitListener mInitListener;

    private ValCurs mData;

    private CurrencyCache() {
        mLoader = new LoadingManager();
    }

    public static synchronized CurrencyCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyCache();
        }

        return mInstance;
    }

    public void init(@NonNull CurrencyCacheInitListener listener) {
        mInitListener = listener;
        mLoader.load(new LoadingListener() {
            @Override
            public void onDataLoaded(ValCurs data) {
                mData = data;
                mInitListener.onInitFinished();
            }

            @Override
            public void onError() {
                // TODO
            }
        });
    }

    public List<Object> getCurrencyList() {
        // TODO
        return null;
    }

    public Object getCurrencyById(int id) {
        // TODO
        return null;
    }

}

package com.razgailova.currencyexchange.data.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.cache.loader.ILoadingManager;
import com.razgailova.currencyexchange.data.cache.loader.ILoadingListener;
import com.razgailova.currencyexchange.data.database.CurrencyRatesDatabase;
import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.Injector;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.razgailova.currencyexchange.data.cache.CurrencyCache.RemoteDataStatus.*;

/**
 * Created by Катерина on 15.11.2017.
 */

public class CurrencyCache implements ICurrencyCache{

    private static CurrencyCache mInstance;

    enum RemoteDataStatus {NONE, REQUESTING, LOADED, ERROR}

    private RemoteDataStatus remoteDataStatus = NONE;

    private SoftReference<CacheInitListener> mCacheInitListener;

    private ExchangeRates mExchangeRates;

    private CurrencyRatesDatabase mDatabase = Injector.getInstance().injectCurrencyRatesDatabase();

    private ILoadingManager mLoadingManager = Injector.getInstance().injectLoadingManager();

    public static synchronized CurrencyCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyCache();
            mInstance.initLocal();
        }

        return mInstance;
    }

    private void initLocal() {
        mExchangeRates = mDatabase.readExchangeRates();
    }

    @Override
    public void initRemote(@NonNull final CacheInitListener listener) {
        if (remoteDataStatus == LOADED) {
            return;
        }

        mCacheInitListener = new SoftReference<>(listener);

        if (remoteDataStatus != REQUESTING) {
            mLoadingManager.load(new ILoadingListener() {
                @Override
                public void onDataLoaded(ExchangeRates data) {
                    saveRemoteData(data);
                    remoteDataStatus = LOADED;

                    if (mCacheInitListener.get() != null && mCacheInitListener.get() != null) {
                        mCacheInitListener.get().onInitFinished();
                    }
                }

                @Override
                public void onError(String error) {
                    remoteDataStatus = ERROR;

                    if (mCacheInitListener.get() != null && mCacheInitListener.get() != null) {
                        mCacheInitListener.get().onError(error);
                    }
                }
            });
            remoteDataStatus = REQUESTING;
        }
    }

    private void saveRemoteData(ExchangeRates exchangeRates){
        Collections.sort(exchangeRates.getCurrencyList(), new Comparator<Volute>() {
            @Override
            public int compare(Volute o1, Volute o2) {
                return Integer.valueOf(o1.getNumCode()).compareTo(o2.getNumCode());
            }
        });
        mExchangeRates = exchangeRates;
        mDatabase.storeCurrencyRates(exchangeRates);
        mDatabase.closeDatabase(); // we done all we need with mDatabase
    }

    @Override
    public void removeRemoteInitListener() {
        mCacheInitListener = null;
    }

    @Override
    public ArrayList<Volute> getCurrencyCollection() {
        return mExchangeRates != null ? mExchangeRates.getCurrencyList() : null;
    }
}

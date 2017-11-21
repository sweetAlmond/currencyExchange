package com.razgailova.currencyexchange.data.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.MyApplication;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.cache.loader.ILoader;
import com.razgailova.currencyexchange.data.cache.loader.LoadingListener;
import com.razgailova.currencyexchange.data.cache.loader.LoadingManager;
import com.razgailova.currencyexchange.data.database.CurrencyRatesDatabase;
import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.Injector;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static com.razgailova.currencyexchange.data.cache.CurrencyCache.RemoteDataStatus.*;

/**
 * Created by Катерина on 15.11.2017.
 */

public class CurrencyCache {

    private static CurrencyCache mInstance;

    enum RemoteDataStatus {NONE, REQUESTING, LOADED, ERROR}

    private RemoteDataStatus remoteDataStatus = NONE;

    private SoftReference<CacheInitListener> cacheInitListener;

    private ExchangeRates mData;

    private CurrencyRatesDatabase database = Injector.getInstance().injectCurrencyRatesDatabase();

    public static synchronized CurrencyCache getInstance() {
        if (mInstance == null) {
            mInstance = new CurrencyCache();
            mInstance.initLocal();
        }

        return mInstance;
    }

    public void initLocal() {
        mData = database.readExchangeRates();
    }

    public void initRemote(@NonNull final CacheInitListener listener) {
        if (remoteDataStatus == LOADED) {
            return;
        }

        cacheInitListener = new SoftReference<>(listener);

        if (remoteDataStatus != REQUESTING) {
            ILoader loader = new LoadingManager(MyApplication.getContext());

            loader.load(new LoadingListener() {
                @Override
                public void onDataLoaded(ExchangeRates data) {
                    saveRemoteData(data);
                    remoteDataStatus = LOADED;

                    if (cacheInitListener.get() != null && cacheInitListener.get() != null) {
                        cacheInitListener.get().onInitFinished();
                    }
                }

                @Override
                public void onError() {
                    remoteDataStatus = ERROR;

                    if (cacheInitListener.get() != null && cacheInitListener.get() != null) {
                        cacheInitListener.get().onError();
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
        mData = exchangeRates;
        database.storeCurrencyRates(exchangeRates);
        database.closeDatabase(); // we done all we need with database
    }

    public void removeCacheInitListener() {
        cacheInitListener = null;
    }

    public ArrayList<Volute> getCurrencyCollection() {
        return mData.getCurrencyList();
    }

}

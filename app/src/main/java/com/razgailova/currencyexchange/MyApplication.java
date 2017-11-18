package com.razgailova.currencyexchange;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.razgailova.currencyexchange.cache.CacheInitListener;
import com.razgailova.currencyexchange.cache.CurrencyCache;

/**
 * Created by Катерина on 15.11.2017.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CurrencyCache.getInstance().init(new CacheInitListener() {
            @Override
            public void onInitFinished() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

}

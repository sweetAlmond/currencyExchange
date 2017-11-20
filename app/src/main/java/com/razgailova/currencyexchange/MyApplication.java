package com.razgailova.currencyexchange;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.razgailova.currencyexchange.domain.Injector;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.screens.converter.VoluteConverterActivity;

/**
 * Created by Катерина on 15.11.2017.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

}

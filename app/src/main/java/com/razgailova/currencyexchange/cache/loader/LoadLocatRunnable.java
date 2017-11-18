package com.razgailova.currencyexchange.cache.loader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.cache.parser.SmartXmlParser;
import com.razgailova.currencyexchange.cache.provider.LocalCurrencyProvider;
import com.razgailova.currencyexchange.cache.parser.data.ValCurs;
import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public class LoadLocatRunnable implements Runnable {

    private Context mContext;
    private Handler mHandler;

    public LoadLocatRunnable(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void run() {
        Message completeMessage;

        try {
            ExchangeRates data = new LocalCurrencyProvider().getCurrency(mContext, new SmartXmlParser());
            completeMessage = mHandler.obtainMessage(LoadingState.LOCAL.getValue(), data);
        } catch (Exception e) {
            completeMessage = mHandler.obtainMessage(LoadingState.FATAL_ERROR.getValue(), null);
        }

        completeMessage.sendToTarget();
    }
}

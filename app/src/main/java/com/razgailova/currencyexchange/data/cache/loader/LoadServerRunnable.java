package com.razgailova.currencyexchange.data.cache.loader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.data.cache.parser.SmartXmlParser;
import com.razgailova.currencyexchange.data.cache.provider.ServiceCurrencyProvider;
import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public class LoadServerRunnable implements Runnable {

    private Context mContext;
    private Handler mHandler;

    public LoadServerRunnable(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void run() {
        Message completeMessage;

        try {
            ExchangeRates data = new ServiceCurrencyProvider().getCurrency(mContext, new SmartXmlParser());
            completeMessage = mHandler.obtainMessage(LoadingState.DATA.getValue(), data);
        } catch (Exception e) {
            completeMessage = mHandler.obtainMessage(LoadingState.ERROR.getValue(), null);
        }

        completeMessage.sendToTarget();

        // TODO write data to local storage
    }
}

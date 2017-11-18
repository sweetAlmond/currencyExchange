package com.razgailova.currencyexchange.cache.loader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.cache.provider.DefaultCurrencyProvider;
import com.razgailova.currencyexchange.cache.provider.LocalCurrencyProvider;
import com.razgailova.currencyexchange.data.ValCurs;

/**
 * Created by Катерина on 16.11.2017.
 */

public class LoadLocalOrDefaultRunnable implements Runnable {

    private Context mContext;
    private Handler mHandler;

    public LoadLocalOrDefaultRunnable(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void run() {
        ValCurs data = new LocalCurrencyProvider().getCurrency(mContext);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (data != null) {
            Message completeMessage = mHandler.obtainMessage(LoadingState.LOCAL.getValue(), data);
            completeMessage.sendToTarget();
            return;
        }

        data = new DefaultCurrencyProvider().getCurrency(mContext);

        if (data != null) {
            Message completeMessage = mHandler.obtainMessage(LoadingState.DEFAULT.getValue(), data);
            completeMessage.sendToTarget();
            return;
        }

        Message completeMessage = mHandler.obtainMessage(LoadingState.FATAL_ERROR.getValue(), data);
        completeMessage.sendToTarget();
    }
}

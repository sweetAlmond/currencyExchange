package com.razgailova.currencyexchange.cache;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.data.ValCurs;

import static com.razgailova.currencyexchange.cache.LoadingState.DEFAULT;
import static com.razgailova.currencyexchange.cache.LoadingState.FATAL_ERROR;
import static com.razgailova.currencyexchange.cache.LoadingState.LOCAL;

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
            Message completeMessage = mHandler.obtainMessage(LOCAL.getValue(), data);
            completeMessage.sendToTarget();
            return;
        }

        data = new DefaultCurrencyProvider().getCurrency(mContext);

        if (data != null) {
            Message completeMessage = mHandler.obtainMessage(DEFAULT.getValue(), data);
            completeMessage.sendToTarget();
            return;
        }

        Message completeMessage = mHandler.obtainMessage(FATAL_ERROR.getValue(), data);
        completeMessage.sendToTarget();
    }
}

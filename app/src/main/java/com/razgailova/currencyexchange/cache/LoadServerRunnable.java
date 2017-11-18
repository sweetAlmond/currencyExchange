package com.razgailova.currencyexchange.cache;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.data.ValCurs;

import static com.razgailova.currencyexchange.cache.LoadingState.ERROR;
import static com.razgailova.currencyexchange.cache.LoadingState.SERVER;

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
        ValCurs data = new ServiceCurrencyProvider().getCurrency(mContext);

        if(data != null) {
            Message completeMessage = mHandler.obtainMessage(SERVER.getValue(), data);
            completeMessage.sendToTarget();
            return;
        }

        Message completeMessage = mHandler.obtainMessage(ERROR.getValue(), data);
        completeMessage.sendToTarget();

        // TODO write data to local file
    }
}

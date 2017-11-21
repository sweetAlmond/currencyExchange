package com.razgailova.currencyexchange.data.cache.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 15.11.2017.
 */

public class LoadingManager implements ILoader {

    private Context mContext;
    private LoadingListener mLoadingListener;

    public LoadingManager(Context context) {
        mContext = context;
    }

    @SuppressLint("HandlerLeak")
    private Handler mLoadingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (LoadingState.fromInt(msg.what)) {
                case DATA:
                    mLoadingListener.onDataLoaded((ExchangeRates) msg.obj);
                    break;

                case ERROR:
                    mLoadingListener.onError();
                    break;
            }
        }
    };

    public void load(@NonNull LoadingListener listener) {
        mLoadingListener = listener;
        load(new LoadLocalRunnable(mContext, mLoadingHandler));
//        load(new LoadServerRunnable(mContext, mLoadingHandler));
    }

    private void load(Runnable runnable) {
        new Thread(runnable).start();
    }
}

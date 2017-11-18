package com.razgailova.currencyexchange.cache.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.MyApplication;
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

        private boolean mProcessed = false;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (LoadingState.fromInt(msg.what)) {
                case DEFAULT:
                case LOCAL:
                case SERVER:
                    if(!mProcessed) {
                        mLoadingListener.onDataLoaded((ExchangeRates) msg.obj);
                        mProcessed = true;
                    }
                    break;
                case ERROR:
                    // работаем со значениями из локального хранилища
                    break;
                case FATAL_ERROR:
                    // не можем продолжать работу
                    throw new RuntimeException("Не загрузились данные из локального хранилища");
            }
        }
    };

    public void load(@NonNull LoadingListener listener) {
        mLoadingListener = listener;
        load(new LoadLocatRunnable(mContext, mLoadingHandler));
        load(new LoadServerRunnable(mContext, mLoadingHandler));
    }

    private void load(Runnable runnable) {
        new Thread(runnable).start();
    }

}

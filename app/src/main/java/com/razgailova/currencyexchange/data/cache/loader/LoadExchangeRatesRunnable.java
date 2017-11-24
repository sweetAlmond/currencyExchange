package com.razgailova.currencyexchange.data.cache.loader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.razgailova.currencyexchange.data.cache.parser.IXmlParser;
import com.razgailova.currencyexchange.data.cache.provider.ICurrencyProvider;
import com.razgailova.currencyexchange.data.ExchangeRates;

/**
 * Created by Катерина on 16.11.2017.
 */

public class LoadExchangeRatesRunnable implements Runnable {
    private Context mContext;
    private Handler mHandler;
    private ICurrencyProvider mCurrencyProvider;
    private IXmlParser mXMLParser;

    public LoadExchangeRatesRunnable(Context context, Handler handler, ICurrencyProvider currencyProvider, IXmlParser хmlParser) {
        mContext = context;
        mHandler = handler;
        mCurrencyProvider = currencyProvider;
        mXMLParser = хmlParser;
    }

    @Override
    public void run() {
        Message completeMessage;

        try {
            ExchangeRates data = mCurrencyProvider.getCurrency(mContext, mXMLParser);
            completeMessage = mHandler.obtainMessage(LoadingState.DATA.getValue(), data);
        } catch (Exception e) {
            completeMessage = mHandler.obtainMessage(LoadingState.ERROR.getValue(), e);
        }

        completeMessage.sendToTarget();
    }
}

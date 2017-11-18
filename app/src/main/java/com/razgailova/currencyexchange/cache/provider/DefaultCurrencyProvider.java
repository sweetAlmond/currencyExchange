package com.razgailova.currencyexchange.cache.provider;

import android.content.Context;
import android.content.res.AssetManager;

import com.razgailova.currencyexchange.BuildConfig;
import com.razgailova.currencyexchange.cache.provider.CurrencyProvider;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Катерина on 16.11.2017.
 */

public class DefaultCurrencyProvider extends CurrencyProvider {

    @Override
    protected String load(Context context) {
        String xmlString = null;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(BuildConfig.DEFAULT_CONTAINER);
            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            xmlString = new String(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return xmlString;
    }
}

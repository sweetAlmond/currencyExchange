package com.razgailova.currencyexchange.cache.provider;

import android.content.Context;
import android.content.res.AssetManager;

import com.razgailova.currencyexchange.BuildConfig;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Катерина on 16.11.2017.
 */

public class DefaultCurrencyProvider extends CurrencyProvider {

    @Override
    protected String load(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();

        InputStream inputStream = assetManager.open(BuildConfig.DEFAULT_CONTAINER);
        int length = inputStream.available();
        byte[] data = new byte[length];
        inputStream.read(data);
        return new String(data);
    }

}

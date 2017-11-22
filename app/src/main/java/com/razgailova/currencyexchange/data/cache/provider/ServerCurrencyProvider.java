package com.razgailova.currencyexchange.data.cache.provider;

import android.content.Context;

import com.razgailova.currencyexchange.data.server.ServerApi;

/**
 * Created by Катерина on 16.11.2017.
 */

public class ServerCurrencyProvider extends ICurrencyProvider {

    private ServerApi serverApi;

    public ServerCurrencyProvider(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    @Override
    protected String load(Context context) throws Exception{
        return serverApi.getExchangeRates();
    }
}

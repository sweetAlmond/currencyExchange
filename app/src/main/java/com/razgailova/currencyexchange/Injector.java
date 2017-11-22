package com.razgailova.currencyexchange;

import com.razgailova.currencyexchange.data.cache.CurrencyCache;
import com.razgailova.currencyexchange.data.cache.loader.ILoadingManager;
import com.razgailova.currencyexchange.data.cache.loader.LoadingManager;
import com.razgailova.currencyexchange.data.cache.parser.IXmlParser;
import com.razgailova.currencyexchange.data.cache.parser.SmartXmlParser;
import com.razgailova.currencyexchange.data.cache.provider.ICurrencyProvider;
import com.razgailova.currencyexchange.data.cache.provider.ServerCurrencyProvider;
import com.razgailova.currencyexchange.data.database.CurrencyRatesDatabase;
import com.razgailova.currencyexchange.data.database.CurrencyRatesDbHelper;
import com.razgailova.currencyexchange.data.server.ServerApiImpl;
import com.razgailova.currencyexchange.domain.converter.CurrencyConverter;
import com.razgailova.currencyexchange.domain.usecase.ConvertVoluteUseCase;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;

/**
 * Created by Катерина on 19.11.2017.
 */

public class Injector {
    private static Injector sInstance;

    public static synchronized Injector getInstance(){
        if (sInstance == null) {
            sInstance = new Injector();
        }

        return sInstance;
    }

    public ConvertVoluteUseCase injectConvertVoluteUseCase(){
        return new ConvertVoluteUseCase(new CurrencyConverter());
    }

    public ExchangeRatesUseCase injectExchangeRatesUseCase(){
        return new ExchangeRatesUseCase(CurrencyCache.getInstance());
    }

    public CurrencyRatesDatabase injectCurrencyRatesDatabase(){
        return new CurrencyRatesDatabase(new CurrencyRatesDbHelper(MyApplication.getContext()));
    }

    public IXmlParser injectParser(){
        return new SmartXmlParser();
    }

    public ICurrencyProvider injectCurrencyProvider(){
//        return new DefaultCurrencyProvider();
        return new ServerCurrencyProvider(new ServerApiImpl());
    }

    public ILoadingManager injectLoadingManager(){
        return new LoadingManager(MyApplication.getContext());
    }
}

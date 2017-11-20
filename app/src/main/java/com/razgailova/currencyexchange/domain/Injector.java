package com.razgailova.currencyexchange.domain;

import com.razgailova.currencyexchange.domain.converter.CurrencyConverter;
import com.razgailova.currencyexchange.domain.converter.ICurrencyConverter;
import com.razgailova.currencyexchange.domain.usecase.ConvertVoluteUseCase;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;

/**
 * Created by Катерина on 19.11.2017.
 */

public class Injector {
    private static Injector instance;

    public static synchronized Injector getInstance(){
        if (instance == null) {
            instance = new Injector();
        }

        return instance;
    }

    public ConvertVoluteUseCase injectConvertVoluteUseCase(){
        return new ConvertVoluteUseCase(new CurrencyConverter());
    }

    public ExchangeRatesUseCase injectExchangeRatesUseCase(){
        return new ExchangeRatesUseCase();
    }
}

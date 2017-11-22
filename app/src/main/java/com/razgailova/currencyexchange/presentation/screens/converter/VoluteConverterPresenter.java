package com.razgailova.currencyexchange.presentation.screens.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.usecase.ConvertVoluteUseCase;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Катерина on 19.11.2017.
 */

public class VoluteConverterPresenter extends BasePresenter<VoluteConverterView> implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ConvertVoluteUseCase converterUseCase;
    private ExchangeRatesUseCase exchangeRatesUseCase;

    public VoluteConverterPresenter(ConvertVoluteUseCase converterUseCase,
                                    ExchangeRatesUseCase exchangeRatesUseCase) {
        this.converterUseCase = converterUseCase;
        this.exchangeRatesUseCase = exchangeRatesUseCase;
    }

    @Override
    public void bindView(@NonNull VoluteConverterView view) {
        super.bindView(view);

        requestExchangeRates();

        view.showVolutesAndRates(exchangeRatesUseCase.getCurrencies());
    }

    public void requestExchangeRates(){
        exchangeRatesUseCase.requestExchangeRates(this);
    }

    @Override
    public void unbindView() {
        unSubscribeFromExchangeRatesUpdates();

        super.unbindView();
    }

    private void unSubscribeFromExchangeRatesUpdates(){
        exchangeRatesUseCase.removeRequestExchangeRatesListener();
    }

    public void onConvertVolutePressed(BigDecimal amount, Volute voluteFrom, Volute voluteTo){
        try {
            BigDecimal result = converterUseCase.convertVolute(amount, voluteFrom, voluteTo);
            view.showConversionResult(result);
        } catch (Exception e) {
            view.showConversionError(e.getMessage());
        }
    }

    @Override
    public void onInitFinished() {
        ArrayList<Volute> volutes = exchangeRatesUseCase.getCurrencies();
        unSubscribeFromExchangeRatesUpdates();
        view.updateVolutesAndRates(volutes);
    }

    @Override
    public void onInitError(String error) {
        view.showInitError(error);
        unSubscribeFromExchangeRatesUpdates();
    }
}

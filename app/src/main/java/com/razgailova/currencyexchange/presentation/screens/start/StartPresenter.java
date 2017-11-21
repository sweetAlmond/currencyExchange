package com.razgailova.currencyexchange.presentation.screens.start;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;

/**
 * Created by Катерина on 20.11.2017.
 */

public class StartPresenter extends BasePresenter<StartView> implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ExchangeRatesUseCase exchangeRatesUseCase;

    public StartPresenter(ExchangeRatesUseCase exchangeRatesUseCase){
        this.exchangeRatesUseCase = exchangeRatesUseCase;
    }

    @Override
    public void bindView(@NonNull StartView view) {
        super.bindView(view);
        requestExchangeRates();
    }

    private void requestExchangeRates(){
        if (exchangeRatesUseCase.getCurrencies().size() == 0) {
            exchangeRatesUseCase.requestExchangeRates(this);
        } else {
            view.showConverterScreen();
        }
    }

    private void unSubscribeFromUpdates(){
        exchangeRatesUseCase.unSubscribeFromUpdates(this);
    }

    @Override
    public void onInitFinished() {
        unSubscribeFromUpdates();
        view.showConverterScreen();
    }

    @Override
    public void onError() {
        unSubscribeFromUpdates();
        // todo show error and retry
    }

    @Override
    public void unbindView() {
        unSubscribeFromUpdates();
        super.unbindView();
    }
}

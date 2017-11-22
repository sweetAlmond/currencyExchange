package com.razgailova.currencyexchange.presentation.screens.start;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;

/**
 * Created by Катерина on 20.11.2017.
 */

public class StartPresenter extends BasePresenter<StartView> implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ExchangeRatesUseCase exchangeRatesUseCase;

    StartPresenter(ExchangeRatesUseCase exchangeRatesUseCase){
        this.exchangeRatesUseCase = exchangeRatesUseCase;
    }

    @Override
    public void bindView(@NonNull StartView view) {
        super.bindView(view);
        requestExchangeRates();
    }

    public void requestExchangeRates(){
        if (exchangeRatesUseCase.isLocalStorageEmpty()) {
            exchangeRatesUseCase.requestExchangeRates(this);
        } else {
            view.showConverterScreen();
        }
    }

    private void unSubscribeFromExchangeRatesUpdates(){
        exchangeRatesUseCase.removeRequestExchangeRatesListener();
    }

    @Override
    public void onInitFinished() {
        unSubscribeFromExchangeRatesUpdates();
        view.showConverterScreen();
    }

    @Override
    public void onInitError(String error) {
        unSubscribeFromExchangeRatesUpdates();
        view.showErrorDialog(error);
    }

    @Override
    public void unbindView() {
        unSubscribeFromExchangeRatesUpdates();
        super.unbindView();
    }
}

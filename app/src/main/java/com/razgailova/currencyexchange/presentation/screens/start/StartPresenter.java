package com.razgailova.currencyexchange.presentation.screens.start;

import android.support.annotation.NonNull;
import android.text.method.DialerKeyListener;

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

    private void unSubscribeFromUpdates(){
        exchangeRatesUseCase.removeRequestExchangeRatesListener();
    }

    @Override
    public void onInitFinished() {
        unSubscribeFromUpdates();
        view.showConverterScreen();
    }

    @Override
    public void onError(String error) {
        unSubscribeFromUpdates();
        view.showErrorDialog(error);
    }

    @Override
    public void unbindView() {
        unSubscribeFromUpdates();
        super.unbindView();
    }
}

package com.razgailova.currencyexchange.presentation.screens.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.razgailova.currencyexchange.domain.Injector;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.screens.converter.VoluteConverterActivity;

public class StartActivity extends AppCompatActivity implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ExchangeRatesUseCase exchangeRatesUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exchangeRatesUseCase = Injector.getInstance().injectExchangeRatesUseCase();
        exchangeRatesUseCase.requestExchangeRates(this);
    }

    @Override
    public void onInitFinished() {
        exchangeRatesUseCase.unSubscribeFromUpdates(this);
        Intent intent = new Intent(this, VoluteConverterActivity.class);
        startActivity(intent);
    }
}

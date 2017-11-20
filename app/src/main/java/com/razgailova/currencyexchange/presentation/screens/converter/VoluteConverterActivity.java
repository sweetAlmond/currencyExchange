package com.razgailova.currencyexchange.presentation.screens.converter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.razgailova.currencyexchange.R;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.Injector;

import java.math.BigDecimal;
import java.util.Collection;

public class VoluteConverterActivity extends AppCompatActivity implements VoluteConverterView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        new VoluteConverterPresenter(
                Injector.getInstance().injectConvertVoluteUseCase(),
                Injector.getInstance().injectExchangeRatesUseCase());
    }

    @Override
    public void showConversionResult(BigDecimal result) {

    }

    @Override
    public void showVolutesAndRates(Collection<Volute> rates) {

    }

    @Override
    public void updateVolutesAndRates(Collection<Volute> rates) {

    }

    @Override
    public void showConversionError(String error) {

    }
}

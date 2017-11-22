package com.razgailova.currencyexchange.presentation.screens.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.razgailova.currencyexchange.Injector;
import com.razgailova.currencyexchange.presentation.screens.converter.VoluteConverterActivity;

public class StartActivity extends AppCompatActivity implements StartView {
    private StartPresenter startPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startPresenter = new StartPresenter(Injector.getInstance().injectExchangeRatesUseCase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        startPresenter.unbindView();
        super.onPause();
    }

    @Override
    public void showConverterScreen() {
        Intent intent = new Intent(this, VoluteConverterActivity.class);
        startActivity(intent);
    }
}

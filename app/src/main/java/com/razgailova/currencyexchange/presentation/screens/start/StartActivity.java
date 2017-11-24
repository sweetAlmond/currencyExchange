package com.razgailova.currencyexchange.presentation.screens.start;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.razgailova.currencyexchange.Injector;
import com.razgailova.currencyexchange.R;
import com.razgailova.currencyexchange.presentation.screens.converter.VoluteConverterActivity;

public class StartActivity extends AppCompatActivity implements StartView {
    private StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new StartPresenter(Injector.getInstance().injectExchangeRatesUseCase());
    }


    @Override protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    protected void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void showConverterScreen() {
        Intent intent = new Intent(this, VoluteConverterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorDialog(String text) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title_error)
                .setMessage(text)
                .setPositiveButton(R.string.button_retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.requestExchangeRates();
                    }
                })
                .setCancelable(false)
                .show();
    }

}

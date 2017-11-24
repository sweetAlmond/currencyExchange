package com.razgailova.currencyexchange.presentation.screens.converter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.razgailova.currencyexchange.Injector;
import com.razgailova.currencyexchange.R;
import com.razgailova.currencyexchange.data.model.Volute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class VoluteConverterActivity extends AppCompatActivity implements VoluteConverterView {
    private static final String BUNDLE_KEY_PRESENTER_STATE = "PRESENTER_STATE";

    private VoluteConverterPresenter presenter;

    private EditText editTextAmount;
    private Spinner spinnerCurrencyFrom;
    private Spinner spinnerCurrencyTo;
    private TextView textViewConversionResult;
    private Button buttonConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        initViews();

        presenter = new VoluteConverterPresenter(
                Injector.getInstance().injectConvertVoluteUseCase(),
                Injector.getInstance().injectExchangeRatesUseCase());


        VoluteConverterState presenterState = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY_PRESENTER_STATE)) {
            presenterState = (VoluteConverterState) savedInstanceState.getSerializable(BUNDLE_KEY_PRESENTER_STATE);
        }
        presenter.readState(presenterState);
    }

    private void initViews(){
        editTextAmount = findViewById(R.id.et_amount);
        spinnerCurrencyFrom = findViewById(R.id.sp_currency_from);
        spinnerCurrencyTo = findViewById(R.id.sp_currency_to);
        textViewConversionResult = findViewById(R.id.tv_result);
        buttonConvert = findViewById(R.id.b_convert);

        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onAmountTextChanged(s.toString());
            }

        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onConvertVolutePressed();
            }
        });

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

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_KEY_PRESENTER_STATE, presenter.getState());
    }

    @Override
    public void setConversionEnabled(boolean enabled) {
        buttonConvert.setEnabled(enabled);
    }

    @Override
    public void showConversionResult(BigDecimal result) {
        textViewConversionResult.setText(result.toPlainString());
    }

    @Override
    public void hideConversionResult() {
        textViewConversionResult.setText("");
    }

    @Override public void initAmountField(String amountText) {
        editTextAmount.setText(amountText);
    }

    @Override
    public void initCurrenciesSpinners(ArrayList<Volute> rates, Volute selectedVoluteFrom, Volute selectedVoluteTo) {
        initSpinner(spinnerCurrencyFrom, rates, selectedVoluteFrom);

        spinnerCurrencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onVoluteFromSelected((Volute) spinnerCurrencyFrom.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        initSpinner(spinnerCurrencyTo, rates, selectedVoluteTo);

        spinnerCurrencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onVoluteToSelected((Volute) spinnerCurrencyTo.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initSpinner(final Spinner spinner, ArrayList<Volute> rates, Volute selectedVolute) {
        spinner.setOnItemSelectedListener(null);

        ArrayAdapter<Volute> adapter = createVolutesAdapter(rates);
        spinner.setAdapter(adapter);

        boolean voluteFound = false;
        for (Volute volute: rates) {
            if (selectedVolute.getNumCode() == volute.getNumCode()){
                spinner.setSelection(adapter.getPosition(volute));
                voluteFound = true;
            }
        }

        if (!voluteFound) { // if selected currency disappeared in new currencies list from server
            spinner.setSelection(0);
        }
    }

    private ArrayAdapter<Volute> createVolutesAdapter(Collection<Volute> spinnerCollection){
        ArrayAdapter<Volute> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, new ArrayList<>(spinnerCollection));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        return spinnerArrayAdapter;
    }

    @Override
    public void updateVolutesAndRates(ArrayList<Volute> rates, Volute selectedVoluteFrom, Volute selectedVoluteTo) {
        Toast.makeText(this, getString(R.string.exchange_rates_updated), Toast.LENGTH_SHORT).show();
        initCurrenciesSpinners(rates, selectedVoluteFrom, selectedVoluteTo);
    }

    @Override
    public void showConversionError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInitError(String error) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title_error)
                .setMessage(error)
                .setPositiveButton(R.string.button_retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.requestExchangeRates();
                    }
                })
                .show();
    }
}

package com.razgailova.currencyexchange.presentation.screens.converter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.razgailova.currencyexchange.R;
import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class VoluteConverterActivity extends AppCompatActivity implements VoluteConverterView {
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
                try {
                    BigDecimal bigDecimal = new BigDecimal(editTextAmount.getText().toString());
                    if (bigDecimal.setScale(4, BigDecimal.ROUND_HALF_EVEN).signum() == 1){
                        buttonConvert.setEnabled(true);
                        return;
                    }
                } catch (Exception ignore) {}
                buttonConvert.setEnabled(false);
            }
        });

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onConvertVolutePressed(getAmount(), getVoluteFrom(), getVoluteTo());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    protected void onPause() {
        presenter.unbindView();
        super.onPause();
    }

    public BigDecimal getAmount() {
        return new BigDecimal(editTextAmount.getText().toString());
    }

    public Volute getVoluteFrom() {
        return (Volute) spinnerCurrencyFrom.getSelectedItem();
    }

    public Volute getVoluteTo() {
        return (Volute) spinnerCurrencyTo.getSelectedItem();
    }

    @Override
    public void showConversionResult(BigDecimal result) {
        textViewConversionResult.setText(result.toPlainString());
    }

    @Override
    public void showVolutesAndRates(ArrayList<Volute> rates) {
        spinnerCurrencyFrom.setAdapter(createVolutesAdapter(rates));
        spinnerCurrencyFrom.setSelection(0);

        spinnerCurrencyTo.setAdapter(createVolutesAdapter(rates));
        spinnerCurrencyTo.setSelection(1);
    }

    private ArrayAdapter<Volute> createVolutesAdapter(Collection<Volute> spinnerCollection){
        ArrayAdapter<Volute> spinnerArrayAdapter = new ArrayAdapter<Volute>
                (this, android.R.layout.simple_spinner_item, new ArrayList<>(spinnerCollection));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        return spinnerArrayAdapter;
    }

    @Override
    public void updateVolutesAndRates(ArrayList<Volute> rates) {
        showVolutesAndRates(rates);
    }

    @Override
    public void showConversionError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}

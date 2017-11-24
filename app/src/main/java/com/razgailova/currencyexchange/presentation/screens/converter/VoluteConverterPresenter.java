package com.razgailova.currencyexchange.presentation.screens.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.usecase.ConvertVoluteUseCase;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Катерина on 19.11.2017.
 */

public class VoluteConverterPresenter extends BasePresenter<VoluteConverterView, VoluteConverterState> implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ConvertVoluteUseCase converterUseCase;
    private ExchangeRatesUseCase exchangeRatesUseCase;

    private boolean isSomethingChanged;
    private String amountText;
    private Volute selectedVoluteFrom;
    private Volute selectedVoluteTo;

    public VoluteConverterPresenter(ConvertVoluteUseCase converterUseCase, ExchangeRatesUseCase exchangeRatesUseCase) {
        this.converterUseCase = converterUseCase;
        this.exchangeRatesUseCase = exchangeRatesUseCase;
    }

    @Override public void readState(@Nullable VoluteConverterState state) {
        if (state != null) {
            this.isSomethingChanged = state.isSomethingChanged();
            this.amountText = state.getAmountText();
            this.selectedVoluteFrom = state.getSelectedVoluteFrom();
            this.selectedVoluteTo = state.getSelectedVoluteTo();
        } else {
            isSomethingChanged = false;
            amountText = "";
            selectedVoluteFrom  = exchangeRatesUseCase.getCurrencies().get(0);
            selectedVoluteTo = exchangeRatesUseCase.getCurrencies().get(1);
        }
    }

    @Override
    public void bindView(@NonNull VoluteConverterView view) {
        super.bindView(view);

        requestExchangeRates();

        view.initCurrenciesSpinners(exchangeRatesUseCase.getCurrencies(), selectedVoluteFrom, selectedVoluteTo);

        view.setConversionEnabled(isAllFieldsValid());
        if (!isSomethingChanged && isAllFieldsValid()){
            onConvertVolutePressed();
        }
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
        exchangeRatesUseCase.removeRequestExchangeRatesListener(this);
    }

    @NonNull
    @Override
    public VoluteConverterState getState() {
        return new VoluteConverterState(isSomethingChanged, amountText, selectedVoluteFrom, selectedVoluteTo);
    }

    void onConvertVolutePressed(){
        try {
            BigDecimal result = converterUseCase.convertVolute(getAmount(), selectedVoluteFrom, selectedVoluteTo);

            isSomethingChanged = false;
            view.showConversionResult(result);
        } catch (Exception e) {
            view.showConversionError(e.getMessage());
        }
    }

    private boolean isAllFieldsValid(){
        return isCurrenciesValid() && isAmountValid(getAmount());
    }

    private boolean isAmountValid(BigDecimal amount) {
        return amount != null && (amount.setScale(4, BigDecimal.ROUND_HALF_EVEN).signum() == 1);
    }

    private boolean isCurrenciesValid(){
        return selectedVoluteFrom != null && selectedVoluteTo != null;
    }

    private void onSomethingChanged(){
        isSomethingChanged = true;

        if (view != null) {
            view.hideConversionResult();
            view.setConversionEnabled(isAllFieldsValid());
        }
    }

    @Override
    public void onInitFinished() {
        ArrayList<Volute> volutes = exchangeRatesUseCase.getCurrencies();
        unSubscribeFromExchangeRatesUpdates();

        view.updateVolutesAndRates(volutes, selectedVoluteFrom, selectedVoluteTo);

        if (isSomethingChanged) {
            view.hideConversionResult();
        } else {
            if (isAllFieldsValid()) {
                onConvertVolutePressed();
            }
        }
    }

    @Override
    public void onInitError(String error) {
        view.showInitError(error);
        unSubscribeFromExchangeRatesUpdates();
    }

    private BigDecimal getAmount() {
        try {
            return new BigDecimal(amountText);
        } catch (Exception ignore){}

        return null;
    }

    public void onAmountTextChanged(String text){
        boolean haveChanges = !text.equals(amountText);
        amountText = text;

        if(haveChanges){
            onSomethingChanged();
        }
    }

    public void onVoluteFromSelected(Volute volute){
        boolean haveChanges = !volute.getId().equals(selectedVoluteFrom.getId());
        selectedVoluteFrom = volute;

        if(haveChanges){
            onSomethingChanged();
        }
    }

    public void onVoluteToSelected(Volute volute){
        boolean haveChanges = !volute.getId().equals(selectedVoluteTo.getId());

        selectedVoluteTo = volute;

        if(haveChanges){
            onSomethingChanged();
        }
    }
}

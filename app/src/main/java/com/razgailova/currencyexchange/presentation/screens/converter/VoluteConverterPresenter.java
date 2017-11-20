package com.razgailova.currencyexchange.presentation.screens.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.usecase.ConvertVoluteUseCase;
import com.razgailova.currencyexchange.domain.usecase.ExchangeRatesUseCase;
import com.razgailova.currencyexchange.presentation.mvp.base.BasePresenter;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by Катерина on 19.11.2017.
 */

public class VoluteConverterPresenter extends BasePresenter<VoluteConverterView> implements ExchangeRatesUseCase.ExchangeRateUpdateListener {
    private ConvertVoluteUseCase converterUseCase;
    private ExchangeRatesUseCase exchangeRatesUseCase;

    public VoluteConverterPresenter(ConvertVoluteUseCase converterUseCase,
                                    ExchangeRatesUseCase exchangeRatesUseCase) {
        this.converterUseCase = converterUseCase;
        this.exchangeRatesUseCase = exchangeRatesUseCase;
    }

    @Override
    public void bindView(@NonNull VoluteConverterView view) {
        super.bindView(view);

        exchangeRatesUseCase.subscribeToUpdates(this);

        view.showVolutesAndRates(exchangeRatesUseCase.getCurrencies());
    }

    @Override
    public void unbindView() {
        exchangeRatesUseCase.unSubscribeFromUpdates(this);

        super.unbindView();
    }

    public void onConvertVolutePressed(BigDecimal amount, Volute voluteFrom, Volute voluteTo){
        try {
            BigDecimal result = converterUseCase.convertVolute(amount, voluteFrom, voluteTo);
            view.showConversionResult(result);
        } catch (Exception e) {
            view.showConversionError(e.getMessage()); // todo make better error handling
        }
    }

    @Override
    public void onInitFinished() {
        Collection<Volute> volutes = exchangeRatesUseCase.getCurrencies();
        view.updateVolutesAndRates(volutes);
    }
}

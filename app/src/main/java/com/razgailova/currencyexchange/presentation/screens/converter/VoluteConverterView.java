package com.razgailova.currencyexchange.presentation.screens.converter;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.presentation.mvp.base.BaseView;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Катерина on 19.11.2017.
 */

public interface VoluteConverterView extends BaseView {

    void setConversionEnabled(boolean enabled);

    void showConversionResult(BigDecimal result);

    void hideConversionResult();

    void initCurrenciesSpinners(ArrayList<Volute> volutes,Volute selectedVoluteFrom, Volute selectedVoluteTo);

    void initAmountField(String amountText);

    void updateVolutesAndRates(ArrayList<Volute> volutes, Volute selectedVoluteFrom, Volute selectedVoluteTo);

    void showConversionError(String error);

    void showInitError(String error);
}

package com.razgailova.currencyexchange.presentation.screens.converter;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.presentation.mvp.base.BaseView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Катерина on 19.11.2017.
 */

public interface VoluteConverterView extends BaseView {

    void showConversionResult(BigDecimal result);

    void showVolutesAndRates(ArrayList<Volute> volutes);

    void updateVolutesAndRates(ArrayList<Volute> volutes);

    void showConversionError(String error);

}

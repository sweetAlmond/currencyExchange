package com.razgailova.currencyexchange.domain.usecase;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.domain.converter.ICurrencyConverter;

import java.math.BigDecimal;

/**
 * Created by Катерина on 19.11.2017.
 */

public class ConvertVoluteUseCase {
    private ICurrencyConverter currencyConverter;

    public ConvertVoluteUseCase(ICurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public BigDecimal convertVolute(@NonNull BigDecimal amount,
                                    @NonNull Volute voluteFrom,
                                    @NonNull Volute voluteTo) throws Exception{
        return currencyConverter.convert(amount, voluteFrom, voluteTo);
    }
}

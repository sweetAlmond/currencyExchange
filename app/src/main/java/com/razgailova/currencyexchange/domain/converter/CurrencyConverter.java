package com.razgailova.currencyexchange.domain.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;

import java.math.BigDecimal;

/**
 * Created by Катерина on 18.11.2017.
 */

public class CurrencyConverter implements ICurrencyConverter {

    public CurrencyConverter() {}

    @Override
    public BigDecimal convert(@NonNull BigDecimal amount, @NonNull Volute voluteFrom, @NonNull Volute voluteTo) throws Exception {
        if (!checkVoluteValidity(voluteFrom)) {
            throw new Exception("origin volute cost is not valid");
        }

        if (!checkVoluteValidity(voluteTo)) {
            throw new Exception("destination volute cost is not valid");
        }

        BigDecimal convertedValue = amount
                .multiply(normalize(voluteFrom))
                .divide(normalize(voluteTo), 4, BigDecimal.ROUND_HALF_EVEN);

        return convertedValue;
    }

    private BigDecimal normalize(Volute volute){
        return volute.getValue().divide(new BigDecimal(volute.getNominal()));
    }

    private boolean checkVoluteValidity(Volute volute){
        return volute.getValue().compareTo(new BigDecimal(0)) > 0 && volute.getNominal() > 0;
    }
}

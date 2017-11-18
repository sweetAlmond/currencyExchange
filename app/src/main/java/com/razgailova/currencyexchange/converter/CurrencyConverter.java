package com.razgailova.currencyexchange.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.Valute;

import java.math.BigDecimal;

/**
 * Created by Катерина on 18.11.2017.
 */

public class CurrencyConverter implements ICurrencyConverter{

    public CurrencyConverter() {}

    @Override
    public BigDecimal convert(@NonNull BigDecimal amount, @NonNull Valute voluteFrom, @NonNull Valute voluteTo) throws Exception {
        if (!checkVoluteValidity(voluteFrom)) {
            throw new Exception("origin volute cost is not valid");
        }

        if (!checkVoluteValidity(voluteTo)) {
            throw new Exception("destination volute cost is not valid");
        }

        BigDecimal convertedValue = amount
                .multiply(normalize(voluteFrom))
                .divide(normalize(voluteTo));

        return round(convertedValue);
    }

    private BigDecimal normalize(Valute valute){
        return valute.getValue().divide(new BigDecimal(valute.getNominal()));
    }

    private BigDecimal round(BigDecimal amount){
        return amount.setScale(4, BigDecimal.ROUND_HALF_EVEN);
    }

    private boolean checkVoluteValidity(Valute volute){
        return volute.getValue().compareTo(new BigDecimal(0)) > 0 && volute.getNominal() > 0;
    }
}

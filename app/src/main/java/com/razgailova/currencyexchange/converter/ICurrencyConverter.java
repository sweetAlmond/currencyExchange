package com.razgailova.currencyexchange.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.Valute;

import java.math.BigDecimal;

/**
 * Created by Катерина on 18.11.2017.
 */

public interface ICurrencyConverter {

    BigDecimal convert(@NonNull BigDecimal amount, @NonNull Valute voluteFrom, @NonNull Valute voluteTo) throws Exception;
}

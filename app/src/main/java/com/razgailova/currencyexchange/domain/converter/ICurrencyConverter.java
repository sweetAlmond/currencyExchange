package com.razgailova.currencyexchange.domain.converter;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;

import java.math.BigDecimal;

/**
 * Created by Катерина on 18.11.2017.
 */

public interface ICurrencyConverter {

    BigDecimal convert(@NonNull BigDecimal amount, @NonNull Volute voluteFrom, @NonNull Volute voluteTo) throws Exception;
}

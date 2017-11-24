package com.razgailova.currencyexchange.presentation.screens.converter;

import com.razgailova.currencyexchange.data.model.Volute;
import com.razgailova.currencyexchange.presentation.mvp.base.BaseState;

import java.io.Serializable;

public class VoluteConverterState implements BaseState, Serializable {

    private final boolean isSomethingChanged;
    private String amountText;
    private Volute selectedVoluteFrom;
    private Volute selectedVoluteTo;

    public VoluteConverterState(boolean isSomethingChanged, String amountText, Volute selectedVoluteFrom, Volute selectedVoluteTo) {
        this.isSomethingChanged = isSomethingChanged;
        this.amountText = amountText;
        this.selectedVoluteFrom = selectedVoluteFrom;
        this.selectedVoluteTo = selectedVoluteTo;
    }

    public boolean isSomethingChanged() {
        return isSomethingChanged;
    }

    public String getAmountText() {
        return amountText;
    }

    public Volute getSelectedVoluteFrom() {
        return selectedVoluteFrom;
    }

    public Volute getSelectedVoluteTo() {
        return selectedVoluteTo;
    }
}

package com.razgailova.currencyexchange.data.cache.loader;

/**
 * Created by Катерина on 16.11.2017.
 */

public enum LoadingState {
    DATA(0), ERROR(1);

    private final int value;

    LoadingState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LoadingState fromInt(int i) {
        for (LoadingState b : LoadingState.values()) {
            if (b.getValue() == i) { return b; }
        }
        return null;
    }
}

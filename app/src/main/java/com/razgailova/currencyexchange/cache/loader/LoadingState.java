package com.razgailova.currencyexchange.cache.loader;

/**
 * Created by Катерина on 16.11.2017.
 */

public enum LoadingState {
    DEFAULT(0), LOCAL(1), SERVER(2), ERROR(3), FATAL_ERROR(4);

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

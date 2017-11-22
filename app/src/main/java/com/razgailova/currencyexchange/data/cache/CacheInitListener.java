package com.razgailova.currencyexchange.data.cache;

/**
 * Created by Катерина on 16.11.2017.
 */

public interface CacheInitListener {
    void onInitFinished();
    void onError(String error);
}

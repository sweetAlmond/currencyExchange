package com.razgailova.currencyexchange.cache.loader;

import com.razgailova.currencyexchange.data.ValCurs;

/**
 * Created by Катерина on 16.11.2017.
 */

public interface LoadingListener {

    void onDataLoaded(ValCurs data);

    void onError();
}

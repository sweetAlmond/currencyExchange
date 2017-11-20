package com.razgailova.currencyexchange.data.cache.loader;

import android.support.annotation.NonNull;

/**
 * Created by Катерина on 18.11.2017.
 */

public interface ILoader {
    void load(@NonNull LoadingListener listener);
}

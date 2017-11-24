package com.razgailova.currencyexchange.data.cache;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.Volute;

import java.util.ArrayList;

/**
 * Created by Катерина on 22.11.2017.
 */

public interface ICurrencyCache {

    void initRemote(@NonNull final CacheInitListener listener);

    void removeRemoteInitListener(@NonNull final CacheInitListener listener);

    ArrayList<Volute> getCurrencyCollection();

}

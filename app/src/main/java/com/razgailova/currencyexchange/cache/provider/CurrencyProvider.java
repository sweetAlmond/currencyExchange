package com.razgailova.currencyexchange.cache.provider;

import android.content.Context;
import android.util.Log;

import com.razgailova.currencyexchange.cache.parcer.BigDecimalTransformer;
import com.razgailova.currencyexchange.cache.parcer.DateFormatTransformer;
import com.razgailova.currencyexchange.data.ValCurs;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Катерина on 16.11.2017.
 */

public abstract class CurrencyProvider {

    private static final String TAG = CurrencyProvider.class.getName();

    public ValCurs getCurrency(Context context) {
        // TODO вынести отдельно
        String xml = load(context);
        if(xml == null) {
            return null;
        }

        Reader reader = new StringReader(xml);

        DateFormat format = new SimpleDateFormat("dd.MM.YYYY", Locale.getDefault());
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransformer(format));
        m.bind(BigDecimal.class, new BigDecimalTransformer());

        Persister serializer = new Persister(m);
        try {
            ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
            return valCurs;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    protected abstract String load(Context context);
}

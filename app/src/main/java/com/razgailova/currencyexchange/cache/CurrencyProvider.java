package com.razgailova.currencyexchange.cache;

import android.content.Context;

import com.razgailova.currencyexchange.data.ValCurs;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by Катерина on 16.11.2017.
 */

public abstract class CurrencyProvider {

    public ValCurs getCurrency(Context context) {
        // TODO
        String xml = load(context);
        if(xml == null) {
            return null;
        }

        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();
        try {
            ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
            return valCurs;
        } catch (Exception e) {
        }

        return null;
    }

    protected abstract String load(Context context);
}

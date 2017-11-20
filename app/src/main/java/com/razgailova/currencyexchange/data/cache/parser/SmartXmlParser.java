package com.razgailova.currencyexchange.data.cache.parser;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.data.model.ValCurs;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.model.Volute;

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
 * Created by Катерина on 18.11.2017.
 */

public class SmartXmlParser implements IParser {

    private static final String DATE_FORMAT = "dd.MM.yyyy";

    @Override
    public ExchangeRates parse(@NonNull String xml) throws Exception {
        Reader reader = new StringReader(xml);

        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        RegistryMatcher m = new RegistryMatcher();
        m.bind(Date.class, new DateFormatTransform(format));
        m.bind(BigDecimal.class, new BigDecimalTransform());

        Persister serializer = new Persister(m);

        ValCurs valCurs = serializer.read(ValCurs.class, reader, false);

        ExchangeRates exchangeRates = new ExchangeRates(valCurs.getDate());

        for (Volute volute : valCurs.getVolutes()) {
            exchangeRates.addCurrency(volute);
        }

        return exchangeRates;
    }

}

package com.razgailova.currencyexchange.cache.parser;

import android.support.annotation.NonNull;

import com.razgailova.currencyexchange.cache.parser.data.ValCurs;
import com.razgailova.currencyexchange.data.ExchangeRates;
import com.razgailova.currencyexchange.data.Valute;

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

    private static final String DATE_FORMAT = "dd.MM.YYYY";

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

        for(Valute valute : valCurs.getValutes()) {
            exchangeRates.addCurrency(valute);
        }

        return exchangeRates;
    }

}

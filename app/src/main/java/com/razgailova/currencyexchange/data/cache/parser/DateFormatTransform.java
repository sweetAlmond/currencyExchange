package com.razgailova.currencyexchange.data.cache.parser;

import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Катерина on 18.11.2017.
 */

public class DateFormatTransform implements Transform<Date> {
    private DateFormat dateFormat;


    public DateFormatTransform(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    @Override
    public Date read(String value) throws Exception {
        return dateFormat.parse(value);
    }


    @Override
    public String write(Date value) throws Exception {
        return dateFormat.format(value);
    }

}

package com.razgailova.currencyexchange.cache;

import org.simpleframework.xml.transform.Transform;

import java.math.BigDecimal;

/**
 * Created by Катерина on 18.11.2017.
 */

public class BigDecimalTransformer implements Transform<BigDecimal> {

    @Override
    public BigDecimal read(String value) throws Exception {
        return new BigDecimal(value.replace(",", "."));
    }


    @Override
    public String write(BigDecimal value) throws Exception {
        return value.toString();
    }

}

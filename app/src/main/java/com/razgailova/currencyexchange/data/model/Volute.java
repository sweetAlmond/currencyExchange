package com.razgailova.currencyexchange.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;

/**
 * Created by Катерина on 16.11.2017.
 */

@Root(name = "Valute")
public class Volute {

    public Volute(){}

    public Volute(String id, int numCode, String charCode, int nominal, String name, BigDecimal value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    @Attribute(name = "ID")
    private String id;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private int nominal;

    @Element(name = "Name")
    private String name;

    @Element(name = "Value")
    private BigDecimal value;

    public String getId() {
        return id;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return charCode;
    }
}

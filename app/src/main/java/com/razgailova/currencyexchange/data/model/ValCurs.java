package com.razgailova.currencyexchange.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.List;

/**
 * Created by Катерина on 16.11.2017.
 */

@Root
public class ValCurs {

    @Attribute(name = "Date")
    private Date date;

    @Attribute
    private String name;

    @ElementList(inline = true)
    private List<Volute> volutes;

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public List<Volute> getVolutes() {
        return volutes;
    }
}

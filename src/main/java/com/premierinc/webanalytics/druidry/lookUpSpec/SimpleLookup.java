package com.premierinc.webanalytics.druidry.lookUpSpec;

import com.fasterxml.jackson.annotation.JsonValue;

public class SimpleLookup extends LookUpSpec {
    private String name;

    public SimpleLookup(String name) {
        this.name = name;
    }


    @JsonValue
    public String getDimension() {
        return this.name;
    }
}

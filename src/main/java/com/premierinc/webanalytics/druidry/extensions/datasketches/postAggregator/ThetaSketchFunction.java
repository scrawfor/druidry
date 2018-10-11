package com.premierinc.webanalytics.druidry.extensions.datasketches.postAggregator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ThetaSketchFunction {

    INTERSECT("INTERSECT"),
    UNION("UNION"),
    NOT("NOT");

    private String value;

    ThetaSketchFunction(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}

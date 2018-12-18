package com.premierinc.webanalytics.druidry.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TrueFilter extends DruidFilter {
    private static final String TRUE_DRUID_FILTER_TYPE = "true";

    public TrueFilter() {
        this.type = TRUE_DRUID_FILTER_TYPE;
    }
}
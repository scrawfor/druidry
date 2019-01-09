package com.premierinc.webanalytics.druidry.datasource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class UnionDatasource extends Datasource{
    private static final String TABLE_DATASOURCE_TYPE = "union";

    @Getter
    private List<String> dataSources;

    public UnionDatasource(@NonNull List<String> dataSources) {
        this.type = TABLE_DATASOURCE_TYPE;
        this.dataSources = dataSources;
    }
}
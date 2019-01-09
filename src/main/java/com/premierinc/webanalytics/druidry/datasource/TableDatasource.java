package com.premierinc.webanalytics.druidry.datasource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.premierinc.webanalytics.druidry.datasource.serializers.SimpleDatasourceSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = SimpleDatasourceSerializer.class)
public class TableDatasource extends Datasource{
    private static final String TABLE_DATASOURCE_TYPE = "table";

    @Getter
    private String name;

    public TableDatasource(@NonNull String name) {
        this.type = TABLE_DATASOURCE_TYPE;
        this.name = name;
    }
}
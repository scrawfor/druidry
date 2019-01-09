package com.premierinc.webanalytics.druidry.datasource.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.premierinc.webanalytics.druidry.datasource.TableDatasource;

import java.io.IOException;

public class SimpleDatasourceSerializer extends JsonSerializer<TableDatasource> {

    @Override
    public void serialize(TableDatasource tableDatasource, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(tableDatasource.getName());
    }

}

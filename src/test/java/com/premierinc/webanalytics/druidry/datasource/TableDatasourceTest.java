package com.premierinc.webanalytics.druidry.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierinc.webanalytics.druidry.aggregator.DoubleMinAggregator;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class TableDatasourceTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        TableDatasource datasource = new TableDatasource("my-datasource");

        String actualJSON = objectMapper.writeValueAsString(datasource);
        Assert.assertEquals("\"my-datasource\"", actualJSON);
    }


}

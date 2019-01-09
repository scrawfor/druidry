package com.premierinc.webanalytics.druidry.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UnionDatasourceTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        List<String> list = new ArrayList<>();
        list.add("my-datasource");
        list.add("another-datasource");
        UnionDatasource datasource = new UnionDatasource(list);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "union");

        JSONArray dsArray = new JSONArray();
        dsArray.put("my-datasource");
        dsArray.put("another-datasource");
        jsonObject.put("dataSources", dsArray);

        String actualJSON = objectMapper.writeValueAsString(datasource);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }


}

package com.premierinc.webanalytics.druidry.extensions.datasketches.aggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.premierinc.webanalytics.druidry.aggregator.DoubleMinAggregator;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ThetaSketchAggregatorTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    private JSONObject getThetaSketchAggregatorJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "thetaSketch");
        jsonObject.put("name", "estimated_stars");
        jsonObject.put("fieldName", "stars");

        return jsonObject;
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        ThetaSketchAggregator thetaSketchAggregator = ThetaSketchAggregator.builder()
                .name("estimated_stars")
                .fieldName("stars")
                .isInputThetaSketch(true)
                .size(1024L)
                .build();

        JSONObject jsonObject = getThetaSketchAggregatorJSON();
        jsonObject.put("isInputThetaSketch", true);
        jsonObject.put("size", 1024L);

        String actualJSON = objectMapper.writeValueAsString(thetaSketchAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testRequiredFields() throws JsonProcessingException, JSONException {

        ThetaSketchAggregator thetaSketchAggregator = ThetaSketchAggregator.builder()
                .name("estimated_stars")
                .fieldName("stars")
                .build();

        JSONObject jsonObject = getThetaSketchAggregatorJSON();

        String actualJSON = objectMapper.writeValueAsString(thetaSketchAggregator);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void preconditionCheck() {

        ThetaSketchAggregator thetaSketchAggregator = ThetaSketchAggregator.builder()
                .name("estimated_stars")
                .fieldName("stars")
                .size(420L)
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullName() {

        ThetaSketchAggregator thetaSketchAggregator = ThetaSketchAggregator.builder()
                .fieldName("stars")
                .build();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullFieldName() {

        ThetaSketchAggregator thetaSketchAggregator = ThetaSketchAggregator.builder()
                .name("estimated_stars")
                .build();
    }

    @Test
    public void testRename() {
        ThetaSketchAggregator aggregator1 = new ThetaSketchAggregator("name", "testField", false, 8L);
        ThetaSketchAggregator aggregator2 = (ThetaSketchAggregator) aggregator1.withName("newName");
        Assert.assertNotEquals(aggregator1, aggregator2);

        Assert.assertEquals(aggregator1.getName(), "name");
        Assert.assertEquals(aggregator2.getName(), "newName");
        Assert.assertEquals(aggregator1.getFieldName(), aggregator2.getFieldName());
        Assert.assertEquals(aggregator1.getIsInputThetaSketch(), aggregator2.getIsInputThetaSketch());
        Assert.assertEquals(aggregator1.getSize(), aggregator2.getSize());
    }
}

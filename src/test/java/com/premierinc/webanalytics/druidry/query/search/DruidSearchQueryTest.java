package com.premierinc.webanalytics.druidry.query.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.premierinc.webanalytics.druidry.Context;
import com.premierinc.webanalytics.druidry.Interval;
import com.premierinc.webanalytics.druidry.SortingOrder;
import com.premierinc.webanalytics.druidry.filter.DruidFilter;
import com.premierinc.webanalytics.druidry.filter.SelectorFilter;
import com.premierinc.webanalytics.druidry.filter.searchQuerySpec.InsensitiveContainsSearchQuerySpec;
import com.premierinc.webanalytics.druidry.filter.searchQuerySpec.SearchQuerySpec;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.premierinc.webanalytics.druidry.dimension.DruidDimension;
import com.premierinc.webanalytics.druidry.dimension.SimpleDimension;
import com.premierinc.webanalytics.druidry.granularity.PredefinedGranularity;
import com.premierinc.webanalytics.druidry.granularity.SimpleGranularity;

public class DruidSearchQueryTest {
    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSampleQuery() throws JsonProcessingException, JSONException {

        List<DruidDimension> searchDimensions
                = Arrays.asList(new SimpleDimension("dim1"), new SimpleDimension("dim2"));

        SearchQuerySpec searchQuerySpec = new InsensitiveContainsSearchQuerySpec("Ke");


        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2013, 1, 1), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2013, 1, 3), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        DruidSearchQuery query = DruidSearchQuery.builder()
                .dataSource("sample_datasource")
                .granularity(new SimpleGranularity(PredefinedGranularity.DAY))
                .searchDimensions(searchDimensions)
                .query(searchQuerySpec)
                .sort(SortingOrder.LEXICOGRAPHIC)
                .intervals(Collections.singletonList(interval))
                .build();

        String expectedJsonAsString = "{\n" +
                "  \"queryType\": \"search\",\n" +
                "  \"dataSource\": \"sample_datasource\",\n" +
                "  \"granularity\": \"day\",\n" +
                "  \"searchDimensions\": [\n" +
                "    \"dim1\",\n" +
                "    \"dim2\"\n" +
                "  ],\n" +
                "  \"query\": {\n" +
                "    \"type\": \"insensitive_contains\",\n" +
                "    \"value\": \"Ke\"\n" +
                "  },\n" +
                "  \"sort\" : {\n" +
                "    \"type\": \"lexicographic\"\n" +
                "  }," +
                "  \"intervals\": [" +
                "    \"2013-01-01T00:00:00.000Z/2013-01-03T00:00:00.000Z\"" +
                "  ]" +
                "}";

        String actualJson = objectMapper.writeValueAsString(query);
        JSONAssert.assertEquals(actualJson, expectedJsonAsString, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testRequiredFields() throws JsonProcessingException, JSONException {

        SearchQuerySpec searchQuerySpec = new InsensitiveContainsSearchQuerySpec("Ke");

        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2013, 1, 1), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2013, 1, 3), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        DruidSearchQuery query = DruidSearchQuery.builder()
                .dataSource("sample_datasource")
                .granularity(new SimpleGranularity(PredefinedGranularity.DAY))
                .query(searchQuerySpec)
                .intervals(Collections.singletonList(interval))
                .build();

        String expectedJsonAsString = "{\n" +
                "  \"queryType\": \"search\",\n" +
                "  \"dataSource\": \"sample_datasource\",\n" +
                "  \"granularity\": \"day\",\n" +
                "  \"query\": {\n" +
                "    \"type\": \"insensitive_contains\",\n" +
                "    \"value\": \"Ke\"\n" +
                "  },\n" +
                "  \"intervals\": [" +
                "    \"2013-01-01T00:00:00.000Z/2013-01-03T00:00:00.000Z\"" +
                "  ]" +
                "}";

        String actualJson = objectMapper.writeValueAsString(query);
        JSONAssert.assertEquals(actualJson, expectedJsonAsString, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        List<DruidDimension> searchDimensions
                = Arrays.asList(new SimpleDimension("dim1"), new SimpleDimension("dim2"));

        SearchQuerySpec searchQuerySpec = new InsensitiveContainsSearchQuerySpec("Ke");

        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2013, 1, 1), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2013, 1, 3), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        DruidFilter druidFilter = new SelectorFilter("Dim", "You");

        Context context = Context.builder()
                .useCache(true)
                .build();

        DruidSearchQuery query = DruidSearchQuery.builder()
                .dataSource("sample_datasource")
                .granularity(new SimpleGranularity(PredefinedGranularity.DAY))
                .filter(druidFilter)
                .limit(16)
                .searchDimensions(searchDimensions)
                .query(searchQuerySpec)
                .sort(SortingOrder.LEXICOGRAPHIC)
                .intervals(Collections.singletonList(interval))
                .context(context)
                .build();

        String expectedJsonAsString = "{\n" +
                "  \"queryType\": \"search\",\n" +
                "  \"dataSource\": \"sample_datasource\",\n" +
                "  \"granularity\": \"day\",\n" +
                "  \"filter\": {\n" +
                "        \"type\": \"selector\",\n" +
                "        \"dimension\": \"Dim\",\n" +
                "        \"value\": \"You\"\n" +
                "    },\n" +
                "    \"limit\": 16," +
                "  \"searchDimensions\": [\n" +
                "    \"dim1\",\n" +
                "    \"dim2\"\n" +
                "  ],\n" +
                "  \"query\": {\n" +
                "    \"type\": \"insensitive_contains\",\n" +
                "    \"value\": \"Ke\"\n" +
                "  },\n" +
                "  \"sort\" : {\n" +
                "    \"type\": \"lexicographic\"\n" +
                "  }," +
                "  \"intervals\": [" +
                "    \"2013-01-01T00:00:00.000Z/2013-01-03T00:00:00.000Z\"" +
                "  ]," +
                "  \"context\": {\n" +
                "    \"useCache\" : true\n" +
                "    }" +
                "}";

        String actualJson = objectMapper.writeValueAsString(query);
        JSONAssert.assertEquals(actualJson, expectedJsonAsString, JSONCompareMode.NON_EXTENSIBLE);
    }
}

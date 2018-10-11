/*
 * Copyright (c) 2017-present, Red Brick Lane Marketing Solutions Pvt. Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.premierinc.webanalytics.druidry.query.aggregation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.premierinc.webanalytics.druidry.Context;
import com.premierinc.webanalytics.druidry.Interval;
import com.premierinc.webanalytics.druidry.filter.AndFilter;
import com.premierinc.webanalytics.druidry.filter.DruidFilter;
import com.premierinc.webanalytics.druidry.filter.OrFilter;
import com.premierinc.webanalytics.druidry.filter.SelectorFilter;
import com.premierinc.webanalytics.druidry.aggregator.CountAggregator;
import com.premierinc.webanalytics.druidry.aggregator.DoubleSumAggregator;
import com.premierinc.webanalytics.druidry.aggregator.DruidAggregator;
import com.premierinc.webanalytics.druidry.aggregator.LongSumAggregator;
import com.premierinc.webanalytics.druidry.granularity.Granularity;
import com.premierinc.webanalytics.druidry.granularity.PredefinedGranularity;
import com.premierinc.webanalytics.druidry.granularity.SimpleGranularity;
import com.premierinc.webanalytics.druidry.postAggregator.ArithmeticFunction;
import com.premierinc.webanalytics.druidry.postAggregator.ArithmeticPostAggregator;
import com.premierinc.webanalytics.druidry.postAggregator.ConstantPostAggregator;
import com.premierinc.webanalytics.druidry.postAggregator.DruidPostAggregator;
import com.premierinc.webanalytics.druidry.postAggregator.FieldAccessPostAggregator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

public class TimeSeriesTest {
    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testSampleQuery() throws JsonProcessingException, JSONException {

        SelectorFilter selectorFilter2 = new SelectorFilter("sample_dimension2",
                "sample_value2");
        SelectorFilter selectorFilter3 = new SelectorFilter("sample_dimension3",
                "sample_value3");

        OrFilter orfilter = new OrFilter(Arrays.asList(selectorFilter2, selectorFilter3));

        SelectorFilter selectorFilter1 = new SelectorFilter("sample_dimension1",
                "sample_value1");

        AndFilter andFilter = new AndFilter(Arrays.asList(selectorFilter1, orfilter));

        DruidAggregator aggregator1 = new LongSumAggregator("sample_name1",
                "sample_fieldName1");
        DruidAggregator aggregator2 = new DoubleSumAggregator("sample_name2",
                "sample_fieldName2");

        FieldAccessPostAggregator fieldAccessPostAggregator1
                = new FieldAccessPostAggregator("postAgg__sample_name1",
                "sample_name1");

        FieldAccessPostAggregator fieldAccessPostAggregator2
                = new FieldAccessPostAggregator("postAgg__sample_name2",
                "sample_name2");

        DruidPostAggregator postAggregator = ArithmeticPostAggregator.builder()
                .name("sample_divide")
                .function(ArithmeticFunction.DIVIDE)
                .fields(Arrays.asList(fieldAccessPostAggregator1, fieldAccessPostAggregator2))
                .build();

        //2013-08-31T00:00:00.000/2013-09-03T00:00:00.000"
        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2012, 1, 1), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2012, 1, 3), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        Granularity granularity = new SimpleGranularity(PredefinedGranularity.DAY);

        DruidTimeSeriesQuery query = DruidTimeSeriesQuery.builder()
                .dataSource("sample_datasource")
                .granularity(granularity)
                .descending(true)
                .filter(andFilter)
                .aggregators(Arrays.asList(aggregator1, aggregator2))
                .postAggregators(Collections.singletonList(postAggregator))
                .intervals(Collections.singletonList(interval))
                .build();

        String expectedJsonAsString = "{\n" +
                "  \"queryType\": \"timeseries\",\n" +
                "  \"dataSource\": \"sample_datasource\",\n" +
                "  \"granularity\": \"day\",\n" +
                "  \"descending\": true,\n" +
                "  \"filter\": {\n" +
                "    \"type\": \"and\",\n" +
                "    \"fields\": [\n" +
                "      { \"type\": \"selector\", \"dimension\": \"sample_dimension1\", \"value\": \"sample_value1\" },\n" +
                "      { \"type\": \"or\",\n" +
                "        \"fields\": [\n" +
                "          { \"type\": \"selector\", \"dimension\": \"sample_dimension2\", \"value\": \"sample_value2\" },\n" +
                "          { \"type\": \"selector\", \"dimension\": \"sample_dimension3\", \"value\": \"sample_value3\" }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"aggregations\": [\n" +
                "    { \"type\": \"longSum\", \"name\": \"sample_name1\", \"fieldName\": \"sample_fieldName1\" },\n" +
                "    { \"type\": \"doubleSum\", \"name\": \"sample_name2\", \"fieldName\": \"sample_fieldName2\" }\n" +
                "  ],\n" +
                "  \"postAggregations\": [\n" +
                "    { \"type\": \"arithmetic\",\n" +
                "      \"name\": \"sample_divide\",\n" +
                "      \"fn\": \"/\",\n" +
                "      \"fields\": [\n" +
                "        { \"type\": \"fieldAccess\", \"name\": \"postAgg__sample_name1\", \"fieldName\": \"sample_name1\" },\n" +
                "        { \"type\": \"fieldAccess\", \"name\": \"postAgg__sample_name2\", \"fieldName\": \"sample_name2\" }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"intervals\": [ \"2012-01-01T00:00:00.000Z/2012-01-03T00:00:00.000Z\" ]\n" +
                "}";

        String actualJson = objectMapper.writeValueAsString(query);
        JSONAssert.assertEquals(actualJson, expectedJsonAsString, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testRequiredFields() throws JsonProcessingException, JSONException {
        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2013, 7, 14), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2013, 11, 16), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        Granularity granularity = new SimpleGranularity(PredefinedGranularity.DAY);

        DruidTimeSeriesQuery seriesQuery = DruidTimeSeriesQuery.builder()
                .dataSource("Matrix")
                .intervals(Collections.singletonList(interval))
                .granularity(granularity)
                .build();

        JSONObject expectedQuery = new JSONObject();
        expectedQuery.put("queryType", "timeseries");
        expectedQuery.put("dataSource", "Matrix");
        expectedQuery.put("intervals", new JSONArray(Collections
                .singletonList("2013-07-14T00:00:00.000Z/2013-11-16T00:00:00.000Z")));
        expectedQuery.put("granularity", "day");

        String actualJson = objectMapper.writeValueAsString(seriesQuery);
        JSONAssert.assertEquals(actualJson, expectedQuery, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testAllFields() throws JSONException, JsonProcessingException {

        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2013, 7, 14), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2013, 11, 16), LocalTime.of(0,0,0), ZoneId.of("UTC"));
        Interval interval = new Interval(startTime, endTime);

        Granularity granularity = new SimpleGranularity(PredefinedGranularity.DAY);

        Context context = Context.builder()
                .useCache(true)
                .build();

        DruidFilter filter = new SelectorFilter("Spread", "Peace");
        DruidAggregator aggregator = new CountAggregator("Chill");
        DruidPostAggregator postAggregator = new ConstantPostAggregator("Keep", 10.47);

        DruidTimeSeriesQuery seriesQuery = DruidTimeSeriesQuery.builder()
                .dataSource("Matrix")
                .descending(true)
                .intervals(Collections.singletonList(interval))
                .granularity(granularity)
                .filter(filter)
                .aggregators(Collections.singletonList(aggregator))
                .postAggregators(Collections.singletonList(postAggregator))
                .context(context)
                .build();

        JSONObject expectedFilter = new JSONObject();
        expectedFilter.put("type", "selector");
        expectedFilter.put("dimension", "Spread");
        expectedFilter.put("value", "Peace");

        JSONObject expectedAggregator = new JSONObject();
        expectedAggregator.put("type", "count");
        expectedAggregator.put("name", "Chill");

        JSONObject expectedPostAggregator = new JSONObject();
        expectedPostAggregator.put("type", "constant");
        expectedPostAggregator.put("name", "Keep");
        expectedPostAggregator.put("value", 10.47);

        JSONObject expectedContext = new JSONObject();
        expectedContext.put("useCache", true);

        JSONObject expectedQuery = new JSONObject();
        expectedQuery.put("queryType", "timeseries");
        expectedQuery.put("dataSource", "Matrix");
        expectedQuery.put("intervals", new JSONArray(Collections
                .singletonList("2013-07-14T00:00:00.000Z/2013-11-16T00:00:00.000Z")));
        expectedQuery.put("granularity", "day");
        expectedQuery.put("aggregations", new JSONArray(Collections.singletonList(expectedAggregator)));
        expectedQuery.put("postAggregations", new JSONArray(Collections.singletonList(expectedPostAggregator)));
        expectedQuery.put("filter", expectedFilter);
        expectedQuery.put("descending", true);
        expectedQuery.put("context", expectedContext);

        String actualJson = objectMapper.writeValueAsString(seriesQuery);
        JSONAssert.assertEquals(actualJson, expectedQuery, JSONCompareMode.NON_EXTENSIBLE);
    }
}
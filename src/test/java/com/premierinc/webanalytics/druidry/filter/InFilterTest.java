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

package com.premierinc.webanalytics.druidry.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.premierinc.webanalytics.druidry.extractionFunctions.ExtractionFunction;
import com.premierinc.webanalytics.druidry.extractionFunctions.RegisteredLookUpExtractionFunction;
import com.premierinc.webanalytics.druidry.lookUpSpec.LookUpSpec;
import com.premierinc.webanalytics.druidry.lookUpSpec.SimpleLookup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class InFilterTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JSONException, JsonProcessingException {

        List<String> values = Arrays.asList("Always", "HP");

        JSONArray jsonArray = new JSONArray(values);

        InFilter filter = new InFilter("Hello", values);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "in");
        jsonObject.put("dimension", "Hello");
        jsonObject.put("values", jsonArray);

        String actualJSON = objectMapper.writeValueAsString(filter);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDimensionMissing() {
        InFilter filter = new InFilter(null, Arrays.asList("Always", "HP"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testValueMissing() {
        InFilter filter = new InFilter("Hello", null);
    }

    @Test
    public void testRegisteredLookupFilter() throws JSONException, JsonProcessingException {

        List<String> values = Arrays.asList("Always", "HP");


        LookUpSpec lookup = new SimpleLookup("user_lookup");
        ExtractionFunction lookupFunc = RegisteredLookUpExtractionFunction.builder().lookUp(lookup).build();
        InFilter filter = new InFilter("client_id", values, lookupFunc);
        String actualJSON = objectMapper.writeValueAsString(filter);

        JSONObject expectedFilter = new JSONObject();
        expectedFilter.put("type", "in");
        expectedFilter.put("dimension", "client_id");
        expectedFilter.put("values", new JSONArray(values));
        JSONObject expectedExtractionFn = new JSONObject();
        expectedExtractionFn.put("type", "registeredLookup");
        expectedExtractionFn.put("lookup", "user_lookup");
        expectedFilter.put("extractionFn", expectedExtractionFn);

        JSONAssert.assertEquals(expectedFilter.toString(), actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

}

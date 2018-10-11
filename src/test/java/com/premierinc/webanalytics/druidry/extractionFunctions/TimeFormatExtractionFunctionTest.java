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

package com.premierinc.webanalytics.druidry.extractionFunctions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.premierinc.webanalytics.druidry.granularity.DurationGranularity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeFormatExtractionFunctionTest {

    private static ObjectMapper objectMapper;
    private final static DateTimeFormatter OFFSET_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JsonProcessingException, JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Locale locale = Locale.FRENCH;

        String timeZone = "America/Montreal";

        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of("UTC"));

        DurationGranularity spec = new DurationGranularity(7200000, originDate);

        TimeFormatExtractionFunction timeFormatExtractonFunction = TimeFormatExtractionFunction.builder()
                .format(sdf)
                .timeZone(timeZone)
                .locale(locale)
                .granularity(spec)
                .asMillis(true)
                .build();

        String actualJSON = objectMapper.writeValueAsString(timeFormatExtractonFunction);

        String expectedJSONString = "{\n\"type\" : \"timeFormat\",\n \"format\" : \"dd-MM-yyyy\",\n    \"timeZone\" : \"America/Montreal\",\n    \"locale\" : \"fr\",\n    \"granularity\": {\"type\": \"duration\", \"duration\": 7200000, \"origin\": \"" +
                originDate.format(OFFSET_DATETIME_FORMAT) +
                "\"},\n    \"asMillis\": true\n\n  }";

        JSONAssert.assertEquals(expectedJSONString, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void TestRequiredFields() throws JsonProcessingException, JSONException {
        TimeFormatExtractionFunction timeFormatExtractonFunction = TimeFormatExtractionFunction.builder().build();

        String actualJSON = objectMapper.writeValueAsString(timeFormatExtractonFunction);
        String expectedJSONString = "{ \"type\" : \"timeFormat\" }\n";

        JSONAssert.assertEquals(expectedJSONString, actualJSON, JSONCompareMode.NON_EXTENSIBLE);

    }
}

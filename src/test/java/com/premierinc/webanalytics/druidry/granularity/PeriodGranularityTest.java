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

package com.premierinc.webanalytics.druidry.granularity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PeriodGranularityTest {
    private static ObjectMapper objectMapper;
    private final static String PERIOD = "PT1H";
    private final static String TIMEZONE = "Asia/Kolkata";
    private final static DateTimeFormatter OFFSET_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


    @BeforeClass
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAllFields() throws JSONException, JsonProcessingException {

        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        PeriodGranularity periodGranularity = PeriodGranularity.builder()
                .origin(originDate)
                .period(PERIOD)
                .build();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "period");
        jsonObject.put("period", PERIOD);
        jsonObject.put("timeZone", TIMEZONE);
        jsonObject.put("origin", originDate.format(OFFSET_DATETIME_FORMAT));

        String actualJSON = objectMapper.writeValueAsString(periodGranularity);
        String expectedJSON = jsonObject.toString();
        JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testEqualsPositive() {
        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        PeriodGranularity granularity1 = PeriodGranularity.builder()
                .origin(originDate)
                .period(PERIOD)
                .build();

        ZonedDateTime originDate2 = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        PeriodGranularity granularity2 = PeriodGranularity.builder()
                .origin(originDate2)
                .period(PERIOD)
                .build();

        Assert.assertEquals(granularity1, granularity2);
    }

    @Test
    public void testEqualsNegative() {

        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        PeriodGranularity granularity1 = PeriodGranularity.builder()
                .origin(originDate)
                .period(PERIOD)
                .build();

        ZonedDateTime originDate2 = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        originDate2 = originDate2.plusDays(1);
        PeriodGranularity granularity2 = PeriodGranularity.builder()
                .origin(originDate2)
                .period(PERIOD)
//                .timeZone(DateTimeZone.forID(TIMEZONE))
                .build();

        Assert.assertNotEquals(granularity1, granularity2);
    }

    @Test
    public void testEqualsWithAnotherSubClass() {
        SimpleGranularity granularity1 = new SimpleGranularity(PredefinedGranularity.ALL);

        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of(TIMEZONE));
        PeriodGranularity granularity2 = PeriodGranularity.builder()
                .origin(originDate)
                .period(PERIOD)
//                .timeZone(DateTimeZone.forID(TIMEZONE))
                .build();

        Assert.assertNotEquals(granularity1, granularity2);
    }
}

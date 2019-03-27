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

package com.premierinc.webanalytics.druidry.aggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class StringLastAggregatorTest
{

  private static ObjectMapper objectMapper;

  @BeforeClass
  public void init()
  {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testAllFields() throws JsonProcessingException, JSONException
  {

    StringLastAggregator aggregator = StringLastAggregator.builder()
                                                            .name("string_name")
                                                            .fieldName("some_dimension")
                                                            .build();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("type", "stringLast");
    jsonObject.put("name", "string_name");
    jsonObject.put("fieldName", "some_dimension");

    String actualJSON = objectMapper.writeValueAsString(aggregator);
    String expectedJSON = jsonObject.toString();
    JSONAssert.assertEquals(expectedJSON, actualJSON, JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testNullName() throws JsonProcessingException, JSONException
  {

    StringLastAggregator aggregator = StringLastAggregator.builder()
                                                               .fieldName("some_dimension")
                                                               .build();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testNullFieldName() throws JsonProcessingException, JSONException
  {
    StringLastAggregator aggregator = StringLastAggregator.builder()
                                                            .name("string_name")
                                                            .build();
  }

  @Test
  public void testEqualsPositive()
  {

    StringLastAggregator aggregator1 = StringLastAggregator.builder()
                                                            .fieldName("some_dimension")
                                                            .name("string_name")
                                                            .build();

    StringLastAggregator aggregator2 = StringLastAggregator.builder()
                                                            .fieldName("some_dimension")
                                                            .name("string_name")
                                                            .build();

    Assert.assertEquals(aggregator1, aggregator2);
  }

  @Test
  public void testEqualsNegative()
  {
    StringLastAggregator aggregator1 = StringLastAggregator.builder()
                                                             .fieldName("some_dimension")
                                                             .name("string_name")
                                                             .build();

    StringLastAggregator aggregator2 = StringLastAggregator.builder()
                                                             .fieldName("some_dimension2")
                                                             .name("string_name2")
                                                             .build();

    Assert.assertNotEquals(aggregator1, aggregator2);
  }

  @Test
  public void testEqualsWithAnotherSubClass()
  {
    StringLastAggregator aggregator1 = StringLastAggregator.builder()
                                                             .fieldName("some_dimension")
                                                             .name("string_name")
                                                             .build();
    CountAggregator aggregator2 = new CountAggregator("countAgg1");

    Assert.assertNotEquals(aggregator1, aggregator2);
  }

  @Test
  public void testRename()
  {
    StringLastAggregator aggregator1 = StringLastAggregator.builder()
                                                             .fieldName("some_dimension")
                                                             .name("name")
                                                             .build();
    StringLastAggregator aggregator2 = (StringLastAggregator) aggregator1.withName("newName");
    Assert.assertNotEquals(aggregator1, aggregator2);

    Assert.assertEquals(aggregator1.getName(), "name");
    Assert.assertEquals(aggregator2.getName(), "newName");
    Assert.assertEquals(aggregator1.getFieldName(), aggregator2.getFieldName());
  }

}
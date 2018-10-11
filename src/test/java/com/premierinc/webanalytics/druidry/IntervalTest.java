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

package com.premierinc.webanalytics.druidry;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;

public class IntervalTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingStartField() {
        ZonedDateTime startTime = ZonedDateTime.now();
        Interval interval = new Interval(startTime, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testMissingEndField() {
        ZonedDateTime endTime = ZonedDateTime.now();
        Interval interval = new Interval(null, endTime);
    }

    @Test
    public void intervalEqualityTest() {
        ZonedDateTime startTime = ZonedDateTime.now();
        ZonedDateTime endTime = startTime.plusDays(1);

        Interval interval1 = new Interval(startTime, endTime);
        Interval interval2 = new Interval(startTime, endTime);
        Assert.assertEquals(interval1, interval2);

        Interval interval3 = new Interval(startTime, endTime.plusDays(1));

        Assert.assertNotEquals(interval1, interval3);

        ZonedDateTime otherStartTime = ZonedDateTime.ofInstant(startTime.toInstant(), startTime.getZone());
        ZonedDateTime otherEndTime = ZonedDateTime.ofInstant(endTime.toInstant(), endTime.getZone());

        Interval interval4 = new Interval(otherStartTime, otherEndTime);
        Assert.assertEquals(interval1, interval4);
    }
}

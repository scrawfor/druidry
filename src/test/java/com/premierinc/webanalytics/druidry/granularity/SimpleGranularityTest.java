package com.premierinc.webanalytics.druidry.granularity;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SimpleGranularityTest {

    @Test
    public void testEqualsPositive() {
        SimpleGranularity granularity1 = new SimpleGranularity(PredefinedGranularity.ALL);
        SimpleGranularity granularity2 = new SimpleGranularity(PredefinedGranularity.ALL);

        Assert.assertEquals(granularity1, granularity2);
    }

    @Test
    public void testEqualsNegative() {
        SimpleGranularity granularity1 = new SimpleGranularity(PredefinedGranularity.ALL);
        SimpleGranularity granularity2 = new SimpleGranularity(PredefinedGranularity.WEEK);

        Assert.assertNotEquals(granularity1, granularity2);
    }

    @Test
    public void testEqualsWithAnotherSubClass() {
        SimpleGranularity granularity1 = new SimpleGranularity(PredefinedGranularity.ALL);

        ZonedDateTime originDate = ZonedDateTime.now(ZoneId.of("UTC"));
        DurationGranularity granularity2 = new DurationGranularity(3141, originDate);

        Assert.assertNotEquals(granularity1, granularity2);
    }
}

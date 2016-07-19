/*
 * Copyright 2014-2016 Message4U Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messagemedia.restapi.client.v1.internal.util;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.ParseException;

import static com.messagemedia.restapi.client.v1.internal.util.JsonUtilities.bytesToObject;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link JsonUtilities} class.
 */
@RunWith(DataProviderRunner.class)
public class JsonUtilitiesTest {

    @DataProvider
    public static Object[][] validDateStrings() {
        return new Object[][]{
                {"2011-10-10T23:00:00+11:00", new DateTime(1318248000L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T12:00:00Z", new DateTime(1318248000L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T12:00:00+11:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T12:00:00-00:00", new DateTime(1318248000L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T01:00:00-00:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T01:00-00:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T01:00:00-07:00", new DateTime(1318233600L * 1000L, DateTimeZone.UTC)},
                {"2011-10-09T18:00:00-07:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T01:00:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10T01:00", new DateTime(1318208400L * 1000L, DateTimeZone.UTC)},
                {"2011-10-10", new DateTime(1318204800L * 1000L, DateTimeZone.UTC)},
                {"2011-10", new DateTime(1317427200L * 1000L, DateTimeZone.UTC)}
        };
    }

    @Test
    @UseDataProvider("validDateStrings")
    public void testDeserializationOfValidDateString(String dateString, DateTime dateObject) throws ParseException, IOException {
        DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
        assertEquals(0, dateTimeComparator.compare(dateObject, bytesToObject(("\"" + dateString + "\"").getBytes("UTF-8"), DateTime.class)));
    }

    @DataProvider
    public static Object[][] invalidDates() {
        return new Object[][]{
                {""},
                {"-"},
                {"201"},
                {"Hello!"}
        };
    }

    @Test(expected = IllegalArgumentException.class)
    @UseDataProvider("invalidDates")
    public void testDeserializationOfInvalidDates(String dateString) throws ParseException, IOException {
        bytesToObject(("\"" + dateString + "\"").getBytes("UTF-8"), DateTime.class);
    }
}

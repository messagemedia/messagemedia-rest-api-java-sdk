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
package com.messagemedia.restapi.client.v1.messaging.deliveryreports;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.messagemedia.restapi.client.v1.internal.util.JsonUtilities;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageStatus;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the {@link DeliveryReportList} class.
 */
@RunWith(DataProviderRunner.class)
public class DeliveryReportListTest {

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains the following parameters:
     * - a JSON string
     * - an expected DeliveryReportList object
     */
    @DataProvider
    public static Object[][] validJson() {
        return new Object[][]{
                // A DeliveryReportList that has a 'delivery_reports' attribute, but no delivery reports, should be accepted
                {"{ \"delivery_reports\": [] }", new DeliveryReportList(Collections.<DeliveryReport>emptyList())},
                // A DeliveryReportList containing a single delivery report should be accepted
                {"{ \"delivery_reports\": [" +
                        " { \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" } ] }",
                        new DeliveryReportList(Collections.singletonList(new DeliveryReport(
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),       // Date received
                                4321,                                    // Delay
                                "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Delivery Report ID
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                "892399291",                             // Source number (handset)
                                MessageStatus.DELIVERED,                 // Status
                                null                                     // Metadata
                        )))
                },
                // A DeliveryReportList containing a more than one delivery report should be accepted
                {"{ \"delivery_reports\": [" +
                        " { \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }, " +
                        " { \"message_id\" : \"fab3bbad-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"46bd46bd-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"1038838833\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"enroute\", " +
                        "   \"delay\": \"100\" } ] }",
                        new DeliveryReportList(Arrays.asList(
                                new DeliveryReport(
                                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),        // Date received
                                        4321,                                                       // Delay
                                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",                     // Delivery Report ID
                                        "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",                     // Message ID
                                        "892399291",                                                // Source number (handset)
                                        MessageStatus.DELIVERED,                                    // Status
                                        null                                                        // Metadata
                                ), new DeliveryReport(
                                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),        // Date received
                                        100,                                                        // Delay
                                        "46bd46bd-6fb6-46bd-ab94-a9e123031b3f",                     // Delivery Report ID
                                        "fab3bbad-bbc5-4f57-94b8-9fbeb0af66cd",                     // Message ID
                                        "1038838833",                                               // Source number (handset)
                                        MessageStatus.ENROUTE,                                      // Status
                                        null                                                        // Metadata
                                )))
                },

                // A DeliveryReportList containing additional properties should be
                // accepted
                {"{\"delivery_reports\": [],\"not_supposed_to_be_here\": \"should be ignored\" }",
                        new DeliveryReportList(Collections.<DeliveryReport>emptyList())
                }
        };


    }

    /**
     * Test that a DeliveryReportList can be de-serialised from JSON format.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String deliveryReportListJson, DeliveryReportList expectedDeliveryReportList) throws IOException {
        assertEquals(expectedDeliveryReportList, JsonUtilities.bytesToObject(deliveryReportListJson.getBytes("UTF-8"), DeliveryReportList.class));
    }

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains an invalid JSON string.
     */
    @DataProvider
    public static Object[][] invalidJson() {
        return new Object[][]{
                // Missing 'delivery_reports' property should trigger an exception
                {"{ }"},
                // Wrong type for 'delivery_reports' property should trigger an exception
                {"{ \"delivery_reports\": {} }"},
                // A DeliveryReportList containing an invalid delivery report should trigger an exception (missing 'delivery_report_id' property)
                {"{ \"delivery_reports\": [" +
                        " { \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" } ] }"
                },
        };
    }

    /**
     * Test that DeliveryReportList objects cannot be de-serialised from invalid JSON.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test(expected = JsonMappingException.class)
    @UseDataProvider("invalidJson")
    public void testDeserialisationOfInvalidJson(String deliveryReportListJson) throws IOException {
        JsonUtilities.bytesToObject(deliveryReportListJson.getBytes("UTF-8"), DeliveryReportList.class);
    }
}

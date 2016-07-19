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
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the {@link DeliveryReport} class.
 */
@RunWith(DataProviderRunner.class)
public class DeliveryReportTest {

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains the following parameters:
     * - a JSON string
     * - an expected DeliveryReport object
     */
    @DataProvider
    public static Object[][] validJson() {
        return new Object[][]{
                // A normal delivery report should be accepted
                {"{\"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\", " +
                        "   \"metadata\": {\"key\": \"value\"}" +
                        "}",
                        new DeliveryReport(
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                                4321,                                    // Delay
                                "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Delivery Report ID
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                "892399291",                             // Source number (handset)
                                MessageStatus.DELIVERED,                 // Status
                                Collections.singletonMap("key", "value") // Metadata
                        )
                },
                // A delivery report containing additional properties should be accepted
                {"{\"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }",
                        new DeliveryReport(
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                                4321,                                    // Delay
                                "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Delivery Report ID
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                "892399291",                             // Source number (handset)
                                MessageStatus.DELIVERED,                 // Status
                                null                                     // Metadata
                        )
                },
                // A delivery report without a 'delay' attribute should be accepted
                {"{\"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"metadata\": {} }",
                        new DeliveryReport(
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                                null,                                    // Delay
                                "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Delivery Report ID
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                "892399291",                             // Source number (handset)
                                MessageStatus.DELIVERED,                 // Status
                                Collections.<String, String>emptyMap()   // Metadata
                        )
                }

        };
    }

    /**
     * Test that a DeliveryReport can be de-serialized from JSON format.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String deliveryReportJson, DeliveryReport expectedDeliveryReport) throws IOException {
        assertEquals(expectedDeliveryReport, JsonUtilities.bytesToObject(deliveryReportJson.getBytes("UTF-8"), DeliveryReport.class));
    }

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains an invalid JSON string.
     */
    @DataProvider
    public static Object[][] invalidJson() {
        return new Object[][]{
                // Missing 'delivery_report_id' property should trigger an exception
                {"{\"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }"
                },
                // Missing 'message_id' property should trigger an exception
                {"{\"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }"
                },
                // Missing 'source_number' property should trigger an exception
                {"{\"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }"
                },
                // Missing 'status' property should trigger an exception
                {"{\"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\", " +
                        "   \"delay\": \"4321\" }"
                },
                // Missing 'date_received' property should trigger an exception
                {"{\"delivery_report_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"status\": \"delivered\", " +
                        "   \"delay\": \"4321\" }"
                },
        };
    }

    /**
     * Test that DeliveryReport objects cannot be de-serialized from invalid JSON.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test(expected = JsonMappingException.class)
    @UseDataProvider("invalidJson")
    public void testDeserialisationOfInvalidJson(String deliveryReportJson) throws IOException {
        JsonUtilities.bytesToObject(deliveryReportJson.getBytes(), DeliveryReport.class);
    }
}

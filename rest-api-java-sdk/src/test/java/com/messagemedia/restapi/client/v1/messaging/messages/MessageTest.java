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
package com.messagemedia.restapi.client.v1.messaging.messages;

import com.messagemedia.restapi.client.v1.internal.util.JsonUtilities;
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
 * Tests for the {@link Message} class.
 */
@RunWith(DataProviderRunner.class)
public class MessageTest {

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains the following parameters:
     * - a JSON string
     * - an expected Message object, created using a constructor that is accessible to Jackson
     */
    @DataProvider
    public static Object[][] validJson() {
        return new Object[][]{
                // A normal message, with the minimum required properties, should be accepted
                {"{\"content\" : \"Hello\", " +
                        " \"destination_number\": \"938918911\", " +
                        " \"message_id\": \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        " \"status\": \"queued\" }",
                        new Message(
                                null,                                    // Callback URL
                                "Hello",                                 // Content
                                null,                                    // Delivery report
                                "938918911",                             // Destination number
                                null,                                    // Format
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                null,                                    // Scheduled date/time
                                null,                                    // Source number
                                null,                                    // Source number type
                                MessageStatus.QUEUED,                    // Status
                                null,                                    // Status reason
                                null,                                    // Message expiry timestamp
                                null)                                    // Metadata
                },

                // A normal message, with all available properties, should be accepted
                {"{\"callback_url\": \"http://testurl\", " +
                        " \"content\" : \"Hello\", " +
                        " \"delivery_report\": true, " +
                        " \"destination_number\": \"938918911\", " +
                        " \"format\": \"SMS\", " +
                        " \"message_id\": \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        " \"scheduled\": \"2011-10-10T12:00:00+11:00\", " +
                        " \"source_number\": \"38918911\", " +
                        " \"source_number_type\": \"SHORTCODE\", " +
                        " \"status\": \"queued\", " +
                        " \"status_reason\": \"Nothing to see here\", " +
                        " \"metadata\": {\"param\":\"value\" }, " +
                        " \"message_expiry_timestamp\": \"2011-10-10T12:00:00+11:00\" }",
                        new Message(
                                "http://testurl",                                       // Callback URL
                                "Hello",                                                // Content
                                true,                                                   // Delivery report
                                "938918911",                                            // Destination number
                                MessageFormat.SMS,                                      // Format
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",                 // Message ID
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),    // Scheduled date/time
                                "38918911",                                             // Source number
                                AddressType.SHORTCODE,                                             // Source number type
                                MessageStatus.QUEUED,                                   // Status
                                "Nothing to see here",                                  // Status reason
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),    // Message expiry timestamp
                                Collections.singletonMap("param", "value"))             // Metadata
                }
        };
    }

    /**
     * Test that a Message can be de-serialized from JSON format.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String messageJson, Message expectedMessage) throws IOException {
        assertEquals(expectedMessage, JsonUtilities.bytesToObject(messageJson.getBytes("UTF-8"), Message.class));
    }

}

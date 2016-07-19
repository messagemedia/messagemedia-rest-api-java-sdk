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
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the {@link MessageList} class.
 */
@RunWith(DataProviderRunner.class)
public class MessageListTest {

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains the following parameters:
     * - a JSON string
     * - an expected MessageList object, created using a constructor that is accessible to Jackson
     */
    @DataProvider
    public static Object[][] validJson() {
        return new Object[][]{
                // Ensure that, when a message list is deserialised from JSON, a single message is accepted
                {"{\"messages\": [ { " +
                        "       \"content\": \"Hello\", " +
                        "       \"destination_number\": \"938918911\", " +
                        "       \"message_id\": \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "       \"status\": \"queued\" } ]}",
                        new MessageList(
                                Collections.singletonList(new Message(
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
                                        null)))                                  // Metadata
                },
                // Ensure that unrecognised properties are ignored
                {"{\"unrecognised_property\": \"should be ignored\", " +
                        " \"messages\": [ { " +
                        "       \"content\": \"Hello\", " +
                        "       \"destination_number\": \"938918911\", " +
                        "       \"message_id\": \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "       \"status\": \"queued\" } ]} ",
                        new MessageList(
                                Collections.singletonList(new Message(
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
                                        null)))                                  // Metadata
                },
        };
    }

    /**
     * Test that a MessageList can be de-serialized from JSON format.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String messageListJson, MessageList expectedMessageList) throws IOException {
        assertEquals(expectedMessageList, JsonUtilities.bytesToObject(messageListJson.getBytes("UTF-8"), MessageList.class));
    }
}

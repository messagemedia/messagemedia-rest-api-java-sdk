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
package com.messagemedia.restapi.client.v1.messaging.replies;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.messagemedia.restapi.client.v1.internal.util.JsonUtilities;
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
 * Tests for the {@link ReplyList} class.
 */
@RunWith(DataProviderRunner.class)
public class ReplyListTest {

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains the following parameters:
     * - a JSON string
     * - an expected ReplyList object
     */
    @DataProvider
    public static Object[][] validJson() {
        return new Object[][]{
                // A DeliveryReportList that has a 'replies' property, but no replies should be accepted
                {"{\"replies\": [] }", new ReplyList(Collections.<Reply>emptyList())},
                // A ReplyList that has a 'replies' properties, but no replies, should be accepted even if the document contains unknown properties
                {"{\"replies\": [], \"not_supposed_to_be_here\": \"should be ignored\" }", new ReplyList(Collections.<Reply>emptyList())},
                // A ReplyList containing a normal reply should be accepted
                {"{ \"replies\": [" +
                        " { \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" } ] }",
                        new ReplyList(Collections.singletonList(new Reply(
                                "Hi there",                              // Content
                                new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                                "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                                "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Reply ID
                                "892399291",                             // Source number (handset)
                                "892399222",                             // Destination number (modem)
                                null                                     // Metadata
                        )))
                },
                // A ReplyList containing more than one normal reply should be accepted
                {"{ \"replies\": [" +
                        " { \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }, " +
                        " { \"message_id\" : \"28982bac-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"4377de9f-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there 2\", " +
                        "   \"source_number\" : \"28787919001\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" } ] }",
                        new ReplyList(Arrays.asList(
                                new Reply(
                                        "Hi there",                                             // Content
                                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),    // Date received
                                        "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",                 // Message ID
                                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",                 // Reply ID
                                        "892399291",                                            // Source number (handset)
                                        "892399222",                                            // Destination number (modem)
                                        null                                                    // Metadata
                                ), new Reply(
                                        "Hi there 2",                                           // Content
                                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),    // Date received
                                        "28982bac-bbc5-4f57-94b8-9fbeb0af66cd",                 // Message ID
                                        "4377de9f-6fb6-46bd-ab94-a9e123031b3f",                 // Reply ID
                                        "28787919001",                                          // Source number (handset)
                                        "892399222",                                            // Destination number (modem)
                                        null                                                    // Metadata
                                )))
                },
        };
    }

    /**
     * Test that a ReplyList can be de-serialised from JSON format.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String replyListJson, ReplyList expectedReplyList) throws IOException {
        assertEquals(expectedReplyList, JsonUtilities.bytesToObject(replyListJson.getBytes("UTF-8"), ReplyList.class));
    }

    /**
     * Data provider that returns an array of object arrays, each of which represents a test case.
     * <p/>
     * Each test case contains an invalid JSON string.
     */
    @DataProvider
    public static Object[][] invalidJson() {
        return new Object[][]{
                // A ReplyList that has a 'replies' property that is not an array should trigger an exception
                {"{ \"replies\": {} }"}
        };
    }

    /**
     * Test that ReplyList objects cannot be de-serialised from invalid JSON.
     * <p/>
     * This test has been parameterised so that the JSON de-serialisation functionality can be tested with a wide range of inputs.
     */
    @Test(expected = JsonMappingException.class)
    @UseDataProvider("invalidJson")
    public void testDeserialisationOfInvalidJson(String replyListJson) throws IOException {
        JsonUtilities.bytesToObject(replyListJson.getBytes("UTF-8"), ReplyList.class);
    }
}

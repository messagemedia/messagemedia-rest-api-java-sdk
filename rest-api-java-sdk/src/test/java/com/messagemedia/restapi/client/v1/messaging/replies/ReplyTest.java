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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the {@link Reply} class.
 */
@RunWith(DataProviderRunner.class)
public class ReplyTest {

    /**
     * Data provider that returns an array of object arrays, each of which
     * represents a test case.
     *
     * Each test case contains the following parameters:
     *    - a JSON string
     *    - an expected Reply object
     */
    @DataProvider
    public static Object[][] validJson() {
        List<Object[]> testCases = new ArrayList<Object[]>();

        // @formatter:off

        // A normal reply should be accepted
        testCases.add(new Object[] {
                "{          \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"metadata\": {\"key\": \"value\"}, " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }",
                new Reply(
                        "Hi there",                              // Content
                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                        "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Reply ID
                        "892399291",                             // Source number (handset)
                        "892399222",                             // Destination number (modem)
                        Collections.singletonMap("key", "value") // Metadata
                )
        });

        // A reply containing additional attributes should be accepted
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }",
                new Reply(
                        "Hi there",                              // Content
                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                        "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Reply ID
                        "892399291",                             // Source number (handset)
                        "892399222",                             // Destination number (modem)
                        null                                     // Metadata
                )
        });


        // Missing 'message_id' field is ok (unsolicited MOs!)
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"metadata\" : {}, " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }",
                new Reply(
                        "Hi there",                              // Content
                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                        null,                                    // Message ID
                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Reply ID
                        "892399291",                             // Source number (handset)
                        "892399222",                             // Destination number (modem),
                        Collections.<String, String>emptyMap()   // Metadata
                )
        });
        
        // Missing 'destination_number' field is ok (gateway could not match the sending number)
        testCases.add(new Object[] {
                "{          \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }",
                new Reply(
                        "Hi there",                              // Content
                        new DateTime(1318208400L * 1000L, DateTimeZone.UTC),           // Date received
                        "4377de9f-bbc5-4f57-94b8-9fbeb0af66cd",  // Message ID
                        "ff532fee-6fb6-46bd-ab94-a9e123031b3f",  // Reply ID
                        "892399291",                             // Source number (handset)
                        null,                                    // Destination number (modem)
                        null                                     // Metadata
                )
        });
        
        // @formatter:on

        return testCases.toArray(new Object[testCases.size()][]);
    }

    /**
     * Test that a Reply can be de-serialised from JSON format.
     *
     * This test has been parameterised so that the JSON de-serialisation
     * functionality can be tested with a wide range of inputs.
     */
    @Test
    @UseDataProvider("validJson")
    public void testDeserialisationOfValidJson(String replyJson,
            Reply expectedReply) throws IOException {
        assertEquals(expectedReply, JsonUtilities.bytesToObject(
                replyJson.getBytes(), Reply.class));
    }

    /**
     * Data provider that returns an array of object arrays, each of which
     * represents a test case.
     *
     * Each test case contains an invalid JSON string.
     */
    @DataProvider
    public static Object[][] invalidJson() {
        List<Object[]> testCases = new ArrayList<Object[]>();

        // @formatter:off

        // Missing 'content' field should trigger an exception
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }"
        });

        // Missing 'reply_id' field should trigger an exception
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }"
        });

        // Missing 'source_number' field should trigger an exception
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"date_received\": \"2011-10-10T12:00:00+11:00\" }"
        });

        // Missing 'date_received' field should trigger an exception
        testCases.add(new Object[] {
                "{          \"not_supposed_to_be_here\" : \"should be ignored\", " +
                        "   \"message_id\" : \"4377de9f-bbc5-4f57-94b8-9fbeb0af66cd\", " +
                        "   \"reply_id\" : \"ff532fee-6fb6-46bd-ab94-a9e123031b3f\", " +
                        "   \"content\": \"Hi there\", " +
                        "   \"source_number\" : \"892399291\", " +
                        "   \"destination_number\" : \"892399222\" }"
        });

        // @formatter:on

        return testCases.toArray(new Object[testCases.size()][]);
    }

    /**
     * Test that Reply objects cannot be de-serialised from invalid JSON.
     *
     * This test has been parameterised so that the JSON de-serialisation
     * functionality can be tested with a wide range of inputs.
     */
    @Test(expected = JsonMappingException.class)
    @UseDataProvider("invalidJson")
    public void testDeserialisationOfInvalidJson(String replyJson)
            throws IOException {
        JsonUtilities.bytesToObject(replyJson.getBytes("UTF-8"), Reply.class);
    }
}

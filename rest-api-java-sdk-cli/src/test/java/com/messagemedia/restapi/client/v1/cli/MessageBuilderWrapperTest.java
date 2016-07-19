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

package com.messagemedia.restapi.client.v1.cli;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageBuilder;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
public class MessageBuilderWrapperTest {

    private static final String[] MANDATORY_PARAMETERS = new String[]{"--destinationNumber", "+1111111111", "--content", "test"};

    @Test(expected = ArgumentValidationException.class)
    @UseDataProvider("invalidParametersForExpiryAndScheduling")
    public void shouldNotAllowAnyCharactersThanDigitsWhenGivingArgumentsForMessageExpiryAndScheduledTime(String[] arguments) throws Exception {
        String[] argumentsArray = mergeArguments(arguments);
        CliFactory.parseArgumentsUsingInstance(new SendMessage.MessageBuilderWrapper(), argumentsArray);
    }

    @DataProvider
    public static Object[][] invalidParametersForExpiryAndScheduling() {
        return new Object[][]{
                {new String[]{"--messageExpiryTimestamp", "+123142343", "--scheduled", "+988070987943"}},
                {new String[]{"--messageExpiryTimestamp", "123142343", "--scheduled", "-988070987943"}},
                {new String[]{"--messageExpiryTimestamp", "$123142343", "--scheduled", "$988070987943"}}
        };
    }

    @Test
    @UseDataProvider("validParameters")
    public void checkValidParameters(String[] arguments, long expectedMessageExpiry, long expectedScheduledTime, String expectedCallbackUrl,
                                     boolean drRequested) throws Exception {
        String[] argumentsArray = mergeArguments(arguments);

        MessageBuilder mb = CliFactory.parseArgumentsUsingInstance(new SendMessage.MessageBuilderWrapper(), argumentsArray).getMessageBuilder();
        Message message = mb.build();

        assertNotNull(message.getMessageExpiryTimestamp());
        assertThat(message.getMessageExpiryTimestamp().getMillis(), is(expectedMessageExpiry));
        assertNotNull(message.getScheduled());
        assertThat(message.getScheduled().getMillis(), is(expectedScheduledTime));
        assertThat(message.getCallbackUrl(), is(expectedCallbackUrl));
        assertThat(message.isDeliveryReportFlagSet(), is(drRequested));
    }

    @DataProvider
    public static Object[][] validParameters() {
        return new Object[][]{
                {new String[]{"--messageExpiryTimestamp", "123142343", "--scheduled", "988070987943",
                        "--callbackUrl", "http://testing", "--deliveryReport", "true"},
                        123142343L, 988070987943L, "http://testing", true},
                {new String[]{"--messageExpiryTimestamp", "123142343", "--scheduled", "988070987943",
                        "--callbackUrl", "http://testing", "--deliveryReport", "false"},
                        123142343L, 988070987943L, "http://testing", false}
        };
    }

    private String[] mergeArguments(String[] arguments) {
        List<String> args = new ArrayList<String>(MANDATORY_PARAMETERS.length + arguments.length);
        Collections.addAll(args, MANDATORY_PARAMETERS);
        Collections.addAll(args, arguments);

        return args.toArray(new String[args.size()]);
    }
}

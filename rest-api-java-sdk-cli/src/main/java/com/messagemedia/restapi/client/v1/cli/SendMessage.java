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
import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.ValidationFailure;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiResponse;
import com.messagemedia.restapi.client.v1.messaging.messages.AddressType;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageBuilder;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * A short example demonstrating how to send a single message.
 */
public class SendMessage extends AbstractAction {

    /**
     * This class converts the parameters into a message builder
     */
    protected static class MessageBuilderWrapper {
        private final MessageBuilder mb = new MessageBuilder();

        @Option(description = "The content of the message")
        void setContent(String content) {
            mb.content(content);
        }

        @Option(description = "Request a delivery report for the message", defaultValue = {"true"})
        void setDeliveryReport(String dr) {
            mb.deliveryReport(Boolean.parseBoolean(dr));
        }

        @Option(description = "The destination number to send to")
        void setDestinationNumber(String s) {
            mb.destinationNumber(s);
        }

        @Option(description = "The callback URL for any Replies or Delivery Reports", defaultToNull = true)
        void setCallbackUrl(String s) {
            mb.callbackUrl(s);
        }

        @Option(description = "The source address to send from", defaultToNull = true)
        void setSourceNumber(String s) {
            mb.sourceNumber(s);
        }

        @Option(description = "The type of the source address", defaultToNull = true)
        void setSourceNumberType(String s) {
            mb.sourceNumberType(AddressType.valueOf(s));
        }

        @Option(description = "The format of the message, e.g., SMS or TTS.", defaultToNull = true)
        void setFormat(String s) {
            mb.format(MessageFormat.forValue(s));
        }

        @Option(description = "The time to schedule the message for, in epoch.", defaultToNull = true, pattern = "[0-9]+")
        void setScheduled(long l) {
            mb.scheduled(new DateTime(l, DateTimeZone.UTC));
        }

        @Option(description = "The time to expire the message at, in epoch", defaultToNull = true, pattern = "[0-9]+")
        void setMessageExpiryTimestamp(long l) {
            mb.messageExpiryTimestamp(new DateTime(l, DateTimeZone.UTC));
        }

        @Option(helpRequest = true)
        void help(boolean x) {
            // ugly, but this enables --help
        }

        MessageBuilder getMessageBuilder() {
            return mb;
        }
    }

    @Override
    public void execute(String[] args) throws RestApiException {
        MessageBuilder messageBuilder = messageBuilder(args);
        try {
            final RestApiResponse<Message> response = Settings.CLIENT.messaging().sendMessage(messageBuilder.build());
            handleSuccess(response);
        } catch (RestApiException e) {
            logException(e);
        }

    }

    @Override
    protected String getActionDescription() {
        return "sending message(s)";
    }

    private void handleSuccess(RestApiResponse<Message> response) {
        Message message = response.getPayload();
        System.out.println("Message ID: " + message.getMessageId());
        System.out.println("  Callback URL: " + (message.getCallbackUrl() == null ? "not specified" : message.getCallbackUrl()));
        System.out.println("  Content: " + message.getContent());
        System.out.println("  Delivery report requested: " + message.isDeliveryReportFlagSet());
        System.out.println("  Destination number: " + message.getDestinationNumber());
        System.out.println("  Format: " + message.getFormat());
        System.out.println("  Scheduled date/time: " + (message.getScheduled() == null ? "not specified" : message.getScheduled()));
        System.out.println("  Source number: " + (message.getSourceNumber() == null ? "not specified" : message.getSourceNumber()));
        System.out.println("  Status: " + message.getStatus());
        System.out.println("  Status reason: " + (message.getStatusReason() == null ? "N/A" : message.getStatusReason()));
        System.out.println("  Message expiry date/time: " +
                                   (message.getMessageExpiryTimestamp() == null ? "not specified" : message.getMessageExpiryTimestamp()));
    }

    private MessageBuilder messageBuilder(String[] args) {
        MessageBuilder messageBuilder = null;

        try {
            messageBuilder = CliFactory.parseArgumentsUsingInstance(new MessageBuilderWrapper(), args).mb;
        } catch (ArgumentValidationException e) {
            for (ValidationFailure each : e.getValidationFailures()) {
                System.out.println(each.getMessage());
            }
            System.exit(1);
        }

        return messageBuilder;
    }
}

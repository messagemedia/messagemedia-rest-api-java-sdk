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
package com.messagemedia.restapi.client.v1.examples;

import com.messagemedia.restapi.client.v1.*;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageBuilder;

/**
 * A short example demonstrating how to send a single message.
 */
public class SendMessage {

    public static void main(String[] args) throws RestApiException {

        // put in your credentials here
        final String apiKey = "<your api key>";
        final String secretKey = "<your secret key>";

        // put in your number here
        String destinationNumber = "+61491570156";

        final RestApiClient restApiClient = RestApiClientBuilder.newBuilder(apiKey, secretKey).build();

        final MessageBuilder messageBuilder = new MessageBuilder().content("Yes, it works!")
                                                                  .destinationNumber(destinationNumber)
                                                                  .deliveryReport(true);
        try {
            final RestApiResponse<Message> response = restApiClient.messaging().sendMessage(messageBuilder.build());

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
        } catch (RestApiException exception) {
            System.err.println("Error confirming message(s). " + exception.getMessage());
            if (exception instanceof RestApiClientException) {
                System.err.println("Details: ");
                for (String item : ((RestApiClientException) exception).getDetails()) {
                    System.err.println("  " + item);
                }
            }
        }
    }
}

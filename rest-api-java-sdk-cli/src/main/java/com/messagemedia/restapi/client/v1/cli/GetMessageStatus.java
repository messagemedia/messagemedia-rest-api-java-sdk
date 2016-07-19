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

import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiResponse;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;

/**
 * A short example demonstrating how to check the current status of a message.
 */
public class GetMessageStatus extends AbstractAction {

    public void execute(String[] args) throws RestApiException {
        if (args.length != 1) {
            System.out.println("Please provide exactly one message ID as a command line argument.");
            System.exit(1);
        }
        try {
            final RestApiResponse<Message> response = Settings.CLIENT.messaging().getMessage(args[0]);
            handleSuccess(response);
        } catch (RestApiException e) {
            logException(e);
        }
    }

    private static void handleSuccess(RestApiResponse<Message> response) {
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
    }

    @Override
    protected String getActionDescription() {
        return "getting message status";
    }
}

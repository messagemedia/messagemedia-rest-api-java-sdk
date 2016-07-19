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

package com.messagemedia.restapi.client.v1.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.messagemedia.restapi.client.v1.*;
import com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient;
import com.messagemedia.restapi.client.v1.messaging.deliveryreports.DeliveryReportList;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageList;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageListProperties;
import com.messagemedia.restapi.client.v1.messaging.replies.ReplyList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RestApiMessagingClientImpl implements RestApiMessagingClient {

    private static final String VERSION = "/v1";

    private static final String URL_REPLIES = VERSION + "/replies";
    private static final String URL_CONFIRM_REPLIES = VERSION + "/replies/confirmed";
    private static final String URL_DELIVERY_REPORTS = VERSION + "/delivery_reports";
    private static final String URL_CONFIRM_DELIVERY_REPORT = VERSION + "/delivery_reports/confirmed";
    private static final String URL_CHECK_MESSAGE = VERSION + "/messages/{messageId}";
    private static final String URL_MESSAGES = VERSION + "/messages";
    private static final String CANCEL_MESSAGE_PAYLOAD = "{\"status\":\"CANCELLED\"}";
    private static final int RESULT_CODE_CONVERSION = 100;
    private static final int CLIENT_ERROR = 4;
    private static final int SERVER_ERROR = 5;

    private final RestClient client;

    public RestApiMessagingClientImpl(RestClient client) {
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<Message> sendMessage(Message message) throws RestApiException {
        return sendMessage(message, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<Message> sendMessage(Message message, MessageListProperties messageListProperties) throws RestApiException {

        RestApiResponse<MessageList> response = sendMessages(Collections.singletonList(message), messageListProperties);
        List<Message> messages = response.getPayload().getMessages();
        if (messages.size() == 1) {
            return RestApiResponseFactory.success(response.getResponseCode(), messages.get(0));
        } else {
            throw new RestApiException("Received " + messages.size() + " messages in the body response. Should not happen.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<MessageList> sendMessages(List<Message> messages) throws RestApiException {
        return sendMessages(messages, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<MessageList> sendMessages(List<Message> messages, MessageListProperties messageListProperties) throws RestApiException {
        try {
            RestResponse response = client.post(URL_MESSAGES).body(new MessageList(messages, messageListProperties)).execute();
            return parseResponse(response, MessageList.class);
        } catch (JsonProcessingException e) {
            throw new RestApiException("Exception trying to serialize the body of the message", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<Void> confirmDeliveryReports(Collection<String> deliveryReportIds) throws RestApiException {
        try {
            DeliveryReportConfirmation deliveryReportConfirmation = new DeliveryReportConfirmation(deliveryReportIds);

            return parseResponse(client.post(URL_CONFIRM_DELIVERY_REPORT).body(deliveryReportConfirmation).execute(), Void.class);
        } catch (JsonProcessingException e) {
            throw new RestApiException("Failed to convert the deliveryReportIdList to JSON", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<DeliveryReportList> checkDeliveryReports() throws RestApiException {
        return parseResponse(client.get(URL_DELIVERY_REPORTS).execute(), DeliveryReportList.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<Void> confirmReplies(Collection<String> replyIds) throws RestApiException {
        try {
            ReplyConfirmation replyConfirmation = new ReplyConfirmation(replyIds);

            return parseResponse(client.post(URL_CONFIRM_REPLIES).body(replyConfirmation).execute(), Void.class);
        } catch (JsonProcessingException e) {
            throw new RestApiException("Failed to convert the replyIdList to JSON", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<ReplyList> checkReplies() throws RestApiException {
        return parseResponse(client.get(URL_REPLIES).execute(), ReplyList.class);
    }

    private <T> RestApiResponse<T> parseResponse(RestResponse response, Class<T> clazz) throws RestApiException {
        int resultCode = response.getResultCode();
        if (response.isSuccessful()) {
            final T result = response.getResponseAs(clazz);
            return RestApiResponseFactory.success(resultCode, result);
        } else {
            // This will convert 400, 404, 403 to 4, 500, 501, 503 etc to 5.
            int codeFamily = resultCode / RESULT_CODE_CONVERSION;
            switch (codeFamily) {
                case CLIENT_ERROR:
                    // Client Error
                    ErrorResponse error = response.getResponseAs(DefaultErrorResponse.class);
                    if (error != null) {
                        throw new RestApiClientException(error.getReason(), resultCode, error.getDetails());
                    } else {
                        throw new RestApiClientException("HTTP status code " + resultCode, resultCode);
                    }
                case SERVER_ERROR:
                    // Server Error
                    throw new RestApiServerException("HTTP status code " + resultCode, resultCode);
                default:
                    throw new RestApiHttpStatusCodeException("HTTP status code " + resultCode, resultCode);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse<Message> getMessage(String messageId) throws RestApiException {
        return parseResponse(client.get(URL_CHECK_MESSAGE).pathVariable("messageId", messageId).execute(), Message.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiResponse cancelMessage(String messageId) throws RestApiException {
        return parseResponse(
                client.put(URL_CHECK_MESSAGE).pathVariable("messageId", messageId).body(CANCEL_MESSAGE_PAYLOAD).execute(), Message.class);
    }
}

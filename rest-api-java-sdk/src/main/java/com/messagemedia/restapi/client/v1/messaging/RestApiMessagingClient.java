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

package com.messagemedia.restapi.client.v1.messaging;

import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiResponse;
import com.messagemedia.restapi.client.v1.messaging.deliveryreports.DeliveryReportList;
import com.messagemedia.restapi.client.v1.messaging.messages.Message;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageList;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageListProperties;
import com.messagemedia.restapi.client.v1.messaging.replies.ReplyList;

import java.util.Collection;
import java.util.List;

/**
 * MessageMedia REST API Messaging Client
 * <p/>
 * Client interface to call the messaging features of the REST API services.
 * <p/>
 * Instances of this client can be accessed from the {@link com.messagemedia.restapi.client.v1.RestApiClient}
 */
public interface RestApiMessagingClient {

    /**
     * Submits a single message to be sent.
     * <p/>
     * This method sends a message using the default message list properties.
     *
     * @param message The message you want to send.
     * @return The response of the call. If successful, it will contain a representation of the submitted message showing its current status.
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Message> sendMessage(Message message) throws RestApiException;

    /**
     * Send a single message, with additional properties.
     *
     * @param message               The message to send
     * @param messageListProperties Additional properties to be applied
     * @return If the request is successful, this method returns a representation of the message that was sent, otherwise a message containing an
     * appropriate status and error message will be returned.
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Message> sendMessage(Message message, MessageListProperties messageListProperties) throws RestApiException;

    /**
     * Send a batch of messages.
     * <p/>
     * This method sends messages using the default message list properties.
     *
     * @param messages The list of the messages to send
     * @return The response of the call. If successful, it will contain a representation of the submitted messages showing their current statuses.
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<MessageList> sendMessages(List<Message> messages) throws RestApiException;

    /**
     * Send a batch of messages.
     *
     * @param messages              The list of the messages to send
     * @param messageListProperties Additional properties to be applied
     * @return The response of the call. If successful, it will contain a representation of the submitted messages showing their current statuses.
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<MessageList> sendMessages(List<Message> messages, MessageListProperties messageListProperties) throws RestApiException;

    /**
     * Gets a message by Message ID. If no such message exists, this will return a response object containing the status code 404 NOT FOUND.
     *
     * @param messageId The Message ID of the message you want to find
     * @return The message, if it exists. null otherwise.
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Message> getMessage(String messageId) throws RestApiException;

    /**
     * Cancels a message by Message ID. If no such message exists, this will return a response object containing the status code 404 NOT FOUND.
     *
     * @param messageId The Message ID of the message you want to cancel
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Message> cancelMessage(String messageId) throws RestApiException;

    /**
     * Checks for unconfirmed replies. The response is limited to the 100 oldest replies currently held on the server. If there are more than 100
     * unconfirmed replies held, repeated calls to this method will always produce the same result. To receive new ones, you must first manually
     * confirm the old ones using {@link #confirmReplies}.
     * <p/>
     * If a callback URL is specified during message submission, replies are pushed to that URL, which removes the necessity to call this method
     * from time to time.
     * <p/>
     * <strong>Note: This feature is disabled by default. If you would like to use it, contact support to enable it.</strong>
     *
     * @return A list of unconfirmed replies
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<ReplyList> checkReplies() throws RestApiException;

    /**
     * Confirms a list of replies.
     * <p/>
     * This method should be called after checking replies with {@link #checkReplies()}. Calling this method is the only way to mark those replies as
     * "read" and start receiving new ones.
     * <p/>
     * <strong>Note: This feature is disabled by default. If you would like to use it, contact support to enable it.</strong>
     *
     * @param replyIds The list of IDs corresponding to the replies you want to confirm
     * @return The response which signals that the REST API accepted the confirmation request if {@link RestApiResponse#isOk()} returns true
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Void> confirmReplies(Collection<String> replyIds) throws RestApiException;

    /**
     * Checks for unconfirmed delivery reports. The response is limited to the 100 oldest delivery reports currently held on the server.
     * If there are more than 100 unconfirmed delivery reports held, repeated calls to this method will always produce the same result.
     * To receive new ones, you must first confirm the old ones using {@link #confirmDeliveryReports}.
     * <p/>
     * If a callback URL is specified during message submission, delivery reports are pushed to that URL, which removes the necessity to call this
     * method from time to time.
     * <p/>
     * <strong>Note: This feature is disabled by default. If you would like to use it, contact support to enable it.</strong>
     *
     * @return A list of unconfirmed delivery reports
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<DeliveryReportList> checkDeliveryReports() throws RestApiException;

    /**
     * Confirms a list of delivery reports.
     * <p/>
     * This method should be called after checking delivery reports with {@link #checkDeliveryReports()}. Calling this method is the only way to
     * mark those delivery reports as "read" and start receiving new ones.
     * <p/>
     * <strong>Note: This feature is disabled by default. If you would like to use it, contact support to enable it.</strong>
     *
     * @param deliveryReportIds The list of IDs corresponding to the delivery reports that you want to confirm
     * @return The response which signals that the REST API accepted the confirmation request if {@link RestApiResponse#isOk()} returns true
     * @throws RestApiException If an error in the client/server communication occurs.
     */
    RestApiResponse<Void> confirmDeliveryReports(Collection<String> deliveryReportIds) throws RestApiException;
}

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

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Possible status of a message.
 */
public enum MessageStatus {
    /**
     * The value is unknown. Please update your SDK.
     */
    UNKNOWN("unknown"),

    /**
     * Queued
     *
     * The message has been accepted and queued for future processing.
     *
     **/
    QUEUED("queued"),

    /**
     * Processing
     *
     * The message is currently being processed.
     */
    PROCESSING("processing"),

    /**
     * Processed
     *
     * The message has been processed successfully.
     *
     **/
    PROCESSED("processed"),

    /**
     * Scheduled
     *
     * The message has been scheduled.
     *
     **/
    SCHEDULED("scheduled"),

    /**
     * Cancelled
     *
     * The message has been cancelled.
     *
     **/
    CANCELLED("cancelled"),

    /**
     * Failed
     *
     * The message could not be processed. Message will not be sent. You need to retry.
     *
     **/
    FAILED("failed"),

    /**
     * Delivered
     *
     * The message has been delivered to the recipient.
     */
    DELIVERED("delivered"),

    /**
     * Expired
     *
     * The message has expired before being delivered to the recipient.
     * For example, if a {@link Message#messageExpiryTimestamp was provided.}
     */
    EXPIRED("expired"),

    /**
     * Enroute
     *
     * The message is enroute to the handset
     */
    ENROUTE("enroute"),

    /**
     * Held
     *
     * The message has been held and will require manual intervention.
     * Contact support for more information.
     */
    HELD("held"),

    /**
     * Submitted
     *
     * The message has been submitted for delivery. If no DR was requested, this is the final status.
     */
    SUBMITTED("submitted"),

    /**
     * Rejected
     *
     * The message will not be delivered, this could be because of usage limit exceeded, attempting to send to a blocked number.
     * Contact support for more information.
     */
    REJECTED("rejected");


    private final String value;

    MessageStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns the enum based on the string value.
     *
     * @param value - string representation
     * @return the corresponding enum element, UNKNOWN if none is found.
     */
    @JsonCreator
    public static MessageStatus forValue(String value) {
        for (MessageStatus each : values()) {
            if (each.value.equals(value)) {
                return each;
            }
        }
        return UNKNOWN;
    }
}

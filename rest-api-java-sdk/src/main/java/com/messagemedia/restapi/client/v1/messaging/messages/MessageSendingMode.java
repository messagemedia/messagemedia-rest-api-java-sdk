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
 * This attribute specifies a “send mode” to be used to send the messages.
 * Normally, this attribute should not be set, or be set to the default value of {@link #PRODUCTION}.
 */
public enum MessageSendingMode {
    /**
     * The value is unknown. Please update your SDK.
     */
    UNKNOWN("unknown"),

    /**
     * The production sending mode:
     *
     * Messages will be sent to the end user. This is the default one.
     **/
    PRODUCTION("production"),

    /**
     * Functional test delivered mode:
     *
     * Messages will be accepted and they will appear as sent but they will no be sent to the end device.
     * This mode is useful for testing without losing credits.
     *
     **/
    FUNCTIONAL_TEST_DELIVERED("functional_test_delivered"),

    /**
     * Functional test delivered failed mode:
     *
     * Messages will be rejected as if something went wrong on the server.
     * This mode is useful when you need to test your failover mechanism when a message is rejected by our API.
     *
     */
    FUNCTIONAL_TEST_DELIVERY_FAILED("functional_test_delivery_failed");

    private final String value;

    MessageSendingMode(String value) {
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
    public static MessageSendingMode forValue(String value) {
        for (MessageSendingMode each : values()) {
            if (each.value.equals(value)) {
                return each;
            }
        }
        return UNKNOWN;
    }
}

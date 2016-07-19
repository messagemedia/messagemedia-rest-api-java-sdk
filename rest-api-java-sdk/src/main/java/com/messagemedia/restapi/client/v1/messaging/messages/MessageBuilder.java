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


import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for the {@link Message} class.
 * <p/>
 * Instances of this class are NOT thread-safe.
 */
public final class MessageBuilder {

    private String callbackUrl;
    private String content;
    private boolean deliveryReport;
    private String destinationNumber;
    private String sourceNumber;
    private AddressType sourceNumberType;
    private MessageFormat format;
    private DateTime scheduled;
    private DateTime messageExpiryTimestamp;
    private Map<String, String> metadata = new HashMap<String, String>();

    /**
     * Construct a new MessageBuilder with the minimum information required to send a message
     *
     * @param content           Message content
     * @param destinationNumber Number of the destination handset
     * @return a new MessageBuilder
     */
    public static MessageBuilder newMessageBuilder(String content,
                                                   String destinationNumber) {
        return new MessageBuilder().content(content)
                                   .destinationNumber(destinationNumber);
    }

    /**
     * Construct a new Message using the properties set on this builder
     *
     * @return a new Message
     */
    public Message build() {
        return new Message(callbackUrl, content, deliveryReport,
                           destinationNumber, format, scheduled, sourceNumber, sourceNumberType, messageExpiryTimestamp, metadata);
    }

    /**
     * Sets the callback url.
     * <p/>
     * The callback url is the endpoint where the replies or delivery report must be sent when received.
     *
     * @param callbackUrl the new callback url
     */
    public MessageBuilder callbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    /**
     * This element specifies the content of the message. There is a hard limit of 5,000 characters on the size of the content.
     * SMS messages greater than 160 characters are split up into multiple SMS messages.
     * SMS concatenation is used so that they are delivered to the recipient as a single long message.
     *
     * @param content The new content
     */
    public MessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    /**
     * The delivery report specifies whether delivery reporting is requested for the message. If this attribute is not specified a default value of
     * <code>false</code> is assumed and no delivery reporting is requested. If this element has a value of <code>true</code>, a delivery report will
     * be requested for each message.
     *
     * @param deliveryReport <code>true</code> to request a delivery report for messages built with this builder.
     */
    public MessageBuilder deliveryReport(boolean deliveryReport) {
        this.deliveryReport = deliveryReport;
        return this;
    }

    /**
     * Sets the destination number.
     *
     * @param destinationNumber the new destination number
     */
    public MessageBuilder destinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
        return this;
    }

    /**
     * Sets the format of the message
     *
     * @param format the new format
     */
    public MessageBuilder format(MessageFormat format) {
        this.format = format;
        return this;
    }

    /**
     * Sets the scheduled date.
     * <p/>
     * This element may be used to schedule a message for future delivery.
     * <p/>
     * Messages that are scheduled for a date and time less than or equal to the current date and time will be sent immediately.
     *
     * @param scheduled The new scheduled date
     */
    public MessageBuilder scheduled(DateTime scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    /**
     * Sets the source number.
     *
     * @param sourceNumber The new source number
     */
    public MessageBuilder sourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
        return this;
    }

    /**
     * Sets the source number address type.
     *
     * @param sourceNumberType The address type
     */
    public MessageBuilder sourceNumberType(AddressType sourceNumberType) {
        this.sourceNumberType = sourceNumberType;
        return this;
    }

    /**
     * Sets the message expiry timestamp.
     * <p/>
     * This element may optionally be used to set a timestamp of when this message is due to expire.
     * Messages that have this set will not be sent after the specified date.
     *
     * @param messageExpiryTimestamp the new message expiry timestamp
     */
    public MessageBuilder messageExpiryTimestamp(DateTime messageExpiryTimestamp) {
        this.messageExpiryTimestamp = messageExpiryTimestamp;
        return this;
    }

    /**
     * Sets the metadata property
     *
     * @param name  The metadata property name
     * @param value The metadata property value
     */
    public MessageBuilder metadataProperty(String name, String value) {
        metadata.put(name, value);
        return this;
    }

    /**
     * Sets the metadata map directly.
     */
    public MessageBuilder metadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }
}

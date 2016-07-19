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
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Collections;
import java.util.Map;

/**
 * A representation of a message that can be submitted to the REST API.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class Message {
    private static final int MAX_CONTENT_LENGTH_IN_CHARS = 5000;

    private final String callbackUrl;
    private final String content;
    private final boolean deliveryReport;
    private final String destinationNumber;
    private final MessageFormat format;
    private final String messageId;
    private final DateTime scheduled;
    private final String sourceNumber;
    private final AddressType sourceNumberType;
    private final MessageStatus status;
    private final String statusReason;
    private final DateTime messageExpiryTimestamp;
    private final Map<String, String> metadata;

    Message(String callbackUrl, String content, Boolean deliveryReport, String destinationNumber, MessageFormat format, DateTime scheduled,
            String sourceNumber, AddressType sourceNumberType, DateTime messageExpiryTimestamp, Map<String, String> metadata) {

        this.content = validatedContent(content);
        this.destinationNumber = validatedDestinationNumber(destinationNumber);

        this.callbackUrl = callbackUrl;
        this.deliveryReport = deliveryReport == null ? false : deliveryReport;
        this.format = format == null ? MessageFormat.SMS : format;
        this.messageId = null;
        this.scheduled = scheduled == null ? null : new DateTime(scheduled.getMillis(), DateTimeZone.UTC);
        this.sourceNumber = sourceNumber;
        this.status = null;
        this.statusReason = null;
        this.messageExpiryTimestamp = messageExpiryTimestamp == null ? null : new DateTime(messageExpiryTimestamp.getMillis(), DateTimeZone.UTC);
        this.metadata = Collections.unmodifiableMap(metadata != null ? metadata : Collections.<String, String>emptyMap());
        this.sourceNumberType = sourceNumberType;
    }

    @JsonCreator
    Message(@JsonProperty(value = "callback_url", required = false) String callbackUrl,
            @JsonProperty(value = "content") String content,
            @JsonProperty(value = "delivery_report", required = false) Boolean deliveryReport,
            @JsonProperty(value = "destination_number") String destinationNumber,
            @JsonProperty(value = "format", required = false) MessageFormat format,
            @JsonProperty(value = "message_id") String messageId,
            @JsonProperty(value = "scheduled", required = false) DateTime scheduled,
            @JsonProperty(value = "source_number", required = false) String sourceNumber,
            @JsonProperty(value = "source_number_type", required = false) AddressType sourceNumberType,
            @JsonProperty(value = "status") MessageStatus status,
            @JsonProperty(value = "status_reason", required = false) String statusReason,
            @JsonProperty(value = "message_expiry_timestamp") DateTime messageExpiryTimestamp,
            @JsonProperty(value = "metadata") Map<String, String> metadata) {

        this.content = validatedContent(content);
        this.destinationNumber = validatedDestinationNumber(destinationNumber);

        // When de-serialising a message from JSON format, the message must
        // have a message ID
        this.messageId = validatedMessageId(messageId);

        // When de-serialising a message from JSON format, the message must
        // have a status
        this.status = validatedStatus(status);

        this.callbackUrl = callbackUrl;
        this.deliveryReport = deliveryReport == null ? false : deliveryReport;
        this.format = format == null ? MessageFormat.SMS : format;
        this.scheduled = scheduled == null ? null : new DateTime(scheduled.getMillis(), DateTimeZone.UTC);
        this.sourceNumber = sourceNumber;
        this.sourceNumberType = sourceNumberType;
        this.statusReason = statusReason;
        this.messageExpiryTimestamp = messageExpiryTimestamp == null ? null : new DateTime(messageExpiryTimestamp.getMillis(), DateTimeZone.UTC);
        this.metadata = Collections.unmodifiableMap(metadata != null ? metadata : Collections.<String, String>emptyMap());
    }

    private MessageStatus validatedStatus(MessageStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Property 'status' cannot be null.");
        }
        return status;
    }

    private String validatedMessageId(String messageId) {
        if (messageId == null || messageId.isEmpty()) {
            throw new IllegalArgumentException("Property 'message_id' cannot be null or empty.");
        }
        return messageId;
    }

    private String validatedDestinationNumber(String destinationNumber) {
        if (destinationNumber == null || destinationNumber.isEmpty()) {
            throw new IllegalArgumentException("Property 'destination_number' cannot be null or empty.");
        }
        return destinationNumber;
    }

    private String validatedContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Property 'content' cannot be null.");
        }
        if (content.length() > MAX_CONTENT_LENGTH_IN_CHARS) {
            throw new IllegalArgumentException("Maximum length for content is " + MAX_CONTENT_LENGTH_IN_CHARS + " characters");
        }
        return content;
    }

    /**
     * Gets the callback URL.
     * <p/>
     * The callback URL is the endpoint where the replies or delivery reports will be sent when received.
     *
     * @return the callback URL or null if none was specified.
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content, never null.
     */
    public String getContent() {
        return content;
    }

    /**
     * Return a flag that indicates whether or not a Delivery Report will be requested for this message.
     * <p/>
     * Note that by requesting a Delivery Report you may incur additional charges for sending a message.
     *
     * @return <code>true</code> if a delivery report will be requested, <code>false</code> otherwise
     */
    @JsonProperty("delivery_report")
    public boolean isDeliveryReportFlagSet() {
        return deliveryReport;
    }

    /**
     * Gets the destination number.
     *
     * @return the destination number
     */
    public String getDestinationNumber() {
        return destinationNumber;
    }

    /**
     * Gets the format of the message.
     *
     * @return the message format. Never null.
     */
    public MessageFormat getFormat() {
        return format;
    }

    /**
     * Gets the source number of the message. This will appear as the sender.
     *
     * @return the source number or null if none was specified.
     */
    public String getSourceNumber() {
        return sourceNumber;
    }

    /**
     * Gets the source number address type.
     *
     * @return the source number address type or null if none was specified.
     */
    public AddressType getSourceNumberType() {
        return sourceNumberType;
    }

    /**
     * Gets the message ID. It is null when the message is being created by the {@link MessageBuilder}. It the message is returned by the API, it is
     * always set.
     *
     * @return the message ID or null.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Gets the date that the message will be sent.
     *
     * @return the scheduled date or null if none was specified.
     */
    public DateTime getScheduled() {
        if (scheduled == null) {
            return null;
        } else {
            return new DateTime(scheduled.getMillis(), DateTimeZone.UTC);
        }
    }

    /**
     * Internal status of the message in the REST API.
     *
     * @return the status
     * @see MessageStatus
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * Gets the String describing the reason of why the message is in the current status. May be null.
     *
     * @return the status reason or null, depending on the status.
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * Gets the expiry timestamp for the message.
     *
     * @return the expiry timestamp or null if none was specified.
     */
    public DateTime getMessageExpiryTimestamp() {
        if (messageExpiryTimestamp == null) {
            return null;
        } else {
            return new DateTime(messageExpiryTimestamp.getMillis(), DateTimeZone.UTC);
        }
    }

    /**
     * Gets the metadata for the message.
     *
     * @return the metadata or null if none was specified.
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    //Checkstyle: START IGNORING
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;

        if (deliveryReport != message.deliveryReport) {
            return false;
        }
        if (callbackUrl != null ? !callbackUrl.equals(message.callbackUrl) : message.callbackUrl != null) {
            return false;
        }
        if (content != null ? !content.equals(message.content) : message.content != null) {
            return false;
        }
        if (destinationNumber != null ? !destinationNumber.equals(message.destinationNumber) : message.destinationNumber != null) {
            return false;
        }
        if (format != message.format) {
            return false;
        }
        if (messageId != null ? !messageId.equals(message.messageId) : message.messageId != null) {
            return false;
        }
        if (scheduled != null ? !scheduled.equals(message.scheduled) : message.scheduled != null) {
            return false;
        }
        if (sourceNumber != null ? !sourceNumber.equals(message.sourceNumber) : message.sourceNumber != null) {
            return false;
        }
        if (sourceNumberType != message.sourceNumberType) {
            return false;
        }
        if (status != message.status) {
            return false;
        }
        if (statusReason != null ? !statusReason.equals(message.statusReason) : message.statusReason != null) {
            return false;
        }
        if (messageExpiryTimestamp != null ? !messageExpiryTimestamp.equals(message.messageExpiryTimestamp) :
                message.messageExpiryTimestamp != null) {
            return false;
        }
        if (metadata != null ? !metadata.equals(message.metadata) : message.metadata != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = callbackUrl != null ? callbackUrl.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (deliveryReport ? 1 : 0);
        result = 31 * result + (destinationNumber != null ? destinationNumber.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (scheduled != null ? scheduled.hashCode() : 0);
        result = 31 * result + (sourceNumber != null ? sourceNumber.hashCode() : 0);
        result = 31 * result + (sourceNumberType != null ? sourceNumberType.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (statusReason != null ? statusReason.hashCode() : 0);
        result = 31 * result + (messageExpiryTimestamp != null ? messageExpiryTimestamp.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
    //Checkstyle: RESUME

    @Override
    public String toString() {
        return "Message{" +
                "callbackUrl='" + callbackUrl + '\'' +
                ", content='" + content + '\'' +
                ", deliveryReport=" + deliveryReport +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", format=" + format +
                ", messageId='" + messageId + '\'' +
                ", scheduled=" + scheduled +
                ", sourceNumber='" + sourceNumber + '\'' +
                ", sourceNumberType='" + sourceNumberType + '\'' +
                ", status=" + status +
                ", statusReason='" + statusReason + '\'' +
                ", messageExpiryTimestamp='" + messageExpiryTimestamp + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}

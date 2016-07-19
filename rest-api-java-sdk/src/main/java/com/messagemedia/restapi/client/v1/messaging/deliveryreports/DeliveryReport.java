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
package com.messagemedia.restapi.client.v1.messaging.deliveryreports;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagemedia.restapi.client.v1.messaging.messages.MessageStatus;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;


/**
 * A Delivery Report represents the state of a message once it has been passed on from MessageMedia to a cellular/mobile provider.
 * <p/>
 * See {@link MessageStatus} for more information about statuses.
 * <p/>
 * Delivery Reports are optional, and can be enabled or disabled when submitting a message.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class DeliveryReport {

    private final String messageId;
    private final DateTime dateReceived;
    private final Integer delay;
    private final String deliveryReportId;
    private final String sourceNumber;
    private final MessageStatus status;
    private final Map<String, String> metadata;

    @JsonCreator
    DeliveryReport(
            @JsonProperty(value = "date_received") DateTime dateReceived,
            @JsonProperty(value = "delay", required = false) Integer delay,
            @JsonProperty(value = "delivery_report_id") String deliveryReportId,
            @JsonProperty(value = "message_id") String messageId,
            @JsonProperty(value = "source_number") String sourceNumber,
            @JsonProperty(value = "status") MessageStatus status,
            @JsonProperty(value = "metadata") Map<String, String> metadata) {
        this.delay = delay;

        this.deliveryReportId = validatedDeliveryReportId(deliveryReportId);
        this.messageId = validatedMessageId(messageId);
        this.sourceNumber = validatedSourceNumber(sourceNumber);
        this.status = validatedStatus(status);
        this.dateReceived = validatedDateReceived(dateReceived);
        this.metadata = Collections.unmodifiableMap((metadata != null) ? metadata : Collections.<String, String>emptyMap());
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

        DeliveryReport that = (DeliveryReport) o;

        if (dateReceived != null ?
                !dateReceived.equals(that.dateReceived) :
                that.dateReceived != null) {
            return false;
        }
        if (delay != null ? !delay.equals(that.delay) : that.delay != null) {
            return false;
        }
        if (deliveryReportId != null ?
                !deliveryReportId.equals(that.deliveryReportId) :
                that.deliveryReportId != null) {
            return false;
        }
        if (messageId != null ?
                !messageId.equals(that.messageId) :
                that.messageId != null) {
            return false;
        }
        if (sourceNumber != null ?
                !sourceNumber.equals(that.sourceNumber) :
                that.sourceNumber != null) {
            return false;
        }
        if (status != that.status) {
            return false;
        }
        if (!Objects.equals(metadata, that.metadata)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (dateReceived != null ? dateReceived.hashCode() : 0);
        result = 31 * result + (delay != null ? delay.hashCode() : 0);
        result = 31 * result + (deliveryReportId != null ? deliveryReportId.hashCode() : 0);
        result = 31 * result + (sourceNumber != null ? sourceNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
    //Checkstyle: RESUME

    /**
     * Return a DateTime that indicates when the message was received.
     *
     * @return the date/time when the Delivery Report was received
     */
    public DateTime getDateReceived() {
        return new DateTime(dateReceived.getMillis(), DateTimeZone.UTC);
    }

    /**
     * Return the message delivery delay.
     * <p/>
     * This delay is the amount of milliseconds that have been passed between that the message was submitted to the provider and the message was
     * actually received in the device.
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     * Return the unique ID that can be used to confirm that the Delivery Report has been received.
     *
     * @return the Delivery Report ID
     */
    public String getDeliveryReportId() {
        return deliveryReportId;
    }

    /**
     * Return the ID of the message that corresponds to this Delivery Report.
     *
     * @return the message ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Return the number that produced the Delivery Report.
     * <p/>
     * This will be the phone number of the destination handset that produced the delivery report.
     *
     * @return the source number
     */
    public String getSourceNumber() {
        return sourceNumber;
    }

    /**
     * Return the status of the delivery.
     *
     * @return the status
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * Return the metadata of the message
     *
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    private DateTime validatedDateReceived(DateTime dateReceived) {
        if (dateReceived == null) {
            throw new IllegalArgumentException("Property 'date_received' cannot be null.");
        }
        return new DateTime(dateReceived.getMillis(), DateTimeZone.UTC);
    }

    private String validatedSourceNumber(String sourceNumber) {
        if (sourceNumber == null || sourceNumber.isEmpty()) {
            throw new IllegalArgumentException("Property 'source_number' cannot be null or empty.");
        }
        return sourceNumber;
    }

    private String validatedMessageId(String messageId) {
        if (messageId == null || messageId.isEmpty()) {
            throw new IllegalArgumentException("Property 'message_id' cannot be null or empty.");
        }
        return messageId;
    }

    private String validatedDeliveryReportId(String deliveryReportId) {
        if (deliveryReportId == null || deliveryReportId.isEmpty()) {
            throw new IllegalArgumentException("Property 'delivery_report_id' cannot be null or empty.");
        }
        return deliveryReportId;
    }

    private MessageStatus validatedStatus(MessageStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Property 'status' cannot be null.");
        }
        return status;
    }
}

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

/**
 * Represents the confirmation state of a single Delivery Report.
 *
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class DeliveryReportConfirmationResultItem {

    private final String deliveryReportId;
    private final boolean confirmed;

    @JsonCreator
    DeliveryReportConfirmationResultItem(
            @JsonProperty(value = "delivery_report_id") String deliveryReportId,
            @JsonProperty(value = "confirmed") Boolean confirmed) {
        this.deliveryReportId = deliveryReportId;
        if (this.deliveryReportId == null || this.deliveryReportId.isEmpty()) {
            throw new IllegalArgumentException("Property 'delivery_report_id' cannot be null.");
        }
        if (confirmed == null) {
            throw new IllegalArgumentException("Property 'confirmed' cannot be null.");
        } else {
            this.confirmed = confirmed;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryReportConfirmationResultItem that = (DeliveryReportConfirmationResultItem) o;

        return confirmed == that.confirmed
                && !(deliveryReportId != null ? !deliveryReportId.equals(that.deliveryReportId) : that.deliveryReportId != null);

    }

    @Override
    public int hashCode() {
        int result = deliveryReportId != null ? deliveryReportId.hashCode() : 0;
        result = 31 * result + (confirmed ? 1 : 0);
        return result;
    }

    /**
     * Return the ID corresponding to the Delivery Report
     *
     * @see DeliveryReport#getDeliveryReportId()
     */
    public String getDeliveryReportId() {
        return deliveryReportId;
    }

    /**
     * Return a boolean indicating whether or not the confirmation operation was successful for this Delivery Report.
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}

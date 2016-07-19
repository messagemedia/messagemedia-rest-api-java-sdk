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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a Delivery Report confirmation operation.
 * <p/>
 * This class contains a list of {@link DeliveryReportConfirmationResultItem}, each of which represent the new confirmation status for the
 * {@link DeliveryReport} that were to be confirmed.
 * <p/>
 * Typically, a successful request will result in all {@link DeliveryReport} being confirmed. However this may not always be the case.
 * <p/>
 * For example, if an invalid Delivery Report ID is provided, then it will not be possible to confirm that it has been received.
 * An additional diagnostic message and error details are included to identify these kinds of problems.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class DeliveryReportConfirmationResult {

    private final List<DeliveryReportConfirmationResultItem> items;

    @JsonCreator
    DeliveryReportConfirmationResult(@JsonProperty(value = "delivery_report_ids") List<DeliveryReportConfirmationResultItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("Property 'delivery_report_ids' cannot be null.");
        } else {
            this.items = new ArrayList<DeliveryReportConfirmationResultItem>(items);
        }
    }

    /**
     * Gets the list of {@link DeliveryReportConfirmationResultItem}.
     * <p/>
     * If the list has no items, it will return an empty list. This method never returns null.
     *
     * @return the delivery report IDs
     */
    public List<DeliveryReportConfirmationResultItem> getItems() {
        return new ArrayList<DeliveryReportConfirmationResultItem>(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryReportConfirmationResult that = (DeliveryReportConfirmationResult) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}

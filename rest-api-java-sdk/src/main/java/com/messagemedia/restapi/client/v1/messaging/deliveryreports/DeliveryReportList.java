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
 * A list of {@link DeliveryReport}
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class DeliveryReportList {

    private final List<DeliveryReport> deliveryReports;

    @JsonCreator
    DeliveryReportList(@JsonProperty("delivery_reports") List<DeliveryReport> deliveryReports) {
        if (deliveryReports == null) {
            throw new IllegalArgumentException("Property 'delivery_reports' cannot be null.");
        } else {
            this.deliveryReports = new ArrayList<DeliveryReport>(deliveryReports);
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

        DeliveryReportList that = (DeliveryReportList) o;

        return deliveryReports.equals(that.deliveryReports);
    }

    @Override
    public int hashCode() {
        return deliveryReports.hashCode();
    }

    /**
     * Gets the delivery reports list.
     *
     * @return the delivery reports
     */
    public List<DeliveryReport> getDeliveryReports() {
        return new ArrayList<DeliveryReport>(deliveryReports);
    }
}

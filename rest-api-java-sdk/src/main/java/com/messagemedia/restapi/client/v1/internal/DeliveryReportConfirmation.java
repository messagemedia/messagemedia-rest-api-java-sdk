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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains a list of IDs corresponding to Delivery Reports that are to be
 * confirmed.
 *
 * Instances of this class are immutable, and can be considered thread-safe.
 */
final class DeliveryReportConfirmation {

    private final Collection<String> deliveryReportIds;

    public DeliveryReportConfirmation(Collection<String> deliveryReportIds) {
        this.deliveryReportIds = new ArrayList<String>(deliveryReportIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryReportConfirmation that = (DeliveryReportConfirmation) o;

        return deliveryReportIds.equals(that.deliveryReportIds);
    }

    @Override
    public int hashCode() {
        return deliveryReportIds.hashCode();
    }

    /**
     * Return the list of IDs corresponding to the Delivery Reports that are
     * to be confirmed.
     *
     * @return A collection of IDs
     */
    public Collection<String> getDeliveryReportIds() {
        return new ArrayList<String>(deliveryReportIds);
    }
}

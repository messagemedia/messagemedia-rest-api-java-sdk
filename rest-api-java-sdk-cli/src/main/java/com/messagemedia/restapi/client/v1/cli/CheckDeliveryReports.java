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
package com.messagemedia.restapi.client.v1.cli;

import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiResponse;
import com.messagemedia.restapi.client.v1.messaging.deliveryreports.DeliveryReport;
import com.messagemedia.restapi.client.v1.messaging.deliveryreports.DeliveryReportList;

import java.util.List;

/**
 * Command line utility that retrieves unconfirmed delivery reports.
 */
public class CheckDeliveryReports extends AbstractAction {

    @Override
    public void execute(String[] args) throws RestApiException {
        try {
            final RestApiResponse<DeliveryReportList> response = Settings.CLIENT.messaging().checkDeliveryReports();
            List<DeliveryReport> deliveryReports = response.getPayload().getDeliveryReports();
            if (deliveryReports.isEmpty()) {
                System.out.println("No unconfirmed delivery reports.");
            } else {
                System.out.println("Retrieved the following delivery reports:");
                for (DeliveryReport item : deliveryReports) {
                    System.out.println("  ID: " + item.getDeliveryReportId());
                    System.out.println("    Date Received: " + item.getDateReceived());
                    System.out.println("    Delay: " + item.getDelay());
                    System.out.println("    Message ID: " + item.getMessageId());
                    System.out.println("    Source Number (handset): " + item.getSourceNumber());
                    System.out.println("    Status: " + item.getStatus());
                    System.out.println("    Metadata: " + item.getMetadata());
                }
            }
        } catch (RestApiException e) {
            logException(e);
        }
    }

    @Override
    protected String getActionDescription() {
        return "retrieving delivery reports";
    }
}

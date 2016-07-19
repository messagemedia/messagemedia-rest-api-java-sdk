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
package com.messagemedia.restapi.client.v1.examples;

import com.messagemedia.restapi.client.v1.*;

import java.util.Arrays;
import java.util.List;

/**
 * A short example demonstrating how to confirm a list of replies.
 */
public class ConfirmReplies {

    public static void main(String[] args) throws RestApiException {
        // put in your credentials here
        final String apiKey = "<your api key>";
        final String secretKey = "<your secret key>";

        // put in your reply ID(s) here
        final List<String> replyIds = Arrays.asList("<your reply ID>", "<another reply id>");

        final RestApiClient restApiClient = RestApiClientBuilder.newBuilder(apiKey, secretKey).build();
        try {
            restApiClient.messaging().confirmReplies(replyIds);
            System.out.println("Successfully requested confirmation!");
        } catch (RestApiException exception) {
            System.err.println("Error confirming replies. " + exception.getMessage());
            if (exception instanceof RestApiClientException) {
                System.err.println("Details: ");
                for (String item : ((RestApiClientException) exception).getDetails()) {
                    System.err.println("  " + item);
                }
            }
        }
    }
}

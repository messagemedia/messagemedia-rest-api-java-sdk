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

import com.messagemedia.restapi.client.v1.AuthorizationScheme;
import com.messagemedia.restapi.client.v1.RestApiClient;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient;

/**
 * Default implementation for {@link RestApiClient}
 */
public class RestApiClientImpl implements RestApiClient {

    private static final String VERSION = "/v1";
    private static final String URL_STATUS = VERSION + "/status";



    private final RestClient client;
    private final RestApiMessagingClient messagingClient;

    public RestApiClientImpl(String endpoint, String key, String secret, Integer maxConnections, Integer connectTimeout,
                             Integer socketTimeout, AuthorizationScheme authorizationScheme) {

        client = new RestClient(endpoint, key, secret, maxConnections, connectTimeout, socketTimeout, authorizationScheme);
        messagingClient = new RestApiMessagingClientImpl(client);
    }

    protected RestApiClientImpl(RestClient client, RestApiMessagingClient messagingClient) {
        this.client = client;
        this.messagingClient = messagingClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        try {
            RestResponse response = client.get(URL_STATUS).execute();
            return response.isSuccessful();
        } catch (RestApiException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiMessagingClient messaging() {
        return messagingClient;
    }


    RestClient getRestClient() {
        return client;
    }

}

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
package com.messagemedia.restapi.client.v1;

import com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient;

/**
 * MessageMedia REST API Client
 * <p/>
 * Client interface to call REST API services.
 * <p/>
 * Instances of this client can be created using {@link RestApiClientBuilder}.
 */
public interface RestApiClient {

    /**
     * Checks if the service is up and running and can be reached by the client.
     *
     * @return <code>true</code> if alive, <code>false</code> otherwise.
     */
    boolean isAlive();

    /**
     * Provides access to the messaging features provided by the Rest API.
     *
     * @return A client that provides access to the messaging features
     */
    RestApiMessagingClient messaging();

}

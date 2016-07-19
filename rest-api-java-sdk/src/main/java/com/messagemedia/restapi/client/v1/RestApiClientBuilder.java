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

import com.messagemedia.restapi.client.v1.internal.ContextAwareRestApiClientImpl;
import com.messagemedia.restapi.client.v1.internal.RestApiClientImpl;

/**
 * Builder class to create {@link RestApiClient}
 */
public final class RestApiClientBuilder {

    private static final String DEFAULT_ENDPOINT = "https://api.messagemedia.com/";

    private final String apiKey;
    private final String secretKey;
    private String endpoint;
    private Integer maxConnections;
    private Integer socketTimeout;
    private Integer connectTimeout;
    private AuthorizationScheme authorizationScheme = AuthorizationScheme.HMAC_MM_V2;

    /**
     * Creates a new builder of {@link RestApiClient} for a given API Key and Secret Key.
     *
     * @param apiKey    the API Key assigned to you by MessageMedia
     * @param secretKey the Secret Key assigned to you by MessageMedia
     * @return A RestApiClientBuilder object which can be used for method chaining. Otherwise, you can call {@link #build()} directly.
     */
    public static RestApiClientBuilder newBuilder(String apiKey, String secretKey) {
        return new RestApiClientBuilder(apiKey, secretKey);
    }

    /**
     * Instantiates a new REST API client builder.
     * <p/>
     * This constructor is private. Instances must be created with {@link #newBuilder(String, String)} and then {@link #build()}
     *
     * @param apiKey    he key ID
     * @param secretKey the key
     */
    private RestApiClientBuilder(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.endpoint = DEFAULT_ENDPOINT;
    }

    /**
     * Sets the REST API endpoint.
     *
     * @param endpoint the new endpoint
     * @return A RestApiClientBuilder object which can be used for method chaining.
     */
    public RestApiClientBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Sets the connect timeout, which specifies the timeout which is used until a connection is established.
     *
     * @param connectTimeout - the connectTimeout in milliseconds
     * @return A RestApiClientBuilder object which can be used for method chaining.
     */
    public RestApiClientBuilder connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * Assigns socket timeout for each request against the REST API.
     *
     * @param socketTimeout the socket timeout
     * @return A RestApiClientBuilder object which can be used for method chaining.
     */
    public RestApiClientBuilder socketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    /**
     * Assigns maximum connection value.
     *
     * @param maxConnections the max connections
     * @return A RestApiClientBuilder object which can be used for method chaining.
     */
    public RestApiClientBuilder maxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    /**
     * Sets an AuthorizationScheme. Don't use it unless you are told so.
     *
     * @param authorizationScheme used for Authorisation
     * @return A RestApiClientBuilder object which can be used for method chaining.
     */
    public RestApiClientBuilder authorizationScheme(AuthorizationScheme authorizationScheme) {
        this.authorizationScheme = authorizationScheme;
        return this;
    }

    /**
     * Builds the {@link RestApiClient}
     *
     * @return the {@link RestApiClient} ready to use
     */
    public RestApiClient build() {
        return buildImpl();
    }

    private RestApiClientImpl buildImpl() {
        return new RestApiClientImpl(endpoint, apiKey, secretKey, maxConnections, connectTimeout, socketTimeout, authorizationScheme);
    }

    /**
     * Builds the {@link ContextAwareRestApiClient}. This client is only rarely needed. Use #build() unless you have been told to use it.
     *
     * @return the {@link ContextAwareRestApiClient} ready to use
     */
    public ContextAwareRestApiClient buildContextAware() {
        return new ContextAwareRestApiClientImpl(buildImpl());
    }

}

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
import com.messagemedia.restapi.client.v1.Context;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.internal.http.interceptors.ContentTypeInterceptor;
import com.messagemedia.restapi.client.v1.internal.http.interceptors.HmacMmv2Interceptor;
import com.messagemedia.restapi.client.v1.internal.http.interceptors.RequestDateInterceptor;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Performs REST operations against the MessageMedia REST API
 */
class RestClient {

    private static final int CLIENT_VERSION = 1;

    private static final String USER_AGENT = "rest-api-java-client-v"
            + CLIENT_VERSION;

    private static final int MAX_CONNECTIONS_DEFAULT = 200;
    private static final int SOCKET_TIMEOUT_DEFAULT = 5 * 60 * 1000;
    private static final int CONNECT_TIMEOUT_DEFAULT = 60 * 1000;

    private final HttpClient httpClient;
    private final String endpoint;
    private ThreadLocal<Context> contextThreadLocal = new ThreadLocal<Context>();

    public RestClient(String endpoint, String key, String secret, Integer maxConnections, Integer connectTimeout, Integer socketTimeout,
                      AuthorizationScheme authorizationScheme) {
        this.endpoint = endpoint;
        int maxConnectionsValue = maxConnections != null ? maxConnections : MAX_CONNECTIONS_DEFAULT;
        int connectTimeoutValue = connectTimeout != null ? connectTimeout : CONNECT_TIMEOUT_DEFAULT;
        int socketTimeoutValue = socketTimeout != null ? socketTimeout : SOCKET_TIMEOUT_DEFAULT;

        RequestConfig config = RequestConfig.custom()
                                            .setConnectTimeout(connectTimeoutValue)
                                            .setSocketTimeout(socketTimeoutValue)
                                            .build();

        httpClient = HttpClientBuilder.create()
                                      .addInterceptorFirst(new ContentTypeInterceptor())
                                      .addInterceptorFirst(new RequestDateInterceptor())
                                      .addInterceptorFirst(toRequestInterceptor(authorizationScheme, key, secret))
                                      .setMaxConnPerRoute(maxConnectionsValue)
                                      .setMaxConnTotal(maxConnectionsValue)
                                      .disableCookieManagement()
                                      .setUserAgent(USER_AGENT)
                                      .setDefaultRequestConfig(config)
                                      .build();
    }

    private HttpRequestInterceptor toRequestInterceptor(AuthorizationScheme authorizationScheme, String key, String secret) {
        switch (authorizationScheme) {
            case HMAC_MM_V2:
                return new HmacMmv2Interceptor(key, secret);
            default:
                throw new IllegalArgumentException(authorizationScheme + " is not supported");
        }
    }

    /**
     * Creates a {@link RestRequestBuilder} for a GET operation
     *
     * @param path The path
     * @return The rest request builder
     */
    public RestRequestBuilder get(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.GET, this);
    }

    /**
     * Creates a {@link RestRequestBuilder} for a PUT operation
     *
     * @param path the path
     * @return the rest request builder
     */
    public RestRequestBuilder put(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.PUT, this);
    }

    /**
     * Creates a {@link RestRequestBuilder} for a DELETE operation
     *
     * @param path the path
     * @return the rest request builder
     */
    public RestRequestBuilder delete(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.DELETE, this);
    }

    /**
     * Creates a {@link RestRequestBuilder} for a HEAD operation
     *
     * @param path the path
     * @return the rest request builder
     */
    public RestRequestBuilder head(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.HEAD, this);
    }

    /**
     * Creates a {@link RestRequestBuilder} for a POST operation
     *
     * @param path the path
     * @return the rest request builder
     */
    public RestRequestBuilder post(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.POST, this);
    }

    public RestRequestBuilder patch(String path) {
        return RestRequestBuilder.create(endpoint, path, HttpMethod.PATCH, this);
    }

    /**
     * Executes the rest request.
     *
     * @param req the request
     * @return the rest response
     * @throws RestApiException if something goes wrong
     */
    RestResponse execute(RestRequest req) throws RestApiException {
        try {
            HttpResponse response = httpClient.execute(addHeaders(req.getHttpRequest()));
            return new RestResponse(response);
        } catch (ClientProtocolException e) {
            throw new RestApiException("Protocol exception", e);
        } catch (IOException e) {
            throw new RestApiException("IO Exception", e);
        }
    }

    private HttpUriRequest addHeaders(HttpUriRequest httpRequest) {
        Context context = contextThreadLocal.get(); // make sure it does not change during the execution
        if (context == null) {
            return httpRequest;
        } else {
            String account = context.getAccount();

            if (account != null && !account.isEmpty()) {
                httpRequest.addHeader("Account", account);
            }

            String username = context.getUsername();
            if (username != null && !username.isEmpty()) {
                httpRequest.addHeader("Username", username);
            }

            return httpRequest;
        }
    }

    void setContext(Context context) {
        if (contextThreadLocal.get() != null) {
            throw new IllegalStateException("There is already a context set! You have to close a context before you set another one!");
        }
        this.contextThreadLocal.set(context);
    }

    void removeCurrentContext() {
        contextThreadLocal.remove();
    }
}

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

import com.messagemedia.restapi.client.v1.*;

public class Settings {

    private static final String API_KEY_PROP = "com.messagemedia.restapi.client.v1.cli.api_key";
    private static final String SECRET_KEY_PROP = "com.messagemedia.restapi.client.v1.cli.secret_key";
    private static final String ENDPOINT_PROP = "com.messagemedia.restapi.client.v1.cli.endpoint";
    private static final String ACCOUNT = "com.messagemedia.restapi.client.v1.cli.account";
    private static final String USERNAME = "com.messagemedia.restapi.client.v1.cli.username";
    private static final String AUTH_SCHEME = "com.messagemedia.restapi.client.v1.cli.authscheme";

    /**
     * REST API client configured to use your API Key and Secret key
     */
    public static final RestApiClient CLIENT = makeRestApiClient();

    private static RestApiClient makeRestApiClient() {

        final String apiKey = System.getProperty(API_KEY_PROP);
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty. Have you tried setting the '" + API_KEY_PROP + "' property.");
        }

        final String secretKey = System.getProperty(SECRET_KEY_PROP);
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("Secret key cannot be null or empty. Have you tried setting the '" + SECRET_KEY_PROP + "' property.");
        }

        RestApiClientBuilder builder = RestApiClientBuilder.newBuilder(apiKey, secretKey);

        final String authScheme = System.getProperty(AUTH_SCHEME);
        if (authScheme != null) {
            builder.authorizationScheme(AuthorizationScheme.valueOf(authScheme));
        }

        setEndpoint(builder);

        ContextAwareRestApiClient contextAwareRestApiClient = builder.buildContextAware();
        setAccountAndUsername(contextAwareRestApiClient.createContextBuilder());

        // NOTE: in this special case the context never gets closed. This is ok for a one time command line action.
        // If you want to use the code in a regular environment, please see the examples!
        return contextAwareRestApiClient;
    }

    private static void setEndpoint(RestApiClientBuilder builder) {
        final String endpoint = System.getProperty(ENDPOINT_PROP);

        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpoint(endpoint);
        }
    }

    private static void setAccountAndUsername(ContextBuilder builder) {
        String account = System.getProperty(ACCOUNT);

        if (account != null && !account.isEmpty()) {
            builder.account(account);
        }

        String username = System.getProperty(USERNAME);

        if (username != null && !username.isEmpty()) {
            builder.username(username);
        }

        builder.build();
    }
}

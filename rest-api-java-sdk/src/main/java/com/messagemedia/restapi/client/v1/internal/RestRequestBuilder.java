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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.internal.util.JsonUtilities;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder for {@link RestRequest}
 */
final class RestRequestBuilder {

    private final RestClient client;

    private String path;
    private byte[] body;
    private final HttpMethod method;
    private final Map<String, String> headers;
    private final URIBuilder uriBuilder;

    private RestRequestBuilder(String endpoint, String path, HttpMethod method, RestClient client) {

        Args.notBlank(path, "path");
        Args.notNull(method, "method");
        Args.notNull(client, "client");
        Args.notBlank(endpoint, "endpoint");

        try {
            this.uriBuilder = new URIBuilder(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }

        this.path = path;
        this.client = client;
        this.method = method;
        this.headers = new HashMap<String, String>();
    }

    public static RestRequestBuilder create(String endpoint, String path, HttpMethod method, RestClient client) {
        return new RestRequestBuilder(endpoint, path, method, client);
    }

    /**
     * Sets the body
     *
     * @param body the body
     * @return the rest request builder
     */
    public RestRequestBuilder body(byte[] body) {
        this.body = body.clone();
        return this;
    }

    /**
     * Sets a string as request body
     *
     * @param body the body
     * @return the rest request builder
     */
    public RestRequestBuilder body(String body) throws RestApiException {
        try {
            this.body = body.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RestApiException("Failed to copy body of REST request.", ex);
        }

        return this;
    }

    /**
     * Sets an object as request body
     *
     * @param <T>    the generic type
     * @param object the object
     * @return the rest request builder
     * @throws JsonProcessingException the json processing exception
     */
    public <T> RestRequestBuilder body(T object) throws JsonProcessingException {
        this.body = JsonUtilities.objectToBytes(object);
        return this;
    }

    public RestRequest build() {
        uriBuilder.setPath(path);

        try {
            return new RestRequest(method, uriBuilder.build().toString(), headers, body);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Adds a header
     *
     * @param name  the header name
     * @param value the header value
     * @return this builder
     */
    public RestRequestBuilder header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Sets the content type for this request
     *
     * @param contentType the content type
     * @return this builder
     */
    public RestRequestBuilder contentType(ContentType contentType) {
        this.headers.put(HTTP.CONTENT_TYPE, contentType.toString());
        return this;
    }

    /**
     * Executes the request and return the response. Throws an exception if something went wrong.
     *
     * @return the rest response
     * @throws RestApiException the rest client exception
     */
    public RestResponse execute() throws RestApiException {
        if (path.contains("{") || path.contains("}")) {
            String message = "Path variables unresolved. Please call #pathVariable method for every path variable. Current path is " + path;
            throw new RestApiException(message);
        }

        return client.execute(build());
    }

    public RestRequestBuilder pathVariable(String name, String value) {
        this.path = path.replaceAll("\\{" + name + "\\}", value);
        return this;
    }

    public RestRequestBuilder requestParam(String name, String value) {
        this.uriBuilder.addParameter(name, value);
        return this;
    }

}

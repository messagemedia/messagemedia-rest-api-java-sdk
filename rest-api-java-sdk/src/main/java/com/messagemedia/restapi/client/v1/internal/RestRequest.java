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

import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a http request.
 * <p>
 * This class is immutable.
 */
class RestRequest {

    private final HttpMethod method;
    private final String url;
    private final byte[] body;
    private final Map<String, String> headers;

    public RestRequest(HttpMethod method, String url, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.url = url;
        this.body = body == null ? null : body.clone();
        this.headers = headers;
    }

    /**
     * Builds an apache http request
     *
     * @return the apache http request
     */
    HttpUriRequest getHttpRequest() {
        HttpUriRequest request;
        switch (method) {
            case GET:
                request = new HttpGet(url);
                break;
            case DELETE:
                request = new HttpDelete(url);
                break;
            case HEAD:
                request = new HttpHead(url);
                break;
            case POST:
                HttpPost post = new HttpPost(url);
                post.setEntity(new ByteArrayEntity(body));
                request = post;
                break;
            case PUT:
                HttpPut put = new HttpPut(url);
                put.setEntity(new ByteArrayEntity(body));
                request = put;
                break;
            case PATCH:
                HttpPatch patch = new HttpPatch(url);
                patch.setEntity(new ByteArrayEntity(body));
                request = patch;
                break;
            default:
                throw new RuntimeException("Method not supported");
        }

        addHeaders(request);

        return request;
    }

    private void addHeaders(HttpUriRequest request) {
        for (Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }

}

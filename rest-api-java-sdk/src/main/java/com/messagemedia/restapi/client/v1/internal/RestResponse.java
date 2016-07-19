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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.internal.util.JsonUtilities;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Rest response
 */
class RestResponse {

    private final int resultCode;
    private final Map<String, String> headers = new HashMap<String, String>();
    private byte[] resultBytes;

    public RestResponse(HttpResponse response) throws RestApiException {
        this.resultCode = response.getStatusLine().getStatusCode();
        for (Header header : response.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        if (response.getEntity() != null) {
            try {
                this.resultBytes = EntityUtils.toByteArray(response.getEntity());
            } catch (IOException e) {
                throw new RestApiException("Error reading response", e);
            } finally {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
    }

    /**
     * Checks if the HTTP status code is successful.
     *
     * @return <code>true</code>, if is successful, <code>false</code> otherwise
     */
    public boolean isSuccessful() {
        return resultCode >= 200 && resultCode < 300;
    }

    /**
     * @return http response code
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Gets the http response headers.
     *
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Try to map response as the required class.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @return the response mapped as the required object
     * @throws RestApiException the rest client exception
     */
    public <T> T getResponseAs(Class<T> clazz) throws RestApiException {
        // no data -> null
        if (resultBytes.length == 0) {
            return null;
        }
        T result;
        try {
            result = JsonUtilities.bytesToObject(resultBytes, clazz);
        } catch (JsonParseException e) {
            throw new RestApiException("Could not parse response as object", e);
        } catch (JsonMappingException e) {
            throw new RestApiException("Could not map response to the required object", e);
        } catch (IOException e) {
            throw new RestApiException("Error reading response", e);
        }
        return result;
    }

    /**
     * Gets the response as string.
     *
     * @return the response as string
     * @throws RestApiException the rest client exception
     */
    public String getResponseAsString() throws RestApiException {
        try {
            return new String(resultBytes, HTTP.DEF_CONTENT_CHARSET);
        } catch (ParseException e) {
            throw new RestApiException("Could not parse response as string", e);
        }
    }
}

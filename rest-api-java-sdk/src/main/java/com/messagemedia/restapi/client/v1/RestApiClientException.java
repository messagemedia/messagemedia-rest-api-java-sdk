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

import java.util.Collections;
import java.util.List;

/**
 * An exception thrown by the MessageMedia REST API SDK.
 * <p/>
 * This exception means that there was an problem in the request to the server.
 * <p/>
 */
public class RestApiClientException extends RestApiHttpStatusCodeException {

    private final List<String> details;

    public RestApiClientException(String message, int statusCode) {
        this(message, statusCode, Collections.singletonList("HTTP status code " + statusCode));
    }

    public RestApiClientException(String message, int statusCode, List<String> details) {
        super(message, statusCode);
        this.details = details;

    }

    public List<String> getDetails() {
        return details;
    }
}

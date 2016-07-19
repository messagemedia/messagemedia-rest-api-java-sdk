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

/**
 * An exception thrown by the MessageMedia REST API SDK.
 * <p/>
 * This exception means that there was an error during client/server communication.
 * <p/>
 * For example, you will receive this type of exception if there is no network connection and you attempt to call a method of the API.
 * <p/>
 */
public class RestApiHttpStatusCodeException extends RestApiException {

    private final int statusCode;

    public RestApiHttpStatusCodeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

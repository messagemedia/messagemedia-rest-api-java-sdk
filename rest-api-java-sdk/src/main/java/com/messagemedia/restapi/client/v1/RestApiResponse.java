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
 * REST API Response.
 * 
 * Every response from the REST API server will be translated into this type.
 * 
 * It will contain the HTTP status code, the body mapped as the type T.
 * 
 * @param <T> the type of the response, if successful
 */
public interface RestApiResponse<T> {

    /**
     * Gets the HTTP status code.
     * 
     * @return the HTTP status code
     */
    int getResponseCode();

    /**
     * Gets body of the response as the required type.
     *
     * If the request were not successful this method will return null.
     *
     * @return the body of the result mapped as T, or null if unsuccessful
     */
    T getPayload();

}

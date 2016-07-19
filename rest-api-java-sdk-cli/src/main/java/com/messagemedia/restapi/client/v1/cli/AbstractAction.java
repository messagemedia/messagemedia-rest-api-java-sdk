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

import com.messagemedia.restapi.client.v1.RestApiClientException;
import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiHttpStatusCodeException;

public abstract class AbstractAction implements Action {

    protected abstract String getActionDescription();

    protected void logException(RestApiException exception) {
        System.err.println("Error " + getActionDescription() + ". " + exception.getMessage());
        if (exception instanceof RestApiHttpStatusCodeException) {
            System.err.println("HTTP status code: " + ((RestApiHttpStatusCodeException) exception).getStatusCode());
        }
        if (exception instanceof RestApiClientException) {
            System.err.println("Details: ");
            for (String item : ((RestApiClientException) exception).getDetails()) {
                System.err.println("  " + item);
            }
        }
        throw exception;
    }
}

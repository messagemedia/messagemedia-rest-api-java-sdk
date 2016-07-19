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

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of {@link ErrorResponse}.
 */
class DefaultErrorResponse implements ErrorResponse {

    private String message;
    private List<String> details;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReason() {
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDetails() {
        return details == null ? new ArrayList<String>() : new ArrayList<String>(details);
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

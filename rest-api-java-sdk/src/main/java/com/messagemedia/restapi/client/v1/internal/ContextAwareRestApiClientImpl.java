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

import com.messagemedia.restapi.client.v1.Context;
import com.messagemedia.restapi.client.v1.ContextAwareRestApiClient;
import com.messagemedia.restapi.client.v1.ContextBuilder;
import com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient;

/**
 * This class wraps a normal Rest API client and passes on the context to it's rest client.
 */
public class ContextAwareRestApiClientImpl implements ContextAwareRestApiClient {

    private final RestApiClientImpl delegate;

    public ContextAwareRestApiClientImpl(RestApiClientImpl restApiClient) {
        this.delegate = restApiClient;
    }

    void setContext(Context context) {
        delegate.getRestClient().setContext(context);
    }

    void removeCurrentContext() {
        delegate.getRestClient().removeCurrentContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContextBuilder createContextBuilder() {
        return new ContextBuilderImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return delegate.isAlive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestApiMessagingClient messaging() {
        return delegate.messaging();
    }
}

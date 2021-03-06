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

/**
 * The context. Immutable.
 */
class ContextImpl implements Context {
    private final String username;
    private final String account;
    private final ContextAwareRestApiClientImpl contextAwareRestApiClient;

    ContextImpl(String username, String account, ContextAwareRestApiClientImpl contextAwareRestApiClient) {
        this.username = username;
        this.account = account;
        this.contextAwareRestApiClient = contextAwareRestApiClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAccount() {
        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        contextAwareRestApiClient.removeCurrentContext();
    }
}

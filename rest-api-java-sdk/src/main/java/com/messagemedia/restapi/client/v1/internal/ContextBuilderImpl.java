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
import com.messagemedia.restapi.client.v1.ContextBuilder;

/**
 * This builder helps building a context
 */
class ContextBuilderImpl implements ContextBuilder {
    private final ContextAwareRestApiClientImpl contextAwareRestApiClientImpl;
    private String account;
    private String username;

    ContextBuilderImpl(ContextAwareRestApiClientImpl contextAwareRestApiClientImpl) {
        this.contextAwareRestApiClientImpl = contextAwareRestApiClientImpl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContextBuilder account(String account) {
        this.account = account;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContextBuilder username(String username) {
        this.username = username;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Context build() {
        ContextImpl context = new ContextImpl(username, account, contextAwareRestApiClientImpl);
        contextAwareRestApiClientImpl.setContext(context);
        return context;
    }
}

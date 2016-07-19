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
 * This builder helps to create a context.
 */
public interface ContextBuilder {

    /**
     * Overrides the account which is associated with the credentials.
     * <p/>
     * The account can be different from the one for authentication, but it must be for the same vendor. This option is rarely used.
     *
     * @param account Another account for the same vendor
     * @return A ContextBuilder object which can be used for method chaining.
     */
    ContextBuilder account(String account);

    /**
     * Sets the username on which behave requests should be made.
     *
     * @param username Username
     * @return A ContextBuilder object which can be used for method chaining.
     */
    ContextBuilder username(String username);

    /**
     * This methods builds and sets the context. This context needs to be closed by the caller once it should not be used anymore.
     *
     * @return The new context
     */
    Context build();

}

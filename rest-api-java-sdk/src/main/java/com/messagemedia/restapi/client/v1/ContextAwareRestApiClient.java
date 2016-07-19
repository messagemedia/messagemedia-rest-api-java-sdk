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
 * This interface provides support for making requests under a specific context. Normally you should not need to use it.
 */
public interface ContextAwareRestApiClient extends RestApiClient {

    /**
     * @return a ContextBuilder which builds a context for this instance.
     */
    ContextBuilder createContextBuilder();
}

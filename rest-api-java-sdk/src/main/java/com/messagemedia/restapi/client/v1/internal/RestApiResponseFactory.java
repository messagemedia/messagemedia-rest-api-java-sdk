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

import com.messagemedia.restapi.client.v1.RestApiResponse;

/**
 * Factory for creating {@link RestApiResponse}
 */
final class RestApiResponseFactory {

    public static <T> RestApiResponse<T> success(int httpCode, T object) {
        return new DefaultRestApiResponse<T>(httpCode, object);
    }

    public static <T> RestApiResponse<T> error(int httpCode, ErrorResponse error) {
        return new DefaultRestApiResponse<T>(httpCode, error);
    }

    private static class DefaultRestApiResponse<T> implements RestApiResponse<T> {

        private final T object;
        private final int responseCode;
        private final ErrorResponse error;

        public DefaultRestApiResponse(int httpCode, T object) {
            this.responseCode = httpCode;
            this.object = object;
            this.error = null;
        }

        public DefaultRestApiResponse(int httpCode, ErrorResponse error) {
            this.responseCode = httpCode;
            this.error = error;
            this.object = null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getResponseCode() {
            return responseCode;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T getPayload() {
            return object;
        }

        @Override
        public String toString() {
            return "DefaultRestApiResponse [object=" + object
                    + ", responseCode=" + responseCode + ", error=" + error
                    + "]";
        }
    }

}

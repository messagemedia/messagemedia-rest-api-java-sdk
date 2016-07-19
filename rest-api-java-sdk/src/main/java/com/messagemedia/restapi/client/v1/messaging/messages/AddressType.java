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
package com.messagemedia.restapi.client.v1.messaging.messages;

public enum AddressType {

    /**
     * E.164 formatted numbers, see:  https://en.wikipedia.org/wiki/E.164
     * <p/>
     * e.g., +61491570156, +13115552368
     */
    INTERNATIONAL,

    /**
     * Short numerical number, e.g., 12345
     */
    SHORTCODE,

    /**
     * Alphanumeric String, e.g., "ALPHA"
     */
    ALPHANUMERIC

}

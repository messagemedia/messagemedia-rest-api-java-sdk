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

package com.messagemedia.restapi.client.v1.internal.http.interceptors;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * This class holds useful methods around the HMAC calculation.
 */
class HMACUtils {

    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * This method encrypts data with secret using HmacSHA1. The strings are converted to bytes using the UTF-8 encoding.
     *
     * @param secret - the secret which is being used
     * @param data   - the data which is encrypted
     * @return the base64 encoded result
     */
    static String sha1(String secret, String data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes("UTF-8"), HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(rawHmac), "UTF-8");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Can not find the UTF-8 charset", e);
        }
    }
}

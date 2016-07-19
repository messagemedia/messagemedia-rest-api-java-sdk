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

import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is in charge of generating the HMAC for every request.
 */
public class HmacMmv2Interceptor implements HttpRequestInterceptor {

    private static final Logger LOGGER = Logger.getLogger(HmacMmv2Interceptor.class.getName());

    private final String userKey;
    private final String userSecret;

    private static final String AUTH_HEADER_TEMPLATE = "hmac username=\"%s\", algorithm=\"hmac-sha1\", headers=\"%s\", signature=\"%s\"";

    private static final String HEADERS_WITHOUT_CONTENT = HttpHeaders.DATE + " request-line";
    private static final String HEADERS_WITH_CONTENT = HEADERS_WITHOUT_CONTENT + " content-md5";

    public HmacMmv2Interceptor(String userKey, String userSecret) {
        super();
        this.userKey = userKey;
        this.userSecret = userSecret;
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {

        StringBuilder toSign = new StringBuilder();
        toSign.append(HttpHeaders.DATE);
        toSign.append(": ");
        toSign.append(request.getFirstHeader(HttpHeaders.DATE).getValue());
        toSign.append("\n");
        toSign.append(request.getRequestLine().toString());

        boolean hasContent = request.containsHeader(HttpHeaders.CONTENT_MD5);
        final String headers;
        if (hasContent) {
            toSign.append("\n");
            toSign.append(HttpHeaders.CONTENT_MD5);
            toSign.append(": ");
            toSign.append(request.getFirstHeader(HttpHeaders.CONTENT_MD5));
            headers = HEADERS_WITH_CONTENT;
        } else {
            headers = HEADERS_WITHOUT_CONTENT;
        }

        String stringToSign = toSign.toString();
        LOGGER.log(Level.FINEST, "String to sign: " + stringToSign);
        String hmac = HMACUtils.sha1(userSecret, stringToSign);
        String headerValue = String.format(AUTH_HEADER_TEMPLATE, userKey, headers, hmac);
        request.setHeader(HttpHeaders.AUTHORIZATION, headerValue);
        LOGGER.log(Level.FINE, "Header value: " + headerValue);
    }
}

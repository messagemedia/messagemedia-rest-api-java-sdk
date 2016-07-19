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
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpDateGenerator;
import org.apache.http.util.Args;

import java.io.IOException;

/**
 * Adds the {@link HTTP#DATE_HEADER} for every request
 */
public class RequestDateInterceptor implements HttpRequestInterceptor {

    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

    @Override
    public void process(HttpRequest request, HttpContext context)
            throws HttpException, IOException {
        Args.notNull(request, "HTTP request");
        if (!request.containsHeader(HTTP.DATE_HEADER)) {
            final String httpdate = DATE_GENERATOR.getCurrentDate();
            request.setHeader(HTTP.DATE_HEADER, httpdate);
        }
    }

}

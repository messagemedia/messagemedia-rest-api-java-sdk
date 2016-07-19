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

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class HmacMmv2InterceptorTest {

    private static final String SECRET_KEY = "456";
    private static final String DATE_HEADER = "Fri, 11 Jul 2014 07:16:51 GMT";
    private static final String API_KEY = "123";

    private static final String EXPECTED_AUTH_HEADER_CONTENT = "hmac username=\"123\", algorithm=\"hmac-sha1\", headers=\"Date request-line\", "
                                                               + "signature=\"bUAvEbDgutL51S3dmtpFp5ONgy0=\"";

    private HmacMmv2Interceptor interceptor;
    private HttpEntityEnclosingRequest request;

    @Before
    public void setUp() throws IllegalStateException, IOException {
        this.interceptor = new HmacMmv2Interceptor(API_KEY, SECRET_KEY);

        request = Mockito.mock(HttpEntityEnclosingRequest.class);
        Mockito.when(request.getFirstHeader(HTTP.DATE_HEADER)).thenReturn(new BasicHeader("", DATE_HEADER));
        Mockito.when(request.getRequestLine()).thenReturn(new BasicRequestLine("POST", "/v1/messages", HttpVersion.HTTP_1_1));
    }

    @Test
    public void testInterceptor() throws HttpException, IOException {
        this.interceptor.process(request, Mockito.mock(HttpContext.class));
        Mockito.verify(request).setHeader(HttpHeaders.AUTHORIZATION, EXPECTED_AUTH_HEADER_CONTENT);
    }
}
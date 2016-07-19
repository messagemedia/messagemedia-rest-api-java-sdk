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

import com.messagemedia.restapi.client.v1.RestApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.messagemedia.restapi.client.v1.internal.HttpMethod.GET;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RestRequestBuilderTest {

    @Mock
    private RestClient client;

    @Test
    public void shouldEncodeRequestParameters() {
        String path = "/path";

        assertEquals("http://localhost/path?phone=123", url(requestBuilder(path).requestParam("phone", "123")));
        assertEquals("http://localhost/path?phone=%2B123", url(requestBuilder(path).requestParam("phone", "+123")));
        assertEquals("http://localhost/path?phone=%24123", url(requestBuilder(path).requestParam("phone", "$123")));
    }

    @Test
    public void shouldReplacePathVariables() {
        String path = "/path/{username}/{email}";

        String url = url(requestBuilder(path).pathVariable("username", "bob").pathVariable("email", "bob@bob.com"));

        assertEquals("http://localhost/path/bob/bob@bob.com", url);
    }

    @Test(expected = RestApiException.class)
    public void shouldRaiseAnExceptionWithUnresolvedPathVariables() throws RestApiException {
        String path = "/path/{username}";

        requestBuilder(path).execute();
    }

    private String url(RestRequestBuilder builder) {
        return builder.build().getHttpRequest().getURI().toString();
    }

    private RestRequestBuilder requestBuilder(String path) {
        return RestRequestBuilder.create("http://localhost", path, GET, client);
    }

}

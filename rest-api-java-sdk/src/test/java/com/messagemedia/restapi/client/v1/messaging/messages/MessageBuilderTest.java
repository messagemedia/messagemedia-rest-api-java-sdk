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

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MessageBuilderTest {

    private MessageBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new MessageBuilder();

        builder.content("content");
        builder.destinationNumber("123");
    }

    @Test
    public void shouldSetMetaProperty() {
        builder.metadataProperty("prop", "value");
        assertEquals("value", builder.build().getMetadata().get("prop"));
    }

    @Test
    public void shouldSetMetadata() throws Exception {
        builder.metadata(Collections.singletonMap("prop", "value"));
        assertEquals("value", builder.build().getMetadata().get("prop"));
    }
}

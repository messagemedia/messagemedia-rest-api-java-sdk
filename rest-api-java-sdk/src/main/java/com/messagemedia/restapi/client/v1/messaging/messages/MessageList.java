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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of {@link Message}
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class MessageList {

    private final List<Message> messages;

    private final MessageListProperties properties;

    @JsonCreator
    public MessageList(@JsonProperty("messages") List<Message> messages, @JsonProperty("properties") MessageListProperties properties) {
        this.messages = new ArrayList<Message>(messages);
        if (this.messages.isEmpty()) {
            throw new IllegalArgumentException("Property 'messages' must contain at least one message.");
        }
        if (properties == null) {
            this.properties = MessageListProperties.withSendingMode(MessageSendingMode.PRODUCTION);
        } else {
            this.properties = properties;
        }
    }

    public MessageList(List<Message> messages) {
        this(messages, MessageListProperties.withSendingMode(MessageSendingMode.PRODUCTION));
    }

    //Checkstyle: START IGNORING
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageList that = (MessageList) o;

        if (messages != null ? !messages.equals(that.messages) : that.messages != null) {
            return false;
        }
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) {
            return false;
        }

        return true;
    }
    //Checkstyle: RESUME

    @Override
    public int hashCode() {
        int result = messages != null ? messages.hashCode() : 0;
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }

    /**
     * Gets the message list. May be empty.
     *
     * @return the new messages. Every caller receives his own copy of the lists. The message objects are shared!
     */
    public List<Message> getMessages() {
        return new ArrayList<Message>(messages);
    }

    /**
     * Gets the properties of this message.
     *
     * @return the properties
     * @see MessageListProperties
     */
    public MessageListProperties getProperties() {
        return properties;
    }
}

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

/**
 * Properties of the list of messages that you are sending.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class MessageListProperties {

    private final MessageSendingMode sendingMode;

    MessageListProperties(MessageSendingMode sendingMode) {
        this.sendingMode = sendingMode;
        if (this.sendingMode == null) {
            throw new IllegalArgumentException("Property 'sending_mode' cannot be null.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageListProperties that = (MessageListProperties) o;

        if (sendingMode != that.sendingMode) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return sendingMode != null ? sendingMode.hashCode() : 0;
    }

    /**
     * Gets the sending mode.
     *
     * @return the sending mode that will be sent
     */
    public MessageSendingMode getSendingMode() {
        return sendingMode;
    }

    public static MessageListProperties withSendingMode(MessageSendingMode sendingMode) {
        return new MessageListProperties(sendingMode);
    }
}

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
package com.messagemedia.restapi.client.v1.messaging.replies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@link Reply} instances.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class ReplyList {

    private final List<Reply> replies;

    @JsonCreator
    ReplyList(@JsonProperty(value = "replies") List<Reply> replies) {
        if (replies == null) {
            throw new IllegalArgumentException("Property 'replies' cannot be null or empty.");
        } else {
            this.replies = new ArrayList<Reply>(replies);
        }
    }

    /**
     * Gets the replies list.
     *
     * @return the replies list. May be empty.
     */
    public List<Reply> getReplies() {
        return new ArrayList<Reply>(replies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReplyList replyList = (ReplyList) o;

        return replies.equals(replyList.replies);
    }

    @Override
    public int hashCode() {
        return replies.hashCode();
    }
}

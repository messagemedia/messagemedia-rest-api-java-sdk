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

/**
 * A confirmed (or not) reply.
 *
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class ReplyConfirmationResultItem {

    private final String replyId;
    private final boolean confirmed;

    @JsonCreator
    ReplyConfirmationResultItem(
            @JsonProperty(value = "reply_id") String replyId,
            @JsonProperty(value = "confirmed") Boolean confirmed) {
        this.replyId = replyId;
        if (this.replyId == null || this.replyId.isEmpty()) {
            throw new IllegalArgumentException("Property 'reply_id' cannot be null or empty.");
        }
        if (confirmed == null) {
            throw new IllegalArgumentException("Property 'confirmed' cannot be null or empty.");
        } else {
            this.confirmed = confirmed;
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

        ReplyConfirmationResultItem that = (ReplyConfirmationResultItem) o;

        return confirmed == that.confirmed
                && !(replyId != null ? !replyId.equals(that.replyId) : that.replyId != null);
    }

    @Override
    public int hashCode() {
        int result = replyId != null ? replyId.hashCode() : 0;
        result = 31 * result + (confirmed ? 1 : 0);
        return result;
    }

    /**
     * Gets the reply ID.
     * 
     * @return the reply ID
     */
    public String getReplyId() {
        return replyId;
    }

    /**
     * Checks if the reply was confirmed.
     *
     * @return <code>true</code> if reply has been confirmed, <code>false</code> otherwise
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}

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
 * A list of {@link ReplyConfirmationResultItem}
 * <p/>
 * This object is received when you try to confirm a
 * {@link com.messagemedia.restapi.client.v1.internal.ReplyConfirmation} using
 * {@link com.messagemedia.restapi.client.v1.messaging.RestApiMessagingClient#confirmReplies(java.util.Collection)}.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class ReplyConfirmationResult {

    private final List<ReplyConfirmationResultItem> items;
    private final String message;
    private final List<String> details;

    @JsonCreator
    ReplyConfirmationResult(@JsonProperty(value = "reply_ids") List<ReplyConfirmationResultItem> items,
                            @JsonProperty(value = "message", required = false) String message,
                            @JsonProperty(value = "details", required = false) List<String> details) {
        if (items == null) {
            throw new IllegalArgumentException("Property 'reply_ids' cannot be null.");
        } else {
            this.items = new ArrayList<ReplyConfirmationResultItem>(items);
        }

        this.message = message;
        this.details = details == null ? null : new ArrayList<String>(details);
    }

    /**
     * Gets the list of {@link ReplyConfirmationResultItem}
     *
     * @return the list of reply IDs
     */
    public List<ReplyConfirmationResultItem> getItems() {
        return new ArrayList<ReplyConfirmationResultItem>(items);
    }

    /**
     * Gets the details of the request.
     *
     * @return the details. May be empty, but never null.
     */
    public List<String> getDetails() {
        return new ArrayList<String>(details);
    }

    /**
     * Gets the message indicating the result of the operation.
     *
     * @return the message. May be null or empty.
     */
    public String getMessage() {
        return message;
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

        ReplyConfirmationResult that = (ReplyConfirmationResult) o;

        if (details != null ? !details.equals(that.details) : that.details != null) {
            return false;
        }
        if (!items.equals(that.items)) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }

        return true;
    }
    //Checkstyle: RESUME

    @Override
    public int hashCode() {
        int result = items.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

}

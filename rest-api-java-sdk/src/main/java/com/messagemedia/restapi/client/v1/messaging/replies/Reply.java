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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a reply that has been received from a destination handset.
 * <p/>
 * Instances of this class are immutable, and can be considered thread-safe.
 */
public final class Reply {

    private final String content;
    private final DateTime dateReceived;
    private final String messageId;
    private final String replyId;
    private final String sourceNumber;
    private final String destinationNumber;
    private final Map<String, String> metadata;

    @JsonCreator
    Reply(@JsonProperty(value = "content") String content,
          @JsonProperty(value = "date_received") DateTime dateReceived,
          @JsonProperty(value = "message_id") String messageId,
          @JsonProperty(value = "reply_id") String replyId,
          @JsonProperty(value = "source_number") String sourceNumber,
          @JsonProperty(value = "destination_number") String destinationNumber,
          @JsonProperty(value = "metadata") Map<String, String> metadata) {

        this.content = validatedContent(content);

        this.messageId = messageId;

        this.replyId = validatedReplyId(replyId);
        this.sourceNumber = validatedSourceNumber(sourceNumber);

        this.destinationNumber = destinationNumber;
        this.dateReceived = validatedDateReceived(dateReceived);
        this.metadata = Collections.unmodifiableMap((metadata != null) ? metadata : Collections.<String, String>emptyMap());
    }

    private DateTime validatedDateReceived(DateTime dateReceived) {
        if (dateReceived == null) {
            throw new IllegalArgumentException("Property 'date_received' cannot be null.");
        }
        return new DateTime(dateReceived.getMillis(), DateTimeZone.UTC);
    }

    private String validatedSourceNumber(String sourceNumber) {
        if (sourceNumber == null || sourceNumber.isEmpty()) {
            throw new IllegalArgumentException("Property 'source_number' cannot be null or empty.");
        }
        return sourceNumber;
    }

    private String validatedReplyId(String replyId) {
        if (replyId == null || replyId.isEmpty()) {
            throw new IllegalArgumentException("Property 'reply_id' cannot be null or empty.");
        }
        return replyId;
    }

    private String validatedContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Property 'content' cannot be null");
        }
        return content;
    }

    /**
     * Gets the content of the reply
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the timestamp of this reply (UTC).
     * <p/>
     * This attribute specifies the date and time at which the gateway received the reply of the message.
     *
     * @return the timestamp
     */
    public DateTime getDateReceived() {
        return new DateTime(dateReceived.getMillis(), DateTimeZone.UTC);
    }

    /**
     * Gets the message ID that this replies refers to.
     *
     * @return the message ID or null if this reply does not refer to a sent message.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Gets the ID of this reply.
     *
     * @return the reply ID
     */
    public String getReplyId() {
        return replyId;
    }

    /**
     * Gets the source number.
     *
     * @return the source number
     */
    public String getSourceNumber() {
        return sourceNumber;
    }

    /**
     * Gets the destination number.
     *
     * @return the destination number
     */
    public String getDestinationNumber() {
        return destinationNumber;
    }

    /**
     * Return the metadata of the message
     *
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
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

        Reply reply = (Reply) o;

        if (content != null ?
                !content.equals(reply.content) :
                reply.content != null) {
            return false;
        }
        if (dateReceived != null ?
                !dateReceived.equals(reply.dateReceived) :
                reply.dateReceived != null) {
            return false;
        }
        if (messageId != null ?
                !messageId.equals(reply.messageId) :
                reply.messageId != null) {
            return false;
        }
        if (replyId != null ?
                !replyId.equals(reply.replyId) :
                reply.replyId != null) {
            return false;
        }
        if (sourceNumber != null ?
                !sourceNumber.equals(reply.sourceNumber) :
                reply.sourceNumber != null) {
            return false;
        }
        if (destinationNumber != null ?
                !destinationNumber.equals(reply.destinationNumber) :
                reply.destinationNumber != null) {
            return false;
        }
        if (!Objects.equals(metadata, reply.metadata)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (replyId != null ? replyId.hashCode() : 0);
        result = 31 * result + (sourceNumber != null ? sourceNumber.hashCode() : 0);
        result = 31 * result + (destinationNumber != null ? destinationNumber.hashCode() : 0);
        result = 31 * result + (dateReceived != null ? dateReceived.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        return result;
    }
    //Checkstyle: RESUME
}

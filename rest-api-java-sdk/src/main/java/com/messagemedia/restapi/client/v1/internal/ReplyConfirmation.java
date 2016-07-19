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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a list of IDs corresponding to replies that are to be confirmed.
 *
 * Instances of this class are immutable, and can be considered thread-safe.
 */
final class ReplyConfirmation {

    private final Collection<String> replyIds;

    public ReplyConfirmation(Collection<String> replyIds) {
        this.replyIds = new ArrayList<String>(replyIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReplyConfirmation that = (ReplyConfirmation) o;

        return replyIds.equals(that.replyIds);
    }

    @Override
    public int hashCode() {
        return replyIds.hashCode();
    }

    /**
     * Gets the current list of reply IDs.
     *
     * @return A collection of reply IDs
     */
    public Collection<String> getReplyIds() {
        return new ArrayList<String>(replyIds);
    }
}

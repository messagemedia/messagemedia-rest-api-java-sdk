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
package com.messagemedia.restapi.client.v1.cli;

import com.messagemedia.restapi.client.v1.RestApiException;
import com.messagemedia.restapi.client.v1.RestApiResponse;
import com.messagemedia.restapi.client.v1.messaging.replies.Reply;
import com.messagemedia.restapi.client.v1.messaging.replies.ReplyList;

import java.util.List;

/**
 * A command line utility that retrieves unconfirmed replies.
 */
public class CheckReplies extends AbstractAction {

    @Override
    public void execute(String[] args) throws RestApiException {
        try {
            final RestApiResponse<ReplyList> response = Settings.CLIENT.messaging().checkReplies();
            List<Reply> replies = response.getPayload().getReplies();
            if (replies.isEmpty()) {
                System.out.println("No unconfirmed replies.");
            } else {
                System.out.println("Retrieved the following replies:");
                for (Reply item : replies) {
                    System.out.println("  ID: " + item.getReplyId());
                    System.out.println("    Content: " + item.getContent());
                    System.out.println("    Date Received: " + item.getDateReceived());
                    System.out.println("    Message ID: " + item.getMessageId());
                    System.out.println("    Source Number (handset): " + item.getSourceNumber());
                    System.out.println("    Destination Number: " + item.getDestinationNumber());
                    System.out.println("    Metadata: " + item.getMetadata());
                }
            }
        } catch (RestApiException e) {
            logException(e);
        }
    }

    @Override
    protected String getActionDescription() {
        return "retrieving replies";
    }
}

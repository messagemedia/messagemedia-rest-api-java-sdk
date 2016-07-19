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

public class CancelMessage extends AbstractAction {

    @Override
    public void execute(String[] args) throws RestApiException {
        if (args.length != 1) {
            System.out.println("Please provide exactly one message ID as a command line argument.");
            System.exit(1);
        }
        try {
            Settings.CLIENT.messaging().cancelMessage(args[0]);
            System.out.println("Message cancelled");
        } catch (RestApiException e) {
            logException(e);
        }
    }

    @Override
    protected String getActionDescription() {
        return "cancelling message";
    }
}
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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ActionFactoryTest {

    private ActionFactory actionFactory = new ActionFactory();

    @Test
    public void canCreateCheckDeliveryReportsAction() {
        Action action = actionFactory.createAction("CheckDeliveryReports");

        assertThat(action, instanceOf(CheckDeliveryReports.class));
    }

    @Test
    public void canCreateConfirmDeliveryReportsAction() {
        Action action = actionFactory.createAction("ConfirmDeliveryReports");

        assertThat(action, instanceOf(ConfirmDeliveryReports.class));
    }

    @Test
    public void canCreateConfirmRepliesAction() {
        Action action = actionFactory.createAction("ConfirmReplies");

        assertThat(action, instanceOf(ConfirmReplies.class));
    }

    @Test
    public void canCreateGetMessageStatusAction() {
        Action action = actionFactory.createAction("GetMessageStatus");

        assertThat(action, instanceOf(GetMessageStatus.class));
    }

    @Test
    public void canCreateCancelMessageAction() {
        Action action = actionFactory.createAction("CancelMessage");

        assertThat(action, instanceOf(CancelMessage.class));
    }

    @Test
    public void canCreateCheckRepliesAction() {
        Action action = actionFactory.createAction("CheckReplies");

        assertThat(action, instanceOf(CheckReplies.class));
    }

    @Test
    public void canCreateSendMessageAction() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        Action action = actionFactory.createAction("SendMessage");

        assertThat(action, instanceOf(SendMessage.class));
    }

}

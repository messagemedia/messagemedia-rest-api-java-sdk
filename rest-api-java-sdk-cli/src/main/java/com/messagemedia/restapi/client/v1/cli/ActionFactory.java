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

public class ActionFactory {

    public Action createAction(String actionName) {

        try {
            return (Action) Class.forName(classNameForAction(actionName)).newInstance();
        } catch (Exception e) {
            throw new ActionException("Failed to create action: " + actionName, e);
        }
    }

    private String classNameForAction(String actionName) {
        return getClass().getPackage().getName() + "." + actionName;
    }

}

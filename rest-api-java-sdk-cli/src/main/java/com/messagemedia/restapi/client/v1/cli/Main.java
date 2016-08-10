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

import java.util.Arrays;

/**
 * This class is main entry point of the command line interface. It delegates to
 * all the other classes, which also can be run individually.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length >= 1) {
            try {
                Action action = new ActionFactory().createAction(args[0]);
                action.execute(Arrays.copyOfRange(args, 1, args.length));
            } catch (ActionException e) {
                System.err.println("Unrecognised action '" + args[0] + "'");
                System.exit(1);
            }
        } else {
            System.err.println("Missing action.");
            System.exit(1);
        }
    }
}

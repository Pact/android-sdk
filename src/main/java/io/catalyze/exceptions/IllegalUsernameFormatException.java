/*
 * Copyright (C) 2013 catalyze.io, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.catalyze.exceptions;

/**
 * Thrown when there is an attempt to set a username to an illegal value.
 *
 * @see io.catalyze.android.external.User#checkUsernameFormat(String)  for format
 *      requirements
 */
public class IllegalUsernameFormatException extends Exception {

    /**
     * @param username which is improperly formatted
     */
    public IllegalUsernameFormatException(final String username) {
        super(username + " is not a valid username");
    }

}
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

package io.catalyze.android.library.exceptions;


/**
 * Thrown when there is an attempt to set a password to an illegal value.
 *
 * @see io.catalyze.android.library.external.User#checkPasswordFormat(String) for format
 *      requirements
 */
public class IllegalPasswordFormatException extends Exception {

    /**
     * @param password which has an invalid format
     */
    public IllegalPasswordFormatException(final String password) {
        super(password + " is not a valid password");
    }

}
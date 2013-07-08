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
 * Thrown when there is an attempt to set an age to an illegal value.
 *
 * @see io.catalyze.android.external.Person#setAge(int) for format requirements
 */
public class IllegalAgeFormatException extends Exception {

    /**
     * @param age which has an invalid format
     */
    public IllegalAgeFormatException(final int age) {
        super(Integer.toString(age) + " is not a valid age");
    }

}

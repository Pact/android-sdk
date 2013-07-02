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

package io.catalyze.android.library.external;

import com.google.common.base.Strings;

/**
 * Provides a reference as to the type of the response expected for a question.
 */
public enum ResponseType {

    // TODO: Provide a mechanism for casting or type checking

    BOOLEAN("boolean"), INTEGER("integer"), DOUBLE("double"), LONG("long"), STRING("string");

    private String mJsonText;

    private ResponseType(String jsonText) {
        mJsonText = jsonText;
    }

    /**
     * Creates this {@link ResponseType} from the provided string if the provided string is valid.
     * @param value the string representation of the {@link ResponseType}
     * @return the {@link ResponseType} the provided string represents or {@code null} if the string is not valid.
     */
    public static ResponseType fromString(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            for (ResponseType t : ResponseType.values()) {
                if (value.equals(t.toString())) {
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("No enum with json text of (" + value + ") found");
    }

    @Override
    public String toString() {
        return mJsonText;
    }

}

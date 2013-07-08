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

package io.catalyze.sdk;

import com.google.common.base.Strings;

/**
 * Provides a reference as to the gender of a {@link io.catalyze.sdk.android.Person}.
 */
public enum Gender {

    FEMALE("female"), MALE("male"), UNDIFFERENTIATED("undifferentiated");

    private String mJsonText;

    private Gender(String jsonText) {
        mJsonText = jsonText;
    }

    /**
     * Creates this {@link Gender} from the provided string if the provided string is valid.
     * @param value the string representation of the {@link Gender}
     * @return the {@link Gender} the provided string represents or {@code null} if the string is not valid.
     */
    public static Gender fromString(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            for (Gender g : Gender.values()) {
                if (value.equals(g.toString())) {
                    return g;
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

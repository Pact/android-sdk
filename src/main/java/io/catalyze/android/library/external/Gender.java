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
 * Created by marius on 6/18/13.
 */
public enum Gender {

    FEMALE("female"), MALE("male"), UNKNOWN("unknown");

    private String mJsonText;

    private Gender(String jsonText) {
        mJsonText = jsonText;
    }

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

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
 * Created by marius on 6/28/13.
 */
public enum TransactionType {

    ANSWER("answer"), CUSTOM("custom");

    private String mJsonText;

    private TransactionType(String jsonText) {
        mJsonText = jsonText;
    }

    public static TransactionType fromString(String value) {
        if (!Strings.isNullOrEmpty(value)) {
            for (TransactionType t : TransactionType.values()) {
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

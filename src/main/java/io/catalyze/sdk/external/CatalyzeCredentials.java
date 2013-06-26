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

package io.catalyze.sdk.external;

import android.content.Context;
import android.content.SharedPreferences;

import io.catalyze.sdk.internal.CatalyzeConstants;

/**
 * Created by marius on 6/20/13.
 */
public class CatalyzeCredentials {

    private static final String sUsernameKey = "username";
    private static final String sPasswordKey = "password";
    private static final String sSessionKey = "session_key";

    private Context mContext;

    public CatalyzeCredentials(Context context) {
        mContext = context;
    }

    public boolean setPassword(String password) {
        return set(sPasswordKey, password);
    }

    public String getPassword() {
        return get(sPasswordKey, "");
    }

    public boolean setUsername(String username) {
        return set(sUsernameKey, username);
    }

    public String getUsername() {
        return get(sUsernameKey, "");
    }

    private boolean set(String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(CatalyzeConstants.PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    private String get(String key, String deflt) {
        SharedPreferences prefs = mContext.getSharedPreferences(CatalyzeConstants.PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, deflt);
    }
}

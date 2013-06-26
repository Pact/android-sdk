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

package io.catalyze.sdk.internal;

/**
 * Created by marius on 6/19/13.
 */
public class Response {

    private boolean mHasError = false;
    private int mStatusCode;
    private String mResponseMessage;
    private String mBody;

    Response setStatus(int status, String responseMessage) {
        mStatusCode = status;
        mResponseMessage = responseMessage;
        mHasError = status / 100 != 2;
        return this;
    }

    Response setBody(String content) {
        mBody = content;
        return this;
    }

    public boolean hasError() {
        return mHasError;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public String getBody() {
        return mBody;
    }
}

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

package io.catalyze.sdk.android;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marius on 6/21/13.
 */
public class OldCatalyzeJsonObjectRequest extends JsonObjectRequest {

    private final Map<String, String> mHeaders = new HashMap<String, String>();

    private Priority mPriority;

    public OldCatalyzeJsonObjectRequest(int method, String url, JSONObject jsonRequest,
                                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        setShouldCache(false);
        mHeaders.put(OldCatalyzeObject.sSessionHeader, OldCatalyzeObject.sSessionToken);
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public OldCatalyzeJsonObjectRequest putHeader(String k, String v) {
        mHeaders.put(k, v);
        return this;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String sessionId = response.headers.get(OldCatalyzeObject.sSessionHeader);
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put(OldCatalyzeObject.sSessionId, sessionId);
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}

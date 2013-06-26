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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marius on 6/20/13.
 */
public class CatalyzeUser extends CatalyzeObject {

    private static final String sPath = "/user";
    private static final String sSessionPath = "/user/authentication";
    private static final String sPassword = "password";
    private static final String sUsername = "username";
    private static final String sPersonId = "person_id";
    private static final String sUserId = "user_id";

    public CatalyzeUser(String username, String password) {
        super();
        setUsername(username);
        setPassword(password);
    }

    private CatalyzeUser(){}

    public CatalyzeJsonObjectRequest create(RequestQueue requestQueue, final Response.Listener<CatalyzePerson> listener, Response.ErrorListener errorListener) {
        return authenticate(requestQueue, listener, errorListener, sBasePath + sPath, mContent);
    }

    public CatalyzePerson create(RequestQueue requestQueue, Response.ErrorListener errorListener, final CatalyzePerson person) {
        Map<String, Object> content = new HashMap<String, Object>();
        content.putAll(person.mContent);
        content.putAll(mContent);

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST, sBasePath + sPath, new JSONObject(content), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                person.inflateFromJson(object);
            }
        }, errorListener);

        requestQueue.add(request);

        return person;
    }

    public CatalyzeUser update(RequestQueue requestQueue, Response.ErrorListener errorListener) {
        final CatalyzeUser retVal = new CatalyzeUser();

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.PUT, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                retVal.inflateFromJson(object);
            }
        }, errorListener);

        requestQueue.add(request);

        return retVal;
    }

    public void delete(RequestQueue requestQueue, Response.ErrorListener errorListener) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.DELETE, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
            }
        }, errorListener);

        requestQueue.add(request);
    }

    /*
    errorListener invoked if bad username/password (401)
     */
    public CatalyzeJsonObjectRequest login(RequestQueue requestQueue, final Response.Listener<CatalyzePerson> listener, Response.ErrorListener errorListener) {
       return authenticate(requestQueue, listener, errorListener, sBasePath + sSessionPath, mContent);
    }

    public void logout(RequestQueue requestQueue, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.DELETE, sBasePath + sSessionPath, null, responseListener, errorListener);
        requestQueue.add(request);
    }

    private CatalyzeJsonObjectRequest authenticate(RequestQueue requestQueue, final Response.Listener<CatalyzePerson> listener, Response.ErrorListener errorListener, String path, Map<String, Object> content) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST, path, new JSONObject(content), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                final CatalyzePerson retVal = new CatalyzePerson();
                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                retVal.inflateFromJson(object);
                listener.onResponse(retVal);
            }
        }, errorListener);
        requestQueue.add(request);
        return request;
    }

    public String getPassword() {
        return super.getString(sPassword);
    }

    public CatalyzeUser setPassword(String password) {
        //TODO: Implement some security requirements
        mContent.put(sPassword, checkNotNull(password, "Cannot have a null password."));
        return this;
    }

    public String getUsername() {
        return super.getString(sUsername);
    }

    public CatalyzeUser setUsername(String username) {
        //TODO: Implement some regex checking
        mContent.put(sUsername, checkNotNull(username, "Cannot have a null username."));
        return this;
    }
}

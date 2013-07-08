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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.catalyze.sdk.exceptions.IllegalPasswordFormatException;
import io.catalyze.sdk.exceptions.IllegalUsernameFormatException;

/**
 * A {@link CatalyzeObject} used to represent Users on the catalyze.io API.
 *
 * <p>Uses of this class follow a pattern: <ol> <li>Verify the user'sTransactionPath username and password using
 * {@link #checkUsernameFormat(String)} and {@link #checkPasswordFormat(String)} respectively.</li>
 * <li>Create a new {@code User} instance.</li> <li> If the user is a returning user, login the user
 * by calling {@link #login(com.android.volley.RequestQueue, com.android.volley.Response.Listener,
 * com.android.volley.Response.ErrorListener) login(queue, null, errorListener}. This will
 * authenticate the user against the catalyze.io backend.</li> <li>If the user is a new user, either
 * retrieve a empty {@link Person} for the user by calling {@link #create(com.android.volley.RequestQueue,
 * com.android.volley.Response.ErrorListener, com.android.volley.Response.Listener)} or create a
 * {@link Person} yourself and call {@link #create(com.android.volley.RequestQueue,
 * com.android.volley.Response.ErrorListener, Person)}. The user will automatically be
 * authenticated.</li>
 *
 * </ol></p>
 *
 * <p>For example, to create a new user without any metadata:</p>
 * <pre>    {@code
 *      RequestQueue queue = Volley.newRequestQueue((Context) this);
 *      User user = new User("name@example.com", "superSecretPassword");
 *      Request request = user.create(queue, new Response.Listener<Person>() {
 *
 *              @Override
 *              public void onResponse(Person person) {
 *                  returnPersonToCaller(person);
 *              }
 *          }, new Response.ErrorListener() {
 *
 *              @Override
 *              public void onErrorResponse(VolleyError error) {
 *                  showErrorMessage(error);
 *              }
 *          });
 *
 * }</pre>
 *
 * <h3>Cancelling Requests</h3> To cancel a request, call {@code Request.cancel()}
 */
public class User extends CatalyzeObject {

    protected static final String sSessionPath = "/user/authentication";

    private static final String sPath = "/user";

    /**
     * @param username the username of the current user
     * @param password the password of the current user
     * @throws io.catalyze.sdk.exceptions.IllegalUsernameFormatException
     *          if the username provided does not meet the catalyze.io API format requirements.
     *          Consider using {@link #checkUsernameFormat(String)} before constructing.
     * @throws io.catalyze.sdk.exceptions.IllegalPasswordFormatException
     *          if the password provided does not meet the catalyze.io API format requirements.
     *          Consider using {@link #checkPasswordFormat(String)} before constructing.
     * @see #checkPasswordFormat(String)
     * @see #checkUsernameFormat(String)
     */
    public User(
            String username,
            String password) throws IllegalUsernameFormatException, IllegalPasswordFormatException {
        super();
        setUsername(username);
        setPassword(password);
    }

    /**
     * Verifies the formatting of the provided username against the requirements of the catalyze.io
     * API. <p/>
     * <pre>{@code
     *      if (User.checkUsernameFormat("user@example.com") ==
     * User.UsernameStatus.VALID) {
     *          // Proceed
     *      } else {
     *          // display error message
     *      }
     * }</pre>
     *
     * @param username the username which should have it'sTransactionPath format checked
     * @return a {@link UsernameStatus} representing that status of the supplied username
     */
    public static UsernameStatus checkUsernameFormat(String username) {
        //TODO: Implement some regex checking
        return UsernameStatus.VALID;
    }

    /**
     * Verifies the formatting of the provided password against the requirements of the catalyze.io
     * API. <p/>
     * <pre>{@code
     *      if (User.checkPasswordFormat("mySuperSecretPassword") ==
     * User.PasswordStatus.VALID) {
     *          // Proceed
     *      } else {
     *          // display error message
     *      }
     * }</pre>
     *
     * @param password the password which should have it'sTransactionPath format checked
     * @return a {@link PasswordStatus} representing that status of the supplied password
     */
    public static PasswordStatus checkPasswordFormat(String password) {
        //TODO: Implement some security requirements
        return PasswordStatus.VALID;
    }

    /**
     * Creates this {@link User} on the catalyze.io API backend. This {@link User} is not modified
     * as a result of this method. If an error occurs performing the HTTP interaction, the
     * interaction is not retried. This {@link User} is not cached for future upload.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @param listener      to handle the response from the catalyze.io API servers. A {@link
     *                      Person} will be returned to this listener which can be returned to the
     *                      calling {@link android.content.Context}.
     * @return the {@link Request} representing this server interaction
     */
    public Request create(
            RequestQueue queue, Response.ErrorListener errorListener,
            Response.Listener<Person> listener) {
        return authenticate(queue, listener, errorListener, sBasePath + sPath, mContent);
    }

    // TODO: Determine if this is needed
    public Request create(
            RequestQueue queue, Response.ErrorListener errorListener, final Person person) {
        Map<String, Object> content = new HashMap<String, Object>();
        content.putAll(person.mContent);
        content.putAll(mContent);

        CatalyzeJsonObjectRequest request =
                new CatalyzeJsonObjectRequest(Request.Method.POST, sBasePath + sPath,
                        new JSONObject(content), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {

                        CatalyzeObject.sSessionToken =
                                (String) object.remove(CatalyzeObject.sSessionId);
                        person.inflateFromJson(object);
                    }
                }, errorListener);

        queue.add(request);

        return request;
    }

    /**
     * Updates this {@link User} on the catalyze.io API backend. This overwrites any previous data
     * stored for this user. If an error occurs performing the HTTP interaction, the interaction is
     * not retried.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request update(RequestQueue queue, Response.ErrorListener errorListener) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.PUT,
                sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);

                // TODO: do we really need to inflate? what is returned? if not, use parent method.
                User.this.inflateFromJson(object);
            }
        }, errorListener);

        queue.add(request);
        return request;
    }

    /**
     * Deletes this {@link User} from the catalyze.io API backend. Also deletes the associated
     * {@link Person} on the backend (but leaves the local copy, if one exists, intact.) If an error
     * occurs performing the HTTP interaction, the interaction is not retried.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request delete(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.delete(queue, errorListener, sBasePath + sPath);
    }

    /**
     * Logs this {@link User} into the catalyze.io API backend. If an error occurs performing the
     * HTTP interaction, the interaction is not retried.
     *
     * <p>This method must be called whenever a user'sTransactionPath session on the server has expired.</p>
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @param listener      to handle the response from the catalyze.io API servers. A {@link
     *                      Person} will be returned to this listener which can be returned to the
     *                      calling {@link android.content.Context}.
     * @return the {@link Request} representing this server interaction
     */
    public Request login(RequestQueue queue, Response.Listener<Person> listener,
            Response.ErrorListener errorListener) {
        return authenticate(queue, listener, errorListener, sBasePath + sSessionPath, mContent);
    }

    /**
     * Revokes this {@link User}'sTransactionPath  session key from the catalyze.io API backend. This {@link User}
     * is not modified as a result of this method, however future interactions will require a call
     * to {@link #login(com.android.volley.RequestQueue, com.android.volley.Response.Listener,
     * com.android.volley.Response.ErrorListener)}. If an error occurs performing the HTTP
     * interaction, the interaction is not retried.
     *
     * <p>This method may optionally be called when a user intends to end a session. If this method
     * is not called, the user'sTransactionPath session will simply expire after a certain amount of time. This
     * method will likely rarely be used.</p>
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request logout(RequestQueue queue, Response.ErrorListener errorListener) {
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                CatalyzeObject.sSessionToken = "";
            }
        };
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.DELETE,
                sBasePath + sSessionPath, null, listener, errorListener);
        queue.add(request);
        return request;
    }

    private CatalyzeJsonObjectRequest authenticate(RequestQueue requestQueue,
            final Response.Listener<Person> listener, Response.ErrorListener errorListener,
            String path, Map<String, Object> content) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST, path,
                new JSONObject(content), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                final Person retVal = new Person();
                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                retVal.inflateFromJson(object);
                listener.onResponse(retVal);
            }
        }, errorListener);
        requestQueue.add(request);
        return request;
    }

    /**
     * @return the password for this user
     */
    public String getPassword() {
        return super.getString(sPasswordKey);
    }

    /**
     * @param password the new password for this user
     * @return this {@link User} instance
     * @throws IllegalPasswordFormatException if the catalyze.io API format requirements for
     *                                        passwords are not met
     * @see User#checkPasswordFormat(String) for format requirements
     */
    public User setPassword(String password) throws IllegalPasswordFormatException {

        if (checkPasswordFormat(password) == PasswordStatus.VALID) {
            mContent.put(sPasswordKey, password);
        } else {
            throw new IllegalPasswordFormatException(password);
        }
        return this;
    }

    /**
     * @return the username for this user
     */
    public String getUsername() {
        return super.getString(sUsernameKey);
    }

    /**
     * @param username the new username for this user
     * @return this {@link User} instance
     * @throws IllegalUsernameFormatException if the catalyze.io API format requirements for
     *                                        usernames are not met
     * @see User#checkUsernameFormat(String) for format requirements
     */
    public User setUsername(String username) throws IllegalUsernameFormatException {

        if (checkUsernameFormat(username) == UsernameStatus.VALID) {
            mContent.put(sUsernameKey, username);
        } else {
            throw new IllegalUsernameFormatException(username);
        }
        return this;
    }

    /**
     * Do not use this method. Calls to this method will simply be dropped as {@link User}sTransactionPath do not
     * have optional fields.
     *
     * @param k do not use
     * @param v do not use
     * @return null
     */
    @Override
    public CatalyzeObject put(String k, Object v) {
        return null;
    }

    public static enum PasswordStatus {VALID}


    public static enum UsernameStatus {VALID}
}



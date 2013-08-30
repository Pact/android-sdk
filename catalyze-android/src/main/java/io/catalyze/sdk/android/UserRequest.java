package io.catalyze.sdk.android;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import io.catalyze.sdk.android.CatalyzeRequest;
import io.catalyze.sdk.android.User;

/**
 * Created by mvolkhart on 8/24/13.
 */
public class UserRequest {

    //FIXME: Hardcoded app id
    private static final String URL = "https://api.catalyze.io/v1/2/user";

    public static class Builder extends CatalyzeRequest.Builder<User, User> {

        public Builder(Context context) {
            super(context, URL);
        }

        @Override
        public CatalyzeRequest.Builder<User, User> setContent(User user) {
            setJson(user.asJson());
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<User, User> setListener(final Response.Listener<User>
                                                                           listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("UserRequest", response.toString(2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listener.onResponse(new User(response));
                }
            });
            return this;
        }
    }
}

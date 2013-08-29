package io.catalyze.sdk.android.user;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

import io.catalyze.sdk.android.CatalyzeRequest;
import io.catalyze.sdk.android.User;

/**
 * Created by mvolkhart on 8/24/13.
 */
public class UserRequest {

    private static final String URL = "https://api.catalyze.io/v1/user";

    public static class Builder extends CatalyzeRequest.Builder<User> {

        public Builder(Context context) {
            super(context, URL);
        }

        @Override
        public CatalyzeRequest.Builder<User> setContent(User user) {
            setJson(user.asJson());
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<User> setListener(final Response.Listener<User> listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onResponse(new User(response));
                }
            });
            return this;
        }
    }
}

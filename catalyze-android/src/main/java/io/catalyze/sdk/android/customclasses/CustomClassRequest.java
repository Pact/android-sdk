package io.catalyze.sdk.android.customclasses;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

import io.catalyze.sdk.android.Catalyze;
import io.catalyze.sdk.android.CatalyzeRequest;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class CustomClassRequest {

    private static final String URL = "https://api.catalyze.io/v1/{appId}/classes/%s";

    public static class Builder extends CatalyzeRequest.Builder<CustomClass, CustomClass> {

        public Builder(Context context, String className) {
            super(context, String.format(URL, Catalyze.INSTANCE.getAppId(), className));
        }

        @Override
        public CatalyzeRequest.Builder<CustomClass, CustomClass> setContent(CustomClass body) {
            setJson(body.asJson());
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<CustomClass, CustomClass> setListener(final Response
                .Listener<CustomClass> listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onResponse(new CustomClass(response));
                }
            });
            return this;
        }
    }
}

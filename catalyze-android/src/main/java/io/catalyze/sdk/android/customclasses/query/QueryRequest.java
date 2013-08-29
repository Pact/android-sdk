package io.catalyze.sdk.android.customclasses.query;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

import io.catalyze.sdk.android.Catalyze;
import io.catalyze.sdk.android.CatalyzeRequest;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class QueryRequest {

    /*
        /<appId>/classes/<className>/query
     */
    private static final String URL = CatalyzeRequest.BASE_PATH + "/%s/classes/%s/query-as-object";

    public static class Builder extends CatalyzeRequest.Builder<Query, QueryResult> {

        public Builder(Context context, String className) {
            super(context, String.format(URL, Catalyze.INSTANCE.getAppId(), className));
        }

        @Override
        public CatalyzeRequest.Builder<Query, QueryResult> setMethod(CatalyzeRequest.Method
                                                                             method) {
            super.setMethod(CatalyzeRequest.Method.CREATE);
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<Query, QueryResult> setContent(Query body) {
            setJson(body.asJson());
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<Query, QueryResult> setListener(final Response
                .Listener<QueryResult> listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onResponse(new QueryResult(response));
                }
            });
            return this;
        }
    }
}

package io.catalyze.sdk.android.customclasses;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.catalyze.sdk.android.Catalyze;
import io.catalyze.sdk.android.CatalyzeRequest;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class QueryRequest {

    /*
        /<appId>/classes/<className>/query-as-object
     */
    private static final String URL = CatalyzeRequest.BASE_PATH + "/%s/classes/%s/query-as-object";

    private static final String RESULTS = "results";

    public static class Builder extends CatalyzeRequest.Builder<Query, List<CustomClass>> {

        public Builder(Context context, String className) {
            super(context, String.format(URL, Catalyze.INSTANCE.getAppId(), className));
        }

        @Override
        public CatalyzeRequest.Builder<Query, List<CustomClass>> setMethod(CatalyzeRequest.Method
                                                                             method) {
            super.setMethod(CatalyzeRequest.Method.CREATE);
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<Query, List<CustomClass>> setContent(Query body) {
            setJson(body.asJson());
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<Query, List<CustomClass>> setListener(final Response
                .Listener<List<CustomClass>> listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONArray array = response.optJSONArray(RESULTS);
                    int max = array.length();
                    List<CustomClass> results = new ArrayList<CustomClass>(max);
                    for (int i = 0; i < max; i++) {
                        results.add(new CustomClass(array.optJSONObject(i)));
                    }
                    listener.onResponse(results);
                }
            });
            return this;
        }
    }
}

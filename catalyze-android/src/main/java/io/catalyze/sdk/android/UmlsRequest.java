package io.catalyze.sdk.android;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class UmlsRequest {

    public enum Type {
        PHRASE, CODE
    }

    /*
        "https://api.catalyze.io/v1/umls/<phrase|code>/<vocab>/<lookupValue>"
     */
    private static final String URL = "https://api.catalyze.io/v1/umls/%s/%s/%s";

    public static class Builder extends CatalyzeRequest.Builder<UmlsResult> {

        protected Builder(Context context, Type type, UMLS vocab, String query) {
            super(context, String.format(URL, type.toString(), vocab.toString(), query));
        }

        @Override
        public CatalyzeRequest.Builder<UmlsResult> setMethod(CatalyzeRequest.Method method){
            super.setMethod(CatalyzeRequest.Method.RETRIEVE);
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<UmlsResult> setContent(UmlsResult body) {
            return this;
        }

        @Override
        public CatalyzeRequest.Builder<UmlsResult> setListener(Response.Listener<UmlsResult>
                                                                               listener) {
            setJsonListener(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            });
            return this;
        }
    }
}

package io.catalyze.sdk.android.umls;

import org.json.JSONObject;

import io.catalyze.sdk.android.CatalyzeObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class UmlsResult extends CatalyzeObject {

    private static final String DESCRIPTION = "description";
    private static final String CODE = "code";
    private static final String VOCAB = "vocab";

    UmlsResult(JSONObject json) {
        super(json);
    }

    public String getCode() {
        return mJson.optString(CODE, null);
    }

    public String getVocab() {
        return mJson.optString(VOCAB, null);
    }

    public String getDescription() {
        return mJson.optString(DESCRIPTION, null);
    }
}

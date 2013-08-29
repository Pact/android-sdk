package io.catalyze.sdk.android.customclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.catalyze.sdk.android.CatalyzeObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class CustomClass extends CatalyzeObject {

    private static final String CONTENT = "content";
    private static final String PHI = "phi";
    private static final String PARENT_ID = "parentId";
    private static final String SCHEMA = "schema";

    public CustomClass() {
        this(new JSONObject());
        try {
            mJson.put(CONTENT, new JSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    CustomClass(JSONObject json) {
        super(json);
    }

    public Object getContent(String key) {
        Object value = mJson.opt(key);
        if (value instanceof JSONArray) {
            return handleJSONArray((JSONArray) value);
        } else if (value instanceof JSONObject) {
            return handleJSONObject((JSONObject) value);
        }
        return value;
    }

    public CustomClass putContent(String key, Object value) {
        try {
            mJson.getJSONObject(CONTENT).put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isPHI() {
        return mJson.optBoolean(PHI, true);
    }

    public CustomClass setPHI(boolean phi) {
        try {
            mJson.put(PHI, phi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public long getParentId() {
        return mJson.optLong(PARENT_ID, Long.MIN_VALUE);
    }

    CustomClass setParentId(long id) {
        try {
            mJson.put(PARENT_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean isSchema() {
        return mJson.optBoolean(SCHEMA, false);
    }
}

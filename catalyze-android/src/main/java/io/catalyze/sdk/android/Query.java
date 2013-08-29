package io.catalyze.sdk.android;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class Query extends CatalyzeObject {

    private static final String NAME = "field";
    private static final String DATA = "searchBy";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";

    public Query() {
        super();
    }

    public String getName() {
        return mJson.optString(NAME, null);
    }

    public Query setName(String name) {
        setSomething(NAME, name);
        return this;
    }

    public String getData() {
        return mJson.optString(DATA, null);
    }

    public Query setData(String data) {
        setSomething(DATA, data);
        return this;
    }

    public Query setPageNumber(int pageNumber) {
        try {
            mJson.put(PAGE_NUMBER, pageNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Query setPageSize(int pageSize) {
        try {
            mJson.put(PAGE_SIZE, pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void setSomething(String key, String value) {
        try {
            mJson.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

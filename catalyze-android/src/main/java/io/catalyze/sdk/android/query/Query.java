package io.catalyze.sdk.android.query;

import org.json.JSONException;

import io.catalyze.sdk.android.CatalyzeObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class Query extends CatalyzeObject {

    private static final String FIELD = "field";
    private static final String SEARCH_BY = "searchBy";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";

    public Query() {
        super();
    }

    public String getField() {
        return mJson.optString(FIELD, null);
    }

    public Query setField(String name) {
        setSomething(FIELD, name);
        return this;
    }

    public String getSearchBy() {
        return mJson.optString(SEARCH_BY, null);
    }

    public Query setSearchBy(String data) {
        setSomething(SEARCH_BY, data);
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

package io.catalyze.sdk.android.query;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.catalyze.sdk.android.CatalyzeObject;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class QueryResult extends CatalyzeObject {

    private static final String RESULTS = "results";

    QueryResult(JSONObject json) {
        super(json);
    }

    public List<Entry> getResults() {
        JSONArray array = mJson.optJSONArray(RESULTS);
        int max = array.length();
        List<Entry> results = new ArrayList<Entry>(max);
        for (int i = 0; i < max; i++) {
            results.
        }
    }

    public static class Entry {

    }



}

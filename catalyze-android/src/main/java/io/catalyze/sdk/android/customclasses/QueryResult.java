package io.catalyze.sdk.android.customclasses;

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

    public List<CustomClass> getResults() {
        JSONArray array = mJson.optJSONArray(RESULTS);
        int max = array.length();
        List<CustomClass> results = new ArrayList<CustomClass>(max);
        for (int i = 0; i < max; i++) {
            results.add(new CustomClass(array.optJSONObject(i)));
        }
        return results;
    }
}

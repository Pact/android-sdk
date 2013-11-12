package io.catalyze.sdk.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Response;


/**
 * Created by mvolkhart on 8/26/13.
 */
public class Query extends CatalyzeObject {

    private static final String FIELD = "field";
    private static final String SEARCH_BY = "searchBy";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";
    private static final String QUERY_ROUTE = Catalyze.BASE_URL + "classes/";
    private String customClassName;
    public ArrayList<CustomClass> queryResults;
    public Query(String className) {
        super();
        customClassName = className;
        queryResults = new ArrayList<CustomClass>();
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

    public void setSearchBy(String data) {
        setSomething(SEARCH_BY, data);
    }

    public void setPageNumber(int pageNumber) {
        try {
            mJson.put(PAGE_NUMBER, pageNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPageSize(int pageSize) {
        try {
            mJson.put(PAGE_SIZE, pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSomething(String key, String value) {
        try {
            mJson.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void executeQuery(Catalyze catalyze, CatalyzeListener<Query> callbackHandler){
    	Response.Listener<JSONArray> responseListener = testListener(callbackHandler, this);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(QUERY_ROUTE + customClassName + "/query", this.asJson(), responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.post(catalyze.getContext());
    }  
    
    private static Response.Listener<JSONArray> testListener(final CatalyzeListener<Query> callbackHandler, final Query q) {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                	//q.setJsonArray(response);
            	for(int i = 0; i < response.length(); i++){
            		try {
						q.queryResults.add(new CustomClass(response.getJSONObject(i)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
            	}
                	callbackHandler.onSuccess(q);
            }
		};
	}
}

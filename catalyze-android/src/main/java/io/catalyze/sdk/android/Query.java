package io.catalyze.sdk.android;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;


/**
 * Created by mvolkhart on 8/26/13.
 */
public class Query extends CatalyzeObject {

    private static final String FIELD = "field";
    private static final String SEARCH_BY = "searchBy";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";
    private static final String QUERY_ROUTE = "";
    private String customClassName;
    private int pageSize;
    private int pageNumber;
    private String queryField;
    private String queryValue;
    
    
    
    
    public Query(String className) {
        super();
        customClassName = className;
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
    	responseListener = createListener(callbackHandler, this);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(QUERY_ROUTE, null, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.get(catalyze.getContext());
    }
    
    private static Response.Listener<JSONObject> createListener(final CatalyzeListener<Query> callbackHandler, final Query q) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                	q.setJson(response);
                	callbackHandler.onSuccess(q);
            }
		};
	}
    
}

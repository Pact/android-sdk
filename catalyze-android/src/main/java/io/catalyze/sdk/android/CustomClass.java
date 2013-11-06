package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;


/**
 * Created by mvolkhart on 8/26/13.
 */
public class CustomClass extends CatalyzeObject {

	//URL CONSTANTS
	private static final String CUSTOM_CLASS_URL = "https://api.catalyze.io/v1/classes";
	
	private static final String NAME = "name";
	private static final String ENTRYID = "entryId";
	//reference should be is actual refID + /refName
	private static final String REFERENCE = "/refName";
	private static final String REFID = "refId";
    private static final String CONTENT = "content";
    private static final String PHI = "phi";
    private static final String PARENT_ID = "parentId";
    private static final String SCHEMA = "schema";
    private static Response.ErrorListener errorListener;
    private static Response.Listener<JSONObject> responseListener;
    public CustomClass() {
        this(new JSONObject());
        try {
			mJson.put(CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public CustomClass(JSONObject json) {
		super(json);
	}

	public CustomClass(String name, boolean phi, JSONObject schema, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler) {
		setPHI(phi);
		mJson = new JSONObject();
		try {
			mJson.put(NAME, name);
			mJson.put(SCHEMA, schema);
			mJson.put(PHI, phi);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	responseListener = createListener(callbackHandler, this);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL, mJson, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.post(catalyze.getContext());
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

    CustomClass setParentId(String id) {
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
    
    /**
     * This method generates a custom class instance with information for the CatalyzeUser user
     * ***Must be passed an authenticated user to generate the custom class instance
     * @param user
     * @return CustomClass instance for CatalyzeUser user
     */
    public static void getInstance(Catalyze catalyze, String customClassName, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListener(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL + "/" + customClassName, null, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.get(catalyze.getContext());
    }
    
    public void getCustomClass(String className, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListener(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL + "/" + className, null, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.get(catalyze.getContext());
    }
    
    public static void addInstance(String className, JSONObject newData, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListener(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL + "/" + className, newData, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.post(catalyze.getContext());
    }
    
    private static Response.Listener<JSONObject> createListener(final CatalyzeListener<CustomClass> callbackHandler, final CustomClass cc) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                	cc.setJson(response);
                	callbackHandler.onSuccess(cc);
            }
		};
	}
    
//    private static Response.Listener<JSONObject> createListener(final CatalyzeListener<CustomClass[]> callbackHandler, final CustomClass[] cc) {
//        return new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                	cc.setJson(response);
//                	callbackHandler.onSuccess(cc);
//            }
//		};
//	}
    
    /***
	 * FIXME add additional error reporting
	 * Generic error handler
	 * @return
	 */	
	private static Response.ErrorListener createErrorListener(final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				CatalyzeError ce = (CatalyzeError) error;
				callbackHandler.onError(ce);
			}
		};
	}
    
}

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
    private static final String CONTENT = "content";
    private static final String PHI = "phi";
    private static final String PARENT_ID = "parentId";
    private static final String SCHEMA = "schema";
    private static final String REF = "ref";
    private CatalyzeUser user;
    public String id;
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
	
	public CustomClass(CatalyzeUser user){
		this(new JSONObject());
        try {
			mJson.put(CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
        this.user = user;
	}
	
	//This is just for testing
	public void createCustomClass(String name, boolean phi, JSONObject schema, CatalyzeListener<CustomClass> callbackHandler) {
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
    	responseListener = createListenerWithCCReturn(callbackHandler, this);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL, mJson, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.post(user.catalyze.getContext());
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
    public static CustomClass getInstance(CatalyzeUser user){
    	CustomClass cc = new CustomClass(user);
    	return cc;
    }
    
    /******
     * Perform= API call to get a custom class
     * TODO: Remove when dev portal can make them?
     * @param className
     * @param callbackHandler
     */
    public void get(String className, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className), null, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.get(user.catalyze.getContext());
    }
    
    /******
     * Api call to add fields to a custom class
     * TODO: Remove when dev portal can make them?
     * @param className
     * @param callbackHandler
     */
    public void addFields(String className, JSONObject newFields, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className), newFields, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.put(user.catalyze.getContext());
    }
    
    /******
     * Api call to delete a custom class
     * TODO: Remove when dev portal can make them?
     * @param className
     * @param callbackHandler
     */
    public void delete(String className, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className), null, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.delete(user.catalyze.getContext());
    }
    
    /**
     * Perform API call to add an entry to a custom class
     * @param className name of class to add entry to
     * @param newData data to add as an entry
     * @param callbackHandler 
     */
    public void addEntry(String className, JSONObject newData, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(CUSTOM_CLASS_URL + "/" + className, newData, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.post(user.catalyze.getContext());
    }
    
    /**
     * Perform API call to retrieve an entry from a custom class
     * @param className
     * @param entryId
     * @param catalyze
     * @param callbackHandler
     */
    public void getEntry(String className, String entryId, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId), null, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.get(catalyze.getContext());
    }
    
    /**
     * Perform api call to update an entry in a custom class, will write data to server as it is passed, so to do partial update first do a get.
     * @param className Name of custom class
     * @param entryId ID of entry to update
     * @param newData New entry to save 
     * @param catalyze
     * @param callbackHandler
     */
    public void updateEntry(String className, String entryId, JSONObject newData, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerNoCCReturned(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId), newData, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.put(catalyze.getContext());
    }
    
    /**
     * Perform api call to delete an entry within a custom class
     * @param className Name of custom class
     * @param entryId ID of entry to delete
     * @param callbackHandler
     */
    public void deleteEntry(String className, String entryId, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerNoCCReturned(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId), null	, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.delete(user.catalyze.getContext());
    }
    
    //TODO Custom class array items need to be fully implemented
    /**
     * Perform api call to retreive array of custom class objects
     * @param className Name of custom class
     * @param entryId ID of entry to delete
     * @param refName 
     * @param callbackHandler
     */
    public void getArray(String className, String entryId, String refName, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId, REF, refName), null	, responseListener, errorListener);
    	request.setHeaders(user.getAuthorizedHeaders());
    	request.get(user.catalyze.getContext());
    }
    
    //FIXME
    public void addReferenceArray(String className, String entryId, String refName, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId, REF, refName), null	, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.put(catalyze.getContext());
    }
    
    public void getArrayRef(String className, String entryId, String refName, String refId, Catalyze catalyze, CatalyzeListener<CustomClass> callbackHandler){
    	CustomClass cc = new CustomClass();
    	responseListener = createListenerWithCCReturn(callbackHandler, cc);
    	errorListener = createErrorListener(callbackHandler);
    	CatalyzeRequest request = new CatalyzeRequest(getCustomClassUrl(className, entryId, REF, refName, refId), null	, responseListener, errorListener);
    	request.setHeaders(catalyze.getUser().getAuthorizedHeaders());
    	request.get(catalyze.getContext());
    }
    
    /**
     * Get custom class url with arguments appended to base url with "/" separating	 each
     * @param args
     * @return
     */
    private String getCustomClassUrl(String... args){
    	String url = CUSTOM_CLASS_URL;
    	for(String s : args){
    		url += "/" + s;
    	}
    	return url;
    }

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
	 * 
	 * @param callbackHandler
	 * @param cc 
	 * @return
	 */
    private static Response.Listener<JSONObject> createListenerWithCCReturn(final CatalyzeListener<CustomClass> callbackHandler, final CustomClass cc) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                	cc.setJson(response);
                	callbackHandler.onSuccess(cc);
            }
		};
	}
    
    /**
     * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
     * @param callbackHandler
     * @param cc
     * @return
     */
    private static Response.Listener<JSONObject> createListenerNoCCReturned(final CatalyzeListener<CustomClass> callbackHandler, final CustomClass cc) {
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
    
}

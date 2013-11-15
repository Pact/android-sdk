package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

/**
 * Created by mvolkhart on 8/26/13.
 */
public class CustomClass extends CatalyzeObject {

	// URL CONSTANTS
	private static final String CUSTOM_CLASS_URL = Catalyze.BASE_URL + "classes";

	private static final String NAME = "name";
	private static final String CONTENT = "content";
	private static final String PHI = "phi";
	private static final String PARENT_ID = "parentId";
	private static final String SCHEMA = "schema";
	private static final String ID = "id";
	private static final String REF = "ref";
	

	private CatalyzeUser user;

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

	private CustomClass(CatalyzeUser user) {
		this(new JSONObject());
		try {
			mJson.put(CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.user = user;
	}

	public String[] getReferenceIDs(String refName){
		
		
		return null;
	}
	
	public void setName(String name){
		try {
			mJson.put(NAME, name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the name of this class, can return null
	 * @return 
	 */
	public String getName() {
		return mJson.optString("NAME", null);
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

	public String getParentId() {
		String id = "";
		try {
			id = mJson.get(PARENT_ID).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	CustomClass setParentId(String id) {
		try {
			mJson.put(PARENT_ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getId() {
		String id = "";
		try {
			id = mJson.get(ID).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	CustomClass setId(String id) {
		try {
			mJson.put(ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public boolean isSchema() {
		return mJson.optBoolean(SCHEMA, false);
	}

	/**
	 * This method generates a custom class instance with information for the
	 * CatalyzeUser user ***Must be passed an authenticated user to generate the
	 * custom class instance
	 * 
	 * @param user
	 * @return CustomClass instance for CatalyzeUser user
	 */
	public static CustomClass getInstance(String className, CatalyzeUser user) {
		CustomClass cc = new CustomClass(user);
		cc.setName(className);
		return cc;
	}

	/******
	 * Perform= API call to get a custom class schema
	 * 
	 * @param callbackHandler
	 */
	public void getSchema(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerWithCCReturn(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName()), null,
				responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

//	/******
//	 * Api call to delete a custom class TODO: Do we want this as part of sdk?
//	 * them?
//	 * 
//	 * @param className
//	 * @param callbackHandler
//	 */
//	public void delete(String className, CatalyzeListener<CustomClass> callbackHandler) {
//		responseListener = createListenerWithCCReturn(callbackHandler);
//		errorListener = createErrorListener(callbackHandler);
//		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(className), null,
//				responseListener, errorListener);
//		request.setHeaders(user.getAuthorizedHeaders());
//		request.delete(user.catalyze.getContext());
//	}

	/**
	 * Perform API call to add an entry to a custom class
	 * 
	 * @param callbackHandler
	 */
	public void createEntry(CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerWithCCReturn(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(CUSTOM_CLASS_URL + "/" + getName(), mJson.getJSONObject(CONTENT),
					responseListener, errorListener);
			request.setHeaders(user.getAuthorizedHeaders());
			request.post(user.catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform API call to retrieve an entry from a custom class
	 * 
	 * @param entryId
	 * @param callbackHandler
	 */
	public void getEntry(String entryId, CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerWithCCReturn(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId),
				null, responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * Perform api call to update an entry in a custom class, will write data to
	 * server as it is passed, so to do partial update first do a get.
	 * 
	 * @param entryId
	 *            ID of entry to update
	 * @param callbackHandler
	 */
	public void updateEntry(String entryId, CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerWithCCReturn(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId),
					mJson.getJSONObject(CONTENT), responseListener, errorListener);
			request.setHeaders(user.getAuthorizedHeaders());
			request.put(user.catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Perform api call to delete an entry within a custom class
	 * 
	 * @param entryId
	 *            ID of entry to delete
	 * @param callbackHandler
	 */
	public void deleteEntry(String entryId, CatalyzeListener<CustomClass> callbackHandler) {
		responseListener = createListenerWithCCReturn(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId),
				null, responseListener, errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.delete(user.catalyze.getContext());
	}

	// TODO Custom class/foreign key array items need to be fully implemented
	/**
	 * Perform api call to retreive array of custom class objects
	 * 
	 * @param entryId
	 *            ID of entry to delete
	 * @param refName
	 * @param callbackHandler
	 */
	public void getArray(String entryId, String refName, CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getCustomClassUrl(getName(), entryId, REF,
				refName), null, createArrayListener(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}

	/**
	 * Add a new reference to the array refName
	 * @param entryId
	 * @param refName
	 * @param callbackHandler
	 */
	public void addReferenceArray(String entryId, String refName, String refID,
			CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		JSONArray newArrayEntry = new JSONArray();
		JSONObject json = new JSONObject();
		try {
			json.put("type", "__ref");
			json.put("class", refName);
			json.put("id", refID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		newArrayEntry.put(json);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(newArrayEntry , getCustomClassUrl(getName(), entryId, REF,
				refName), createListenerWithCCReturn(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.put(user.catalyze.getContext());
	}

	/**
	 * 
	 * @param entryId
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 */
	public void getArrayRef(String entryId, String refName, String refId,
			CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId,
				REF, refName, refId), null, createListenerWithCCReturn(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.get(user.catalyze.getContext());
	}
	
	/**
	 * 
	 * @param entryId
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 */
	public void deleteArrayRef(String entryId, String refName, String refId,
			CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId,
				REF, refName, refId), null, createListenerWithCCReturn(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.delete(user.catalyze.getContext());
	}

	/**
	 * Perform query on this custom class
	 * 
	 * @param callbackHandler
	 */
	public void query(CatalyzeListener<CustomClass> callbackHandler) {
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getCustomClassUrl(getName(), "query"),
				null, createArrayListener(callbackHandler), errorListener);
		request.setHeaders(user.getAuthorizedHeaders());
		request.post(user.catalyze.getContext());
	}

	/**
	 * Get custom class url with arguments appended to base url with "/"
	 * separating each
	 * 
	 * @param args
	 * @return
	 */
	private String getCustomClassUrl(String... args) {
		String url = CUSTOM_CLASS_URL;
		for (String s : args) {
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
	private static Response.Listener<JSONObject> createListenerWithCCReturn(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass cc  = new CustomClass(response); 
				callbackHandler.onSuccess(cc);
			}
		};
	}

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
	 * For methods that do not have a response - 
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private static Response.Listener<JSONObject> createListenerNoCCReturned(
			final CatalyzeListener<Boolean> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				callbackHandler.onSuccess(true);
			}
		};
	}

	/**
	 * Handle volley response when response will be a JSONArray
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private static Response.Listener<JSONArray> createArrayListener(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				// FIXME
				for(int i = 0; i < response.length(); i++){
					
				}
				CustomClass cc = new CustomClass();
				cc.setJsonArray(response);
				//cc.setJson(response);
				callbackHandler.onSuccess(cc);
			}
		};
	}

}

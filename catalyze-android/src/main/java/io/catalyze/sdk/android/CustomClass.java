package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

/**
 * This class allows you to interact with the Catalyze Custom Class API routes
 * and easily interact with the responses from these routes. To use a Custom
 * Class, a user authenticated instance of Custom Class must first be created by
 * calling the static method getInstance and passing in a currently
 * authenticated user. Next set the Custom Class name by calling setName().
 */
public class CustomClass extends CatalyzeObject {

	// URL CONSTANTS
	private static final String CUSTOM_CLASS_URL = Catalyze.BASE_URL + "classes";

	private static final String CONTENT = "content";
	private static final String PHI = "phi";
	private static final String PARENT_ID = "parentId";
	private static final String SCHEMA = "schema";
	private static final String ID = "id";
	private static final String REF = "ref";

	private String className;

	protected CustomClass(String className, Catalyze catalyze, JSONObject json) {
		super(catalyze);
		try {
			mJson.put(CONTENT, json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.className = className;
	}

	protected CustomClass(String className, Catalyze catalyze) {
		this(className, catalyze, new JSONObject());
	}

	public void setName(String className) {
		this.className = className;
	}

	public String getName() {
		return className;
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
	
	public CustomClass removeContent(String key){
		mJson.remove(key);
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
			id = mJson.getString(ID);
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

	/******
	 * Perform Catalze API call to get an already created custom class schema
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the the same instance of custom class that was
	 *            used to call this method
	 */
	public void getSchema(CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnNewInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName()), null,
				responseListener, Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.get(catalyze.getContext());
	}

	/**
	 * Perform Catalyze API call to add an entry to a custom class, this entry
	 * will have it's data saved to this custom class instance upon callback
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the the same instance of custom class that was
	 *            used to call this method
	 */
	public void createEntry(CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(CUSTOM_CLASS_URL + "/" + getName(), mJson.getJSONObject(CONTENT),
					responseListener, Catalyze.createErrorListener(callbackHandler));
			request.setHeaders(catalyze.getAuthorizedHeaders());
			request.post(catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform Catalyze API call to retrieve an existing custom class entry.
	 * 
	 * @param entryId
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the the same instance of custom class that was
	 *            used to call this method
	 */
	public void getEntry(String entryId, CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), entryId),
				null, responseListener, Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.get(catalyze.getContext());
	}

	/**
	 * Perform api call to update an entry in a custom class, will write data to
	 * server as it is passed, so to do partial update first do a get. This
	 * class requires that the id be set. The id will be saved automatically if
	 * createEntry or getEntry has been called, otherwise you can set the id
	 * manually by calling setId.
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the the same instance of custom class that was
	 *            used to call this method
	 */
	public void updateEntry(CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request;
		try {
			request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId()),
					mJson.getJSONObject(CONTENT), responseListener, Catalyze.createErrorListener(callbackHandler));
			request.setHeaders(catalyze.getAuthorizedHeaders());
			request.put(catalyze.getContext());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Perform api call to delete an entry within a custom class. This class
	 * requires that the id be set. The id will be saved automatically if
	 * createEntry or getEntry has been called, otherwise you can set the id
	 * manually by calling setId.
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the the same instance of custom class that was
	 *            used to call this method
	 */
	public void deleteEntry(CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId()),
				null, responseListener, Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.delete(catalyze.getContext());
	}

	/**
	 * Perform api call to retrieve array of custom class objects. This class
	 * requires that the id be set. The id will be saved automatically if
	 * createEntry or getEntry has been called, otherwise you can set the id
	 * manually by calling setId.
	 * 
	 * @param refName  
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass array on
	 *            successful callback. This custom class array will be a set of
	 *            references to a new set of CustomClass instances
	 */
	public void getArray(String refName, CatalyzeListener<CustomClass[]> callbackHandler) {
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getCustomClassUrl(getName(), getId(), REF,
				refName), null, createArrayListener(callbackHandler), Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.get(catalyze.getContext());
	}

	/**
	 * Add a new reference to the array refName. This class requires that the id
	 * be set. The id will be saved automatically if createEntry or getEntry has
	 * been called, otherwise you can set the id manually by calling setId.
	 * 
	 * @param refName
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. The custom class instance returned will be a
	 *            reference to the original Custom Class instance that this
	 *            method was called upon, after it has been updated to match the
	 *            server response.
	 */
	public void addReferenceArray(String refName, String refID, CatalyzeListener<CustomClass> callbackHandler) {
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
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(newArrayEntry, getCustomClassUrl(
				getName(), getId(), REF, refName), createListenerReturnUpdatedInstance(callbackHandler), Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.put(catalyze.getContext());
	}

	/**
	 * Performs api call to retrieve the a referenced array instance. The entire
	 * custom class instance referenced will be returned to the callback
	 * handler. This class requires that the id be set. The id will be saved
	 * automatically if createEntry or getEntry has been called, otherwise you
	 * can set the id manually by calling setId.
	 * 
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. This custom class will be a new Custom Class
	 *            instance containing the information gotten from the array of
	 *            references in the original Custom Class
	 */
	public void getArrayRef(String refName, String refId, CatalyzeListener<CustomClass> callbackHandler) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId(),
				REF, refName, refId), null, createListenerReturnNewInstance(callbackHandler), Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.get(catalyze.getContext());
	}

	/**
	 * 
	 * @param refName
	 * @param refId
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CustomClass on successful
	 *            callback. This custom class will be a new Custom Class
	 *            instance that contains no data. FIXME?
	 */
	public void deleteArrayRef(String refName, String refId, CatalyzeListener<CustomClass> callbackHandler) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getCustomClassUrl(getName(), getId(),
				REF, refName, refId), null, createListenerReturnNewInstance(callbackHandler), Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.delete(catalyze.getContext());
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
	private Response.Listener<JSONObject> createListenerReturnNewInstance(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass cc = new CustomClass(CustomClass.this.className, CustomClass.this.catalyze, response);
				callbackHandler.onSuccess(cc);
			}
		};
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
	private Response.Listener<JSONObject> createListenerReturnUpdatedInstance(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass.this.mJson = response;
				callbackHandler.onSuccess(CustomClass.this);
			}
		};
	}

	/**
	 * Handle volley response when response will be a JSONArray
	 * 
	 * @param callbackHandler
	 * @param cc
	 * @return
	 */
	private  Response.Listener<JSONArray> createArrayListener(
			final CatalyzeListener<CustomClass[]> callbackHandler) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				CustomClass[] ccArray = new CustomClass[response.length()];
				for (int i = 0; i < response.length(); i++) {
					try {
						ccArray[i] = new CustomClass(CustomClass.this.className, CustomClass.this.catalyze, response.getJSONObject(i));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				callbackHandler.onSuccess(ccArray);
			}
		};
	}

}

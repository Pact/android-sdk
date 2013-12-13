package io.catalyze.sdk.android;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Response;

/**
 * This class allows you to interact with the Catalyze Custom Class API routes
 * and easily interact with the responses from these routes. To use a Custom
 * Class, a user authenticated instance of Custom Class must first be created by
 * calling the static method getInstance and passing in a currently
 * authenticated user. Next set the Custom Class name by calling setName().
 */
public class CustomClass extends CatalyzeObject implements Serializable {

	// UID
	private static final long serialVersionUID = -5178734838731452308L;

	// Field constants
	protected static final String CONTENT = "content";
	private static final String PHI = "phi";
	private static final String PARENT_ID = "parentId";
	private static final String SCHEMA = "schema";
	private static final String ID = "id";
	private static final String REF = "ref";

	// The name of this custom class
	private String className;

	/**
	 * Create a custom class instance associated to a Catalyze instance.
	 * 
	 * @param catalyze
	 *            The authenticated Catalyze instance
	 * @param className
	 *            The name of this instance's custom class
	 */
	protected CustomClass(Catalyze catalyze, String className) {
		this(catalyze, className, new JSONObject());
	}

	/**
	 * Create a custom class instance associated to a Catalyze instance. Use
	 * provided JSON data to initialize the object's content.
	 * 
	 * @param catalyze
	 *            The authenticated Catalyze instance
	 * @param className
	 *            The name of this instance's custom class
	 * @param json
	 *            The data to populate the instance's content with
	 */
	protected CustomClass(Catalyze catalyze, String className, JSONObject json) {
		super(catalyze);
		mJson = json;
		this.className = className;
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
	public void addReferenceArray(String refName, String refID,
			CatalyzeListener<CustomClass> callbackHandler) {
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
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.PUT, newArrayEntry, getCustomClassUrl(
						getName(), getId(), REF, refName),
				createListenerReturnUpdatedInstance(callbackHandler),
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
	}

	/**
	 * Used to determine if this instance may contain Protected Health
	 * Information (PHI).
	 * 
	 * @return true - when the class's data may contain PHI, else false
	 */
	public boolean containsPHI() {
		return mJson.optBoolean(PHI, true);
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
		try {
			JSONObject newJson = new JSONObject("{\"content\":"
					+ mJson.getJSONObject(CONTENT) + "}");

			Log.i("Catalyze", "New: " + newJson.toString());

			CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
					CatalyzeRequest.POST, getCustomClassUrl(getName()),
					newJson, responseListener,
					Catalyze.createErrorListener(callbackHandler));
			request.setHeaders(catalyze.getAuthorizedHeaders());

			request.execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
	public void deleteArrayRef(String refName, String refId,
			CatalyzeListener<CustomClass> callbackHandler) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, getCustomClassUrl(getName(), getId(),
						REF, refName, refId), null,
				createListenerReturnNewInstance(callbackHandler),
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
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

		try {
			Log.i("Catalyze", "Delete: "
					+ mJson.getJSONObject(CONTENT).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, getCustomClassUrl(getName(), getId()),
				null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
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
	public void getArray(String refName,
			final CatalyzeListener<CustomClass[]> callbackHandler) {

		Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				CustomClass[] ccArray = new CustomClass[response.length()];
				for (int i = 0; i < response.length(); i++) {
					try {
						ccArray[i] = new CustomClass(CustomClass.this.catalyze,
								CustomClass.this.className,
								response.getJSONObject(i));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				callbackHandler.onSuccess(ccArray);
			}
		};

		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, getCustomClassUrl(getName(), getId(), REF,
						refName), null, listener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
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
	public void getArrayRef(String refName, String refId,
			CatalyzeListener<CustomClass> callbackHandler) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, getCustomClassUrl(getName(), getId(), REF,
						refName, refId), null,
				createListenerReturnNewInstance(callbackHandler),
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
	}

	/**
	 * Allows access to the underlying JSON in the custom class.
	 * 
	 * @return The JSON content of this intance.
	 */
	public JSONObject getContent() {
		JSONObject json = null;
		try {
			json = this.mJson.getJSONObject(CONTENT);
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return json;

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
	public void getEntry(String entryId,
			CatalyzeListener<CustomClass> callbackHandler) {
		Response.Listener<JSONObject> responseListener = createListenerReturnUpdatedInstance(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, getCustomClassUrl(getName(), entryId),
				null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
	}

	/**
	 * Gets the ID of this custom class.
	 * 
	 * @return
	 */
	public String getId() {
		String id = "";
		try {
			id = mJson.getString(ID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Gets the name of this instance's custom class.
	 * 
	 * @return The custom class name
	 */
	public String getName() {
		return className;
	}

	/**
	 * For retrieving the parent ID of a custom class parent in a hierarchy.
	 * 
	 * @return The ID of the parent
	 */
	public String getParentId() {
		String id = "";
		try {
			id = mJson.get(PARENT_ID).toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return id;
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
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, getCustomClassUrl(getName()), null,
				responseListener, Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
	}

	/**
	 * If this entry is a schema definition, not an entry.
	 * 
	 * @return true iff this is a schema definition 
	 */
	public boolean isSchema() {
		return mJson.optBoolean(SCHEMA, false);
	}

	/**
	 * Sets the parent ID. Used for creating custom class hierarchies.
	 * 
	 * @param id
	 *            The ID of the parent
	 */
	public void setParentId(String id) {
		try {
			mJson.put(PARENT_ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets if the custom class instance can contain PHI or not.
	 * 
	 * Be careful how you use this method. Don't changing something to not being
	 * PHI unless you are sure about the contents.
	 * 
	 * @param phi
	 * @return
	 */
	public CustomClass setPHI(boolean phi) {
		try {
			mJson.put(PHI, phi);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
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
			Log.i("Catalyze", "Update: "
					+ mJson.getJSONObject(CONTENT).toString());

			request = new CatalyzeRequest<JSONObject>(CatalyzeRequest.PUT,
					getCustomClassUrl(getName(), getId()),
					mJson.getJSONObject(CONTENT), responseListener,
					Catalyze.createErrorListener(callbackHandler));
			request.setHeaders(catalyze.getAuthorizedHeaders());
			request.execute();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses json to this then returns it to user callback
	 * handler
	 * 
	 * @param callbackHandler The catalyze handler
	 * @return The volley handler
	 */
	private Response.Listener<JSONObject> createListenerReturnNewInstance(
			final CatalyzeListener<CustomClass> callbackHandler) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CustomClass cc = new CustomClass(CustomClass.this.catalyze,
						CustomClass.this.className, response);
				callbackHandler.onSuccess(cc);
			}
		};
	}

	/**
	 * Volley response handler for custom class, takes the JSONObject response
	 * and sets the CustomClasses JSON to this then returns it to user callback
	 * handler.
	 * 
	 * @param callbackHandler The Catalyze handler
	 * @return The volley listener
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
	 * Get custom class url with arguments appended to base url with "/"
	 * separating each
	 * 
	 * @param args
	 * @return
	 */
	private String getCustomClassUrl(String... args) {
		String url = catalyze.customClassUrl;
		for (String s : args) {
			url += "/" + s;
		}
		return url;
	}

	/**
	 * Set the ID of this custom class. Should not be called from the SDK.
	 * 
	 * @param id
	 *            The new ID.
	 */
	protected void setId(String id) {
		try {
			mJson.put(ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

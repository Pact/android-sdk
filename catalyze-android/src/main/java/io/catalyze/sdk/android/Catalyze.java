package io.catalyze.sdk.android;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;
import android.widget.TextView;

/**
 * Base class for making Catalyze.io API calls. This class must be instantiated
 * with an API key and Application identifier before any api calls can be made.
 * Once Catalyze has been instantiated it can be used to generate an
 * authenticated CatalyzeUser instance that will be associated with this
 * instance of Catalyze.
 * 
 * Any API routes being performed must be provided a CatalyzeListener. Upon
 * completion of API request the provided CatalyzeListener will be called with
 * either a success or error response.
 * 
 * All API calls are handled asynchronously, if calls require a fixed order,
 * this must be handled outside of the Catalyze Android SDK
 */
public class Catalyze {

	protected String apiKey;
	protected static TextView mResult;
	private static final String ANDROID = "android";
	private final String identifier;
	private CatalyzeUser user;
	private Context appContext;

	/**
	 * Create a Catalyze interface for making authenticated api calls
	 * 
	 * @param apiKey
	 * @param identifier
	 * @param context
	 */
	public Catalyze(String apiKey, String identifier, Context context) {
		this.apiKey = apiKey;
		appContext = context;
		this.identifier = identifier;
	}

	/**
	 * Catalyze Constructor for logging in an existing user
	 * 
	 * @param apiKey
	 * @param userName
	 * @param password
	 * @param context
	 */
	public Catalyze(String apiKey, String identifier, String userName,
			String password, CatalyzeListener<CatalyzeUser> handleResponse,
			Context context) {
		this.apiKey = apiKey;
		user = new CatalyzeUser(this);
		user.getAuthenticatedUser(userName, password, handleResponse, context);
		appContext = context;
		this.identifier = identifier;
	}

	public void updateContext(Context context) {
		this.appContext = context;
	}

	protected Context getContext() {
		return appContext;
	}

	protected CatalyzeUser getUser() {
		return user;
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param callbackHandler
	 */
	public void getUser(String userName, String password,
			CatalyzeListener<CatalyzeUser> callbackHandler) {
		user = new CatalyzeUser(this);
		user.getAuthenticatedUser(userName, password, callbackHandler,
				appContext);
	}

	/**
	 * Perform API call to create a new user for this application. Will return a
	 * CatalyzeUser to the provided callback function
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param callbackHandler
	 */
	public void signUp(String userName, String password, String firstName,
			String lastName, CatalyzeListener<CatalyzeUser> callbackHandler) {
		user = new CatalyzeUser(this);
		user.signUp(userName, password, firstName, lastName, callbackHandler,
				appContext);
	}

	/**
	 * Log out the current user
	 * 
	 * @param callbackHandler
	 */
	public void logoutCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.signOut(callbackHandler, appContext);
	}

	/**
	 * Delete the user currently associated with Catalyze
	 * 
	 * @param callbackHandler
	 */
	public void deleteCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.deleteUser(callbackHandler, appContext);
	}

	protected String getAPIKey() {
		return apiKey;
	}

	/**
	 * Returns headers required for a non-user authorized API call
	 * 
	 * @return
	 */
	protected Map<String, String> getDefaultHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Api-Key", ANDROID + " " + identifier + " " + getAPIKey());
		headers.put("Content-Type", "application/json");
		return headers;
	}

	/**
	 * Returns the currently signed in user. If no user has been previously
	 * created will return null
	 * 
	 * @return
	 */
	public CatalyzeUser getCurrentUser() {
		return user;
	}

	
}

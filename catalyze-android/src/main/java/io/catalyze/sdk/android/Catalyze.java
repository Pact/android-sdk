package io.catalyze.sdk.android;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Base class for making Catalyze.io API calls. This class must be instantiated
 * with an API key and Application identifier before any api calls can be made.
 * Once Catalyze has been instantiated it must be authenticated to retrieve a
 * CatalyzeUser instance that will be associated with this instance of Catalyze.
 * Once authenticated the instance can be used.
 * 
 * Any API routes being performed must be provided a CatalyzeListener. Upon
 * completion of API request the provided CatalyzeListener will be called with
 * either a success or error response.
 * 
 * All API calls are handled asynchronously, if calls require a fixed order,
 * this must be handled outside of the Catalyze Android SDK (handled by app
 * logic).
 */
public class Catalyze implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -395490221349117410L;

	// Const defining the API key prefix
	private static final String ANDROID = "android";

	/**
	 * The API key of the application. This is generated in the Catalyze
	 * Developer Portal. You must set it in your app's onCreate() method before
	 * using the SDK.
	 */
	public static String API_KEY;

	/**
	 * The identifier (name) of the application. You must set it in your app's
	 * onCreate() method before using the SDK.
	 */
	public static String IDENTIFIER;

	// The authenticated user. Cannot be used until the instance is
	// authenticated.
	private CatalyzeUser user;

	/**
	 * URLs: The fields below define the route URLS for the operations supported
	 * by the API.
	 */

	// Base URL of the app.
	protected String baseUrl;

	// URL of route for custom class operations
	protected String customClassUrl;

	// URL of route for query operations
	protected String queryUrl;

	// URL of route for sign in (log in)
	protected String signInUrl;

	// URL of route for sign out (log out)
	protected String signOutUrl;

	// URL of route for user operations
	protected String userUrl;

	// URL of route for file operations
	protected String fileUrl;

	// URL of route for application-related file operations
	protected String appFileUrl;

	// URL of route for user-related file operations
	protected String userFileUrl;

	/**
	 * Initializes the instance by authenticating as the user provided. This
	 * method must be called before a Catalyze instance can be used for any
	 * operations other than UMLS lookups.
	 * 
	 * @param userName
	 *            The user to authenticate this instance as
	 * @param password
	 *            The user's password
	 * @param callbackHandler
	 *            The callback to handle the server's response. The instance is
	 *            not active until a successful callback result is returned.
	 */
	public static void authenticate(String userName, String password,
			final CatalyzeListener<Catalyze> callbackHandler) {

		final Catalyze catalyze = new Catalyze();

		CatalyzeRequest.initRequestQueue(callbackHandler.getContext());

		Map<String, String> headers = catalyze.getDefaultHeaders();
		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put(CatalyzeUser.USERNAME, userName);
			jsonBody.put(CatalyzeUser.PASSWORD, password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = new CatalyzeUser(catalyze);
				user.setJson(response);
				user.setSessionToken(response.optString(
						CatalyzeUser.SESSION_TOKEN, null));
				catalyze.user = user;
				callbackHandler.onResponse(catalyze);
			}
		};

		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.POST, catalyze.signInUrl, jsonBody,
				responseListener, createErrorListener(callbackHandler));
		request.setHeaders(headers);

		request.execute();
	}

	/**
	 * Perform an API to create a new user. The callback will return an
	 * authenticated Catalyze instance (authenticated as the new user).
	 * 
	 * Also initializes the singelton RequestQueue if needed.
	 * 
	 * @param userName
	 *            The user name. Should be an email address.
	 * @param password
	 *            The user's password.
	 * @param firstName
	 *            The user's first name.
	 * @param lastName
	 *            The user's last name.
	 * @param callbackHandler
	 *            The call back to report back success or failure.
	 */
	public static void signUp(String userName, String password,
			String firstName, String lastName,
			final CatalyzeListener<Catalyze> callbackHandler) {

		final Catalyze catalyze = new Catalyze();

		CatalyzeRequest.initRequestQueue(callbackHandler.getContext());

		Map<String, String> headers = catalyze.getDefaultHeaders();
		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put(CatalyzeUser.USERNAME, userName);
			jsonBody.put(CatalyzeUser.FIRST_NAME, firstName);
			jsonBody.put(CatalyzeUser.LAST_NAME, lastName);
			jsonBody.put(CatalyzeUser.PASSWORD, password);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = new CatalyzeUser(catalyze);
				catalyze.user = user;
				callbackHandler.onResponse(catalyze);
			}
		};

		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.POST, catalyze.userUrl, jsonBody,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
	}

	/**
	 * Return the context associated with this instance. Cannot be changed.
	 * 
	 * @return The context associated with this instance
	 */
	// protected Context getContext() {
	// return appContext;
	// }

	/**
	 * Generic volley error callback handler, returns a CatalyzeError back to
	 * the user passed callback handler
	 * 
	 * @param userCallback
	 *            The user-defined callback to send results back to
	 * @return The listener
	 */
	protected static <T> Response.ErrorListener createErrorListener(
			final CatalyzeListener<T> userCallback) {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				CatalyzeException ce = new CatalyzeException(error);
				userCallback.onError(ce);
			}
		};
	}

	/**
	 * Create a Catalyze interface for making authenticated api calls.
	 * 
	 * @param apiKey
	 *            The app's API key
	 * @param identifier
	 *            The name of the app
	 * 
	 */
	protected Catalyze() {
		if (Catalyze.API_KEY == null) {
			throw new IllegalStateException("The API key must be non-null.");
		} else if (Catalyze.IDENTIFIER == null) {
			throw new IllegalStateException("The identifier must be non-null.");
		}

		this.setBaseURL("https://api.catalyze.io/v1/");

	}

	/**
	 * All Catalyze instances must be associated with an authenticated user via
	 * the authenticate() method. This method returns that user.
	 * 
	 * @return The authenticated user
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not already been authenticated.
	 */
	public CatalyzeUser getAuthenticatedUser() {
		if (user == null)
			throw new IllegalStateException(
					"No authenticated user has been assigned. Must call Catalyze.authenticate() and wait for the callback before using this instance.");
		return user;
	}

	/**
	 * Builds and returns a new instance of CatalyzeUser associated with this
	 * Catalyze instance.
	 * 
	 * @return The instance of CatalyzeUser associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public CatalyzeUser getCatalyzeUserInstance() {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		return new CatalyzeUser(this);
	}

	/**
	 * Builds and returns a new instance of CustomClass associated with this
	 * Catalyze instance. The JSON content is set to '{}'.
	 * 
	 * @param className
	 *            The name of the custom class
	 * @return The instance of CustomClass associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public CustomClass getCustomClassInstance(String className) {
		JSONObject obj = new JSONObject();
		try {
			obj = new JSONObject("{}");

			obj.put(CustomClass.CONTENT, new JSONObject());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.getCustomClassInstance(className, obj);
	}

	/**
	 * Builds and returns a new instance of CustomClass associated with this
	 * Catalyze instance.
	 * 
	 * @param className
	 *            The name of the custom class
	 * @param json
	 *            The JSON content to use for this instance
	 * @return The instance of CustomClass associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public CustomClass getCustomClassInstance(String className, JSONObject json) {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		JSONObject newJson = new JSONObject();
		try {
			newJson.put(CustomClass.CONTENT, json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new CustomClass(this, className, newJson);
	}

	/**
	 * Returns a FileManager instance associated with this Catalyze instance
	 * capable of uploading/downloading and deleting files that the
	 * authenticated user can access.
	 * 
	 * Marking as private for first release as the functionality is not quite
	 * ready.
	 * 
	 * @return The instance of FileManager associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	private FileManager getFileManagerInstance() {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		return new FileManager(this);
	}

	/**
	 * Builds and returns a Query instance associated with this authenticated
	 * Catalyze instance and a custom class.
	 * 
	 * @param className
	 *            The custom class name to query
	 * @return The Query instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public Query getQueryInstance(String className) {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		return new Query(className, this);
	}

	/**
	 * Builds and returns an instance of UMLS associated with this Catalyze
	 * instance.
	 * 
	 * @return An instance of UMLS
	 */
	public UMLS getUmlsInstance() {
		UMLS umls = new UMLS(this);
		return umls;
	}

	/**
	 * The instance is authenticated off the user is non-null. The session token
	 * may have expired for the user but this test must be checked through an
	 * API call to the backed and should be handled by program logic (handling
	 * of CatalyzeErrors on callbacks),
	 * 
	 * @return Returns true iff the user has been authenticated
	 */
	public boolean isAuthenticated() {
		return user == null;
	}

	/**
	 * The version 1.0 URL is set by default but this method allows overriding
	 * that URL in case of future upgrades/changes.
	 * 
	 * @param baseUrl
	 *            The full base URL (default is 'https://api.catalyze.io/v1/')
	 */
	public void setBaseURL(String baseUrl) {
		this.baseUrl = baseUrl;
		this.signInUrl = baseUrl + "auth/signin";
		this.signOutUrl = baseUrl + "auth/signout";
		this.userUrl = baseUrl + "user";
		this.queryUrl = baseUrl + "classes/";
		this.customClassUrl = baseUrl + "classes";

		this.fileUrl = baseUrl + "file";
		this.appFileUrl = baseUrl + "file/app";
		this.userFileUrl = baseUrl + "file/user";
	}

	/**
	 * Sign out this authenticated CatalyzeUser.
	 * 
	 * @param callbackHandler
	 *            Funtion to call after HTTP response handled
	 */
	public void signOut(CatalyzeListener<CatalyzeUser> callbackHandler) {
		if (user == null)
			throw new IllegalStateException(
					"No authenticated user has been assigned. Must call Catalyze.authenticate() and wait for the callback before using this instance.");
		user.signOut(callbackHandler);
	}

	/**
	 * Returns the API key for this Catalyze instance.
	 * 
	 * @return The API key
	 */
	protected String getAPIKey() {
		return Catalyze.API_KEY;
	}

	/**
	 * Builds the header fields needed to make an authenticated connection to
	 * the API.
	 * 
	 * @return The headers in a Map.
	 */
	protected Map<String, String> getAuthorizedHeaders() {
		Map<String, String> headers = this.getDefaultHeaders();
		headers.put("Authorization", "Bearer " + user.getSessionToken());
		return headers;
	}

	/**
	 * Returns headers required for a non-user authorized API call
	 * 
	 * @return The Map of fields to add to the HTTP header
	 */
	protected Map<String, String> getDefaultHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Api-Key", ANDROID + " " + Catalyze.IDENTIFIER + " "
				+ getAPIKey());
		headers.put("Content-Type", "application/json");
		return headers;
	}

}

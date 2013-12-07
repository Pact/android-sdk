package io.catalyze.sdk.android;

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

	protected final String apiKey;
	private static final String ANDROID = "android";
	private final String identifier;
	private CatalyzeUser user;
	private Context appContext;

	// URLs
	protected String baseUrl;
	protected String customClassUrl;
	protected String queryUrl;
	protected String signInUrl;
	protected String signOutUrl;
	protected String userUrl;
	protected String fileUrl;
	protected String appFileUrl;
	protected String userFileUrl;

	/**
	 * Create a Catalyze interface for making authenticated api calls
	 * 
	 * @param apiKey
	 * @param identifier
	 * @param context
	 */
	public Catalyze(String apiKey, String identifier, Context context) {
		if (apiKey == null) {
			throw new IllegalStateException("The API key must be non-null.");
		} else if (identifier == null) {
			throw new IllegalStateException("The identifier must be non-null.");
		} else if (context == null) {
			throw new IllegalStateException("The context must be non-null.");
		}
		this.apiKey = apiKey;
		appContext = context;
		this.identifier = identifier;

		this.setBaseURL("https://api.catalyze.io/v1/");
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
	 * Return the context associated with this instance. Cannot be changed.
	 * 
	 * @return The context associated with this instance
	 */
	protected Context getContext() {
		return appContext;
	}

	/**
	 * All Catalyze instances must be associated with an authenticated user via
	 * the authenticate() method. This method returns that user.
	 * 
	 * @return The authenticated user
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not already been authenticated.
	 */
	protected CatalyzeUser getAuthenticatedUser() {
		if (user == null)
			throw new IllegalStateException(
					"No authenticated user has been assigned. Must call Catalyze.authenticate() and wait for the callback before using this instance.");
		return user;
	}

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
	public void authenticate(String userName, String password,
			final CatalyzeListener<Catalyze> callbackHandler) {

		Map<String, String> headers = this.getDefaultHeaders();
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
				Catalyze.this.user = new CatalyzeUser(Catalyze.this);
				user.setJson(response);
				user.setUserSessionToken(response.optString(
						CatalyzeUser.SESSION_TOKEN, null));
				callbackHandler.onResponse(Catalyze.this);
			}
		};
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.POST, this.signInUrl, jsonBody,
				responseListener, createErrorListener(callbackHandler));
		request.setHeaders(headers);
		request.execute(this.appContext);
	}
	
	/**
	 * Perform an API to create a new user.
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param callbackHandler
	 * @param context
	 */
	protected void signUp(String userName, String password, String firstName,
			String lastName, final CatalyzeListener<CatalyzeUser> callbackHandler,
			Context context) {
	
		Map<String, String> headers = this.getDefaultHeaders();
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
				CatalyzeUser user = new CatalyzeUser(Catalyze.this);
				Catalyze.this.user = user;
				callbackHandler.onResponse(user);
			}
		};
		
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.POST, userUrl, jsonBody, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.execute(context);
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
	 * Returns the API key for this Catalyze instance.
	 * 
	 * @return The API key
	 */
	protected String getAPIKey() {
		return apiKey;
	}

	/**
	 * Returns headers required for a non-user authorized API call
	 * 
	 * @return The Map of fields to add to the HTTP header
	 */
	protected Map<String, String> getDefaultHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Api-Key", ANDROID + " " + identifier + " " + getAPIKey());
		headers.put("Content-Type", "application/json");
		return headers;
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
	 * Returns a FileManager instance associated with this Catalyze instance
	 * capable of uploading/downloading and deleting files that the
	 * authenticated user can access.
	 * 
	 * @return The instance of FileManager associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public FileManager getFileManagerInstance() {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		return new FileManager(this);
	}

	/**
	 * Builds and returns a new instance of CustomClass associated with this
	 * Catalyze instance.
	 * 
	 * @param className
	 *            The name of the custom class
	 * @return The instance of CustomClass associated with this Catalyze
	 *         instance
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not been authenticated.
	 */
	public CustomClass getCustomClassInstance(String className) {
		if (user == null) {
			throw new IllegalStateException(
					"The Catalyze instance was not authenticated.");
		}
		return new CustomClass(this, className);
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
				// Currently we just pass up volley errors but this will be
				// extended in time
				CatalyzeError ce = (CatalyzeError) error;
				userCallback.onError(ce);
			}
		};
	}

}

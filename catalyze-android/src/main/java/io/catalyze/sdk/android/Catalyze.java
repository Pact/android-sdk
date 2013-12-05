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
	protected static final String BASE_URL = "https://api.catalyze.io/v1/";

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

	protected Context getContext() {
		return appContext;
	}

	protected CatalyzeUser getAuthenticatedUser() {
		return user;
	}

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
				CatalyzeUser.SIGNIN_URL, jsonBody, responseListener,
				createErrorListener(callbackHandler));
		request.setHeaders(headers);
		request.post(this.appContext);
	}

	private Response.Listener<JSONObject> createSupervisorListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = new CatalyzeUser(Catalyze.this);
				user.setJson(response);
				user.setUserSessionToken(user.getSessionToken());
				userCallback.onResponse(user);
			}
		};
	}

	protected void getUser(String userName,
			CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSupervisorListener(callbackHandler);
		Response.ErrorListener errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeUser.USER_ROUTE + "/" + userName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	protected Map<String, String> getAuthorizedHeaders() {
		Map<String, String> headers = this.getDefaultHeaders();
		headers.put("Authorization", "Bearer " + user.getSessionToken());
		return headers;
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

	public Query getQueryInstance(String className) {
		return new Query(className, this);
	}

	public FileManager getFileManagerInstance() {
		return new FileManager(this);
	}

	public CustomClass getCustomClassInstance(String className) {
		return new CustomClass(className, this);
	}

	public CatalyzeUser getCatalyzeUserInstance() {
		return new CatalyzeUser(this);
	}

	public UMLS getUmlsInstance() {
		UMLS umls = new UMLS(this);
		return umls;
	}

	/**
	 * Generic volley error callback handler, returns a CatalyzeError back to
	 * the user passed callback handler
	 * 
	 * @param userCallback
	 * @return
	 */
	protected static <T> Response.ErrorListener createErrorListener(
			final CatalyzeListener<T> userCallback) {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				CatalyzeError ce = new CatalyzeError(error);
				userCallback.onError(ce);
			}
		};
	}

}

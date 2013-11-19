package io.catalyze.sdk.android;

import java.util.HashMap;
import java.util.Map;

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

	/**
	 * Catalyze Constructor for logging in an existing user
	 * 
	 * @param apiKey
	 * @param userName
	 * @param password
	 * @param context
	 */
	public Catalyze(String apiKey, String identifier, String userName, String password,
			CatalyzeListener<CatalyzeUser> handleResponse, Context context) {
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
	 * Use method to perform an API call to sign into the application.
	 * 
	 * @param userName
	 * @param password
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will contain all
	 *            of the user info for the now logged in user
	 */
	public void getUser(String userName, String password, CatalyzeListener<CatalyzeUser> callbackHandler) {
		user = new CatalyzeUser(this);
		user.getAuthenticatedUser(userName, password, callbackHandler, appContext);
	}

	/**
	 * Perform API call to create a new user for this application. Will return
	 * an instance of CatalyzeUser to the provided callback function
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will contain all
	 *            of the user info for the newly created now logged in user
	 */
	public void signUp(String userName, String password, String firstName, String lastName,
			CatalyzeListener<CatalyzeUser> callbackHandler) {
		user = new CatalyzeUser(this);
		user.signUp(userName, password, firstName, lastName, callbackHandler, appContext);
	}

	/**
	 * Log out the current user
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will be the same
	 *            instance used to call this method, but will no longer be
	 *            authenticated or contain user info.
	 */
	public void logoutCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.signOut(callbackHandler, appContext);
	}

	/**
	 * Delete the user currently associated with this instance of Catalyze. Be
	 * careful using this as it will delete the user completely from the
	 * database.
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will be the same
	 *            instance used to call this method, but will no longer be
	 *            authenticated or contain user info.
	 */
	public void deleteCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.delete(callbackHandler, appContext);
	}

	/**
	 * This route can only be used if you are the supervisor of your
	 * application. Get details about a user with the given userName.
	 * 
	 * @param userName
	 * @param callbackHandler
	 */
	public void lookupUser(String userName, CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.getUserInfo(userName, callbackHandler, appContext);
	}

	/**
	 * This route can only be used if you are the supervisor of your
	 * application. Updates a given user and associated details. Include the
	 * fields or data elements you wish to add or update into the CatalyzeUser
	 * you wish to update. Reccommend using lookupUser to first get CatalyzeUser
	 * to update, as the CatalyzeUser will be pushed to server as is, meaning
	 * any fields that are null or blank will be saved on the server as null or
	 * blank.
	 * 
	 * @param user
	 * @param callbackHandler
	 */
	public void updateUser(CatalyzeUser otherUser, CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.updateUser(otherUser, callbackHandler, appContext);
	}

	/**
	 * This route can only be used if you are the supervisor of your
	 * application. Delete a specific field from the indicated user's data. This
	 * is primarily meant to manage the data captured by the "extras" array.
	 * Replace "fieldName" in the route with the key value that you used in the
	 * extras array. Please do not use this to delete the username etc. As
	 * always, Be careful using this as it will delete the data completely from
	 * the database.
	 * 
	 * @param userName
	 * @param fieldName
	 * @param callbackHandler
	 */
	public void deleteAUserField(String userName, String fieldName, CatalyzeListener<CatalyzeUser> callbackHandler) {
		user.deleteUserField(userName, fieldName, callbackHandler, appContext);
	}

	/**
	 * Get details about a user with the given username. This route will only
	 * work if the currently logged in User is a supervisor for the given
	 * application.
	 * 
	 * @param partialUsername
	 * @param callbackHandler
	 */
	public void searchForUser(String partialUsername, CatalyzeListener<String[]> callbackHandler) {
		user.searchForUser(partialUsername, callbackHandler, appContext);
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

package io.catalyze.sdk.android;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.base.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import io.catalyze.sdk.android.user.Gender;
import io.catalyze.sdk.android.user.ZipCode;

/**
 * Created by mvolkhart on 8/23/13.
 */
/***
 * This class contains methods to interact with all Catalyze User and Auth API
 * routes. An authenticated user should be created using Catalze.getUser(),
 * which will pass back an authenticated CatalyzeUser to the provided callback
 * hander.
 * 
 * 
 * @author Tyler
 * 
 */
public class CatalyzeUser extends CatalyzeObject implements Comparable<CatalyzeUser> {

	// JSON user field headers
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String DATE_OF_BIRTH = "dateOfBirth";
	private static final String AGE = "age";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String STREET = "street";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String COUNTRY = "country";
	static final String ID = "id";
	private static final String EXTRAS = "extras";
	private static final String GENDER = "gender";
	static final String SESSION_TOKEN = "sessionToken";
	private static final String ZIP_CODE = "zipCode";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	// URL s
	private static final String SIGNIN_URL = Catalyze.BASE_URL + "auth/signin";
	private static final String SIGOUT_URL = Catalyze.BASE_URL + "auth/signout";
	private static final String USER_ROUTE = Catalyze.BASE_URL + "user";
	private String userSessionToken;
	Catalyze catalyze;

	public CatalyzeUser(Catalyze catalyze) {
		this.catalyze = catalyze;
	}

	public CatalyzeUser(JSONObject json) {
		super(json);
	}

	// Getter/Setters
	public String getUsername() {
		return mJson.optString(USERNAME, null);
	}

	public CatalyzeUser setUsername(String username) {
		try {
			mJson.put(USERNAME, username);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getFirstName() {
		return mJson.optString(FIRST_NAME, null);
	}

	public CatalyzeUser setFirstName(String firstName) {
		try {
			mJson.put(FIRST_NAME, firstName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getLastName() {
		return mJson.optString(LAST_NAME, null);
	}

	public CatalyzeUser setLastName(String lastName) {
		try {
			mJson.put(LAST_NAME, lastName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Date getDateOfBirth() {
		String fromJson = mJson.optString(DATE_OF_BIRTH, null);
		Date retVal = null;
		if (fromJson != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			try {
				retVal = formatter.parse(fromJson);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	public CatalyzeUser setDateOfBirth(Date dateOfBirth) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			mJson.put(DATE_OF_BIRTH, dateOfBirth);
			// mJson.put(DATE_OF_BIRTH, formatter.format(dateOfBirth));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public CatalyzeUser setDateOfBirth(String dateOfBirth) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			mJson.put(DATE_OF_BIRTH, dateOfBirth);
			// mJson.put(DATE_OF_BIRTH, formatter.format(dateOfBirth));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public int getAge() {
		return mJson.optInt(AGE, 0);
	}

	public CatalyzeUser setAge(int age) {
		try {
			mJson.put(AGE, age);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getPhoneNumber() {
		return mJson.optString(PHONE_NUMBER, null);
	}

	public CatalyzeUser setPhoneNumber(String phoneNumber) {
		try {
			mJson.put(PHONE_NUMBER, phoneNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getStreet() {
		return mJson.optString(STREET, null);
	}

	public CatalyzeUser setStreet(String street) {
		try {
			mJson.put(STREET, street);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getCity() {
		return mJson.optString(CITY, null);
	}

	public CatalyzeUser setCity(String city) {
		try {
			mJson.put(CITY, city);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getState() {
		return mJson.optString(STATE, null);
	}

	public CatalyzeUser setState(String state) {
		try {
			mJson.put(STATE, state);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public ZipCode getZipCode() {
		String fromJson = mJson.optString(ZIP_CODE, null);
		if (fromJson != null) {
			return new ZipCode(fromJson);
		}
		return null;
	}

	public CatalyzeUser setZipCode(ZipCode zipCode) {
		try {
			mJson.put(ZIP_CODE, zipCode.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public CatalyzeUser setCountry(String country) {
		try {
			mJson.put(COUNTRY, country);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getCountry() {
		return mJson.optString(COUNTRY, null);
	}

	public Gender getGender() {
		String fromJson = mJson.optString(STATE, null);
		if (fromJson != null) {
			return Gender.fromString(fromJson);
		}
		return null;
	}

	public CatalyzeUser setGender(Gender gender) {
		try {
			mJson.put(GENDER, gender.toString());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	CatalyzeUser setId(String id) {
		try {
			mJson.put(ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getUserSessionToken() {
		return userSessionToken;
	}

	public void setUserSessionToken(String userSessionToken) {
		this.userSessionToken = userSessionToken;
	}

	private String getSessionToken() {
		return mJson.optString(SESSION_TOKEN, null);
	}

	private CatalyzeUser setSessionToken(String sessionToken) {
		try {
			mJson.put(SESSION_TOKEN, sessionToken);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Map<String, Object> getExtras() {
		return handleJSONObject(mJson.optJSONObject(EXTRAS));
	}

	public CatalyzeUser setExtras(Map<String, Object> extras) {
		new JSONObject(extras);
		return this;
	}

	@Override
	public int compareTo(CatalyzeUser user) {
		final String thisFullName = Strings.nullToEmpty(getFirstName()) + Strings.nullToEmpty(getLastName());
		final String thatFullName = Strings.nullToEmpty(user.getFirstName()) + Strings.nullToEmpty(user.getLastName());
		return thisFullName.compareTo(thatFullName);
	}

	/**
	 * Authenticate this user with userName and password upon HTTP response
	 * 
	 * @param userName
	 * @param password
	 * @param callbackHandler
	 * @param context
	 */
	protected void getAuthenticatedUser(String userName, String password,
			CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = catalyze.getDefaultHeaders();
		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put(USERNAME, userName);
			jsonBody.put(PASSWORD, password);
		} catch (JSONException e) {
		}
		responseListener = createListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(SIGNIN_URL, jsonBody, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.post(context);
	}

	/**
	 * Retrieve user details
	 * 
	 * @param callbackHandler
	 * @param context
	 */
	public void getUser(CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/***
	 * TODO currently removes nulls Pushes any changes made to this instance of
	 * user to server
	 * 
	 * @param context
	 */
	public void update(CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		JSONObject json = asJson();
		JSONObject updates = new JSONObject();

		// Remove data fields from user that should not be included in update
		// Iterator<String> keys = json.keys();
		try {
			// do {
			updates.put(FIRST_NAME, json.get(FIRST_NAME));
			updates.put(LAST_NAME, json.get(LAST_NAME));
			updates.put(DATE_OF_BIRTH, json.get(DATE_OF_BIRTH));
			updates.put(AGE, json.get(AGE));
			updates.put(PHONE_NUMBER, json.get(PHONE_NUMBER));
			updates.put(STREET, json.get(STREET));
			updates.put(CITY, json.get(CITY));
			updates.put(STATE, json.get(STATE));
			updates.put(COUNTRY, json.get(COUNTRY));
			updates.put(EXTRAS, json.get(EXTRAS));
			updates.put(GENDER, json.get(GENDER));
			updates.put(ZIP_CODE, json.get(ZIP_CODE));
			// String k = keys.next();
			// if (!json.isNull(k)) {
			// updates.put(k, json.get(k));
			// }
			// } while (keys.hasNext());
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		// updates.remove(USERNAME);
		// updates.remove(ID);
		// updates.remove(SESSION_TOKEN);
		// updates.remove("appId");
		// updates.remove("updatedAt");
		// updates.remove("createdAt");
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE, updates, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.put(catalyze.getContext());
	}

	/**
	 * Call the delete user api route, will also clear session data and current
	 * user info
	 * 
	 * @param callbackHandler
	 * @param context
	 */
	protected void delete(CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSignoutListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.delete(context);
	}

	/**
	 * Perform an API to create a new user
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param callbackHandler
	 * @param context
	 */
	protected void signUp(String userName, String password, String firstName, String lastName,
			CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		this.setUsername(userName);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		signUp(password, callbackHandler, context);
	}

	/**
	 * Signup new user with the information in this user and the provided
	 * password
	 * 
	 * @param password
	 * @param callbackHandler
	 * @param context
	 */
	private void signUp(String password, CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = catalyze.getDefaultHeaders();
		JSONObject jsonBody = asJson();
		try {
			jsonBody.put(PASSWORD, password);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		responseListener = createListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		// errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE, jsonBody, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.post(context);
	}

	/**
	 * Sign out this user
	 * 
	 * @param callbackHandler
	 *            Funtion to call after HTTP response handled
	 * @param context
	 */
	protected void signOut(CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSignoutListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(SIGOUT_URL, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	// TODO - test
	public void deleteField(String fieldName, CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSignoutListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + fieldName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.delete(context);
	}

	// TODO Supervisor routes - delete field need testing
	/***
	 * Lookup user info, must be a supervisor for this route to work
	 * 
	 * @param userName
	 *            name of user to lookup
	 * @param callbackHandler
	 * @param context
	 */
	protected void getUserInfo(String userName, CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSupervisorListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + userName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/**
	 * Supervisor call to update a user
	 * @param user
	 * @param callbackHandler
	 * @param context
	 */
	protected void updateUser(CatalyzeUser user, CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {

		JSONObject json = user.asJson();
		JSONObject updates = new JSONObject();
		try {
			updates.put(FIRST_NAME, json.get(FIRST_NAME));
			updates.put(LAST_NAME, json.get(LAST_NAME));
			updates.put(DATE_OF_BIRTH, json.get(DATE_OF_BIRTH));
			updates.put(AGE, json.get(AGE));
			updates.put(PHONE_NUMBER, json.get(PHONE_NUMBER));
			updates.put(STREET, json.get(STREET));
			updates.put(CITY, json.get(CITY));
			updates.put(STATE, json.get(STATE));
			updates.put(COUNTRY, json.get(COUNTRY));
			updates.put(EXTRAS, json.get(EXTRAS));
			updates.put(GENDER, json.get(GENDER));
			updates.put(ZIP_CODE, json.get(ZIP_CODE));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSupervisorListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + user.getUsername(),
				updates, responseListener, errorListener);
		request.setHeaders(headers);
		request.put(context);
	}

	protected void deleteUserField(String userName, String fieldName, CatalyzeListener<CatalyzeUser> callbackHandler,
			Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSupervisorDeleteListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + userName + "/"
				+ fieldName, null, responseListener, errorListener);
		request.setHeaders(headers);
		request.delete(context);
	}

	/**
	 * Supervisor route, returns array of strings containing all users with
	 * 'partialUsername' as part of username to callback
	 * 
	 * @param partialUsername
	 * @param callbackHandler
	 * @param context
	 */
	protected void searchForUser(String partialUsername, CatalyzeListener<String[]> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		responseListener = createSupervisorSearchListener(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				USER_ROUTE + "/search/" + partialUsername, null, responseListener, errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/**
	 * Return HTTP headers needed for authorized api calls
	 * 
	 * @return
	 */
	protected Map<String, String> getAuthorizedHeaders() {
		Map<String, String> headers = catalyze.getDefaultHeaders();
		headers.put("Authorization", "Bearer " + getSessionToken());
		return headers;
	}

	// User response handlers

	/**
	 * Create response handler that a user object based on the server response
	 * 
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<JSONObject> createListener(final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = CatalyzeUser.this;
				user.setJson(response);
				user.setUserSessionToken(getSessionToken());
				userCallback.onResponse(user);
			}
		};
	}

	/***
	 * Response handler to return user but not overwrite current user
	 * 
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<JSONObject> createSupervisorListener(final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = new CatalyzeUser(response);
				user.setUserSessionToken(getSessionToken());
				userCallback.onResponse(user);
			}
		};
	}

	// TODO
	private Response.Listener<JSONObject> createSupervisorDeleteListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				userCallback.onResponse(null);
			}
		};
	}

	/**
	 * Callback handler for search route response
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<JSONObject> createSupervisorSearchListener(final CatalyzeListener<String[]> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				String[] stringResults = new String[0];
				try {
					JSONArray results = response.getJSONArray("results");
					stringResults = new String[results.length()];
					for (int i = 0; i < results.length(); i++) {
						stringResults[i] = results.getString(i);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				userCallback.onResponse(stringResults);
			}
		};
	}

	/**
	 * This is supposed to be the logout/delete handler, not currently reached
	 * as those cause a weird bug that goes to the error handler
	 * 
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<JSONObject> createSignoutListener(final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				setJson(null);
				setUserSessionToken(null);

				userCallback.onResponse(CatalyzeUser.this);
			}
		};
	}

//	/***
//	 * FIXME Maybe figure out a better way around this bug? Specialized error
//	 * listener for use in delete and signout path to deal with "End of input"
//	 * json error - test that this is fixed
//	 * 
//	 * @return
//	 */
//	private Response.ErrorListener blankResponseErrorHandler(final CatalyzeListener<CatalyzeUser> userCallback) {
//		return new Response.ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				CatalyzeError ce = new CatalyzeError(error);
//				if (error.getMessage() != null
//						&& error.getMessage().equals("org.json.JSONException: End of input at character 0 of ")) {
//					setJson(null);
//					setUserSessionToken(null);
//					userCallback.onSuccess(CatalyzeUser.this);
//				} else
//					userCallback.onError(ce);
//			}
//		};
//	}
}

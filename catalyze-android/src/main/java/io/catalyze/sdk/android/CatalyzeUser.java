package io.catalyze.sdk.android;

import io.catalyze.sdk.android.user.Gender;
import io.catalyze.sdk.android.user.ZipCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.google.common.base.Strings;

/***
 * This class contains methods to interact with all CatalyzeUser and Auth API
 * routes. An authenticated user should be created using Catalze.getUser(),
 * which will pass back an authenticated CatalyzeUser to the provided callback
 * hander.
 * 
 * 
 * 
 */
public class CatalyzeUser extends CatalyzeObject implements Comparable<CatalyzeUser> {

	// JSON user field headers
	protected static final String USERNAME = "username";
	protected static final String PASSWORD = "password";
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
	protected static final String SESSION_TOKEN = "sessionToken";
	private static final String ZIP_CODE = "zipCode";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	// URL s
	protected static final String SIGNIN_URL = Catalyze.BASE_URL + "auth/signin";
	protected static final String SIGOUT_URL = Catalyze.BASE_URL + "auth/signout";
	protected static final String USER_ROUTE = Catalyze.BASE_URL + "user";
	private String userSessionToken;
	
	public CatalyzeUser(Catalyze catalyze) {
		super(catalyze);
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
		// SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			mJson.put(DATE_OF_BIRTH, dateOfBirth);
			// mJson.put(DATE_OF_BIRTH, formatter.format(dateOfBirth));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public CatalyzeUser setDateOfBirth(String dateOfBirth) {
		// SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
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

	protected String getSessionToken() {
		return mJson.optString(SESSION_TOKEN, null);
	}

	public Map<String, Object> getExtras() {
		return handleJSONObject(mJson.optJSONObject(EXTRAS));
	}

	public CatalyzeUser setExtras(Map<String, Object> extras) {
		new JSONObject(extras);
		return this;
	}
	
	public void setExtra(String key, Object value){
		try {
			mJson.getJSONObject(EXTRAS).put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public Object getExtra(String key){
		try {
			return mJson.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void removeExtra(String key){
		mJson.remove(key);
	}

	@Override
	public int compareTo(CatalyzeUser user) {
		final String thisFullName = Strings.nullToEmpty(getFirstName()) + Strings.nullToEmpty(getLastName());
		final String thatFullName = Strings.nullToEmpty(user.getFirstName()) + Strings.nullToEmpty(user.getLastName());
		return thisFullName.compareTo(thatFullName);
	}

	/**
	 * Use this to sign into the application.
	 * 
	 * @param userName
	 * @param password
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will contain all
	 *            of the user info for the now logged in user
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
	    
	    Response.Listener<JSONObject> responseListener = createListener(callbackHandler);
	    Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
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
		Response.Listener<JSONObject> responseListener = createListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/***
	 * Update a currently logged in user and associated details. Include the
	 * fields or data elements you wish to add or update in the appropriate data
	 * fields of this instance of CatalyzeUser.
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a CatalyzeUser on successful
	 *            callback. The CatalyzeUser instance returned will be a
	 *            reference to the the same instance of CatalyzeUser that was
	 *            used to call this method.
	 */
	public void update(CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		JSONObject json = asJson();
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
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
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
		Response.Listener<JSONObject> responseListener = createListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
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
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(SIGOUT_URL, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/**
	 * Delete a specific field from the user's data. This is primarily meant to
	 * manage the data captured by the "extras" array. Replace "fieldName" in
	 * the route with the key value that you used in the extras array. Please do
	 * not use this to delete the username etc. As always, Be careful using this
	 * as it will delete the data completely from the database.
	 * 
	 * @param fieldName
	 * @param callbackHandler
	 */
	public void deleteField(String fieldName, CatalyzeListener<CatalyzeUser> callbackHandler) {
		if (fieldName == null || fieldName.trim().length() == 0) {
			//if fieldName is blank this will otherwise delete the user
			return;
		}
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = deleteFieldListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + fieldName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.delete(catalyze.getContext());
	}

	/***
	 * This route can only be used if you are the supervisor of your
	 * application. Get details about a user with the given username.
	 * 
	 * @param userName
	 *            name of user to lookup
	 * @param callbackHandler
	 * @param context
	 */
	protected void getUserInfo(String userName, CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSupervisorListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + userName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.get(context);
	}

	/**
	 * This route can only be used if you are the supervisor of your
	 * application. Updates a given user and associated details. Include the
	 * fields or data elements you wish to add or update in the instance of the
	 * CatalyzeUser. Any fields that are null or empty will be updated as such.
	 * 
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
		Response.Listener<JSONObject> responseListener = createSupervisorListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(USER_ROUTE + "/" + user.getUsername(),
				updates, responseListener, errorListener);
		request.setHeaders(headers);
		request.put(context);
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
	 * @param context
	 */
	protected void deleteUserField(String userName, String fieldName, CatalyzeListener<CatalyzeUser> callbackHandler,
			Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSupervisorListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
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
		Response.Listener<JSONObject> responseListener = createSupervisorSearchListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze.createErrorListener(callbackHandler);
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
	
	private Response.Listener<JSONObject> deleteFieldListener(final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				userCallback.onResponse(CatalyzeUser.this);
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
				CatalyzeUser user = new CatalyzeUser(CatalyzeUser.this.catalyze);
				user.setJson(response);
				user.setUserSessionToken(getSessionToken());
				userCallback.onResponse(user);
			}
		};
	}

	/**
	 * Callback handler for search route response
	 * 
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
}

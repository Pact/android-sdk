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
 * 
 * 
 */
public class CatalyzeUser extends CatalyzeObject implements
		Comparable<CatalyzeUser> {

	// JSON user field headers
	protected static final String USERNAME = "username";
	protected static final String PASSWORD = "password";
	protected static final String FIRST_NAME = "firstName";
	protected static final String LAST_NAME = "lastName";
	protected static final String DATE_OF_BIRTH = "dateOfBirth";
	protected static final String AGE = "age";
	protected static final String PHONE_NUMBER = "phoneNumber";
	protected static final String STREET = "street";
	protected static final String CITY = "city";
	protected static final String STATE = "state";
	protected static final String COUNTRY = "country";
	protected static final String ID = "id";
	protected static final String EXTRAS = "extras";
	protected static final String GENDER = "gender";
	protected static final String SESSION_TOKEN = "sessionToken";
	protected static final String ZIP_CODE = "zipCode";
	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	// URLs
	protected String signOutUrl = null;
	protected String userUrl = null;
	
	// The authenticated session token
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

	public void setExtra(String key, Object value) {
		try {
			mJson.getJSONObject(EXTRAS).put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Object getExtra(String key) {
		try {
			return mJson.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void removeExtra(String key) {
		mJson.remove(key);
	}

	@Override
	public int compareTo(CatalyzeUser user) {
		return this.getId().compareTo(user.getId());
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
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		JSONObject json = this.mJson;
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
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.PUT, userUrl, updates, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.execute(catalyze.getContext());
	}

	/**
	 * Call the delete user api route, will also clear session data and current
	 * user info
	 * 
	 * @param callbackHandler
	 * @param context
	 */
	public void delete(CatalyzeListener<CatalyzeUser> callbackHandler,
			Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, userUrl, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.execute(context);
	}

	

	/**
	 * Sign out this user
	 * 
	 * @param callbackHandler
	 *            Funtion to call after HTTP response handled
	 * @param context
	 */
	public void signOut(CatalyzeListener<CatalyzeUser> callbackHandler,
			Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, signOutUrl, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.execute(context);
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
	public void deleteField(String fieldName,
			CatalyzeListener<CatalyzeUser> callbackHandler) {
		if (fieldName == null || fieldName.trim().length() == 0) {
			// if fieldName is blank this will otherwise delete the user
			return;
		}
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = deleteFieldListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, userUrl + "/" + fieldName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute(catalyze.getContext());
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
	public void getUser(String userName,
			CatalyzeListener<CatalyzeUser> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSupervisorListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, userUrl + "/" + userName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute(context);
	}


	/**
	 * Supervisor route, returns array of strings containing all users with
	 * 'partialUsername' as part of username to callback
	 * 
	 * @param partialUsername
	 * @param callbackHandler
	 * @param context
	 */
	public void search(String partialUsername,
			CatalyzeListener<String[]> callbackHandler, Context context) {
		Map<String, String> headers = getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSupervisorSearchListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, userUrl + "/search/" + partialUsername,
				null, responseListener, errorListener);
		request.setHeaders(headers);
		request.execute(context);
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
	private Response.Listener<JSONObject> createListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
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

	private Response.Listener<JSONObject> deleteFieldListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
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
	private Response.Listener<JSONObject> createSupervisorListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
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
	private Response.Listener<JSONObject> createSupervisorSearchListener(
			final CatalyzeListener<String[]> userCallback) {
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
	private Response.Listener<JSONObject> createSignoutListener(
			final CatalyzeListener<CatalyzeUser> userCallback) {
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

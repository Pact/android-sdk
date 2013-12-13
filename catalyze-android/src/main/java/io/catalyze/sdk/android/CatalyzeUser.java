package io.catalyze.sdk.android;

import io.catalyze.sdk.android.user.Gender;
import io.catalyze.sdk.android.user.ZipCode;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

/***
 * This class is a critical element to understand when interacting with the SDK
 * (and thus the Catalyze API).
 * 
 * To perform most operations in the SDK and authenticated CataklyzeUser is
 * needed. A call to Catalyze.authenticate() will take care of this and is a
 * necessary first step for using a Catalyze instance (an exception to this is
 * UMLS lookups which only require an API key).
 * 
 * CatalyzeUsers can also be created from authenticated Catalyze instances. This
 * becomes possible when the authenticated user is a supervisor of the other
 * users that are being accessed.
 * 
 * 
 */
public class CatalyzeUser extends CatalyzeObject implements
		Comparable<CatalyzeUser>, Serializable {

	/**    
	 * 
	 */
	private static final long serialVersionUID = 4152351948089497620L;
	
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

	// The authenticated session token
	private String sessionToken;

	/**
	 * Create a new CatalyzeUser associated with an authenticated Catalyze.
	 * 
	 * @param catalyze
	 *            An authenticated Catalyze instance
	 */
	protected CatalyzeUser(Catalyze catalyze) {
		super(catalyze);
	}

	/**
	 * Compare by user ID, a unique value defined by the API. 
	 */
	@Override
	public int compareTo(CatalyzeUser user) {
		return this.getId().compareTo(user.getId());
	}

	/**
	 * Call the delete user api route, will also clear session data and current
	 * user info
	 * 
	 * @param callbackHandler
	 */
	public void delete(CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = catalyze.getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, catalyze.userUrl, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
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
			final CatalyzeListener<CatalyzeUser> callbackHandler) {
		if (fieldName == null || fieldName.trim().length() == 0) {
			// if fieldName is blank this will otherwise delete the user
			return;
		}
		Map<String, String> headers = catalyze.getAuthorizedHeaders();

		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				callbackHandler.onResponse(CatalyzeUser.this);
			}
		};

		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, catalyze.userUrl + "/" + fieldName,
				null, responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
	}

	/**
	 * Returns the age of the user.
	 * 
	 * @return The user's age
	 */
	public int getAge() {
		return mJson.optInt(AGE, 0);
	}

	/**
	 * Returns the city portion of the user's address.
	 * 
	 * @return The city
	 */
	public String getCity() {
		return mJson.optString(CITY, null);
	}

	/**
	 * Gets the country portion of the user's address.
	 * 
	 * @return The country
	 */
	public String getCountry() {
		return mJson.optString(COUNTRY, null);
	}

	/**
	 * Returns the date of birth of this user.
	 * 
	 * @return The birthday
	 */
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

	/**
	 * Return the extra associated with this key. Returns null if there is no
	 * matching key.
	 * 
	 * @param key
	 *            The extra key to find
	 * @return The value of the extra or null if no such key exists
	 */
	public Object getExtra(String key) {
		try {

			// Extras may not exist, check first
			JSONObject extras = mJson.optJSONObject(EXTRAS);
			if (extras != null) {
				return extras.get(key);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null; // If there were no extras at all
	}

	/**
	 * Return a Map of the user-defined extras for this object.
	 * 
	 * @return The extras
	 */
	public Map<String, Object> getExtras() {
		return handleJSONObject(mJson.optJSONObject(EXTRAS));
	}

	/**
	 * Returns the first name of the user.
	 * 
	 * @return The first name
	 */
	public String getFirstName() {
		return mJson.optString(FIRST_NAME, null);
	}

	/**
	 * Gets the user's gender.
	 * 
	 * @return The gender
	 */
	public Gender getGender() {
		String fromJson = mJson.optString(STATE, null);
		if (fromJson != null) {
			return Gender.fromString(fromJson);
		}
		return null;
	}

	/**
	 * Returns the user's ID which is generated upon creation on the backend.
	 * 
	 * @return The user's ID
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
	 * Returns the user's last name.
	 * 
	 * @return The last name
	 */
	public String getLastName() {
		return mJson.optString(LAST_NAME, null);
	}

	/**
	 * Returns the user's phone number. Currently free-form.
	 * 
	 * @return The phone number
	 */
	public String getPhoneNumber() {
		return mJson.optString(PHONE_NUMBER, null);
	}

	/**
	 * Gets the user's session token, if any (only an authenticated CatalyzeUser
	 * instance has a session token).
	 * 
	 * @return The session token, of any
	 */
	public String getSessionToken() {
		return sessionToken;
	}

	/**
	 * Returns the state portion of the user's address.
	 * 
	 * @return The state
	 */
	public String getState() {
		return mJson.optString(STATE, null);
	}

	/**
	 * Returns the street portion of the user's address.
	 * 
	 * @return The street
	 */
	public String getStreet() {
		return mJson.optString(STREET, null);
	}

	/***
	 * This route can only be used if you are the supervisor of your
	 * application. Get details about a user with the given username.
	 * 
	 * @param userName
	 *            name of user to lookup
	 * @param callbackHandler
	 */
	public void getUser(String userName,
			final CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = catalyze.getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = new CatalyzeUser(CatalyzeUser.this.catalyze);
				user.setJson(response);
				callbackHandler.onResponse(user);
			}
		};

		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, catalyze.userUrl + "/" + userName, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
	}

	/**
	 * Returns the username of the user.
	 * 
	 * @return The username
	 */
	public String getUsername() {
		return mJson.optString(USERNAME, null);
	}

	/**
	 * Gets the zip code portion of the user's address.
	 * 
	 * @return The zip code
	 */
	public ZipCode getZipCode() {
		String fromJson = mJson.optString(ZIP_CODE, null);
		if (fromJson != null) {
			return new ZipCode(fromJson);
		}
		return null;
	}

	/**
	 * Remove an extra identified by key and return the value.
	 * 
	 * @param key
	 *            The key to remove from the user extras
	 * @return The value of the item removed or null if nothing was removed (or
	 *         null was set as the value).
	 */
	public Object removeExtra(String key) {

		// Extras may not exist, check first
		JSONObject extras = mJson.optJSONObject(EXTRAS);
		if (extras != null) {
			return extras.remove(key);
		}

		return null;
	}

	/**
	 * Supervisor route, returns array of strings containing all users with
	 * 'partialUsername' as part of username to callback
	 * 
	 * @param partialUsername
	 * @param callbackHandler
	 */
	public void search(String partialUsername,
			final CatalyzeListener<String[]> callbackHandler) {
		Map<String, String> headers = catalyze.getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
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
				callbackHandler.onResponse(stringResults);
			}
		};

		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, catalyze.userUrl + "/search/"
						+ partialUsername, null, responseListener,
				errorListener);
		request.setHeaders(headers);
		request.execute();
	}

	/**
	 * Sets the age of the user locally. Call CatalyzeUser.update() to update on
	 * the backend.
	 * 
	 * @param age
	 *            The new age.
	 */
	public void setAge(int age) {
		try {
			mJson.put(AGE, age);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the city portion of the user's address locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param city
	 *            The new city
	 */
	public void setCity(String city) {
		try {
			mJson.put(CITY, city);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the country portion of the user's address locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param country
	 *            The new country
	 */
	public void setCountry(String country) {
		try {
			mJson.put(COUNTRY, country);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the data of birth of the user locally. Call CatalyzeUser.update() to
	 * update on the backend.
	 * 
	 * @param dateOfBirth
	 *            The new birth date
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		try {
			mJson.put(DATE_OF_BIRTH, dateOfBirth.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set part (a single entry) of the user-defined extras. Extras allows for
	 * adding additional fields, values (e.g. adding another phone number or
	 * address). Call CatalyzeUser.update() to update on the backend.
	 * 
	 * @param extras
	 *            The new extras.
	 */
	public void setExtra(String key, Object value) {
		try {
			// Extras may not exist, check first before adding
			JSONObject extras = mJson.optJSONObject(EXTRAS);

			if (extras == null) {
				// If no extras exist create an empty on first
				extras = new JSONObject();
				mJson.put(EXTRAS, extras);
			}

			// Add the extras
			extras.put(key, value);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the user-defined extras. Allows for adding additional fields, values
	 * (e.g. adding another phone number or address). Call CatalyzeUser.update()
	 * to update on the backend.
	 * 
	 * @param extras
	 *            The new extras. Overwrites the old extras.
	 */
	public void setExtras(Map<String, Object> extras) {
		try {
			mJson.put(EXTRAS, new JSONObject(extras));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the first name of the user locally. Call CatalyzeUser.update() to
	 * update on the backend.
	 * 
	 * @param firstName
	 *            The new first name
	 */
	public void setFirstName(String firstName) {
		try {
			mJson.put(FIRST_NAME, firstName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the user's gender locally. Call CatalyzeUser.update() to update on
	 * the backend.
	 * 
	 * @param gender
	 *            The gender
	 */
	public void setGender(Gender gender) {
		try {
			mJson.put(GENDER, gender.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the last name of the user locally. Call CatalyzeUser.update() to
	 * update on the backend.
	 * 
	 * @param lastName
	 *            The new last name
	 */
	public void setLastName(String lastName) {
		try {
			mJson.put(LAST_NAME, lastName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the phone number (currently free-form) of the user locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param phoneNumber
	 *            The new phone number.
	 */
	public void setPhoneNumber(String phoneNumber) {
		try {
			mJson.put(PHONE_NUMBER, phoneNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the state portion of the user's address locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param state
	 *            The new state
	 */
	public void setState(String state) {
		try {
			mJson.put(STATE, state);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the street portion of the user's address locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param street
	 *            The new street
	 */
	public CatalyzeUser setStreet(String street) {
		try {
			mJson.put(STREET, street);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Sets the username of the user locally. Call CatalyzeUser.update() to
	 * update on the backend.
	 * 
	 * @param username
	 *            The new username
	 */
	public void setUsername(String username) {
		try {
			mJson.put(USERNAME, username);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the zip code portion of the user's address locally. Call
	 * CatalyzeUser.update() to update on the backend.
	 * 
	 * @param zipCode
	 *            The new zip code
	 */
	public void setZipCode(ZipCode zipCode) {
		try {
			mJson.put(ZIP_CODE, zipCode.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
	public void update(final CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = catalyze.getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CatalyzeUser user = CatalyzeUser.this;
				user.setJson(response);
				callbackHandler.onResponse(user);
			}
		};

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
				CatalyzeRequest.PUT, catalyze.userUrl, updates,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
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
				setSessionToken(null);

				userCallback.onResponse(CatalyzeUser.this);
			}
		};
	}

	/**
	 * Sets the session token. Not callable from the SDK. This is handled
	 * automatically.
	 * 
	 * @param sessionToken
	 *            The new session token.
	 */
	protected void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	/**
	 * Sign out this user. This is called only from an authenticated Catalyze
	 * object.
	 * 
	 * @param callbackHandler
	 *            Method to call after HTTP response handled
	 */
	protected void signOut(CatalyzeListener<CatalyzeUser> callbackHandler) {
		Map<String, String> headers = catalyze.getAuthorizedHeaders();
		Response.Listener<JSONObject> responseListener = createSignoutListener(callbackHandler);
		Response.ErrorListener errorListener = Catalyze
				.createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, catalyze.signOutUrl, null,
				responseListener, errorListener);
		request.setHeaders(headers);
		request.execute();
	}

	/**
	 * Helper method. Not callable from the SDK as the ID cannot be changed.
	 * 
	 * @param id
	 *            The new ID.
	 */
	void setId(String id) {
		try {
			mJson.put(ID, id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

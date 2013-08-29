package io.catalyze.sdk.android;

import io.catalyze.sdk.ZipCode;
import com.google.common.base.Strings;
import com.neovisionaries.i18n.CountryCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import io.catalyze.sdk.Gender;

/**
 * Created by mvolkhart on 8/23/13.
 */
public class User extends CatalyzeObject implements Comparable<User> {

    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastNAme";
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

    public User() {
        this (new JSONObject());
    }

    User(JSONObject json) {
        super(json);
    }

    public String getUsername() {
        return mJson.optString(USERNAME, null);
    }

    public User setUsername(String username) {
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

    public User setFirstName(String firstName) {
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

    public User setLastName(String lastName) {
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

    public User setDateOfBirth(Date dateOfBirth) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            mJson.put(DATE_OF_BIRTH, formatter.format(dateOfBirth));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public int getAge() {
        return mJson.optInt(AGE, 0);
    }

    public User setAge(int age) {
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

    public User setPhoneNumber(String phoneNumber) {
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

    public User setStreet(String street) {
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

    public User setCity(String city) {
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

    public User setState(String state) {
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

    public User setZipCode(ZipCode zipCode) {
        try {
            mJson.put(ZIP_CODE, zipCode.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CountryCode getCountry() {
        String fromJson = mJson.optString(COUNTRY, null);
        if (fromJson != null) {
            return CountryCode.getByCode(fromJson);
        }
        return null;
    }

    public User setCountry(CountryCode country) {
        try {
            mJson.put(COUNTRY, country.getAlpha2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Gender getGender() {
        String fromJson = mJson.optString(STATE, null);
        if (fromJson != null) {
            return Gender.fromString(fromJson);
        }
        return null;
    }

    public User setGender(Gender gender) {
        try {
            mJson.put(GENDER, gender.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    long getId() {
        return mJson.optLong(ID, Long.MIN_VALUE);
    }

    User setId(long id) {
        try {
            mJson.put(ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    String getSessionToken() {
        return mJson.optString(SESSION_TOKEN, null);
    }

    User setSessionToken(String sessionToken) {
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

    public User setExtras(Map<String, Object> extras) {
        new JSONObject(extras);
        return this;
    }

    @Override
    public int compareTo(User user) {
        final String thisFullName = Strings.nullToEmpty(getFirstName()) + Strings.nullToEmpty
                (getLastName());
        final String thatFullName = Strings.nullToEmpty(user.getFirstName()) + Strings.nullToEmpty
                (user.getLastName());
        return thisFullName.compareTo(thatFullName);
    }
}

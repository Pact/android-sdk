package io.catalyze.sdk.external;

import com.ajah.geo.us.State;
import com.ajah.geo.us.ZipCode;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.catalyze.sdk.exceptions.IllegalAgeFormatException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by marius on 6/18/13.
 */
public class Person extends CatalyzeObject {

    protected static final String sPath = "/person";

    public Person(String firstName, String lastName) {
        mContent.put(sFirstNameKey, firstName);
        mContent.put(sLastNameKey, lastName);
    }

    Person() {
    }

    public Person create(RequestQueue requestQueue, Response.ErrorListener errorListener) {

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
            }
        }, errorListener);
        requestQueue.add(request);
        return this;
    }

    public Person retrieve(RequestQueue requestQueue, Response.ErrorListener errorListener) {

        final CatalyzeObject holder = this;
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.GET, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                holder.inflateFromJson(object);
            }
        }, errorListener);
        requestQueue.add(request);
        return this;
    }

    public Person update(RequestQueue requestQueue, Response.ErrorListener errorListener) {
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.PUT, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
            }
        }, errorListener);
        requestQueue.add(request);
        return this;
    }

    public void delete(RequestQueue requestQueue, Response.ErrorListener errorListener) {
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.DELETE, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
            }
        }, errorListener);
        requestQueue.add(request);
    }

    public String getPhone() {
        return super.getString(sPhoneKey);
    }

    /**
     * @param phone Must be formatted as xxx-xxx-xxxx
     */
    public Person setPhone(String phone) {
        // TODO: do some phone number verification
        mContent.put(sPhoneKey, phone);
        return this;
    }

    public Gender getGender() {
        String gender = super.getString(sGenderKey);
        return Gender.fromString(gender);
    }

    public Person setGender(Gender gender) {
        mContent.put(sGenderKey, gender.toString());
        return this;
    }

    public int getAge() {
        return super.getInt(sAgeKey);
    }

    public Person setAge(int age) throws IllegalAgeFormatException {
        if (age < 0) {
            throw new IllegalAgeFormatException(age);
        }
        mContent.put(sAgeKey, age);
        return this;
    }

    public Date getBirthDate() throws ParseException {
        String s = super.getString(sBirthDateKey);
        return new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    public Person setBirthDate(Date birthDate) {
        mContent.put(sBirthDateKey, new SimpleDateFormat("yyy-MM-dd").format(birthDate));
        return this;
    }

    public String getPersonId() {
        return super.getString(sPersonIdKey);
    }

    public String getEmail() {
        return super.getString(sEmailKey);
    }

    public Person setEmail(String email) {
        //TODO: Implement some regex checking
        // https://code.google.com/p/emailaddress/source/detail?r=5
        mContent.put(sEmailKey, email);
        return this;
    }

    /**
     * Null removes the value
     *
     * @param street
     * @param city
     * @param state
     * @param zipCode
     * @return
     */
    public Person setAddress(String street, String city, State state, ZipCode zipCode) {

        // City, State and zip code are required when a street is provided. Fail fast.
        if (!isNullOrEmpty(street)) {
            checkNotNull(emptyToNull(city));
            checkNotNull(state);
            checkNotNull(zipCode);
        }

        mContent.put(sStreetKey, street);
        mContent.put(sCityKey, city);
        mContent.put(sStateKey, state.getAbbr());
        mContent.put(sZipCodeKey, zipCode.toString());
        return this;
    }

    public ZipCode getZipCode() {
        ZipCode z = new ZipCode();
        z.setZip(super.getString(sZipCodeKey));
        return z;
    }

    public State getState() {
        return State.valueOf(super.getString(sStateKey));
    }

    public String getCity() {
        return super.getString(sCityKey);
    }

    public String getStreet() {
        return super.getString(sStreetKey);
    }

    public String getFirstName() {
        return super.getString(sFirstNameKey);
    }

    public String getLastName() {
        return super.getString(sLastNameKey);
    }
}

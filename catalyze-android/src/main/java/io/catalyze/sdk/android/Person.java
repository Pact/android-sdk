/*
 * Copyright (C) 2013 catalyze.io, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.catalyze.sdk.android;

import com.google.common.base.Strings;

import com.ajah.geo.us.State;
import com.ajah.geo.us.ZipCode;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.catalyze.sdk.Gender;
import io.catalyze.sdk.exceptions.IllegalAgeFormatException;

/**
 * A {@link CatalyzeObject} used to represent Persons on the catalyze.io API.
 *
 * <p>Uses of this class follow a pattern: <ol> <li>Create a new {@code Person} instance.</li> <li>
 * If the person is a returning person, use the {@link #Person(String) Person(uniquePersonId}
 * constructor. Retrieve the person by calling {@link #retrieve(com.android.volley.RequestQueue,
 * com.android.volley.Response.ErrorListener) retrieve(queue, errorListener}.</li> <li>If the person
 * is a new person, use the {@link #Person(String, String) Person(firstName, lastName} constructor.
 * Now call {@link #create(com.android.volley.RequestQueue, com.android.volley.Response.ErrorListener)
 * create(queue, errorListener} and the person will be added to the catalyze.io backend.</li>
 *
 * </ol></p>
 *
 * <p>For example, to create a new person without any metadata:</p>
 * <pre>    {@code
 *      RequestQueue queue = Volley.newRequestQueue((Context) this);
 *      Person person = new Person("John", "Doe");
 *      Request request = person.create(queue, new Response.ErrorListener() {
 *
 *              @Override
 *              public void onErrorResponse(VolleyError error) {
 *                  showErrorMessage(error);
 *              }
 *          });
 *
 * }</pre>
 *
 * <h3>Cancelling Requests</h3> To cancel a request, call {@code Request.cancel()}
 */
public class Person extends CatalyzeObject {

    protected static final String sPath = "/person";

    /**
     * Creates a new {@code Person} object. This {@code Person} will be empty besides the first and
     * last names provided until a {@link #retrieve(com.android.volley.RequestQueue,
     * com.android.volley.Response.ErrorListener)} is called.
     *
     * Use this constructor when creating a new person. Use {@link #Person(String)} when needing to
     * retrieve a {@code Person} from the catalyze.io backend.
     *
     * @param firstName the first name of this {@code Person}
     * @param lastName  the last name of this {@code Person}
     * @see #setLastName(String)  for formatting specifications
     * @see #setFirstName(String) for formatting specifications
     */
    public Person(String firstName, String lastName) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * Creates a new {@code Person} object. This {@code Person} will be empty besides the unique id
     * provided until a {@link #retrieve(com.android.volley.RequestQueue,
     * com.android.volley.Response.ErrorListener)} is called.
     *
     * Use this constructor when needing to retrieve a {@code Person} from the catalyze.io backend.
     * Use {@link #Person(String, String)} when needing to create a new person.
     *
     * @param personId the unique person id of this {@code Person}. This Id is provided by the
     *                 catalyze.io backend.
     */
    public Person(String personId) {
        super();
        put(sPersonIdKey, personId);
    }

    /**
     * Internal constructor used to create empty and populate with data.
     */
    Person() {
        //TODO: See if we can limit scope on this to private
        super();
    }

    /**
     * Creates this {@link Person} on the catalyze.io API backend. This {@link Person} is not
     * modified as a result of this method. If an error occurs performing the HTTP interaction, the
     * interaction is not retried. This {@link Person} is not cached for future upload.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request create(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.create(queue, errorListener, sBasePath + sPath);
    }

    /**
     * Retrieves this {@link Person} from the catalyze.io API backend. This {@link Person} is
     * modified as a result of this method. If an error occurs performing the HTTP interaction, the
     * interaction is not retried.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request retrieve(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.retrieve(queue, errorListener, sBasePath + sPath);
    }

    /**
     * Updates this {@link Person} on the catalyze.io API backend. This {@link Person} is not
     * modified as a result of this method. If an error occurs performing the HTTP interaction, the
     * interaction is not retried. This {@link Person} is not cached for future upload. This call
     * will override any existing data on the backend for this {@link Person} regardless of whether
     * or not the data is out of sync with the local copy.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request update(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.update(queue, errorListener, sBasePath + sPath);
    }

    /**
     * Deletes this {@link Person} from the catalyze.io API backend. This {@link Person} is not
     * modified as a result of this method. If an error occurs performing the HTTP interaction, the
     * interaction is not retried.
     *
     * @param queue         a {@link RequestQueue} from the Volley android library. This queue will
     *                      handle the HTTP interaction with the catalyze.io API servers.
     * @param errorListener to handle errors that may occur during the server interaction. A {@code
     *                      null} value will ignore all errors.
     * @return the {@link Request} representing this server interaction
     */
    public Request delete(RequestQueue queue, Response.ErrorListener errorListener) {
        return super.delete(queue, errorListener, sBasePath + sPath);
    }

    /**
     * @return the phone number for this {@link Person}
     */
    public String getPhone() {
        return super.getString(sPhoneKey);
    }

    /**
     * @param phone the phone number for this {@link Person}
     * @return this {@link Person}
     */
    public Person setPhone(String phone) {
        // TODO: do some phone number verification https://www.pivotaltracker.com/story/show/52651481
        mContent.put(sPhoneKey, phone);
        return this;
    }

    /**
     * @return the {@link io.catalyze.sdk.Gender} of this {@link Person}
     */
    public Gender getGender() {
        String gender = super.getString(sGenderKey);
        return Gender.fromString(gender);
    }

    /**
     * @param gender the {@link Gender} of this {@link Person}
     * @return this {@link Person}
     */
    public Person setGender(Gender gender) {
        mContent.put(sGenderKey, gender.toString());
        return this;
    }

    /**
     * @return the age of this {@link Person}
     * @throws io.catalyze.sdk.exceptions.IllegalAgeFormatException
     *          if the age provided is less than 0
     */
    public int getAge() throws IllegalAgeFormatException {
        int age = super.getInt(sAgeKey);
        if (age < 0) {
            throw new IllegalAgeFormatException(age);
        }
        return age;
    }

    /**
     * @param age the age of this {@link Person}
     * @return this {@link Person}
     * @throws IllegalAgeFormatException if the age provided is less than 0
     */
    public Person setAge(int age) throws IllegalAgeFormatException {
        if (age < 0) {
            throw new IllegalAgeFormatException(age);
        }
        mContent.put(sAgeKey, age);
        return this;
    }

    /**
     * @return a {@link Date} representing the month, day and year of when this {@link Person} was
     *         born
     * @throws ParseException if the date on the catalyze.io backend has been modified into an
     *                        non-ISO-8601 compliant format
     */
    public Date getBirthDate() throws ParseException {
        String s = super.getString(sBirthDateKey);
        return new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    /**
     * @param birthDate a {@link Date} of when this {@link Person} was born. This date does not need
     *                  to be in any specific format.
     * @return this {@link Person}
     */
    public Person setBirthDate(Date birthDate) {
        mContent.put(sBirthDateKey, new SimpleDateFormat("yyyy-MM-dd").format(birthDate));
        return this;
    }

    /**
     * @return a string which uniquely identifies this {@link Person} in the catalyze.io database
     */
    public String getPersonId() {
        return super.getString(sPersonIdKey);
    }

    /**
     * @return the email address of this {@link Person}
     */
    public String getEmail() {
        return super.getString(sEmailKey);
    }

    /**
     * @param email this person's email address. This must be a valid format
     * @return this {@link Person}
     */
    public Person setEmail(String email) {
        //TODO: Implement some regex checking & specify what valid formats are.
        // https://code.google.com/p/emailaddress/source/detail?r=5
        mContent.put(sEmailKey, email);
        return this;
    }

    /**
     * @return the street on which this {@link Person} lives
     */
    public String getStreet() {
        return super.getString(sStreetKey);
    }

    /**
     * @param street on which this {@link Person} lives
     * @return this {@link Person}
     */
    public Person setStreet(String street) {
        mContent.put(sStreetKey, street);
        return this;
    }

    /**
     * @return the city in which this {@link Person} lives
     */
    public String getCity() {
        return super.getString(sCityKey);
    }

    /**
     * @param city in which this {@link Person} lives
     * @return this {@link Person}
     */
    public Person setCity(String city) {
        mContent.put(sCityKey, city);
        return this;
    }

    /**
     * @return the state in which this {@link Person} lives. States are returned as valid 2-letter
     *         abbreviations
     * @see State
     */
    public State getState() {
        return State.valueOf(super.getString(sStateKey));
    }

    /**
     * @param state the state in which this {@link Person} lives
     * @return this {@link Person}
     */
    public Person setState(State state) {
        mContent.put(sStateKey, state.toString());
        return this;
    }

    /**
     * @return the zip code in which this {@link Person} lives.
     */
    public ZipCode getZipCode() {
        ZipCode z = new ZipCode();
        z.setZip(super.getString(sZipCodeKey));
        return z;
    }

    /**
     * @param zipCode the zip code in which this {@link Person} lives
     * @return this {@link Person}
     */
    public Person setZipCode(ZipCode zipCode) {
        mContent.put(sZipCodeKey, zipCode.toString());
        return this;
    }

    /**
     * @return the name of the country in which this {@link Person} lives.
     */
    public String getCountry() {
        return super.getString(sCountryCodeKey);
    }

    /**
     * @param country the name of the country in which this {@link Person} lives
     * @return this {@link Person}
     */
    public Person setCountry(String country) {
        mContent.put(sCountryCodeKey, country);
        return this;
    }

    /**
     * @return the first name of this {@link Person}
     */
    public String getFirstName() {
        return super.getString(sFirstNameKey);
    }

    /**
     * @param firstName the first name for this {@link Person}. {@code null} values will be replaced
     *                  with an empty string.
     * @return this {@link Person}
     */
    public Person setFirstName(String firstName) {
        mContent.put(sFirstNameKey, Strings.nullToEmpty(firstName));
        return this;
    }

    /**
     * @return the last name of this {@link Person}
     */
    public String getLastName() {
        return super.getString(sLastNameKey);
    }

    /**
     * @param lastName the last name for this {@link Person}. {@code null} values will be replaced
     *                 with an empty string.
     * @return this {@link Person}
     */
    public Person setLastName(String lastName) {
        mContent.put(sLastNameKey, Strings.nullToEmpty(lastName));
        return this;
    }
}

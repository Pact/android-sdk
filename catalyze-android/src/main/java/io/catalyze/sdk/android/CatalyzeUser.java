package io.catalyze.sdk.android;

import io.catalyze.sdk.android.user.Address;
import io.catalyze.sdk.android.user.Email;
import io.catalyze.sdk.android.user.Guardian;
import io.catalyze.sdk.android.user.HealthPlan;
import io.catalyze.sdk.android.user.Language;
import io.catalyze.sdk.android.user.Mrn;
import io.catalyze.sdk.android.user.Name;
import io.catalyze.sdk.android.user.PhoneNumber;
import io.catalyze.sdk.android.user.SocialId;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * This class is a critical element to understand when interacting with the SDK
 * (and thus the Catalyze API).
 * <p/>
 * To perform most operations in the SDK an authenticated CatalyzeUser is
 * needed. A call to Catalyze.getInstance().authenticate() will take care of this and is a
 * necessary first step for using a Catalyze instance (an exception to this is
 * UMLS lookups which only require an API key).
 * <p/>
 * CatalyzeUsers can also be created from authenticated Catalyze instances. This
 * becomes possible when the authenticated user is a supervisor of the other
 * users that are being accessed.
 */
public class CatalyzeUser implements Comparable<CatalyzeUser>, CatalyzeObjectProtocol<CatalyzeUser> {

    private String sessionToken;
    private String usersId;
    private boolean active;
    private String username;
    private String password;
    private Email email;
    private Name name;
    private PhoneNumber phoneNumber;
    private Date dob;
    private int age;
    private List<Address> addresses;
    private String gender;
    private String maritalStatus;
    private String religion;
    private String race;
    private String ethnicity;
    private List<Guardian> guardians;
    private String confCode;
    private List<Language> languages;
    private List<SocialId> socialIds;
    private List<Mrn> mrns;
    private List<HealthPlan> healthPlans;
    private String avatar;
    private String ssn;
    private String profilePhoto;
    private String type;
    private Map<String, Object> extras;

	public CatalyzeUser() { }

    /**
     * Used internally.
     * @return sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * Used internally.
     * @param sessionToken
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * @return the unique identifier of this user
     */
    public String getUsersId() {
        return usersId;
    }

    /**
     * Set the unique identifier of this user.
     * @param usersId
     */
    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    /**
     * @return whether or not this user has activated their account through email confirmation
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set whether or not this user has activated their account through email confirmation. This
     * will be ignored on the API.
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the username of this user, this is what thei authenticate with.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of this user. This is what they will authenticate with.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password of the user. This is only used for authentication and should never be
     * set any other time.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password. This is only used for authentication and should never be set any other time.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return email, will never be null
     */
    public Email getEmail() {
        if (email == null) {
            email = new Email();
        }
        return email;
    }

    /**
     * Set the email object.
     * @param email
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * @return name, will never be null
     */
    public Name getName() {
        if (name == null) {
            name = new Name();
        }
        return name;
    }

    /**
     * Set the name object.
     * @param name
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * @return phoneNumber, will never be null
     */
    public PhoneNumber getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new PhoneNumber();
        }
        return phoneNumber;
    }

    /**
     * Set the phone number object.
     * @param phoneNumber
     */
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Set the date of birth.
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the age.
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the list of addresses, will never be null
     */
    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        return addresses;
    }

    /**
     * Set the list of addresses.
     * @param addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the gender.
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Set the marital status.
     * @param maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Set the religion.
     * @param religion
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return race
     */
    public String getRace() {
        return race;
    }

    /**
     * Set the race.
     * @param race
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * @return ethnicity
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     * Set the ethnicity.
     * @param ethnicity
     */
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    /**
     * @return the list of guardians, will never be null
     */
    public List<Guardian> getGuardians() {
        if (guardians == null) {
            guardians = new ArrayList<Guardian>();
        }
        return guardians;
    }

    /**
     * Set the list of guardians.
     * @param guardians
     */
    public void setGuardians(List<Guardian> guardians) {
        this.guardians = guardians;
    }

    /**
     * @return the confidentiality code
     */
    public String getConfCode() {
        return confCode;
    }

    /**
     * Set the confidentiality code.
     * @param confCode
     */
    public void setConfCode(String confCode) {
        this.confCode = confCode;
    }

    /**
     * @return the list of languages, will never be null
     */
    public List<Language> getLanguages() {
        if (languages == null) {
            languages = new ArrayList<Language>();
        }
        return languages;
    }

    /**
     * Set the list of languages.
     * @param languages
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    /**
     * @return the list of social IDs, will never be null
     */
    public List<SocialId> getSocialIds() {
        if (socialIds == null) {
            socialIds = new ArrayList<SocialId>();
        }
        return socialIds;
    }

    /**
     * Set the list of social IDs.
     * @param socialIds
     */
    public void setSocialIds(List<SocialId> socialIds) {
        this.socialIds = socialIds;
    }

    /**
     * @return the list of MRNs, will never be null
     */
    public List<Mrn> getMrns() {
        if (mrns == null) {
            mrns = new ArrayList<Mrn>();
        }
        return mrns;
    }

    /**
     * Set the list of MRNs.
     * @param mrns
     */
    public void setMrns(List<Mrn> mrns) {
        this.mrns = mrns;
    }

    /**
     * @return the list of health plans, will never be null
     */
    public List<HealthPlan> getHealthPlans() {
        if (healthPlans == null) {
            healthPlans = new ArrayList<HealthPlan>();
        }
        return healthPlans;
    }

    /**
     * Set the list of health plans.
     * @param healthPlans
     */
    public void setHealthPlans(List<HealthPlan> healthPlans) {
        this.healthPlans = healthPlans;
    }

    /**
     * @return the unique ID of a file which is the avatar for this user
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Set the unique ID of a file which is the avatar for this user.
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return social security number
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Set the social security number.
     * @param ssn
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * @return the ID of a file which is the profile photo for this user
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * Set the ID of a file which is the profile photo for this user.
     * @param profilePhoto
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return extras
     */
    public Map<String, Object> getExtras() {
        if (extras == null) {
            extras = new HashMap<String, Object>();
        }
        return extras;
    }

    /**
     * Set the extras.
     * @param extras
     */
    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    /**
	 * Compare by user ID, a unique value defined by the API. 
	 */
	@Override
	public int compareTo(CatalyzeUser user) {
		return this.getUsersId().compareTo(user.getUsersId());
	}

    /**
     * Creates a new user. This does not login the user. You must call
     * {@link io.catalyze.sdk.android.Catalyze#authenticate(String, String, io.catalyze.sdk.android.CatalyzeListener)}
     *
     * @param callbackHandler
     */
    @Override
    public void create(final CatalyzeListener<CatalyzeUser> callbackHandler) {
        CatalyzeAPIAdapter.getApi().createUser(this, new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                copy(catalyzeUser);
                callbackHandler.onSuccess(CatalyzeUser.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    /**
     * After setting the usersId, this retrieves that user with the given usersId.
     * @param callbackHandler
     */
    @Override
    public void retrieve(final CatalyzeListener<CatalyzeUser> callbackHandler) {
        CatalyzeAPIAdapter.getApi().retrieveUser(getUsersId(), new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                copy(catalyzeUser);
                callbackHandler.onSuccess(CatalyzeUser.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
    @Override
    public void update(final CatalyzeListener<CatalyzeUser> callbackHandler) {
        CatalyzeAPIAdapter.getApi().updateUser(getUsersId(), this, new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                copy(catalyzeUser);
                callbackHandler.onSuccess(CatalyzeUser.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

	/**
	 * Call the delete user api route, will also clear session data and current
	 * user info.
	 * 
	 * @param callbackHandler
	 */
    @Override
	public void delete(final CatalyzeListener<CatalyzeUser> callbackHandler) {
        CatalyzeAPIAdapter.getApi().deleteUser(getUsersId(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                copy(new CatalyzeUser());
                CatalyzeSession.getInstance().setSessionToken(null);
                callbackHandler.onSuccess(CatalyzeUser.this);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (retrofitError.getResponse().getStatus() == 200) {
                    success("", retrofitError.getResponse());
                } else {
                    callbackHandler.onError(new CatalyzeException(retrofitError));
                }
            }
        });
	}

	/***
	 * This route can only be used if you are the supervisor of your
	 * application. Get details about a user with the given usersId.
	 * 
	 * @param usersId
	 *            id of the user to lookup
	 * @param callbackHandler
	 */
	public void getUser(String usersId, final CatalyzeListener<CatalyzeUser> callbackHandler) {
		CatalyzeAPIAdapter.getApi().retrieveUser(usersId, new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                callbackHandler.onSuccess(catalyzeUser);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}

    /**
     * Supervisor route, returns array of strings containing all users with
     * 'partialUsername' as part of username to callback
     *
     * @param partialUsername
     * @param callbackHandler
     */
    public void search(String partialUsername, final CatalyzeListener<List<CatalyzeUser>> callbackHandler) {
        CatalyzeAPIAdapter.getApi().searchUsers(Catalyze.getAppId(), partialUsername, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                List<CatalyzeUser> users = new ArrayList<CatalyzeUser>();
                try {
                    JSONObject json = new JSONObject(s);
                    JSONArray usersArray = json.getJSONArray("results");
                    for (int i = 0; i < users.size(); i++) {
                        JSONObject userObj = usersArray.getJSONObject(i);
                        CatalyzeUser user = new CatalyzeUser();
                        user.setUsersId(userObj.getString("userId"));
                        user.setUsername(userObj.getString("username"));
                        users.add(user);
                    }
                } catch (JSONException je) { }
                callbackHandler.onSuccess(users);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }

    private void copy(CatalyzeUser other) {
        setSessionToken(other.getSessionToken());
        setUsersId(other.getUsersId());
        setActive(other.isActive());
        setUsername(other.getUsername());
        setPassword(other.getPassword());
        setEmail(other.getEmail());
        setName(other.getName());
        setPhoneNumber(other.getPhoneNumber());
        setDob(other.getDob());
        setAge(other.getAge());
        setAddresses(other.getAddresses());
        setGender(other.getGender());
        setMaritalStatus(other.getMaritalStatus());
        setReligion(other.getReligion());
        setRace(other.getRace());
        setEthnicity(other.getEthnicity());
        setGuardians(other.getGuardians());
        setConfCode(other.getConfCode());
        setLanguages(other.getLanguages());
        setSocialIds(other.getSocialIds());
        setMrns(other.getMrns());
        setHealthPlans(other.getHealthPlans());
        setAvatar(other.getAvatar());
        setSsn(other.getSsn());
        setProfilePhoto(other.getProfilePhoto());
        setType(other.getType());
        setExtras(other.getExtras());
    }
}

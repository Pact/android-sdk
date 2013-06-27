package io.catalyze.sdk.external;

import com.google.common.base.Strings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by marius on 6/18/13.
 */
public class CatalyzeObject {

    static final String sSessionId = "session_token";
    static final String sSessionHeader = "X-Session";

    protected static final String sBasePath = "https://api.catalyze.io/v1";

    // API Map Keys
    protected static final String sAppIdKey = "app_key";
    protected static final String sSourceKey = "source";
    protected static final String sGenderKey = "gender";
    protected static final String sPhoneKey = "phone";
    protected static final String sAgeKey = "age";
    protected static final String sBirthDateKey = "birth_date";
    protected static final String sZipCodeKey = "zip_code";
    protected static final String sStateKey = "state";
    protected static final String sCityKey = "city";
    protected static final String sStreetKey = "street";
    protected static final String sEmailKey = "email";
    protected static final String sFirstNameKey = "first_name";
    protected static final String sLastNameKey = "last_name";
    protected static final String sPersonIdKey = "person_id";
    protected static final String sTransactionTypeKey = "transaction_type";
    protected static final String sDateCommittedKey = "date_committed";
    protected static final String sPasswordKey = "password";
    protected static final String sUsernameKey = "username";
    protected static final String sUserIdKey = "user_id";

    static String sSessionToken = "";
    private static String sAppId = "";
    protected Map<String, Object> mContent;

    protected CatalyzeObject() {
        mContent = new HashMap<String, Object>();
        mContent.put(sAppIdKey, getAppId());
    }

    public static void setAppId(String appId) {
        sAppId = appId;
    }

    static String getAppId() {
        sAppId = Strings.emptyToNull(sAppId);
        return checkNotNull(sAppId, "Specify the appId by calling " + CatalyzeObject.class.getSimpleName() + ".setAppId()");
    }

    protected void inflateFromJson(JSONObject json) {
        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                mContent.put(key, json.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public int getInt(String key)   {
        return (Integer) get(key);
    }

    public boolean getBoolean(String key)   {
        return (Boolean) get(key);
    }

    public double getDouble(String key)   {
        return (Double) get(key);
    }

    public String getString(String key)   {
        return (String) get(key);
    }

    public long getLong(String key)   {
        return (Long) get(key);
    }

    public Object get(String key)   {
        return mContent.get(key);
    }

    public CatalyzeObject put(String k, Object v)  {
        mContent.put(k, v);
        return this;
    }

    @Override
    public String toString() {
        return new JSONObject(mContent).toString();
    }

    public String toString(int indentSpaces) throws JSONException {
        return new JSONObject(mContent).toString(indentSpaces);
    }
}

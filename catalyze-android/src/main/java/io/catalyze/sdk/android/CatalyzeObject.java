package io.catalyze.sdk.android;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.common.base.Objects;

/**
 * Base class for different Catalyze objects
 *
 */
public abstract class CatalyzeObject{
	
    protected JSONObject mJson = new JSONObject();
    protected final Catalyze catalyze;
    protected Context context;
    
    public CatalyzeObject(Catalyze catalyze) {
		this.catalyze = catalyze;
		this.context = catalyze.getContext();
	}
    
	protected CatalyzeObject(Catalyze catalyze, JSONObject json) {
		this(catalyze);
		mJson = json;
	}

	protected void setJson(JSONObject json) {
		mJson = json;
	}

	protected JSONObject asJson() {
		return mJson;
	}

    protected Map<String, Object> handleJSONObject(JSONObject object) {
        Map<String, Object> retVal = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
		Iterator<String> iter = object.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = object.get(key);
                if (value instanceof JSONArray) {
                    handleJSONArray((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    retVal.put(key, handleJSONObject(object.getJSONObject(key)));
                } else {
                    retVal.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    protected Object[] handleJSONArray(JSONArray array) {
        Object[] retVal = new Object[array.length()];
        for (int i = 0, max = array.length(); i < max; i++) {
            Object value = array.opt(i);
            if (value instanceof JSONArray) {
                retVal[i] = handleJSONArray((JSONArray) value);
            } else if (value instanceof JSONObject) {
                retVal[i] = handleJSONObject((JSONObject) value);
            } else {
                try {
                    retVal[i] = array.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        return mJson.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CatalyzeUser)) {
            return false;
        }

        CatalyzeUser that = (CatalyzeUser) o;
        return Objects.equal(mJson, that.mJson);
    }
    
    @Override
    public String toString() {
        return mJson.toString();
    }

    public String toString(int indentSpaces) {
        try {
            return mJson.toString(indentSpaces);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

 
}

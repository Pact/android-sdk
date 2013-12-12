package io.catalyze.sdk.android;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public abstract class CatalyzeObject implements Serializable {

	// Core data storage is in JSON as that's what the API talks
	protected transient JSONObject mJson = new JSONObject();

	// The Catalyze instance associated with this object
	protected final Catalyze catalyze;

	/**
	 * Creates an instance attached to an authenticated Catalyze instance.
	 * 
	 * @param catalyze
	 *            The authenticated connection to the Catalyze API
	 * @throws IllegalStateException
	 *             If the Catalyze instance is null or the instance has not been
	 *             authenticated.
	 */
	protected CatalyzeObject(Catalyze catalyze) {
		if (catalyze == null) {
			throw new IllegalStateException(
					"Trying to create a CatalyzeObject with a null Catalyze reference.");
		}
		this.catalyze = catalyze;
	}

	/**
	 * Helper constructor to set the instance's JSON content upon
	 * initialization.
	 * 
	 * @param catalyze
	 *            The authenticated connection to the Catalyze API
	 * @param json
	 *            The JSON content to use for this instance
	 */
	protected CatalyzeObject(Catalyze catalyze, JSONObject json) {
		this(catalyze);
		mJson = json;
	}

	/**
	 * Gets the Catalyze instance associated with this object.
	 * 
	 * @return The Catalyze instance
	 */
	public Catalyze getCatalyze() {
		return this.catalyze;
	}

	/**
	 * Provides the ability to overwrite the current JSON. Used in the results
	 * returned from update operations.
	 * 
	 * @param json
	 *            The new JSON to use
	 */
	protected void setJson(JSONObject json) {
		mJson = json;
	}

	/**
	 * Helper method for handling JSON data.
	 * 
	 * @param object
	 *            The object to process
	 * @return A key-value map from the JSON data
	 */
	protected Map<String, Object> handleJSONObject(JSONObject object) {
		Map<String, Object> retVal = new HashMap<String, Object>();

		Iterator iter = object.keys();
		while (iter.hasNext()) {

			Object key = iter.next();
			if (!(key instanceof String)) {
				throw new IllegalStateException("Non-String key in JSONObject.");
			}

			String keyStr = (String) key;
			try {
				Object value = object.get(keyStr);
				if (value instanceof JSONArray) {
					handleJSONArray((JSONArray) value);
				} else if (value instanceof JSONObject) {
					retVal.put(keyStr,
							handleJSONObject(object.getJSONObject(keyStr)));
				} else {
					retVal.put(keyStr, value);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	/**
	 * Helper method for manipulating JSON array data.
	 * 
	 * @param array
	 *            The array to process
	 * @return Objects extracted from the input array
	 */
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

	/**
	 * Use the JSON's hash.
	 */
	@Override
	public int hashCode() {
		return mJson.hashCode();
	}

	/**
	 * Compare by JSON content.
	 */
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

	/**
	 * Return the object as a JSON String.
	 */
	@Override
	public String toString() {
		return mJson.toString();
	}

	/**
	 * A better formatted JSON String representation than the default
	 * toString().
	 * 
	 * @param indentSpaces
	 *            Number of spaces to indent on each level.
	 * @return A Formatted String indented and useful for user presentation of
	 *         JSON.
	 */
	public String toString(int indentSpaces) {
		try {
			return mJson.toString(indentSpaces);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		// default serialization
		oos.defaultWriteObject();
		// write the object
		byte[] json = this.mJson.toString().getBytes();
		oos.writeInt(json.length);
		oos.write(json);
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		// default deserialization
		ois.defaultReadObject();

		byte[] bytes = new byte[ois.readInt()];
		ois.read(bytes, 0, bytes.length);
		try {
			this.mJson = new JSONObject(new String(bytes));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

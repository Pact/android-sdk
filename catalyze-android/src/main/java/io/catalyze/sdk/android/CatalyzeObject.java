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

/**
 * Base class for different Catalyze objects
 * 
 */
public abstract class CatalyzeObject implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1952650184533673793L;

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
	 * Compare by JSON content.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof CatalyzeObject)) {
			return false;
		}

		CatalyzeObject that = (CatalyzeObject) o;
		return mJson.equals(that.mJson);
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
	 * Use the JSON's hash.
	 */
	@Override
	public int hashCode() {
		return mJson.hashCode();
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

	/**
	 * For de-serializing to/from Activities via Intents as extras.
	 * 
	 * @param ois
	 *            The input stream
	 * @throws ClassNotFoundException
	 *             If there is a path problem
	 * @throws IOException
	 *             If there is an IO issue
	 */
	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		// default deserialization
		ois.defaultReadObject();

		byte[] bytes = new byte[ois.readInt()];
		ois.read(bytes, 0, bytes.length);
		try {
			this.mJson = new JSONObject(new String(bytes));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * For de-serializing to/from Activities via Intents as extras.
	 * 
	 * @param oos
	 *            The output stream
	 * @throws IOException
	 *             If there is an IO issue
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		byte[] json = this.mJson.toString().getBytes();
		oos.writeInt(json.length);
		oos.write(json);
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
	 * Provides the ability to overwrite the current JSON. Used in the results
	 * returned from update operations.
	 * 
	 * @param json
	 *            The new JSON to use
	 */
	protected void setJson(JSONObject json) {
		mJson = json;
	}

}

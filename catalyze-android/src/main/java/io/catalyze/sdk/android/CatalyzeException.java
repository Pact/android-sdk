package io.catalyze.sdk.android;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * CatalyzeError will be returned to the onError method of the user callback
 * handler if an error is encountered during a Catalyze API call. CatalyzeError
 * will contain the information about whatever error has occurred
 * 
 * 
 */
public class CatalyzeException extends Exception {

	/**
	 * The UID
	 */
	private static final long serialVersionUID = 6762913468935195414L;

	// Encapsulated VolleyError
	private VolleyError volleyError = null;

	private int httpCode = -1;

	private CatalyzeError[] errors = null;

	/**
	 * Create a new exception baded on a VolleyError (or subclass).
	 */
	protected CatalyzeException(VolleyError error) {
		this.volleyError = error;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			this.httpCode = response.statusCode;
			JSONObject errorJson = null;
			try {
				errorJson = new JSONObject(new String(response.data));
			} catch (Exception jse) {
				// May not contain JSON
			}

			if (errorJson != null) {
				// There's JSON data: process it
				try {
					JSONArray errors = errorJson.getJSONArray("errors");

					// Turn JSON into CatalyzeError instances
					this.errors = new CatalyzeError[errors.length()];
					for (int i = 0; i < errors.length(); i++) {
						JSONObject json = errors.getJSONObject(i);
						this.errors[i] = new CatalyzeError(json.getInt("code"),
								json.getString("message"));
					}
				} catch (JSONException e) {
					Log.e("Catalyze",
							"Error in Error Handling (really, there is an error in the error handling)!",
							e);
				}
			}
		}
	}

	@Override
	public void printStackTrace() {
		volleyError.printStackTrace();
	}

	@Override
	public String getMessage() {
		return volleyError.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return volleyError.getLocalizedMessage();
	}

	@Override
	public Throwable getCause() {
		return volleyError.getCause();
	}

	@Override
	public Throwable initCause(Throwable cause) {
		return volleyError.initCause(cause);
	}

	@Override
	public String toString() {
		return volleyError.toString();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		volleyError.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		volleyError.printStackTrace(s);
	}

	@Override
	public Throwable fillInStackTrace() {
		if (volleyError != null)
			return volleyError.fillInStackTrace();
		else
			return new Exception().fillInStackTrace();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return volleyError.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		volleyError.setStackTrace(stackTrace);
	}

}

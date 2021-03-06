package io.catalyze.sdk.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * CatalyzeException will be returned to the onError method of the user callback
 * handler if an error is encountered during a Catalyze API call.
 * CatalyzeException will contain the information about whatever error has
 * occurred
 * 
 * 
 */
public class CatalyzeException extends Exception {

	/**
	 * The UID
	 */
	private static final long serialVersionUID = 6762913468935195414L;

	// Encapsulated RetrofitError
	private RetrofitError retrofitError = null;

	private int httpCode = -1;

	private CatalyzeError[] errors = new CatalyzeError[0];

	/**
	 * Create a new exception baded on a RetrofitError (or subclass).
	 */
	protected CatalyzeException(RetrofitError error) {
		this.retrofitError = error;

		// Check for API error information
		Response response = error.getResponse();
		if (response != null) {
			this.httpCode = response.getStatus();
			JSONObject errorJson = null;
			try {
				errorJson = new JSONObject(new String(streamToBytes(response.getBody().in()), "UTF-8"));
			} catch (Exception jse) {
				// May not contain JSON
			}

			if (errorJson != null) {
				// There's JSON data: process it
				JSONArray jsonErrors = errorJson.optJSONArray("errors");
				if (jsonErrors != null) {
					// Turn JSON into CatalyzeError instances
					this.errors = new CatalyzeError[jsonErrors.length()];
					for (int i = 0; i < jsonErrors.length(); i++) {
						try {
							JSONObject json = jsonErrors.getJSONObject(i);
							this.errors[i] = new CatalyzeError(
									json.getInt("code"),
									json.getString("message"));
						} catch (JSONException jse) {

							String message = "Unknown error.";
							try {
								// Might be a string, try it
								message = jsonErrors.getString(i);
							} catch (JSONException je) {
								// ignore bad messages
							}

							// Create a fallback. This should not happen once
							// error reporting is consistent
							this.errors[i] = new CatalyzeError(-1, message);
						}

					}
				}
			}
		}
	}

    // Taken from https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit/Utils.java
    private byte[] streamToBytes(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (stream != null) {
            byte[] buf = new byte[0x1000];
            int r;
            while ((r = stream.read(buf)) != -1) {
                baos.write(buf, 0, r);
            }
        }
        return baos.toByteArray();
    }

	@Override
	public Throwable fillInStackTrace() {
		if (retrofitError != null)
			return retrofitError.fillInStackTrace();
		else
			return new Exception().fillInStackTrace();
	}

	@Override
	public Throwable getCause() {
		return retrofitError.getCause();
	}

	/**
	 * Gets an array of zero or more non-null CatalyzeError instannce that
	 * provide more detail about what went wrong on a call to the Catalyze API.
	 * A zero length array means that no errors were returned by the API (when
	 * the HTTP code is not -1) or that the error was not generated on the
	 * backend (i.e. it is an SDK-level or device-level error).
	 * 
	 * @return The array of errors
	 */
	public CatalyzeError[] getErrors() {
		return errors;
	}

	/**
	 * Gets the HTTP code related to this exception which was generated by the
	 * Catalyze API.
	 * 
	 * @return The HTTP code if any (400, 401, 403, 404, 500) or -1 if the error
	 *         was not generated on the backend (i.e. it is an SDK-level or
	 *         device-level error).
	 */
	public int getHttpCode() {
		return httpCode;
	}

	@Override
	public String getLocalizedMessage() {
		return retrofitError.getLocalizedMessage();
	}

	@Override
	public String getMessage() {
		if (httpCode == -1) {
			// In this case rely on Volley, don't know what happened
			return retrofitError.getMessage();
		} else {
			// Here rely on the API's error response to tell us what went wrong
			String message = "The Catalyze API returned HTTP code " + httpCode
					+ " with ";
			if (this.errors.length == 0) {
				message += " no error messages. Bummer.";
			} else {
				message += " " + errors.length + " error message(s):";
				for (int i = 0; i < errors.length; i++) {
					if (errors[i] != null) {
						message += "\n" + errors[i].getMessage() + " (Code "
								+ errors[i].getCode() + ")";
					}
				}
			}
			return message;
		}
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return retrofitError.getStackTrace();
	}

	@Override
	public Throwable initCause(Throwable cause) {
		return retrofitError.initCause(cause);
	}

	@Override
	public void printStackTrace() {
        retrofitError.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
        retrofitError.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
        retrofitError.printStackTrace(s);
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
        retrofitError.setStackTrace(stackTrace);
	}

	@Override
	public String toString() {
		return retrofitError.toString();
	}

}

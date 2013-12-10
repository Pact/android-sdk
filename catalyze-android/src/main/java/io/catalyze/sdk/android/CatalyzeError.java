package io.catalyze.sdk.android;

/**
 * On an API call failure the Catalyze API will return one or more error
 * entries. Multiple things can cause errors on a single request, for example
 * submitting form data with incorrectly formatted City, State and Zip Code
 * values. This class provides a wrapped version of these types of errors.
 * 
 * @author uphoff
 * 
 */
public class CatalyzeError {

	// The Catalyze-internal code for this error
	private int code;

	// A message describing the error, suitable for display to a user
	private String message;

	/**
	 * Create an instance and populate the internally defined error code and
	 * descriptive message.
	 * 
	 * @param code
	 *            The code as defined by the Catalyze API
	 * @param message
	 *            The message reported back from the Catalyze API describing
	 *            this error
	 */
	protected CatalyzeError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Gets the error code as defined by the Catalyze API.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets the message. Suitable for display to the user.
	 * 
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Return a JSON-parsable version of the error.
	 * 
	 * @return The error as a JSON string
	 */
	public String toString() {
		return "{\"code\":" + code + ",\"message\":\"" + message + "\"}";
	}

}

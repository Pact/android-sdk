package io.catalyze.sdk.android;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.android.volley.VolleyError;

/**
 * CatalyzeError will be returned to the onError method of the user callback
 * handler if an error is encountered during a Catalyze API call. CatalyzeError
 * will contain the information about whatever error has occurred
 * 
 * 
 */
public class CatalyzeError extends Exception {

	/**
	 * The UID
	 */
	private static final long serialVersionUID = 6762913468935195414L;

	// Encapsulated VolleyError
	private VolleyError volleyError = null;

	/**
	 * Create a new exception baded on a VolleyError (or subclass).
	 */
	protected CatalyzeError(VolleyError error) {
		this.volleyError = error;
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
		else return new Exception().fillInStackTrace();
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

package io.catalyze.sdk.android;

import com.android.volley.VolleyError;

/**
 * CatalyzeError will be returned to the onError method of the user callback
 * handler if an error is encountered during a Catalyze API call. CatalyzeError
 * will contain the information about whatever error has occurred
 * 
 * 
 */
@SuppressWarnings("serial")
public class CatalyzeError extends VolleyError {
	public CatalyzeError() {

	}

	// TODO expand this to have additional error reporting functionality beyond
	// that of VolleyError
	public CatalyzeError(VolleyError error) {

	}

}

package io.catalyze.sdk.android;

import com.android.volley.VolleyError;

/**
 * CatalyzeError will be returned to the onError method of the user callback
 * handler if an error is encountered during a Catalyze API call. CatalyzeError
 * will contain the information about whatever error has occurred
 * 
 * 
 */
public class CatalyzeError extends VolleyError {
	
	/**
	 * The UID 
	 */
	private static final long serialVersionUID = 6762913468935195414L;

	/**
	 * Generic constructor reserved for future use. 
	 */
	public CatalyzeError() {
		super();
	}

}

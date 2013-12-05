package io.catalyze.sdk.android;

import com.android.volley.Response;

/**
 * Response handler for Catalyze API calls. A CatalyzeListener must be passed to
 * all Catalyze API call methods.
 * 
 * T should be the type of the expected response, i.e. if retrieving an
 * authenticated user, a CatalyzeUser, then CatalyzeUser. If retrieving a
 * CustomClass, then CustomClass.
 * 
 * If an error occurs a CatalyzeError will be passed back as a response.
 * 
 * @param <T>
 */
public abstract class CatalyzeListener<T> implements Response.Listener<T> {

	/**
	 * A successful call to the backend API will result in this method being
	 * called. Simply redirect to onSuccess().
	 */
	public void onResponse(T response) {
		onSuccess(response);
	}

	/***
	 * Handle error response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onError(CatalyzeError response);

	/**
	 * Handle success response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onSuccess(T response);
}

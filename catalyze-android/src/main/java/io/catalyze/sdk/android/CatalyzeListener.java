package io.catalyze.sdk.android;

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
public abstract class CatalyzeListener<T> {

	public CatalyzeListener() { }

	/***
	 * Handle error response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onError(CatalyzeException response);

	/**
	 * Handle success response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onSuccess(T response);
}

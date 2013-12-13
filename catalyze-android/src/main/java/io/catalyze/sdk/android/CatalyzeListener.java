package io.catalyze.sdk.android;

import android.content.Context;

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

	private Context context;

	/**
	 * The listener must be provided with a context to execute under (this is
	 * used beneath the Catalyze SDK by Volley).
	 * 
	 * @param context
	 *            The context for linking to background operations.
	 */
	public CatalyzeListener(Context context) {
		this.context = context;
	}

	/**
	 * The context associated with this callback.
	 * 
	 * @return The Context
	 */
	public Context getContext() {
		return context;
	}

	/***
	 * Handle error response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onError(CatalyzeException response);

	/**
	 * A successful call to the backend API will result in this method being
	 * called. Simply redirect to onSuccess().
	 */
	public void onResponse(T response) {
		onSuccess(response);
	}

	/**
	 * Handle success response from Catalyze API call
	 * 
	 * @param response
	 */
	public abstract void onSuccess(T response);
}

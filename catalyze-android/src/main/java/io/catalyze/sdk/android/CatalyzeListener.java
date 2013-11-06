package io.catalyze.sdk.android;

import com.android.volley.Response;

public abstract class CatalyzeListener<T> implements Response.Listener<T> {
	
	public void onResponse(T response) {
		onSuccess(response);
	}

	public abstract void onError(CatalyzeError response);

	public abstract void onSuccess(T response);
	
	
	
}

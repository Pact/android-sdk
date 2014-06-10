package io.catalyze.sdk.android.api;

import io.catalyze.sdk.android.Catalyze;
import retrofit.RequestInterceptor;

public class UmlsInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Content-Type", "application/json");
        request.addHeader("X-Api-Key", Catalyze.getApiKey());
    }
}


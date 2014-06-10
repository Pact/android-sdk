package io.catalyze.sdk.android.api;

import io.catalyze.sdk.android.Catalyze;
import io.catalyze.sdk.android.CatalyzeSession;
import retrofit.RequestInterceptor;

public class AuthorizationInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Bearer " + CatalyzeSession.getInstance().getSessionToken());
        request.addHeader("X-Api-Key", Catalyze.getApiKey());
    }
}


package io.catalyze.sdk.android;

import retrofit.RequestInterceptor;

public class AuthorizationInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", "Bearer " + CatalyzeSession.getInstance().getSessionToken());
        request.addHeader("X-Api-Key", Catalyze.getApiKey());
    }
}

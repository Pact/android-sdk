package io.catalyze.sdk.android;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class CatalyzeAPIAdapter {

    private static CatalyzeAPI api = new RestAdapter.Builder()
            .setEndpoint("https://apiv2.catalyze.io/v2")
            .setRequestInterceptor(new AuthorizationInterceptor())
            .setClient(new OkClient(new OkHttpClient()))
            .build().create(CatalyzeAPI.class);

    private CatalyzeAPIAdapter() { }

    protected static CatalyzeAPI getApi() {
        return api;
    }
}
package io.catalyze.sdk.android.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class CatalyzeAPIAdapter {

    private static CatalyzeAPI api = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint("https://apiv2.catalyze.io/v2")
            .setRequestInterceptor(new AuthorizationInterceptor())
            .setClient(new OkClient(new OkHttpClient()))
            .build().create(CatalyzeAPI.class);

    private CatalyzeAPIAdapter() { }

    public static CatalyzeAPI getApi() {
        return api;
    }
}
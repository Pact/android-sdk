package io.catalyze.sdk.android.api;

import android.net.SSLCertificateSocketFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.lang.reflect.Modifier;
import java.security.KeyStore;

import javax.net.ssl.SSLSocketFactory;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class CatalyzeAPIAdapter {

    private static CatalyzeAPI api = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint("https://10.23.16.103:8443/v2")
            .setRequestInterceptor(new AuthorizationInterceptor())
            .setClient(new OkClient(configureClient()))
            .build().create(CatalyzeAPI.class);

    private CatalyzeAPIAdapter() { }

    private static OkHttpClient configureClient() {
        OkHttpClient client = new OkHttpClient();
        client.setHostnameVerifier(new AllowAllHostnameVerifier());
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = SSLCertificateSocketFactory.getInsecure(60000, null);

            client.setSslSocketFactory(sf);
        } catch (Exception e) {
        }
        return client;
    }

    public static CatalyzeAPI getApi() {
        return api;
    }
}
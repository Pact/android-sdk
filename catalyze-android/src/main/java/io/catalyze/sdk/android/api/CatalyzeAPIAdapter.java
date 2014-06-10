package io.catalyze.sdk.android.api;

import android.net.SSLCertificateSocketFactory;

import com.squareup.okhttp.OkHttpClient;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.security.KeyStore;

import javax.net.ssl.SSLSocketFactory;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class CatalyzeAPIAdapter {

    private static CatalyzeAPI api = new RestAdapter.Builder()
            .setEndpoint("https://10.23.16.107:8443/v2")
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
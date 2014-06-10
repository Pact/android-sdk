package io.catalyze.android.example;

import android.app.Application;

import io.catalyze.sdk.android.Catalyze;

/**
 * Created by mvolkhart on 8/25/13.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Catalyze.getInstance(this);
    }
}

package io.catalyze.android.example;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mvolkhart on 8/25/13.
 */
public class MyApplication extends Application {

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);

    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}

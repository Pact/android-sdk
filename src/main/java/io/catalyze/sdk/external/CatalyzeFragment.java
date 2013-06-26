/*
 * Copyright (C) 2013 catalyze.io, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.catalyze.sdk.external;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;

import io.catalyze.sdk.internal.OkHttpStack;

/**
 * Created by marius on 6/19/13.
 */
public class CatalyzeFragment extends Fragment {

    private static final String TAG = CatalyzeFragment.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private final URL mUrl;
    private final String mPayload;
    private HttpPostCallbacks mCallbacks;
    //private PostTask mPostTask;
    private int mNumberRunning;
    //private List<CatalyzeTask> mTasks;

    public CatalyzeFragment(URL url, String payload) {
        mUrl = url;
        mPayload = payload;
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext(), new OkHttpStack());
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof HttpPostCallbacks)) {
            throw new IllegalStateException("Activity must implement the " + HttpPostCallbacks.class.getSimpleName() + " interface.");
        }
        mCallbacks = (HttpPostCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);    // Make this fragment span activity lifecycle
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //cancel();
    }

//    public void start() {
//        if (mNumberRunning == 0) {
//            mPostTask = new PostTask();
//            mPostTask.execute();
//            mNumberRunning = true;
//        }
//    }
//
//    public void cancel() {
//        if (mNumberRunning) {
//            mPostTask.cancel(false);
//            mPostTask = null;
//            mNumberRunning = false;
//        }
//    }

//    public boolean isRunning() {
//        return mNumberRunning;
//    }

    static interface HttpPostCallbacks {
        public void onCancelled();

        public void onPostExecute(JSONObject object);
    }

//    private class PostTask extends AsyncTask<Void, Void, Response> {
//
//        @Override
//        protected void onPreExecute() {
//            mNumberRunning = true;
//        }
//
//        @Override
//        protected Response doInBackground(Void... voids) {
//            return CatalyzeHttpManager.post(mUrl, mPayload);
//        }
//
//        @Override
//        protected void onCancelled() {
//
//            // Proxy the call to the Activity
//            mCallbacks.onCancelled();
//            mNumberRunning = false;
//        }
//
//        @Override
//        protected void onPostExecute(Response response) {
//
//            // Proxy the call to the Activity
//            mCallbacks.onPostExecute(object);
//            mNumberRunning = false;
//        }
//    }
}

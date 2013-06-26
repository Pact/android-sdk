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

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ajah.geo.us.ZipCode;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by marius on 6/24/13.
 */
public class Transaction extends CatalyzeObject {

    private static final String sPath = "/transaction";

    public Transaction(Context context) {
        super();
        String source = "android/0";
        try {
            String packageName = context.getPackageName();

            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            source = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        mContent.put(sSourceKey, source);

    }

    public Transaction create(RequestQueue requestQueue, Response.ErrorListener errorListener) {

        // Add the date_committed field
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        mContent.put(sDateCommittedKey, formatter.format(new Date()));

        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.POST, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
            }
        }, errorListener);
        requestQueue.add(request);
        return this;
    }

    public Transaction retrieve(RequestQueue requestQueue, Response.ErrorListener errorListener) {
        final CatalyzeObject holder = this;
        CatalyzeJsonObjectRequest request = new CatalyzeJsonObjectRequest(Request.Method.GET, sBasePath + sPath, new JSONObject(mContent), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject object) {

                CatalyzeObject.sSessionToken = (String) object.remove(CatalyzeObject.sSessionId);
                holder.inflateFromJson(object);
            }
        }, errorListener);
        requestQueue.add(request);
        return this;
    }

    public ZipCode getZipCode() {
        ZipCode zip = new ZipCode();
        zip.setZip(super.getString(sZipCodeKey));
        return zip;
    }

    public Transaction setZipCode(ZipCode zipCode) {
        mContent.put(sZipCodeKey, zipCode);
        return this;
    }

    public String getTransactionType() {
        return super.getString(sTransactionTypeKey);
    }

    // TODO: Update per discussion on Trello. Consider using enums. https://trello.com/c/8jRteIrs
    public Transaction setTransactionType(String type) {
        mContent.put(sTransactionTypeKey, type);
        return this;
    }
}

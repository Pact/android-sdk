package io.catalyze.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.*;
import com.android.volley.*;
import com.google.common.collect.ImmutableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mvolkhart on 8/24/13. CatalzeRequest is an extension of This class
 * handles all Catalyze network requests
 */
public class CatalyzeRequest extends JsonObjectRequest {

	public static final String BASE_PATH = "https://api.catalyze.io/v1";
	private Map<String, String> mHeaders = new HashMap<String, String>();
	private static RequestQueue mRequestQueue;
	private static TextView mResult;
	private int mMethod;

	public CatalyzeRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {

		super(url, jsonRequest, listener, errorListener);
	}

	/**
	 * Make a new HTTP request
	 * 
	 * @param url
	 * @param jsonBody
	 *            JSON to send to server, can be left null if there is none to
	 *            send
	 * @param listener
	 *            Handler for Volley success response
	 * @param errorListener
	 *            Handler for Volley error response
	 * @param headers
	 */
	public CatalyzeRequest(String url, JSONObject jsonBody, Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener, Map<String, String> headers) {
		super(url, jsonBody, listener, errorListener);
		mHeaders = headers;
	}

	@Override
	public Map<String, String> getHeaders() {
		return mHeaders;
	}

	@Override
	public int getMethod() {
		return mMethod;
	}

	/**
	 * Set HTTP Request headers
	 * 
	 * @param customHeaders
	 */
	public void setHeaders(Map<String, String> customHeaders) {
		mHeaders = customHeaders;
	}

	/***
	 * Add this request to Volley queue as a post request
	 * 
	 * @param context
	 */
	public void post(Context context) {
		mResult = new TextView(context);
		mMethod = Request.Method.POST;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a get request
	 * 
	 * @param context
	 */
	public void get(Context context) {
		mResult = new TextView(context);
		mMethod = Request.Method.GET;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a put request
	 * 
	 * @param context
	 */
	public void put(Context context) {
		mResult = new TextView(context);
		mMethod = Request.Method.PUT;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/***
	 * Add this request to Volley queue as a delete request
	 * 
	 * @param context
	 */
	public void delete(Context context) {
		mResult = new TextView(context);
		mMethod = Request.Method.DELETE;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/**
	 * FIXME: this could this go in constructor?
	 * 
	 * @param context
	 * @return
	 */
	private RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
			return mRequestQueue;
		} else
			return mRequestQueue;
	}
}

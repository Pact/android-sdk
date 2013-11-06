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
 * Created by mvolkhart on 8/24/13. CatalzeRequest is an extension of
 * This class handles all Catalyze network requests
 */
public class CatalyzeRequest extends JsonObjectRequest{
	
    public static final String BASE_PATH = "https://api.catalyze.io/v1";
    private static final String TAG = CatalyzeRequest.class.getSimpleName();
    private static final String API_KEY_HEADER = "X-Api-Key";
    private static final String ACCESS_KEY_TYPE = "android ";
    private static final String ACCESS_KEY_VALIDATOR_KEY = "io.catalyze.android.sdk.v1.ACCESS_KEY";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_SCHEMA = "Bearer ";
    private static final String LOCATION_HEADER = "Location";
    private Map<String, String> mHeaders = new HashMap<String, String>();
    private static RequestQueue mRequestQueue;
    private static TextView mResult;
    private int mMethod;

	public CatalyzeRequest(String url, JSONObject jsonRequest,
			Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		
		super(url, jsonRequest, listener, errorListener);
	}

	public CatalyzeRequest(String url, JSONObject jsonBody,
			Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener, Map<String,String> headers) {
		super(url, jsonBody, listener, errorListener);
		mHeaders = headers;
	}
    
    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }
    
    @Override
    public int getMethod(){
    	return mMethod;
    }
    
    
    public void setHeaders(Map<String, String> customHeaders){
    	mHeaders = customHeaders;
    }
    
    /***
	 * Add request to post to server to Volley queue
	 * @param context
	 */
	public void post(Context context){
		mResult = new TextView(context);
		mMethod = Request.Method.POST;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}
	
	public void get(Context context){
		mResult = new TextView(context);
		mMethod = Request.Method.GET;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}
	
	public void put(Context context){
		mResult = new TextView(context);
		mMethod = Request.Method.PUT;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}
	
	public void delete(Context context){
		mResult = new TextView(context);
		mMethod = Request.Method.DELETE;
		mRequestQueue = getRequestQueue(context);
		mRequestQueue.add(this);
	}

	/**
	 * FIXME: this could easily have gone in the constructor instead
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

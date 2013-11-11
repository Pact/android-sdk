package io.catalyze.sdk.android;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mvolkhart on 8/24/13. CatalzeRequest is an extension of This class
 * handles all Catalyze network requests
 * @param <T>
 */
public class CatalyzeRequest<T> extends JsonRequest<T> {

	public static final String BASE_PATH = "https://api.catalyze.io/v1";
	private Map<String, String> mHeaders = new HashMap<String, String>();
	private static RequestQueue mRequestQueue;
	private static TextView mResult;
	private int mMethod;

	@SuppressWarnings("deprecation")
	public CatalyzeRequest(String url, JSONObject jsonRequest, Response.Listener<T> listener,
			Response.ErrorListener errorListener) {
		super(url, jsonRequest.toString(), listener, errorListener);
		//super(url, jsonRequest, listener, errorListener);
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
//	public CatalyzeRequest(String url, JSONObject jsonBody, Response.Listener<JSONObject> listener,
//			Response.ErrorListener errorListener, Map<String, String> headers) {
//		super(url, jsonBody, listener, errorListener);
//		mHeaders = headers;
//	}

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

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

			if (jsonString.charAt(0) == '{') {
				return (Response<T>) Response.success(new JSONObject(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			} else {
				return (Response<T>) Response.success(new JSONArray(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		} 
	}
}

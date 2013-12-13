package io.catalyze.sdk.android;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

/**
 * This class handles all Catalyze JSON network requests. All calls are here are
 * made asynchronously using the Android Volley library.
 * 
 * @param <T>
 */
public class CatalyzeRequest<T> extends JsonRequest<T> {

	// Borrowed from volley
	public static final int GET = Request.Method.GET;
	public static final int POST = Request.Method.POST;
	public static final int PUT = Request.Method.PUT;
	public static final int DELETE = Request.Method.DELETE;

	// The HTTP headers to include in the request (GET, POST, etc)
	private Map<String, String> mHeaders = new HashMap<String, String>();

	// The Volley request queue for doing concurrent/queued requests.
	private static RequestQueue mRequestQueue; // Enforces one queue

	/**
	 * Exposes access to the Volley queue (a singleton here). Can be used to add
	 * other HTTP operations as Volley requests.
	 * 
	 * This singleton is set when 
	 * 
	 * @return The Volley RequestQueue
	 */
	public static RequestQueue getRequestQueue() {
		return CatalyzeRequest.mRequestQueue;
	}

	/**
	 * Return the Volley request queue or instantiate one if needed. This is a
	 * singleton by design.
	 * 
	 * @param context
	 *            The context to tie back to the application for network
	 *            background tasks
	 */
	protected static void initRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context
					.getApplicationContext());
		}
	}

	/**
	 * Make a request where the content is a JSON array.
	 * 
	 * @param method
	 *            The HTTP method (GET, POST, etc)
	 * @param jsonRequest
	 *            The JSON data (an array)
	 * @param url
	 *            The URL of the API route
	 * @param listener
	 *            The callback for success
	 * @param errorListener
	 *            The callback for erros
	 */
	public CatalyzeRequest(int method, JSONArray jsonRequest, String url,
			Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, url, jsonRequest != null ? jsonRequest.toString() : null,
				listener, errorListener);
	}

	/**
	 * Make a request where the content is JSON data (not an array).
	 * 
	 * @param method
	 *            The HTTP method (GET, POST, etc)
	 * @param jsonRequest
	 *            The JSON data
	 * @param url
	 *            The URL of the API route
	 * @param listener
	 *            The callback for success
	 * @param errorListener
	 *            The callback for erros
	 */
	public CatalyzeRequest(int method, String url, JSONObject jsonRequest,
			Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(method, url, jsonRequest != null ? jsonRequest.toString() : null,
				listener, errorListener);
	}

	/***
	 * Add this request to the Volley queue to be executed.
	 * 
	 * @param context
	 *            The context to execute under.
	 */
	public void execute() {
		mRequestQueue.add(this);
	}

	@Override
	public Map<String, String> getHeaders() {
		return mHeaders;
	}

	/**
	 * Set HTTP Request headers
	 * 
	 * @param customHeaders
	 */
	public void setHeaders(Map<String, String> customHeaders) {
		mHeaders = customHeaders;
	}

	@Override
	public String toString() {
		String method = "";
		switch (getMethod()) {
		case CatalyzeRequest.DELETE:
			method = "DELETE";
			break;
		case CatalyzeRequest.GET:
			method = "GET";
			break;
		case CatalyzeRequest.POST:
			method = "POST";
			break;
		case CatalyzeRequest.PUT:
			method = "PUT";
			break;
		default:
			method = "Unkown";
			break;

		}
		String message = method + "," + this.getUrl() + ", "
				+ this.getBodyContentType();
		Map<String, String> headers = this.getHeaders();
		message += "\n== Headers ==";
		for (String key : headers.keySet()) {
			message += "\n" + key + ": " + headers.get(key);
		}
		byte[] body = super.getBody();
		if (body == null) {
			message += "\nNo body.";
		} else {
			message += "\n== Body ==\n" + new String(body);
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers)).trim();

			if (jsonString.charAt(0) == '{') {
				return (Response<T>) Response.success(
						new JSONObject(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			} else {
				return (Response<T>) Response.success(
						new JSONArray(jsonString),
						HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}
}
